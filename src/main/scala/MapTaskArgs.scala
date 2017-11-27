class MapTaskArgs(val file: String,
                  val jobId : String,
                  val mapFunc: String => List[(String, String)],
                  val numOfReduce: Int) {

}
