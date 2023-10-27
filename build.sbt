ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "wordscounter"
  )

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.2"

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test