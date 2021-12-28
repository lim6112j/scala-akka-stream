package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.OverflowStrategy
import scala.language.postfixOps
object BackpressureBasics extends App {

  implicit val system = ActorSystem("FirstPrinciples")
  implicit val materializer = ActorMaterializer()

  val fastSource = Source(1 to 100)
  val slowSink = Sink.foreach[Int] { x =>
    // simulate long process
    Thread.sleep(1000)
    println(s"Sink: $x")
  }
  //fastSource.to(slowSink).run() // fusing?
  // not backpressure
  //
  //fastSource.async.to(slowSink).run()

  val simpleFlow = Flow[Int].map { x =>
    println(s"Incoming: $x")
    x + 1
  }
  fastSource.async
    .via(simpleFlow)
    .async
    .to(slowSink)
  //.run()

  /*
  reactions to backpressure (in order):
   - try to slow down if possible
   - buffer elements until there's more deman
   - drop down elements from the buffer if it overflows
   - tear down/kill the whole stream(failure)
   */
  val bufferedFlow =
    simpleFlow.buffer(10, overflowStrategy = OverflowStrategy.dropHead)
  fastSource.async
    .via(bufferedFlow)
    .async
    .to(slowSink)
  //.run()

  /*
   - drop head = oldest
   * drop tail = newest
   * drop new = exact element to be added = keeps the buffer
   * drop the entire buffer
   * backpressure signal
   * fail
   */
  //throttling
  import scala.concurrent.duration._

  fastSource.throttle(10, 1 second).runWith(Sink.foreach(println))
}
