class JobArgs(val files: List[String],
              val mapFunc: String => List[(String, String)],
              val reduceFunc : List[String] => (String, String),
              val numOfReduce: Int) {

}
