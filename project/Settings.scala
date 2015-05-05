import sbt.Keys._
import sbt.Resolver
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys


object Settings {

  val compilerOptions = Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-Xlint",
    "-Xfatal-warnings",
    "-target:jvm-1.8"
  )

  val javaCompilerOptions = Seq(
    "-source", "1.8",
    "-target", "1.8"
  )

  val additionalResolvers = Seq(
    Resolver.typesafeRepo("releases")
  )

  val scalariformSettings = SbtScalariform.scalariformSettings ++ Seq(ScalariformKeys.preferences := formattingPreferences)

  def formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences().
      setPreference(PreserveSpaceBeforeArguments, true).
      setPreference(PreserveDanglingCloseParenthesis, true).
      setPreference(AlignParameters, true).
      setPreference(AlignSingleLineCaseStatements, true)
  }

  val common = Seq(
    organization := "com.virtuslab",
    scalaVersion := "2.11.6",
    resolvers ++= additionalResolvers,
    scalacOptions ++= compilerOptions,
    javacOptions ++= javaCompilerOptions
  ) ++ scalariformSettings

}