import java.util.UUID

import akka.actor.{Actor, ActorRef}

abstract class Task(jobId : String) extends Actor{
  val taskId : String = UUID.randomUUID().toString
}
