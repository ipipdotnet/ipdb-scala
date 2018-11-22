package net.ipip.ipdb.test

import net.ipip.ipdb.{BaseStation, City, District, IDC}

object Main {
  def main(args: Array[String]): Unit = {
    testCityV4()

    testBaseStation()

    testFree()
    testIDC()
    testDistrict()
    testCity()
  }

  def testBaseStation(): Unit = {
    try {
      val db = new BaseStation("c:/work/ipdb/base_station.ipdb")
      println(db.getSupportFields)
      println(db.find("112.224.4.99", "CN"))
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def testFree(): Unit = {
    try {
      val db = new District("C:/work/ipdb/city.free.ipdb")
      println(db.find("1.12.13.1", "CN"))
      val info = db.findInfo("8.8.8.8", "CN")
      if (info != null) println(info.getCountryName)
      val m = db.findMap("114.114.114.114", "CN")
      println(m)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def testDistrict(): Unit = {
    try {
      val db = new District("C:/work/ipdb/china_district.ipdb")
      println(db.find("1.12.13.1", "CN"))
      val info = db.findInfo("1.12.13.1", "CN")
      if (info != null) {
        println(info)
        println(info.getCountryName)
      }
      val m = db.findMap("1.12.13.1", "CN")
      println(m)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def testIDC(): Unit = {
    try {
      val db = new IDC("C:/work/ipdb/idc_list.ipdb")
      println(db.find("1.1.1.1", "CN"))
      val info = db.findInfo("8.8.8.8", "CN")
      println(info.getCountryName)
      val m = db.findMap("114.114.114.114", "CN")
      println(m)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def testCityV4(): Unit = {
    try {
      val db = new City("C:/work/ipdb/city.ipv4.ipdb")
      println(db.find("1.1.1.1", "CN"))
      val info = db.findInfo("118.28.1.1", "CN")
      println(info)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def testCity(): Unit = {
    try {
      val db = new City("C:/work/ipdb/city.ipv6.ipdb")
      println(db.find("2001:250:200::", "CN"))
      val info = db.findInfo("2001:250:201::", "CN")
      println(info)
      val m = db.findMap("2001:250:220::", "CN")
      println(m)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
