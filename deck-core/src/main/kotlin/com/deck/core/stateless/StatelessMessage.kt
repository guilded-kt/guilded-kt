package com.deck.core.stateless

import com.deck.common.util.DeckUnsupported
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Message
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessServer
import com.deck.core.util.ReactionHolder
import com.deck.rest.builder.SendMessageRequestBuilder
import java.util.*

public interface StatelessMessage: StatelessEntity, ReactionHolder {
    public val id: UUID
    public val channelId: UUID
    public val serverId: GenericId?

    public val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, serverId)
    public val server: StatelessServer? get() = serverId?.let { BlankStatelessServer(client, it) }

    /**
     * Sends another message replying to this one.
     *
     * @param builder reply builder
     * @return the created message
     */
    public suspend fun sendReply(builder: SendMessageRequestBuilder.() -> Unit): Message = channel.sendMessage {
        builder(this)
        replyTo(this@StatelessMessage.id)
    }

    /**
     * Adds the specified reaction to this message.
     * For a list of all emoji ids, you can check the extras module.
     *
     * @param reactionId
     */
    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToContent(channel.id, id, reactionId)

    @DeckUnsupported
    /** Not yet supported */
    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromContent(channel.id, id, reactionId)

    /**
     * Overwrites this message's content
     *
     * @param content new content
     * @return new message with the new content
     */
    public suspend fun update(content: String): Message =
        channel.updateMessage(id, content)

    /**
     * Deletes this message
     */
    public suspend fun delete(): Unit =
        channel.deleteMessage(id)
}