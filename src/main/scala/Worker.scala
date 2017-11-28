import akka.actor.{Actor, ActorRef, Props}

class Worker(val schedule: ActorRef) extends Actor {
  override def receive: Receive = {
    case (MessageType.AssignMapTask, mapTaskArgs: MapTaskArgs) =>
      val taskRef = context.actorOf(Props(new MapTask(mapTaskArgs.jobId,
        mapTaskArgs.file,
        mapTaskArgs.mapFunc,
        mapTaskArgs.numOfReduce)))

      taskRef ! MessageType.StartMapTask

    case (MessageType.AssignReduceTask, reduceTaskArgs: ReduceTaskArgs) =>
      val taskRef = context.actorOf(Props(new ReduceTask(
        reduceTaskArgs.reduceFiles,
        reduceTaskArgs.jobId,
        reduceTaskArgs.reduceFunc)))

      taskRef ! MessageType.StartReduceTask

    case (MessageType.MapTaskDone, jobId, taskId) =>
      schedule ! (MessageType.MapTaskDone, self, jobId, taskId)
  }
}
