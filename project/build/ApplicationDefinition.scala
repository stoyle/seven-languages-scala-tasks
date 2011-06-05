import sbt._

class ApplicationProject(info: ProjectInfo) extends DefaultProject(info) {

  override def testFrameworks = super.testFrameworks ++ List(new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker"))
  
  val scalatest = "org.scalatest" % "scalatest_2.9.0" % "1.6-SNAPSHOT" % "test"
	val junit = "junit" % "junit" % "4.8.2" % "test"
	
	val junitInterface = "com.novocode" %% "junit-interface" % "0.4.0" from "http://cloud.github.com/downloads/bryanjswift/junit-interface/junit-interface-0.4.0.jar"

	val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

	override val mainCompilePath = "target" / "classes"
	override val testCompilePath = "target" / "test-classes"

}