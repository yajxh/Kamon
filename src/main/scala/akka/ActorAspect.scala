package akka

import org.aspectj.lang.annotation.{Around, Pointcut, Aspect}
import org.aspectj.lang.ProceedingJoinPoint
import kamon.metric.Metrics
import akka.actor.ActorCell

@Aspect
class ActorAspect extends Metrics {

  @Pointcut("execution(* akka.actor.ActorCell+.receiveMessage(..))")
  protected def actorReceive:Unit = {}

  @Around("actorReceive() && this(actor)")
  def around(pjp: ProceedingJoinPoint, actor: akka.actor.ActorCell): AnyRef = {

    //println("The path is: "+actor.self.path.)
    val actorName:String  = actor.self.path.toString

    markAndCountMeter(actorName){
      pjp.proceed
    }

  }
}