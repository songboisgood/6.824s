object MessageType extends Enumeration {

  val MapTaskDone, ReduceTaskDone, TaskStart,
  RegisterWorker, JobDone, StartMapTask, StartReduceTask,
  AddWorker, SubmitJob, ScheduleMapTask, AwakeSchedule,
  ScheduleReduceTask, StartJob, AssignMapTask, AssignReduceTask = Value
}
