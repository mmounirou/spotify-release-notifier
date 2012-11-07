import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "spotify-release-notifier"
    val appVersion      = "1.0-SNAPSHOT"

    val ssDependencies = Seq(
      // Add your project dependencies here,
      "com.typesafe" %% "play-plugins-util" % "2.0.1",
      "org.mindrot" % "jbcrypt" % "0.3m"
    )

	val secureSocial = PlayProject(
	    	"securesocial", appVersion, ssDependencies, mainLang = SCALA, path = file("modules/securesocial")
	    ).settings(
	      resolvers ++= Seq(
	        "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
	        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
	      )
	    )

    val appDependencies = Seq(
      "com.mmounirou.spotify" % "spotify-web-4j" % "1.1-SNAPSHOT",
      "org.reflections" % "reflections" % "0.9.8",
      "com.sun.jersey" % "jersey-core" % "1.14"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
		resolvers += "Personal Cloudbees Snapshot Repo" at "http://repository-mmounirou.forge.cloudbees.com/snapshot/"
    ).dependsOn(secureSocial).aggregate(secureSocial)

}
