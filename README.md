# Demo exercise

This is a demo for an exercise, and this solution is based on keynmol/http4s-laminar-stack which I have been wanting to try out. It features an architecture where the protocol is compiled once and is shared between frontend and backend. For this exercise it is a bit overblown, granted, but I got where I wanted quick by adapting it.

- Language: Scala 3
- backend server: Http4s
- The service is implemented in `modules/backend/src/main/scala/example/backend/TestService.scala`
- The JSON protocol is defined in `modules/shared/src/main/scala/example/shared/Protocol.scala`
- The routes are defined in `modules/backend/src/main/scala/example/backend/Routes.scala`

# A note on the validation of the JSON message

I decided to only validate the incoming JSON message where it concerned the task; i.e., fields like "meta" can be bissing and the service will still compute the correct number.

On invalid JSON objects, the automatic JSON decoder this project uses (provided by circe) fails and the failure message is returned in a HTTP Bad Request response.

# Running it

**1) building yourself**

You will need to install the build tool [`sbt`](https://www.scala-sbt.org/download.html)

`sbt runDev`

**2) using the compiled docker image**

For speed and convenience I hosted the exported docker image directly in this repository, even if that is not the best place for such artifacts. Download and run it via:
  
```bash

```

# Testing it

test the request with e.g.:

```bash
#!/usr/bin/env bash

json=$(cat <<-"END"
{
  "address": {
    "colorKeys": [
      "A",
      "G",
      "Z"
    ],
    "values": [
      74,
      117,
      115,
      116,
      79,
      110
    ]
  },
  "meta": {
    "digits": 33,
    "processingPattern": "d{5}+[a-z&$§]"
  }
}
END
)

curl -X POST localhost:9000/demo123 \
   -H "Content-Type: application/json" \
   -d "$json"
```

(this script is also available as `bin/test.sh` in this repository)

## Docker packaging 

```
sbt> backend/docker:publishLocal
```

Then, run it:

```bash
✗ docker run --rm -p 8080:8080 demo123
```

