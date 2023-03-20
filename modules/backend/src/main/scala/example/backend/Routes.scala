package example.backend

import scala.concurrent.duration.*

import cats.*
import cats.effect.*

import org.http4s.HttpRoutes
import org.http4s.StaticFile
import org.http4s.circe.CirceEntityDecoder
import org.http4s.circe.CirceEntityDecoder.*
import org.http4s.circe.CirceEntityEncoder.*
import org.http4s.dsl.Http4sDsl

import example.shared.Protocol.*

class Routes(
    service: Service,
    testService: TestService,
    frontendJS: String
) extends Http4sDsl[IO]:
  def routes = HttpRoutes.of[IO] {

    case request @ POST -> Root / "demo123" =>
      for
        req <- circeEntityDecoder[IO, demo123.Request]
          .decode(request, strict = true)
          .value
        response <- req match
          case Right(reqValid) => Ok(testService.getResponse(reqValid))
          case Left(failure) => BadRequest(IO.pure(failure.getMessage))
      yield response

  }

  private def staticFileAllowed(path: String) =
    List(".gif", ".js", ".css", ".map", ".html", ".webm").exists(path.endsWith)
end Routes
