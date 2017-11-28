import java.io.{BufferedWriter, File, FileWriter}

import org.apache.commons.lang3.StringEscapeUtils.escapeJava

class ReduceTask(reduceFiles: List[String],
                 jobId: String,
                 reduceFunc: List[String] => (String, String)) extends Task(jobId) {
  override def receive: Receive = {
    case MessageType.StartReduceTask =>
      val (key, value): (String, String) = this.reduceFunc(this.reduceFiles)
      val fileName = Util.reduceOutputFileName(this.jobId, key)

      val file = new File(fileName)
      val bw = new BufferedWriter(new FileWriter(file))

      bw.write(s"${escapeJava(key)}\t${escapeJava(value)}")
      bw.close()

      sender() ! MessageType.ReduceTaskDone
  }
}
