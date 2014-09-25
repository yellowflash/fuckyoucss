import sbt._
import Keys._

object Build extends Build {

  lazy val project = Project("root", file(".")).settings(

    // basics
    name := "fuckyoucss",
    organization := "fuckyoucss",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.10.0",

    // dependencies
    libraryDependencies ++= Seq(
      "org.jsoup" % "jsoup" % "1.7.2",
      "org.scalatest" %% "scalatest" % "1.9.2" % "test",
      "org.mockito" % "mockito-all" % "1.8.4" % "test"
    ),
    resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",

    // enable forking in run
    fork in run := true,

    // we need to add the runtime classpath as a "-cp" argument to the `javaOptions in run`, otherwise caliper
    // will not see the right classpath and die with a ConfigurationException
    javaOptions in run ++= Seq("-cp",
      Attributed.data((fullClasspath in Runtime).value).mkString(":"))
  )
}
