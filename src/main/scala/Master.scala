import akka.actor.Actor
import scala.collection.mutable.Queue

case class Worker(id: String)

class Master extends Actor {
  val workers: Queue[String] = Queue()

  def receive = {
    case Worker(id) => workers.enqueue(id)
  }
}