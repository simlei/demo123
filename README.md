# Demo exercise

This is a demo for an exercise, and this solution is based on keynmol/http4s-laminar-stack which I have been wanting to try out. It features an architecture where the protocol data classes are the ground truth for the HTTP message en- and decoding. For that reason this project consists of a frontend, backend and shared structure even if only the backend component is used to service this demo.

- Language: Scala 3
- backend server: Http4s
- The service is implemented in [modules/backend/src/main/scala/example/backend/TestService.scala](https://github.com/simlei/demo123/blob/master/modules/backend/src/main/scala/example/backend/TestService.scala)
- The JSON protocol is defined in [modules/shared/src/main/scala/example/shared/Protocol.scala](https://github.com/simlei/demo123/blob/master/modules/shared/src/main/scala/example/shared/Protocol.scala)
- The routes are defined in [modules/backend/src/main/scala/example/backend/Routes.scala](https://github.com/simlei/demo123/blob/master/modules/backend/src/main/scala/example/backend/Routes.scala)

# A note on the validation of the JSON message

I decided to only validate the incoming JSON message where it concerned the task; i.e., fields like "meta" can be missing and the service will still compute the correct number.

On invalid JSON objects, the automatic JSON decoder this project uses (provided by circe) fails and the failure message is returned in a HTTP Bad Request response.

# Running it

There are two options:

**1) Building yourself**

You will need to install the build tool [`sbt`](https://www.scala-sbt.org/download.html)

`sbt runDev`

**2) Using the compiled docker image**

For speed and convenience I hosted the exported docker image in my dropbox. Download and run it via:
  
```bash
curl -L https://www.dropbox.com/s/1hwvbpjth6tw66s/demo123-docker.tar?dl=1 > ./demo123-docker.tar
docker load -i ./demo123-docker.tar
docker run --rm -p 8080:8080 demo123
```

# Testing it

Test the request with e.g.:

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

curl -X POST localhost:8080/demo123 \
   -H "Content-Type: application/json" \
   -d "$json"
```

This script is also available as `bin/test.sh` in this repository.
Observe the console output of your docker or sbt console; if it says it uses another port than 8080, you need to change the script accordingly.

The output of the bash invocation of the script should be:

```txt
{"result":8}
```


## Docker packaging 

```
sbt> backend/docker:publishLocal
```

Then, run it:

```bash
✗ docker run --rm -p 8080:8080 demo123
```

