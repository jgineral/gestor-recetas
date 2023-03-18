name := """gestor-recetas"""
organization := "jginera"
maintainer := "https://github.com/jgineral/gestor-recetas"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies += guice

enablePlugins(PlayEbean)
libraryDependencies += evolutions
libraryDependencies += javaJdbc

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.5"

libraryDependencies += "com.h2database" % "h2" % "1.4.200"

libraryDependencies ++= Seq(
  ehcache
)