package http4szio.services

import org.http4s.{Method, _}
import org.scalatest._
import scalaz.zio.interop.catz._
import scalaz.zio.{DefaultRuntime, Task}

class ExampleSpec extends FlatSpec with Matchers {
  val runtime = new DefaultRuntime {}

  "GET -> /hello/bobo" should "say hello with the greeting specified in the response body" in {
    val request = Request[Task](Method.GET, Uri.uri("/hello/bobo"))
    val responseTask = Example.service.apply(request).value
    val actual = runtime.unsafeRun(EntityDecoder.decodeString(runtime.unsafeRun(responseTask).get))
    actual shouldEqual "Hello bobo"
  }
}
