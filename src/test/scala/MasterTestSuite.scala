import akka.actor.{ActorSystem, Props}
import akka.pattern.gracefulStop
import org.scalatest.{BeforeAndAfter, FunSuite}
import scala.concurrent.duration._


import scala.concurrent.{Await, Future}

class MasterTestSuite extends FunSuite with BeforeAndAfter {

  private def mapFunc(file: String) : (String, String) = {
    return (null, null)
  }

  private def reduceFunc(key : String, value : String) : (String) = {
    return null;
  }

  before {
    val system = ActorSystem("pingpong")

    val master = system.actorOf(Props[Master], "pinger")

    val job = new Job(mapFunc, reduceFunc)
    master ! job

    val stopped: Future[Boolean] = gracefulStop(master, 5 seconds)
    Await.result(stopped, 6 seconds)


  }


  test("Distributed") {

  }

  after {

  }
}
