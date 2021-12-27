package part2_primer
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import scala.concurrent.Future

object FirstPrinciples extends App {
  implicit val system = ActorSystem("FirstPrinciples")
  implicit val materializer = ActorMaterializer()

  // sources
  val source = Source(1 to 10)
  // sinks
  val sink = Sink.foreach[Int](println)
  val graph = source.to(sink)
  //graph.run()
  val flow = Flow[Int].map(x => x + 1)
  val sourceWithFlow = source.via(flow)
  val flowWithSink = flow.to(sink)
  //sourceWithFlow.to(sink).run()
  //source.to(flowWithSink).run()
  //source.via(flow).to(sink).run()
  // nulls are not allowed
  //val illegalSouruce = Source.single[String](null)
  //illegalSouruce.to(Sink.foreach(println)).run()
  //use options instead

  // various kinds of sources
  val finiteSource = Source.single(1)
  val anotherFiniteSource = Source(List(1, 2, 3))
  val emptySource = Source.empty[Int]
  val infiniteSource = Source(Stream.from(1))
  import scala.concurrent.ExecutionContext.Implicits.global
  val futureSource = Source.fromFuture(Future(42))
  // sinks
  val theMostBoringSink = Sink.ignore
  val foreachSink = Sink.foreach[String](println)
  val headSink = Sink.head[Int]
  val foldSink = Sink.fold[Int, Int](0)((a, b) => a + b)

  // flows
  val mapFlow = Flow[Int].map(x => 2 * x)
  val takeFlow = Flow[Int].take(5)

  // drop, filter
  // Not have flatmap
  //

  // source -> flow -> ... -> sink
  val doubleFlowGraph = source.via(mapFlow).via(takeFlow).to(sink)
  //doubleFlowGraph.run()

  // syntactic sugars
  val mapSource = Source(1 to 10).map(x => x * 2)
  // run streams directly
  //mapSource.runForeach(println)

  // OPERATORS = components

  // exercise
  val names = List("Alicein", "B", "C", "Dietyy", "E")
  val nameSource = Source(names)
  val longNameFlow = Flow[String].filter(name => name.length > 5)
  val limitFlow = Flow[String].take(2)
  val nameSink = Sink.foreach[String](println)
  nameSource.via(longNameFlow).via(limitFlow).to(nameSink).run();
  nameSource.filter(_.length > 5).take(2).runForeach(println)
}
