package io.github.deck.gateway

import io.github.deck.common.log.DeckLogger
import io.github.deck.common.util.MicroutilsLogger
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.util.EventSupplier
import io.github.deck.gateway.util.EventSupplierData
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

public class GatewayOrchestrator(private val token: String): EventSupplier, CoroutineScope {
    override val coroutineContext: CoroutineContext = Executors
        .newFixedThreadPool(4)
        .asCoroutineDispatcher()
    private val httpClient = HttpClient(CIO.create()) {
        install(WebSockets)
    }
    public val globalEventsFlow: MutableSharedFlow<GatewayEvent> = MutableSharedFlow()
    override val eventSupplierData: EventSupplierData by lazy {
        EventSupplierData(
            scope = this,
            sharedFlow = globalEventsFlow
        )
    }
    public var logger: DeckLogger = MicroutilsLogger("Gateway Logger")

    public var logEventBodies: Boolean = false
    public val gateways: MutableMap<Int, Gateway> = mutableMapOf()
    private val gatewayAtomicId: AtomicInteger = AtomicInteger(0)

    public fun openGateway(): Gateway = DefaultGateway(
        token = token,
        debugPayloads = logEventBodies,
        gatewayId = gatewayAtomicId.getAndIncrement(),
        scope = CoroutineScope(coroutineContext),
        logger = logger,
        client = httpClient,
        eventSharedFlow = globalEventsFlow
    ).also { gateways[it.gatewayId] = it }

    public suspend fun closeGateway(id: Int) {
        val gateway = gateways[id] ?: return
        gateway.disconnect()
        gateways.remove(id)
    }
}