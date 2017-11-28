object Util {

  def mapOutputFileName(jobId : String, mapTaskId: String, reduceKey: String) : String = {
    s"$jobId-map-$mapTaskId-$reduceKey.txt"
  }

  def reduceOutputFileName(jobId : String, reduceKey: String) : String = {
    s"$jobId-reduce-$reduceKey.txt"
  }

}
