import cats.effect.kernel.Sync
import cats.effect.Resource
import cats.syntax.functor._

import java.io.File
import scala.io.{BufferedSource, Source}

trait FileReader[F[_], A] {
  def getFileSource(filePath: File): Resource[F, A]
}

object FileReader {
  def make[F[_]: Sync](implicit logger: Logger[F]): FileReader[F, BufferedSource] =
    (filePath: File) => Resource
      .make(
        Sync[F].blocking(Source.fromFile(filePath))
      )(
        source =>
          for {
            _ <- Sync[F]
              .handleErrorWith(Sync[F].blocking(source.close()))(_ => logger.log(s"error closing file $filePath."))
          } yield ()
      )
}
