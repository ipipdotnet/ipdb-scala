package net.ipip.ipdb

class CityInfo(var data: Array[String]) {
  def getCountryName = this.data(0)

  def getRegionName = this.data(1)

  def getCityName = this.data(2)

  def getOwnerDomain = this.data(3)

  def getIspDomain = this.data(4)

  def getLatitude = this.data(5)

  def getLongitude = this.data(6)

  def getTimezone = this.data(7)

  def getUtcOffset = this.data(8)

  def getChinaAdminCode = this.data(9)

  def getIddCode = this.data(10)

  def getCountryCode = this.data(11)

  def getContinentCode = this.data(12)

  def getIDC = this.data(13)

  def getBaseStation = this.data(14)

  def getCountryCode3 = this.data(15)

  def getEuropeanUnion = this.data(16)

  def getCurrencyCode = this.data(17)

  def getCurrencyName = this.data(18)

  def getAnycast = this.data(19)

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
    sb.append("latitude:")
    sb.append(this.getLatitude)
    sb.append("\n")
    sb.append("longitude:")
    sb.append(this.getLongitude)
    sb.append("\n")
    sb.append("timezone:")
    sb.append(this.getTimezone)
    sb.append("\n")
    sb.append("utc_offset:")
    sb.append(this.getUtcOffset)
    sb.append("\n")
    sb.append("china_admin_code:")
    sb.append(this.getChinaAdminCode)
    sb.append("\n")
    sb.append("idd_code:")
    sb.append(this.getIddCode)
    sb.append("\n")
    sb.append("country_code:")
    sb.append(this.getCountryCode)
    sb.append("\n")
    sb.append("continent_code:")
    sb.append(this.getContinentCode)
    sb.append("\n")
    sb.append("idc:")
    sb.append(this.getIDC)
    sb.append("\n")
    sb.append("base_station:")
    sb.append(this.getBaseStation)
    sb.append("\n")
    sb.append("country_code3:")
    sb.append(this.getCountryCode3)
    sb.append("\n")
    sb.append("european_union:")
    sb.append(this.getEuropeanUnion)
    sb.append("\n")
    sb.append("currency_code:")
    sb.append(this.getCurrencyCode)
    sb.append("\n")
    sb.append("currency_name:")
    sb.append(this.getCurrencyName)
    sb.append("\n")
    sb.append("anycast:")
    sb.append(this.getAnycast)
    sb.toString
  }
}

class City(name: String) extends Reader(name) {

  def findInfo(addr: String, language: String): CityInfo = {
    val data = this.find(addr, language)
    if (data == null) return null
    new CityInfo(data)
  }
}