package com.axiommd

import org.scalatest._,  wordspec._, matchers._



class UndefOrOpsTest extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {
      import scala.scalajs.js

      import scala.scalajs.js.UndefOr
      import scala.scalajs.js.JSConverters._

      val a : Null | Double  = 1
      val b : UndefOr[Double] = 2
      val c : Null | Double = null
      val d : UndefOr[Double] = js.undefined

      //TODO [REFINE] HERE

      d.toOption should be (None)

      b should be (2)
      a should be (1)
      




      
      
    }
  }
}
