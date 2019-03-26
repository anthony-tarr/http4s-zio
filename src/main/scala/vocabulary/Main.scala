package vocabulary

import cats.effect
import cats.effect.{ExitCode, Timer}
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._
import scalaz.zio.clock.Clock
import scalaz.zio.duration.Duration
import scalaz.zio.interop.catz._
import scalaz.zio.{App, Task}
import vocabulary.services.Example

import scala.concurrent.duration.{FiniteDuration, NANOSECONDS, TimeUnit}
import scala.util.Properties.envOrNone

object Main extends App {
  val port: Int = envOrNone("HTTP_PORT").fold(8080)(_.toInt)
  println(s"Starting server on port $port")

  implicit val timer: Timer[Task] = new Timer[Task] {
    val zioClock = Clock.Live.clock

    override def clock: effect.Clock[Task] = new effect.Clock[Task] {
      override def realTime(unit: TimeUnit) = zioClock.nanoTime.map(unit.convert(_, NANOSECONDS))

      override def monotonic(unit: TimeUnit) = zioClock.currentTime(unit)
    }

    override def sleep(duration: FiniteDuration): Task[Unit] = zioClock.sleep(Duration.fromScala(duration))
  }

  val httpApp = Router("/" -> Example.service).orNotFound

  override def run(args: List[String]) =
    BlazeServerBuilder[Task]
      .bindHttp(port, "localhost")
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .run
      .map(_ => 0)
}
