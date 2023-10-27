import cats.effect.{IO, Ref, Resource}

import java.io.File
import scala.io.BufferedSource

class TestFileReader(ref: Ref[IO, Int]) extends FileReader[IO, BufferedSource] {
  implicit val logger: TestLogger = new TestLogger
  val realReader: FileReader[IO, BufferedSource] = FileReader.make[IO]
  def getCounter: IO[Int] = ref.get
  override def getFileSource(filePath: File): Resource[IO, BufferedSource] =
    realReader.getFileSource(filePath).evalMap(source => ref.update(_ + 1).map(_ => source))
}

object TestFileReader{
  def make: IO[TestFileReader] = for {
    ref <- Ref.of[IO, Int](0)
  } yield new TestFileReader(ref)
}


