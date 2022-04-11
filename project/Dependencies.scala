import sbt._

object Dependencies {
  val library: Object {
    val hdfsBundle: ModuleID
    val slf4jApi: ModuleID
    val slf4jSimple: ModuleID
  } = new {
    object Version {
      lazy val hdfsBundle = "3.2.1"
      lazy val slf4j = "1.7.35"
    }
    val hdfsBundle = "org.apache.hadoop" % "hadoop-client" % Version.hdfsBundle
    val slf4jApi = "org.slf4j" % "slf4j-api" % Version.slf4j
    val slf4jSimple = "org.slf4j" % "slf4j-simple" % Version.slf4j
  }
}
