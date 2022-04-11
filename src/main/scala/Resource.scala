package otus.chuchalov

import Constants.{HDFS_URI, RECURSIVE_MODE}
import model.FsItem

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.slf4j.{Logger, LoggerFactory}

import java.net.URI
import scala.collection.mutable.ListBuffer

object Resource {

  private val LOGGER: Logger = LoggerFactory.getLogger(Resource.getClass)

  def folderElements(path: String): Seq[FsItem] = {

    val files: ListBuffer[FsItem] = ListBuffer[FsItem]()
    val fs = getFileSystem
    val sourcePath = fs.listFiles(new Path(path), RECURSIVE_MODE)

    while(sourcePath.hasNext) {

      val sourceFile = sourcePath.next()
      files.append(FsItem(sourceFile.getPath.getParent.getName, sourceFile.getPath.getName))

    }
    fs.close()

    files.toSeq

  }

  def combineElements(srcFolder: String, destFolder: String, items: Seq[FsItem]): Unit = {

    val fs = getFileSystem
    fs.setReplication(new Path("/"), 1.toShort)
    val MAX_BUFFER_SIZE = 10 * 1024 * 1024

    items
      .foreach(element => {
        val inFileName = new Path(s"$srcFolder/${element.path}/${element.name}")
        val outFileName = new Path(s"$destFolder/${element.path}/part-0000.csv")

        if (!fs.exists(outFileName)) {
          fs.createNewFile(outFileName)
        }

        LOGGER.info(s"Appending $outFileName with $inFileName")

        val inFile = fs.open(inFileName)
        val outFile = fs.append(outFileName)

        val buffer: Array[Byte] = new Array[Byte](MAX_BUFFER_SIZE)

        var numBytes = inFile.read(buffer)
        while (numBytes > 0) {
          outFile.write(buffer, 0, numBytes)
          numBytes = inFile.read(buffer)
        }
        inFile.close()
        outFile.close()

      })

    fs.close()
  }

  def deleteFolder(folder: String): Unit = {
    LOGGER.info(s"Delete folder: $folder")
    val fs = getFileSystem
    fs.delete(new Path(folder), true)
    fs.close()
  }

  private def getFileSystem: FileSystem = {
    val conf = new Configuration()
    conf.set("dfs.replication", "1")

    FileSystem.get(new URI(HDFS_URI), conf)
  }


}
