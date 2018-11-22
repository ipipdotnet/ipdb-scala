package net.ipip.ipdb

class MetaData {
  var Build: Long = 0
  var IPVersion = 0
  var NodeCount = 0
  var Languages: Map[String, Int] = _
  var Fields: List[String] = _
  var TotalSize: Int = 0

  def parse(json: String): Unit = {
    import scala.util.parsing.json.JSON
    JSON.parseFull(json) match {
      case Some(j: Map[String, _]) =>
        j("build") match {
          case d: Double => Build = d.toLong
          case _ =>
        }
        j("ip_version") match {
          case d: Double => IPVersion = d.toInt
          case _ =>
        }
        j("node_count") match {
          case d: Double => NodeCount = d.toInt
          case _ =>
        }
        j("total_size") match {
          case d: Double => TotalSize = d.toInt
          case _ =>
        }
        j("fields") match {
          case l: List[String] => Fields = l;
          case _ =>
        }
        j("languages") match {
          case m: Map[String, Double] => Languages = m.map(i => (i._1, i._2.toInt))
          case _ =>
        }
      case _ =>
    }
  }
}

class Reader(val name: String) {

  import java.io.File

  import java.nio.file.Files
  import java.nio.file.Path
  import java.nio.file.Paths

  private def bytesToLong(a: Byte, b: Byte, c: Byte, d: Byte) = int2long(((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff))

  private def int2long(i: Int) = {
    var l = i & 0x7fffffffL
    if (i < 0) l |= 0x080000000L
    l
  }

  private val (fileSize: Int, meta: MetaData, data: Array[Byte], nodeCount: Int) = {
    val fileSize: Int = new File(name).length.intValue()

    val path: Path = Paths.get(name)
    val data2: Array[Byte] = Files.readAllBytes(path)

    val metaLength: Long = bytesToLong(data2(0), data2(1), data2(2), data2(3))

    val meta: MetaData = new MetaData()
    meta.parse(new String(data2.slice(4, metaLength.intValue + 8)))
    val nodeCount: Int = meta.NodeCount

    if ((meta.TotalSize + metaLength.intValue + 4) != data2.length) throw new InvalidDatabaseException("database file size error")

    val data: Array[Byte] = data2.slice(metaLength.intValue + 4, fileSize)

    (fileSize, meta, data, nodeCount)
  }

  private val v4offset: Int = {
    var node = 0
    var i = 0
    while (i < 96 && node < nodeCount) {
      if (i >= 80) node = readNode(node, 1)
      else node = readNode(node, 0)
      i += 1
    }
    node
  }

  private def readNode(node: Int, index: Int) = {
    val off = node * 8 + index * 4
    bytesToLong(data(off), data(off + 1), data(off + 2), data(off + 3)).intValue
  }

  private def resolve(node: Int) = {
    val resolved = node - nodeCount + nodeCount * 8
    if (resolved >= fileSize) throw new InvalidDatabaseException("database resolve error")
    val b: Byte = 0
    val size = bytesToLong(b, b, data(resolved), data(resolved + 1)).intValue
    if (data.length < (resolved + 2 + size)) throw new InvalidDatabaseException("database resolve error")
    new String(data, resolved + 2, size, "UTF-8")
  }

  private def findNode(binary: Array[Byte]): Int = {
    import scala.util.control.Breaks._
    var node = 0
    val bit = binary.length * 8
    if (bit == 32) node = v4offset
    var i = 0
    breakable {
      while (i < bit) {
        if (node > nodeCount) break
        node = readNode(node, 1 & ((0xFF & binary(i / 8)) >> 7 - (i % 8)))
        i += 1
      }
    }
    if (node == nodeCount) return 0
    else if (node > nodeCount) return node
    throw new NotFoundException("ip not found")
  }

  import sun.net.util.IPAddressUtil

  def find(addr: String, language: String): Array[String] = {
    var off = 0
    try
      off = meta.Languages.get(language).head
    catch {
      case _: NullPointerException =>
        return null
    }
    var ipv: Array[Byte] = null
    if (addr.indexOf(":") > 0) {
      ipv = IPAddressUtil.textToNumericFormatV6(addr)
      if (ipv == null) throw new IPFormatException("ipv6 format error")
      if (!isIPv6) throw new IPFormatException("no support ipv6")
    }
    else if (addr.indexOf(".") > 0) {
      ipv = IPAddressUtil.textToNumericFormatV4(addr)
      if (ipv == null) throw new IPFormatException("ipv4 format error")
      if (!isIPv4) throw new IPFormatException("no support ipv4")
    }
    else throw new IPFormatException("ip format error")
    var node = 0
    try
      node = findNode(ipv)
    catch {
      case _: NotFoundException =>
        return null
    }
    val data = resolve(node)
    data.split("\t", meta.Fields.length * meta.Languages.size).slice(off, off + meta.Fields.length)
  }

  def findMap(addr: String, language: String): Map[String, String] = Option(find(addr, language))
    .map(data => data.indices.map(i => (getSupportFields(i), data(i))).toMap)
    .orNull

  def isIPv4: Boolean = (meta.IPVersion & 0x01) == 0x01

  def isIPv6: Boolean = (meta.IPVersion & 0x02) == 0x02

  def getMeta: MetaData = meta

  def getBuildUTCTime: Long = meta.Build

  def getSupportFields: List[String] = meta.Fields
}
