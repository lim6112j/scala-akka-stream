package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object BackpressureBasics extends App {

  implicit val system = ActorSystem("FirstPrinciples")
  implicit val materializer = ActorMaterializer()

  val fastSource = Source(1 to 100)
  val slowSink = Sink.foreach[Int] { x =>
    // simulate long process
    Thread.sleep(1000)
    println(s"Sink: $x")
  }
  fastSource.to(slowSink).run() // fusing?
}
