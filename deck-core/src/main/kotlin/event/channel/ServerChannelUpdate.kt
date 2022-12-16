package io.github.srgaabriel.deck.core.event.channel

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.channel.ServerChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckServerChannel
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayServerChannelUpdatedEvent

/**
 * Called when a [ServerChannel] is edited
 */
public data class ServerChannelUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val channel: ServerChannel
): DeckEvent {
    public val server: StatelessServer by lazy { BlankStatelessServer(client, serverId) }
}

internal val EventService.serverChannelUpdate: EventMapper<GatewayServerChannelUpdatedEvent, ServerChannelUpdateEvent> get() = mapper { client, event ->
    ServerChannelUpdateEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        channel = DeckServerChannel.from(client, event.channel)
    )
}