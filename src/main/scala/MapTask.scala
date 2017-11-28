import java.io.{BufferedWriter, File, FileWriter}

import org.apache.commons.lang3.StringEscapeUtils.escapeJava

import scala.collection.mutable

class MapTask(jobId: String,
              file: String,
              mapFunc: String => List[(String, String)],
              numOfReduce: Int) extends Task(jobId) {

  override def receive: Receive = {
    case MessageType.StartMapTask =>
      val keyValues: List[(String, String)] = this.mapFunc(this.file)
      val fileMap = new mutable.HashMap[String, BufferedWriter]()
      for ((key, value) <- keyValues) {
        val index = key.hashCode % this.numOfReduce
        val fileName = s"${this.jobId}-reduce-${this.taskId}-$index"

        val bw: BufferedWriter = fileMap.getOrElse(fileName,
          {
            val file = new File(fileName)
            val newBw = new BufferedWriter(new FileWriter(file))
            fileMap.put(fileName, newBw)
            newBw
          })

        bw.write(s"${escapeJava(key)}\t${escapeJava(value)}")
        bw.newLine()
        bw.close()
      }

      sender() ! (MessageType.MapTaskDone, this.jobId, this.taskId)
  }
}
