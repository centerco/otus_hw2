package otus.chuchalov

import Constants.{DEST_PATH, SOURCE_PATH}
import Resource.combineElements

import org.slf4j.{Logger, LoggerFactory}

object HomeWork2 {

  private val LOGGER: Logger = LoggerFactory.getLogger(HomeWork2.getClass)

  def main(args: Array[String]): Unit = {
    LOGGER.info("Hadoop simple processor.")

    val files = Resource.folderElements(SOURCE_PATH)
    files
      .groupBy(f => f.path)
      .foreach(e => combineElements(SOURCE_PATH, DEST_PATH, e._2))

    Resource.deleteFolder(SOURCE_PATH)
  }

}
