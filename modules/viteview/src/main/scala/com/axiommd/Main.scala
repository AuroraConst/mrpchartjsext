package com.axiommd


import com.axiommd.charts.ChartLaminar
import com.raquo.laminar.api.L._
import org.scalajs.dom




object Main :
  def consoleOut(msg: String): Unit = {
     dom.console.log(s"%c $msg","background: #222; color: #bada55")
  }
  @main def entrypoint(): Unit = 

    consoleOut ("Hello, world from console!!!!???")
    println("Hello, world from println!!!")
    // Scala.js outputs to the browser dev console, not the sbt session
    // Always have the browser dev console open when developing web UIs.


    val element = dom.document.querySelector("#app")
    ModelFetch.fetchPatients
    
  
    renderOnDomContentLoaded(element,ChartLaminar())
