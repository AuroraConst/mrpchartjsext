package com.axiommd.charts

import com.axiommd.charts.DataTypes.Service
import com.raquo.laminar.api.L._
import org.scalajs.dom
import typings.chartJs.mod._

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import com.axiommd.Main.consoleOut
import scala.collection.immutable.LazyList.cons
import typings.std.stdStrings.connect





object ChartLaminar:
  def apply () =
    ctag

  
  val servicesOrdered = Service.values.toList.sortBy(_.id).toIndexedSeq.zipWithIndex 
  
  var optChart: Option[Chart] = None
  val ctag =  canvasTag(
      width := "100%",
      height := "500px",

      onMountUnmountCallback(
        mount = { nodeCtx =>
          val ctx = nodeCtx.thisNode.ref // the DOM HTMLCanvasElement
          val chart = Chart.apply.newInstance2(ctx, stackedChartConfig)
          optChart = Some(chart)
        },
        unmount = { thisNode =>
          for (chart <- optChart)
            chart.destroy()
          optChart = None
        }
      ),

      DataModel.mrpDataSignal --> { mrpData =>
        val mrpsOrdered = mrpData.keys.toList.sortBy( k => mrpData(k).patients.size)
        for (chart <- optChart) {
          
          chart.data.labels = mrpsOrdered.toJSArray

          servicesOrdered.foreach{ service => 
            chart.data.datasets.get(service._2).data =
              mrpsOrdered.map{k => mrpData(k).servicePatientCount(service._1).toDouble}.toJSArray
          }
          chart.update()
        }
      },
    )



  val stackedChartConfig =
    new ChartConfiguration {
      `type` = ChartType.bar

      
      
      data = new ChartData {
        datasets = mrpServiceChartDatasets
      }
      
      options = new ChartOptions {


      onResize = (chart:Chart, size) => {
        import scala.scalajs.js.UndefOr
        import scala.scalajs.js.JSConverters._
        consoleOut("resizing and creating button!!!!")
        // consoleOut(s"get by id ${Some(dom.document.getElementById("btn"))}")

        // consoleOut(s"${Option(dom.document.getElementById("btn")) == None}")

        def undefNullOption(o : js.Object): Option[js.Object] = if (o== js.undefined) None else Option(o)



        //TODO [WORKING] HERE
        val btn  = Option( dom.document.getElementById("btn")).map{_.asInstanceOf[dom.html.Button]}
            .getOrElse{
              val newButton = dom.document.createElement("BUTTON").asInstanceOf[dom.html.Button]
              consoleOut("created Button!!!!  $newButton")
              
              val btnText = dom.document.createTextNode("Reset") 
              newButton.id = "btn"
              newButton.classList.add("hello")
              newButton.style.position = "relative"
              newButton.appendChild(btnText)
              newButton
            }

        val parentElement = dom.document.getElementById("app")   
        parentElement.appendChild(btn)
        btn.style.left = Option(chart.width.asInstanceOf[Double]).map{_ - 50 + "px"}.getOrElse("")
        val parentResult = for { 
          canvas <- undefNullOption(chart.canvas).map{_.asInstanceOf[dom.html.Canvas]}
          parentNode <- undefNullOption(canvas.parentNode)
        } {
          consoleOut(s"ParentNode: ${parentNode}")
          // parentNode.appendChild(btn)
        }

        consoleOut(s"ParentResult: ${parentResult}")
        // consoleOut(s"Canvas: ${Option(chart.canvas)}")

        // consoleOut(s"${btn}, ${parentElement}")
        
        // if(chart.canvas !=null && chart.canvas.parentNode!=null) {
        //   chart.canvas.parentNode.appendChild(btn)

        //  }  
        // // import scala.scalajs.js.JSConverters._
        // chart.canvas.parentNode.appendChild(btn)

        // dom.console.log(s"${chart}")  
        // consoleOut(s"HEY!!!!!!!!!!!!  ${chart}")

        
        
          
        // consoleOut(s"HEY!!!!!!!!!!!!  ${Some(chart.canvas)}")   
        // chart.canvas.parentNode.appendChild(btn)
          // chart.canvas.appendChild(btn)

      }      


        
        maintainAspectRatio = false
        legend = new ChartLegendOptions {
          labels = new ChartLegendLabelOptions {
            fontSize = 8
            fontColor = "#FFFF00"
          }

        }



        scales = new ChartScales {
          xAxes = js.Array(new ChartXAxe {
            ticks = new TickOptions {
              fontSize = 8
              rotation = 90
              fontColor = "#AAFF00"

            }
            stacked = true
          })

          yAxes = js.Array(new CommonAxe {
            gridLines = new GridLineOptions {
              color = "#AAFF00"
            }
            ticks = new TickOptions {
              
              fontColor = "#AAFF00"

              beginAtZero = true
            }
            stacked = true
          })
        }
      }
    }
  end stackedChartConfig


  lazy val mrpServiceChartDatasets = servicesOrdered.map{service =>
    new ChartDataSets {
      label = s"${service._1 }"
      backgroundColor = service._1.color
      borderWidth = 1
      data = js.Array()
    }
  }.toJSArray



end ChartLaminar
    
