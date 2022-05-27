package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.init() {
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureSerialization()
}

//fun main() {
//    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
//        configureRouting()
////        configureSecurity()
//        configureHTTP()
//        configureSerialization()
//    }.start(wait = true)
//}
