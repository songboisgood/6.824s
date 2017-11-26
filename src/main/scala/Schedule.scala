import akka.actor.Actor

import scala.collection.mutable

class Schedule extends Actor{
  val available_workers = mutable.Queue[String]()
  override def receive: Receive = {
    case worker_id: String => available_workers.enqueue(worker_id)
  }
}
