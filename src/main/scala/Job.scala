import java.util.UUID

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable

class Job(jobArgs: JobArgs,
          master: ActorRef,
          schedule: ActorRef,
          client : ActorRef) extends Actor {

  val jobId: String = UUID.randomUUID().toString
  val reduceTasks: mutable.HashSet[String] = new mutable.HashSet[String]()
  val mapTasks: mutable.HashSet[String] = new mutable.HashSet[String]()

  override def receive: Receive = {
    case MessageType.StartJob =>
      for (file <- this.jobArgs.files) {
        val mapTask = new MapTask(file, this.jobId, jobArgs.mapFunc, jobArgs.numOfReduce)
        this.schedule ! (MessageType.ScheduleMapTask, mapTask)
        mapTasks += mapTask.taskId
      }

    case (MessageType.MapTaskDone, taskId: String) =>
      mapTasks -= taskId

      if (mapTasks.isEmpty) {
        for (reduceKey <- 0 until this.jobArgs.numOfReduce) {
          val reduceFiles = this.mapTasks.map(taskId => Util.mapOutputFileName(this.jobId, taskId, reduceKey.toString)).toList
          val reduceTask = new ReduceTask(reduceFiles, this.jobId, this.jobArgs.reduceFunc)
          this.schedule ! (MessageType.ScheduleReduceTask, reduceTask)
          this.reduceTasks += reduceTask.taskId
        }

      }

    case (MessageType.ReduceTaskDone, taskId: String) =>
      reduceTasks -= taskId
      if (reduceTasks.isEmpty) {
        this.master ! (MessageType.JobDone, this.jobId, self, this.client)
      }
  }
}
