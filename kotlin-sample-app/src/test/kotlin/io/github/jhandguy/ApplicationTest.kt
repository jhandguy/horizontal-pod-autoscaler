package io.github.jhandguy

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testApplication() = testApplication {
        environment {
            config = MapApplicationConfig(
                "kubernetes.node" to "kind-control-plane",
                "kubernetes.namespace" to "sample-app",
                "kubernetes.pod" to "sample-app-6bd9dc6d5d-jstn2",
            )
        }
        application {
            module()
        }
        client.get("/success").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("{\"node\":\"kind-control-plane\",\"namespace\":\"sample-app\",\"pod\":\"sample-app-6bd9dc6d5d-jstn2\"}", bodyAsText())
        }
        client.get("/failure").apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
            assertEquals("", bodyAsText())
        }
    }
}
