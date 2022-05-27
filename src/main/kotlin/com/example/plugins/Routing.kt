package com.example.plugins

import com.example.authentication.authController
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

@Serializable
data class WE(val name: String, val value: Int)


fun Application.configureRouting() {
    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        post("/we") {
            val body: WE = call.receive()
            val principal = this.context.authentication.principal
            println("Principal: $principal")
            call.respond(body)
        }


    }

    this.authController()
}
