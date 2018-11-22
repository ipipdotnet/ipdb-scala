package net.ipip.ipdb

import java.io.IOException

@SerialVersionUID(7818375828106090155L)
class InvalidDatabaseException(val message: String) extends IOException(message) {
}

class IPFormatException(val name: String) extends Exception(name) {
}

class NotFoundException(val name: String) extends Exception(name) {
}
