import java.util.UUID

class Job(val files : List[String],
          val mapFunc: String => List[(String, String)],
          val reduceFunc: (String, String) => String,
          val numOfReduce: Int) {

  val jobId: String = UUID.randomUUID().toString

}
