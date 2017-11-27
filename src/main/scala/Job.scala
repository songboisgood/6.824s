import java.util.UUID

class Job(val files : List[String],
          val mapFunc: String => (String, String),
          val reduceFunc: (String, String) => String) {

  val id: String = UUID.randomUUID().toString

}
