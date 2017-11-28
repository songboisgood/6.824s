case class ReduceTaskArgs(reduceFiles: List[String],
                          jobId: String,
                          reduceFunc: List[String] => (String, String),
                          numOfReduce: Int) extends TaskArgs(jobId, numOfReduce) {

}
