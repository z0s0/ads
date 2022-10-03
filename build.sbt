name := "ads"

version := "0.1"

scalaVersion := "2.13.9"

libraryDependencies ++= Deps.all

// for circe @JsonCodec annotation
scalacOptions ++= List(
  "-Ymacro-annotations"
)