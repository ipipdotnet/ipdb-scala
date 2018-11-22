package net.ipip.ipdb

class IDCInfo(var data: Array[String]) {
  def getCountryName = this.data(0)

  def getRegionName = this.data(1)

  def getCityName = this.data(2)

  def getOwnerDomain = this.data(3)

  def getIspDomain = this.data(4)

  def getIDC = this.data(5)

  override def toString: String = {
    val sb = new StringBuffer
    sb.append("country_name:")
    sb.append(this.getCountryName)
    sb.append("\n")
    sb.append("region_name:")
    sb.append(this.getRegionName)
    sb.append("\n")
    sb.append("city_name:")
    sb.append(this.getCityName)
    sb.append("\n")
    sb.append("owner_domain:")
    sb.append(this.getOwnerDomain)
    sb.append("\n")
    sb.append("isp_domain:")
    sb.append(this.getIspDomain)
    sb.append("\n")
    sb.append("idc:")
    sb.append(this.getIDC)
    sb.toString
  }
}

class IDC(name: String) extends Reader(name) {

  def findInfo(addr: String, language: String): IDCInfo = {
    val data = this.find(addr, language)
    if (data == null) return null
    new IDCInfo(data)
  }
}