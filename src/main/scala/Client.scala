import scala.concurrent.{Await, Future}

class Client {

  def submitJob(jobArgs: JobArgs) = {
    master ! job

    val stopped: Future[Boolean] = gracefulStop(master, 5 seconds)
    Await.result(stopped, 6 seconds)
  }
}
