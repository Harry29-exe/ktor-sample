package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureSecurity() {

    authentication {
        jwt {
            val config = this@configureSecurity.environment.config
            val algorithm = Algorithm.HMAC512(config.property("jwt.secret").getString())
            val domain = config.property("jwt.domain").getString()
            JWTService.domain = domain
            JWTService.algorithm = algorithm

            realm = config.property("jwt.realm").getString()

            verifier(
                JWT
                    .require(algorithm)
                    .withIssuer(domain)
                    .build()
            )
            validate { credential ->
                JWTPrincipal(credential.payload)
            }
        }
    }

}

object JWTService {
    internal lateinit var domain: String
    internal lateinit var algorithm: Algorithm


    fun create(username: String): String {
        return JWT.create()
            .withIssuer(domain)
            .withClaim("username", username)
            .sign(algorithm)
    }
}