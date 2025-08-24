
package com.axiommd

import org.scalatest._

import wordspec._
import matchers._
import scala.concurrent.Future


class FetchAsyncTest extends wordspec.AsyncWordSpec with should.Matchers{
  "this" should {
    "work" in {
      
      val fut = Future(3)
      fut.map(_ + 1).map(_ should be(4))
    }
  }
  "fetch" should {
    "work" in {
      import io.laminext.fetch._
      import com.axiom.model.shared.dto.Patient 
      import zio.json._
      import org.scalajs.dom.AbortController 
      import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

      val abortController = new AbortController()
      Fetch.get("http://localhost:8080/patientsjson").future.text(abortController )
        .map(s => s.data.fromJson[List[Patient]])
        .map(r => r.toOption.getOrElse(Nil))
        .map{ patients => 
          patients.size should be > 0 
        }


     
    }
  }
}
