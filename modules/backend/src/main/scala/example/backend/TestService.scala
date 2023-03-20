package example.backend

import cats.effect.IO

import example.shared.Protocol.demo123

trait TestService:
  def getResponse( request: demo123.Request ): IO[demo123.Response]

object TestServiceImpl extends TestService:

  def getResponse(
      request: demo123.Request
  ): IO[demo123.Response] =

    import demo123.* // import the data objects
    request match
      case Request(Address(values)) => // unpack the request
        IO.pure(
          demo123.Response(
            values
              .sum // step 1
              .toString.map{_.asDigit}.sum // step 2 and 3
          )
        )

end TestServiceImpl

