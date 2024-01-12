package org.example.sockets

import java.net.ServerSocket
import java.net.Socket
import java.util.Scanner

/**
 * @property id
 */
class User(internal val id: Int) {
    private var server: ServerSocket? = null
    private var client: Socket? = null
    internal var scanner: Scanner? = null

    override fun equals(other: Any?): Boolean =
        when (other) {
            !is User -> false
            else -> other.id == id
        }

    /**
     * @param port
     */
    fun connectToServer(port: Int) {
        server = ServerSocket(port)
        println("Server started on port ${server?.localPort}")
        client = server?.accept()
        println(
            "Client connected to server on port ${server?.localPort}: ${
                client?.inetAddress?.hostAddress
            }",
        )
        scanner = client?.getInputStream()?.let { Scanner(it) }
    }

    /**
     * @param message
     */
    fun sendMessage(message: String) {
        client?.getOutputStream()?.write(message.toByteArray())
        client?.getOutputStream()?.flush()
    }

    override fun hashCode(): Int = id
}

/**
 * @return
 */
fun User?.hasMessage(): Boolean = this?.scanner?.hasNext() ?: false

/**
 * @return
 */
fun User?.getMessage(): String? = this?.scanner?.next()
