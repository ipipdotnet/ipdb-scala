package net.ipip.ipdb

class DistrictInfo(var data: Array[String]) {
  def getCountryName = this.data(0)

  def getRegionName = this.data(1)

  def getCityName = this.data(2)

  def getDistrictName = this.data(3)

  def getChinaAdminCode = this.data(4)

  def getCoveringRadius = this.data(5)

  def getLatitude = this.data(6)

  def getLongitude = this.data(7)

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
    sb.append("district_name:")
    sb.append(this.getDistrictName)
    sb.append("\n")
    sb.append("china_admin_code:")
    sb.append(this.getChinaAdminCode)
    sb.append("\n")
    sb.append("covering_radius:")
    sb.append(this.getCoveringRadius)
    sb.append("\n")
    sb.append("latitude:")
    sb.append(this.getLatitude)
    sb.append("\n")
    sb.append("longitude:")
    sb.append(this.getLongitude)
    sb.toString
  }
}

class District(name: String) extends Reader(name) {

  def findInfo(addr: String, language: String): DistrictInfo = {
    val data = this.find(addr, language)
    if (data == null) return null
    new DistrictInfo(data)
  }
}