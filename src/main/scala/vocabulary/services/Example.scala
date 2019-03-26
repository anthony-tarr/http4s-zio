package vocabulary.services

import org.http4s._
import org.http4s.dsl.impl.Root
import org.http4s.dsl.io._
import scalaz.zio.Task
import scalaz.zio.interop.catz._

object Example {
  val service: HttpRoutes[Task] = HttpRoutes.of[Task] {
    case GET -> Root / "hello" / name =>
      OtherExample.test(name).map { name =>
        Response[Task](Ok).withEntity(s"Hello $name")
      }

  }
}

object OtherExample {
  val test = (name: String) => Task.succeed(name)
}
