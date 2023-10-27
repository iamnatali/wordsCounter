import cats.effect.kernel.Sync
import cats.syntax.functor._
import java.io.File
import scala.io.BufferedSource

sealed trait BusinessError extends Exception
object BusinessError {
  case class WrongFilePath(path: String) extends BusinessError
  case object NoFilePath extends BusinessError
}

case class App[F[_]: Sync](fileReader: FileReader[F, BufferedSource], wordsCounter: WordsCounter){
  def run(args: List[String]): F[Either[BusinessError, Map[String, Int]]] = {
    args.headOption match {
      case Some(path) =>
        val file = new File(path)
        for {
        res <- if (file.exists()) fileReader.getFileSource(file).use(
          source =>
            for {
              lines <- Sync[F].blocking(source.getLines())
            } yield Right(wordsCounter.getCountMap(lines))
        )
        else
          Sync[F].pure(Left(BusinessError.WrongFilePath(path)))
      } yield res
      case None => Sync[F].pure(Left(BusinessError.NoFilePath))
    }
  }
}

