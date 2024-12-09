package com.axiommd.charts
import com.raquo.laminar.api.L.*
import com.axiommd.charts.DataTypes.MrpData


object DataModel :
  final class DataItemID  //used to generate unique ids
  
  // case class MRPData(id: DataItemID, mrpLabel: String, value: Int)


  val dataVar = Var[List[MrpData]](List())
  val dataSignal = dataVar.signal


    
    


end DataModel


object DataProcessing :
  import com.axiom.model.shared.dto.Patient
  import DataTypes.{MrpData,Service}

  
  def services(pts:List[Patient]) :List[String] =  pts.groupBy(_.service.getOrElse("")).keys.toList

  def mrpPatients(allPatients:List[Patient]) :Map[String,MrpData] =  
    allPatients.groupBy(_.mrp.getOrElse(""))
      .map{(mrp,patient) => mrp -> MrpData(mrp,patient)}


