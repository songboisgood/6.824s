import akka.actor.Actor

import scala.collection.mutable

case class Worker(id: String)

class Master extends Actor {
  val workers: mutable.Queue[String] = mutable.Queue()

  def receive = {
    case Worker(id) => workers.enqueue(id)
  }
}