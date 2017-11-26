import akka.actor.{Actor, ActorRef}

import scala.collection.mutable

case class Worker(id: String)

class Master(val schedule: ActorRef) extends Actor {

  override def receive: Receive = {
    case worker: ActorRef => schedule ! worker
    case job : Job => {
      for(file <- job.files) {
        val mapTask = new MapTask(file, job.id, job.mapFunc)
        this.schedule !
      }
    }
  }
}