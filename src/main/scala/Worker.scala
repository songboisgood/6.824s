import akka.actor.{Actor, ActorRef, Props}

class Worker(val schedule: ActorRef) extends Actor {
  override def receive: Receive = {
    case mapTaskArgs: MapTaskArgs =>
      val taskRef = context.actorOf(Props(new MapTask(mapTaskArgs.jobId,
        mapTaskArgs.file,
        mapTaskArgs.mapFunc,
        mapTaskArgs.numOfReduce,
        this.schedule)))

      taskRef ! MessageType.TaskStart

    case (MessageType.TaskDone, jobId, taskId) =>
      schedule ! (MessageType.TaskDone, this.sender(), jobId, taskId)
  }
}
