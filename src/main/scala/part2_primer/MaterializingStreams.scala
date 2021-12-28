package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import scala.util.{Failure, Success}
object MaterializingStreams extends App {
  implicit val system = ActorSystem("MaterializingStreams")
  implicit val materializer = ActorMaterializer()
  import system.dispatcher
  val simpleGraph = Source(1 to 10).to(Sink.foreach(println))
  //val simpleMaterializedValue = simpleGraph.run()
  val source = Source(1 to 10)
  val sink = Sink.reduce[Int]((a, b) => a + b)
  val sumFuture = source.runWith(sink)
  sumFuture.onComplete {
    case Success(value) => println(s"sum : $value")
    case Failure(ex)    => println(s"err : $ex")
  }
}
