import akka.actor.{Actor, ActorRef}

import scala.collection.mutable

class Master(schedule: ActorRef) extends Actor {

  // val taskMap : mutable.HashMap[String, Int]
  override def receive: Receive = {
    case worker: ActorRef => this.schedule ! worker
    case job: Job =>
      for (file <- job.files) {
        val mapTask = new MapTask(file, job.jobId, job.mapFunc, job.numOfReduce, this.schedule)
        this.schedule ! mapTask
      }

    case MessageType.TaskDone => {

    }


  }
}