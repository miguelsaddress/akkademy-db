import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.ActorLogging

import com.akkademy.messages._
import com.akkademy.AkkademyDb

object Main extends App {
    val system = ActorSystem("akkademy-db")
    val akkademyDb = system.actorOf(Props[AkkademyDb], "akkademy-db")

    akkademyDb ! SetRequest("Hola", "Buna")
    akkademyDb ! SetRequest("Adiós", "La revedere")
    akkademyDb ! SetRequest("Gracias", "Multumesc")
    
    //system.awaitTermination()
    system.shutdown()
}