lazy val root = (project in file(".")).
  settings(
    name          := "project-euler",
    organization  := "gg.tm",
    scalaVersion  := "2.11.7",
    scalacOptions in Test ++= Seq(
      "-Yrangepos",
      "-feature",
      "-deprecated",
      "-unchecked"
    ),
    libraryDependencies ++= Seq(
      "org.specs2" %% "specs2-core" % "3.7.2" % Test withSources() withJavadoc()
    )
  )
