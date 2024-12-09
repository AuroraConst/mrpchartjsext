package com.axiommd.charts

import scala.scalajs.js
import org.scalajs.dom
import scala.scalajs.js.JSConverters.*
import typings.chartJs.mod.*
import com.raquo.laminar.api.L.{*, given}
import com.axiommd.charts.DataTypes.MrpData


object ChartLaminar:
  def apply () =
    ctag
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

      DataModel.dataSignal --> { data =>
        for (chart <- optChart) {
          chart.data.labels = data.map(_.mrp).toJSArray
          chart.data.datasets.get(0).data = data.map(_.patients.size.toDouble).toJSArray
          // chart.data.datasets.get(1).data = data.map(_.value.toDouble*2).toJSArray
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
        scales = new ChartScales {
          xAxes = js.Array(new ChartXAxe {
            stacked = true
          })

          yAxes = js.Array(new CommonAxe {
            ticks = new TickOptions {
              beginAtZero = true
            }
            stacked = true
          })
        }
      }
    }
  end stackedChartConfig


  lazy val mrpServiceChartDatasets = DataTypes.servicesMap.values.map{service =>
    new ChartDataSets {
      label = service.id
      backgroundColor = service.color
      borderWidth = 1
      data = js.Array(0)
    }
  }.toJSArray



end ChartLaminar
    
