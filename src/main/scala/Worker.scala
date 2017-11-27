import akka.actor.{Actor, ActorRef}

class Worker(val schedule: ActorRef) extends Actor {
  override def receive: Receive = {
    case task : Task => {
      // TODO create new task actor
    }

    case (MessageType.TaskDone, job_id, task_id) => schedule ! (MessageType.TaskDone, this.sender(), job_id, task_id)
  }
}
