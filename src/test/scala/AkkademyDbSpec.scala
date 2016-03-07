package com.akkademy

import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}
import org.scalatest.concurrent.ScalaFutures

import akka.testkit.TestActorRef
import akka.util.Timeout
import akka.actor.ActorSystem
import akka.actor.Status._
import akka.pattern.ask

import scala.util.Success
import scala.concurrent.duration._
import scala.concurrent.Future
import com.akkademy.messages._

class AkkademyDbSpec extends FunSpecLike with Matchers with BeforeAndAfterEach with ScalaFutures {
    implicit val system = ActorSystem()
    implicit val timeout = Timeout(1 second)

    describe("akkademyDb") {
        describe("given SetRequest") {
            it("should place key/value into map") {
                val actorRef = TestActorRef(new AkkademyDb)
                actorRef ! SetRequest("key", "value")
                val akkademyDb = actorRef.underlyingActor
                akkademyDb.map.get("key") should equal(Some("value"))
            }

            it("should return true"){
                val actorRef = TestActorRef(new AkkademyDb)
                val futureResult = actorRef ? SetRequest("key", "value")
                whenReady(futureResult) { result => 
                    result should equal(true)
                }
            }
        }

        describe("given SetIfNotExistsRequest") {
            it("should add key if it does not exist") {
                val actorRef = TestActorRef(new AkkademyDb)
                actorRef ! SetIfNotExistsRequest("key", "value")

                val akkademyDb = actorRef.underlyingActor
                akkademyDb.map.get("key") should equal(Some("value"))
            }

            it("should return true"){
                val actorRef = TestActorRef(new AkkademyDb)
                val futureResult = actorRef ? SetIfNotExistsRequest("key", "value")
                whenReady(futureResult) { result =>
                    result should equal(true)
                }
            }

            it("should not update key if it exists") {
                val actorRef = TestActorRef(new AkkademyDb)
                actorRef ! SetIfNotExistsRequest("key", "value")
                actorRef ! SetIfNotExistsRequest("key", "another value")

                val akkademyDb = actorRef.underlyingActor
                akkademyDb.map.get("key") should equal(Some("value"))
            }

            it("should return true even if key already exists") {
                val actorRef = TestActorRef(new AkkademyDb)
                actorRef ! SetIfNotExistsRequest("key", "value")
                val futureResult = actorRef ? SetIfNotExistsRequest("key", "another value")
                whenReady(futureResult) { result =>
                    result should equal(true)
                }
            }
        }

        describe("given GetRequest") {
            it("should get value for the key") {
                val actorRef = TestActorRef(new AkkademyDb)
                actorRef ! SetRequest("key", "value")
                val futureResult = actorRef ? GetRequest("key")
                whenReady(futureResult) { result => 
                    result should equal("value")
                }
            }
            
            it("should fail to get value for an unknown key") {
                val actorRef = TestActorRef(new AkkademyDb)
                actorRef ! SetRequest("key", "value")
                val futureResult = actorRef ? GetRequest("unknown")
                whenReady(futureResult.failed) { ex => 
                    ex shouldBe a [KeyNotFoundException]
                    ex.asInstanceOf[KeyNotFoundException].key should equal("unknown")
                }
            }
        }

        describe("given an unkown type of message") {
            it("should fail with ClassNotFoundException") {
                case class UnkownMessage()

                val actorRef = TestActorRef(new AkkademyDb)
                val futureResult = actorRef ? UnkownMessage
                whenReady(futureResult.failed) { ex => 
                    ex shouldBe a [ClassNotFoundException]
                }
            }
        }

    }
}