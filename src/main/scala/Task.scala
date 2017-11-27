import java.util.UUID

import akka.actor.{Actor, ActorRef}

abstract class Task(jobId : String,
                    schedule: ActorRef) extends Actor{
  protected val taskId : String = UUID.randomUUID().toString
  override def receive: Receive = {
    case MessageType.TaskStart  =>
      this.doTask()
      schedule ! (MessageType.TaskDone, this.jobId, this.taskId)
  }

  abstract def doTask()
}
