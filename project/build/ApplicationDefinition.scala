import sbt._

class ApplicationProject(info: ProjectInfo) extends DefaultProject(info) {

  val scalatest = "org.scalatest" % "scalatest_2.9.0" % "1.6-SNAPSHOT"% "test"
	val junit = "junit" % "junit" % "4.8.2" % "test"

	val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

	override val mainCompilePath = "target" / "classes"
	override val testCompilePath = "target" / "test-classes"

}