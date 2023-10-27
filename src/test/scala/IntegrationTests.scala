import cats.effect.IO
import munit.CatsEffectSuite

class IntegrationTests extends CatsEffectSuite {
  implicit val logger: Logger[IO] = Logger.make[IO]

  val madeApp = new App[IO](FileReader.make[IO], WordsCounter.make)

  def makeApp: IO[(App[IO], TestFileReader)] = for {
    reader <- TestFileReader.make
  } yield (new App[IO](
    reader,
    WordsCounter.make,
  ), reader)

  test("got real path in arguments 1") {
    for {
      res <- madeApp.run(List("src/test/resources/iamhere.txt"))
    } yield assertEquals(res.map(_.toSet), Right(Map("i" -> 1, "am" -> 1, "here" -> 1).toSet))
  }

  test("got real path in arguments 2") {
    for {
      res <- madeApp.run(List("src\\test\\resources\\iamhere.txt"))
    } yield assertEquals(res.map(_.toSet), Right(Map("i" -> 1, "am" -> 1, "here" -> 1).toSet))
  }

  test("got real path with empty file in arguments") {
    for {
      res <- madeApp.run(List("src\\test\\resources\\empty.txt"))
    } yield assertEquals(res, Right(Map.empty[String, Int]))
  }

  test("got no path in arguments") {
    for {
      res <- madeApp.run(List.empty[String])
    } yield assertEquals(res, Left(BusinessError.NoFilePath))
  }

  test("got wrong path in arguments") {
    for {
      (app, reader) <- makeApp
      res <- app.run(List(""))
      count <- reader.getCounter
    } yield assertEquals((res, count), (Left(BusinessError.WrongFilePath("")), 0))
  }
}
