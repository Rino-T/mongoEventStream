package com.github.rinotc.mongoEventStream

import akka.actor.ActorSystem
import akka.stream.alpakka.mongodb.scaladsl.MongoSource
import akka.stream.scaladsl.Sink
import com.mongodb.reactivestreams.client.MongoClients

object Main {
  def main(args: Array[String]): Unit = {
    val client     = MongoClients.create("mongodb://localhost:27017")
    val db         = client.getDatabase("test_db")
    val collection = db.getCollection("test_collection")
    val publisher  = collection.watch()
    val source     = MongoSource(publisher)

    implicit val system: ActorSystem = ActorSystem()

    source.runWith(Sink.foreach(println))
  }
}
