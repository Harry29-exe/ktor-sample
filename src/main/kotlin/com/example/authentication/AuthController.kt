package com.example.authentication

import com.example.plugins.JWTService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authController() = routing {
    get("/test") {
        call.respond("test succeeded2")
    }
}

class LoginRequest(val username: String, val password: String)

object AuthController {

    suspend fun login(call: ApplicationCall) {
        val request: LoginRequest = call.receive()
        //todo checking credentials
        JWTService.create(request.username)
    }

}