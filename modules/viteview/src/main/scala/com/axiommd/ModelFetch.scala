package com.axiommd

import com.axiommd.charts.DataModel
import com.axiommd.charts.DataProcessing
import org.scalajs.dom





  
object ModelFetch :
  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
  import zio.json._

  import io.laminext.fetch._

  import org.scalajs.dom.AbortController


  val abortController = new AbortController()

  def fetchPatients = 
    import com.axiom.model.shared.dto.Patient 
    
    Fetch.get("http://localhost:8080/patientsjson").future.text(abortController)
      .map(s => s.data.fromJson[List[Patient]])
      .map(r => r.toOption.getOrElse(Nil))
      .foreach{ patients => 
        val mrpMap = DataProcessing.mrpPatients(patients)
        DataModel.mrpDataVar.set( mrpMap )
      }




    




