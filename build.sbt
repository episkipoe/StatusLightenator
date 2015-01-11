name := """StatusLightenator"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean
)

libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2"

