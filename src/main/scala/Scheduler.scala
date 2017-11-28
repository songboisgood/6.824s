import akka.actor.{Actor, ActorRef}

import scala.collection.mutable

class Scheduler() extends Actor {
  private val available_workers = mutable.Queue[ActorRef]()
  private val taskArgsQueue = mutable.Queue[TaskArgs]()

  override def receive: Receive = {
    case (MessageType.AddWorker, worker: ActorRef) =>
      available_workers.enqueue(worker)

    case (MessageType.ScheduleMapTask, mapTaskArgs: MapTaskArgs) =>
      if (available_workers.nonEmpty) {
        val worker = available_workers.dequeue()
        worker ! (MessageType.AssignMapTask, mapTaskArgs)
      }
      else {
        taskArgsQueue.enqueue(mapTaskArgs)
      }

    case (MessageType.ScheduleReduceTask, reduceTaskArgs: ReduceTaskArgs) =>
      if (available_workers.nonEmpty) {
        val worker = available_workers.dequeue()
        worker ! (MessageType.AssignReduceTask, reduceTaskArgs)
      }
      else {
        taskArgsQueue.enqueue(reduceTaskArgs)
      }

    case (MessageType.AwakeSchedule) =>
      if (available_workers.nonEmpty && taskArgsQueue.nonEmpty) {

        val worker = available_workers.dequeue()
        val taskArgs = taskArgsQueue.dequeue()

        if (taskArgs.isInstanceOf[MapTaskArgs]) {
          worker ! (MessageType.AssignMapTask, taskArgs)
        }
        else {
          worker ! (MessageType.AssignReduceTask, taskArgs)
        }
      }
    case (MessageType.MapTaskDone, worker: ActorRef, jobRef: ActorRef, task_id: String) =>
      available_workers.enqueue(worker)
      jobRef ! (MessageType.MapTaskDone, task_id)

  }
}
