package io.github.jhandguy

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Serializable
data class Response(val node: String, val namespace: String, val pod: String)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    routeHealth()
    routeSuccess()
    routeFailure()
}

fun Application.routeHealth(): Routing = routing {
    get("/monitoring/health") {
        call.respond(HttpStatusCode.OK)
    }
}

fun Application.routeSuccess(): Routing = routing {
    fun ApplicationConfig.propertyOrDefault(path: String, default: String = ""): String = propertyOrNull(path)?.getString() ?: default
    val response = environment?.config?.let {
        Response(
            it.propertyOrDefault("kubernetes.node"),
            it.propertyOrDefault("kubernetes.namespace"),
            it.propertyOrDefault("kubernetes.pod"),
        )
    }
    get("/success") {
        response?.let {
            call.respond(it)
        } ?: call.respond(HttpStatusCode.InternalServerError)
    }
}

fun Application.routeFailure(): Routing = routing {
    get("/failure") {
        call.respond(HttpStatusCode.InternalServerError)
    }
}
