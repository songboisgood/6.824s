import akka.actor.{Actor, ActorRef, Props}

class Master() extends Actor {

  private val schedule: ActorRef = context.actorOf(Props(new Schedule()))

  override def receive: Receive = {
    case (MessageType.RegisterWorker, worker: ActorRef) =>
      this.schedule ! (MessageType.AddWorker, worker)

    case (MessageType.SubmitJob, jobArgs: JobArgs) =>
      val jobRef = context.actorOf(Props(new Job(jobArgs, self, sender(), this.schedule)))

      jobRef ! MessageType.StartJob

    case (MessageType.JobDone, jobId: String, jobRef: ActorRef, client: ActorRef) =>
      client ! (MessageType.JobDone, jobId)
      context.stop(jobRef)
  }
}