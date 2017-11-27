import akka.actor.{Actor, ActorRef}

import scala.collection.mutable

class Schedule(master: ActorRef) extends Actor {
  private val available_workers = mutable.Queue[ActorRef]()
  private val task_queue = mutable.Queue[Task]()

  override def receive: Receive = {
    case worker: ActorRef => available_workers.enqueue(worker)
    case task: Task =>
      if (available_workers.nonEmpty) {
        val worker = available_workers.dequeue()
        worker ! task
      }
      else {
        task_queue.enqueue(task)
      }

    case (MessageType.TaskDone, worker: ActorRef, job_id: String, task_id: String) =>
      available_workers.enqueue(worker)
      this.master ! (MessageType.TaskDone, job_id, task_id)
  }
}
