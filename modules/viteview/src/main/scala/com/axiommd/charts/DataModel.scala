package com.axiommd.charts
import com.raquo.laminar.api.L.*
import com.axiommd.charts.DataTypes.MrpData


object DataModel :
  final class DataItemID  //used to generate unique ids
  
  // case class MRPData(id: DataItemID, mrpLabel: String, value: Int)


  val mrpDataVar = Var[Map[String,MrpData]](Map())
  val mrpDataSignal = mrpDataVar.signal


    
    


end DataModel


object DataProcessing :
  import com.axiom.model.shared.dto.Patient
  import DataTypes.MrpData

  


  //TODO   (maps to each bar horizontally)
  def mrpPatients(allPatients:List[Patient]) :Map[String,MrpData] =  
    allPatients.groupBy(_.mrp.getOrElse(""))
      .map{case (mrp,patient) => mrp -> MrpData(mrp,patient)}


