import scala.concurrent.duration._

// 2
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/*
* This test simulates 1000 users that connect to example fake API for 15sec and validate if response is HTTP 200.
*
* application response time before test- about 200ms
* application response time during test- mean 3182ms
* */
class PerfTest extends Simulation {
  // define address and headers
  val httpProtocol = http
    .baseUrl("https://jsonplaceholder.typicode.com/todos/1")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate, br")
    .userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0) Gecko/20100101 Firefox/96.0")

  // define user scenario that will be executed for each user
  val scn = scenario("PerfTestTask")
    // in loop connect to service
    .forever(
      exec(
        http("getHomepage")
          .get("/")
          // validate if the response is HTTP 200
          .check(status.is(200)))
    )

  // setup number of users and duration time
  setUp(
    scn.inject(atOnceUsers(100))
  ).protocols(httpProtocol).maxDuration(15.seconds)
}