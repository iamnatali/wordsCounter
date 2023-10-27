import cats.effect.IO

class TestLogger extends Logger[IO]{
  override def log(input: String): IO[Unit] = IO.unit
}
