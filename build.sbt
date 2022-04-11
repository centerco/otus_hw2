import Dependencies.library

name := "hw2-hdfs"

ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.0"

scalacOptions ++= Seq("-target:jvm-1.8")

lazy val commonLibraryDependencies = Seq(
  libraryDependencies ++= Seq(
    library.hdfsBundle,
    library.slf4jApi,
    library.slf4jSimple
  )
)

lazy val root = (project in file("."))
  .settings(
    name := "hw2",
    idePackagePrefix := Some("otus.chuchalov"),
    commonLibraryDependencies
  )
