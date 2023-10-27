import cats.effect.std.Console

trait Logger[F[_]] {
  def log(input: String): F[Unit]
}

object Logger{
  def make[F[_]: Console]: Logger[F] = (input: String) => Console[F].println(input)
}
