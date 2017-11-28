case class MapTaskArgs(file: String,
                  jobId : String,
                  mapFunc: String => List[(String, String)],
                  numOfReduce: Int) extends TaskArgs(jobId, numOfReduce){
}
