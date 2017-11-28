import akka.actor.{ActorSystem, Props}
import akka.pattern.gracefulStop
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class MasterTestSuite extends FunSuite with BeforeAndAfter {

  private def mapFunc(file: String): List[(String, String)] = {
    List()
  }

  private def reduceFunc(key: String, value: String): (String) = {
    null
  }

  before {


  }


  test("Distributed") {

    val system = ActorSystem("MapReduce")

    val master = system.actorOf(Props[Master], "master")
    val files: List[String] = List("a", "b", "c")
    /*val job = new Job(files, this.mapFunc, this.reduceFunc, 5)
    master ! job*/

    val stopped: Future[Boolean] = gracefulStop(master, 5 seconds)
    Await.result(stopped, 6 seconds)
  }

  after {

  }
}
