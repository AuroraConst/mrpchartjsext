package com.axiommd.charts


import com.axiom.model.shared.dto.Patient

object DataTypes :
  enum Service(_id:String, _color:String):
    def id = _id
    def color = _color

    case Med               extends Service("MED",         "#FFFF66")
    case MedCard           extends Service("MEDCARD",     "#FFdd66")
    case Hospitalist       extends Service("HOSPITALIST", "#C71585")
    case HospitalistNoFlag extends Service("HOSPNOFLAG",  "#1E90FF")
    case MCTU              extends Service("MEDCTU",      "#0000e0") 
    case TelCTU            extends Service("TELCTU",      "#00CCCC") 
    case TelAcon           extends Service("TELACON",     "#44ff33") 
    case CardTele          extends Service("TELCARD",     "#FF0000") 
    case MedTele           extends Service("TELMED",      "#FF69B4") 
    case PalOnc            extends Service("PALONC",      "#FFEE66") 
    case MedOnc            extends Service("MEDONCOLGY",  "#FF00FF") 
    case Acon              extends Service("ACON",        "#F322EE") 
    case MedRenal          extends Service("MEDRENAL",    "#CDA776") 
    case TelRenal          extends Service("TELRENAL",    "#EFC998") 
    case Stroke            extends Service("STROKE",      "#FF5733") 
    case StrokeTele        extends Service("TELSTROKE",   "#BBAAAA") 
    case MH                extends Service("MH",          "#00AA00") 
    case Forensics         extends Service("FORENSICS",   "#CCBBCC") 
    case Undefined         extends Service("UNDEFINED",   "#333333") 

   
  lazy val servicesMap = Service.values.map(s => (s.id,s)).toMap

  import Service.* 
  case class MrpData(mrp:String, patients:List[Patient]) :
    lazy val servicePatients = patients.groupBy(_.service.getOrElse(""))
      .map{(id,patient) => servicesMap.get(id).getOrElse(Undefined) -> patient}

    lazy val servicePatientCount = servicePatients.map{case (k,v) => k -> v.size}
    lazy val sortedServiceKeys = servicePatients.keys.toList.sortBy(_.id)

end DataTypes
