package part2_primer
import akka.actor.{Props, ActorSystem, Actor}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source, Keep}
object OperatorFusion extends App {
  implicit val system = ActorSystem("OperatorFusion")
  implicit val materializer = ActorMaterializer()
  val simpleSource = Source(1 to 1000)
  val simpleFlow = Flow[Int].map(_ + 1)
  val simpleFlow2 = Flow[Int].map(_ * 10)
  val simpleSink = Sink.foreach[Int](println)
  // this runs on the same actor
  //simpleSource.via(simpleFlow).via(simpleFlow2).to(simpleSink).run()
  // operator/component fusion

  // "equivalent" behavior
  class SimpleActor extends Actor {
    override def receive: Receive = { case x: Int =>
      // flow operations
      val x2 = x + 1
      val y = x2 + 10
      // sink operation
      println(y)
    }
  }
  val simpleActor = system.actorOf(Props[SimpleActor])
  //(1 to 1000).foreach(simpleActor ! _)

  // complex flows:
  val complexFlow = Flow[Int].map { x =>
    // simulating a long computation
    Thread.sleep(1000)
    x + 1
  }
  val complexFlow2 = Flow[Int].map { x =>
    // simulation long computation
    Thread.sleep(1000)
    x * 10
  }
  //simpleSource.via(complexFlow).via(complexFlow2).to(simpleSink).run()

  // async boundary
  //simpleSource
  //.via(complexFlow)
  //.async
  //.via(complexFlow2)
  //.async
  //.to(simpleSink)
  //.run()

  // ordering guarantees
  Source(1 to 3)
    .map(ele => { println(s"Flow A: $ele"); ele })
    .async
    .map(ele => { println(s"Flow B: $ele"); ele })
    .async
    .map(ele => { println(s"Flow C: $ele"); ele })
    .async
    .runWith(Sink.ignore)
}
