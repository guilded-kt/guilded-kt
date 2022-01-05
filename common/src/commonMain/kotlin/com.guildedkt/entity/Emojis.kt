package com.guildedkt.entity

import com.guildedkt.util.GenericId
import com.guildedkt.util.GuildedUnknown
import com.guildedkt.util.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class RawEmoji(
    val id: Int,
    val name: String,
    val createdBy: GenericId,
    val createdAt: Timestamp,
    val png: String,
    val webp: String,
    val apng: String,
    val aliases: List<String>,
    val teamId: GenericId,
    val isDeleted: Boolean,
    val discordEmojiId: Long?,
    val discordSyncedAt: Timestamp?
)

@Serializable
@GuildedUnknown
data class RawReaction(
    val id: Int,
    val customReaction: RawCustomReaction,
    val customReactionId: Int
)

@Serializable
data class RawCustomReaction(
    val id: Int,
    val name: String,
    val png: String,
    val webp: String,
    val apgn: String
)
