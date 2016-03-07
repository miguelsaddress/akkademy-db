package com.akkademy

import akka.actor.Actor
import akka.event.Logging
import akka.actor.Status
import scala.collection.mutable.HashMap

import com.akkademy.messages._

class AkkademyDb extends Actor {
    val map = new HashMap[String, Object]
    val log = Logging(context.system, this)

    override def receive = {
        case SetRequest(key, value) => {
            log.info("received SetRequest - key: {}  - value: {}", key, value)
            map.put(key, value)
            sender() ! Status.Success(true)
        }
        case SetIfNotExistsRequest(key, value) => {
            log.info("received SetIfNotExistsRequest - key: {}  - value: {}", key, value)
            if (!map.contains(key)) map.put(key, value)
            sender() ! Status.Success(true)
        }
        case GetRequest(key) => {
            log.info("received GetRequest - key {}", key)
            map.get(key) match {
                case Some(value) => sender() ! value
                case None => sender() ! Status.Failure (KeyNotFoundException(key))
            }
        }
        case DeleteRequest(key) => {
            log.info("received DeleteRequest - key {}", key)
            if (map.contains(key)) {
                map -= key
                sender() ! Status.Success(true)
            } else {
                sender() ! Status.Failure(KeyNotFoundException(key))
            }
        }
        case o => {
            log.info("received unknown message: {}", o);
            sender() ! Status.Failure (new ClassNotFoundException)
        }
    }
}