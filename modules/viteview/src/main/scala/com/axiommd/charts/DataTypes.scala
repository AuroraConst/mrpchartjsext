package com.axiommd.charts


import com.axiom.model.shared.dto.Patient



object DataTypes :
  enum Service(_id:String, _color:String):
    def id = _id
    def color = _color
    def serviceFromOrdinal(i:Int) = Service.values(i)

    case MED               extends Service("MED",         "#FF7F50")
    case MEDCARD           extends Service("MEDCARD",     "#FFdd66")
    case HOSPITALIST       extends Service("HOSPITALIST", "#DE3163")
    case HOSPITALISTNOFLAG extends Service("HOSPNOFLAG",  "#1E90FF")
    case MEDCTU            extends Service("MEDCTU",      "#0000e0") 
    case TELCTU            extends Service("TELCTU",      "#00CCCC") 
    case TELACON           extends Service("TELACON",     "#44ff33") 
    case TELCARD           extends Service("TELCARD",     "#FF0000") 
    case TELMED            extends Service("TELMED",      "#FF69B4") 
    case PALONC            extends Service("PALONC",      "#FFEE66") 
    case MEDONCOLGY        extends Service("MEDONCOLGY",  "#CCCCFF") 
    case ACON              extends Service("ACON",        "#F322EE") 
    case MEDRENAL          extends Service("MEDRENAL",    "#CDA776") 
    case TELRENAL          extends Service("TELRENAL",    "#EFC998") 
    case STROKE            extends Service("STROKE",      "#FF5733") 
    case TELSTROKE         extends Service("TELSTROKE",   "#6495ED") 
    case MH                extends Service("MH",          "#00AA00") 
    case FORENSICS         extends Service("FORENSICS",   "#CCBBCC") 
    case UNDEFINED         extends Service("UNDEFINED",   "#333333") 

  def getServiceByName(name: String): Service = 
    try
      Service.valueOf(name)
    catch
      case _: NoSuchElementException => Service.UNDEFINED



  import Service.* 

  case class MrpsPatientList(mrps:List[String],patients:List[Patient])
  
  

  case class MrpData(mrp:String, patients:List[Patient]) :
    private def convertToHospitalist (s:Service,p:Patient)   =
      if(s == MED && (p.hosp.getOrElse("N").toUpperCase() == "Y")) 
        HOSPITALIST -> p
          else  
        s -> p

    
    private def servicePatient(p:Patient) = 
      val s = p.service.map{getServiceByName}.getOrElse(UNDEFINED)
      convertToHospitalist(s,p)


    lazy val servicePatients =  patients.map {  servicePatient}
      .groupBy(  (a,_) => a)
      .map{(k,l) => k -> l.map{(_,p) => p }}

    def  servicePatientCount(s:Service) = servicePatients.get(s).map{x => x.size}.getOrElse(0)



end DataTypes
