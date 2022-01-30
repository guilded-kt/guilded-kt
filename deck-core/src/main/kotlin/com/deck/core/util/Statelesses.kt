package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessDocumentationChannel
import com.deck.core.stateless.channel.StatelessListChannel
import com.deck.core.stateless.channel.StatelessMessageChannel
import java.util.*

public fun StatelessMessage(
    client: DeckClient,
    id: UUID,
    channel: StatelessMessageChannel
): StatelessMessage = BlankStatelessMessage(client, id, channel)

internal class BlankStatelessMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val channel: StatelessMessageChannel
): StatelessMessage

public fun StatelessMessageChannel(
    client: DeckClient,
    id: UUID,
    server: StatelessServer?
): StatelessMessageChannel = BlankStatelessMessageChannel(client, id, server)

internal class BlankStatelessMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val server: StatelessServer?
): StatelessMessageChannel

public fun StatelessDocumentationChannel(
    client: DeckClient,
    id: UUID,
    server: StatelessServer
): StatelessDocumentationChannel = BlankStatelessDocumentationChannel(client, id, server)

internal class BlankStatelessDocumentationChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val server: StatelessServer
): StatelessDocumentationChannel

public fun StatelessListChannel(
    client: DeckClient,
    id: UUID,
    server: StatelessServer
): StatelessListChannel = BlankStatelessListChannel(client, id, server)

internal class BlankStatelessListChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val server: StatelessServer
): StatelessListChannel

public fun StatelessServer(
    client: DeckClient,
    id: GenericId,
): StatelessServer = BlankStatelessServer(client, id)

internal class BlankStatelessServer(
    override val client: DeckClient,
    override val id: GenericId
): StatelessServer

public fun StatelessUser(
    client: DeckClient,
    id: GenericId,
): StatelessUser = BlankStatelessUser(client, id)

internal class BlankStatelessUser(
    override val client: DeckClient,
    override val id: GenericId
): StatelessUser