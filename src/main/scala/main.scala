import cats.effect.{ExitCode, IO, IOApp}

object main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    implicit val logger: Logger[IO] = Logger.make[IO]
    new App[IO](
      FileReader.make[IO],
      WordsCounter.make,
    ).run(args)
      .flatMap {
        case Left(BusinessError.NoFilePath) => logger.log("no path in arguments was obtained.")
        case Left(BusinessError.WrongFilePath(path)) => logger.log(s"wrong file path $path. no file was obtained.")
        case Right(dict) => logger.log(dict.toString())
      }
      .map(_ => ExitCode.Success)
  }
}
