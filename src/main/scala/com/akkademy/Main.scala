import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.ActorLogging
import com.akkademy.AkkademyDb


object Main extends App {
    val system = ActorSystem("akkademy")
    system.actorOf(Props[AkkademyDb], name = "akkademy-db")
}