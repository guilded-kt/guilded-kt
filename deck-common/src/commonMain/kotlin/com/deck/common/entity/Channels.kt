package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.serialization.Serializable

@Serializable
data class RawChannel(
    val id: UniqueId,
    val type: RawChannelType,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val updatedAt: Timestamp,
    val name: String,
    val contentType: RawChannelContentType,
    val archivedAt: Timestamp?,
    val parentChannelId: UniqueId?,
    val autoArchiveAt: Timestamp?,
    val deletedAt: Timestamp?,
    val archivedBy: GenericId?,
    val description: String?,
    val createdByWebhookId: UniqueId?,
    val archivedByWebhookId: UniqueId?,
    val teamId: GenericId?,
    val channelCategoryId: UniqueId?,
    val addedAt: Timestamp?,
    val channelId: UniqueId?,
    val isRoleSynced: Boolean?,
    val roles: List<RawRole>?,
    val userPermissions: List<RawUser>?,
    @DeckUnknown val tournamentRoles: List<Unit>? = null,
    val isPublic: Boolean,
    @DeckUnknown val priority: Int?,
    val groupId: GenericId?,
    @DeckUnknown val settings: Unit? = null,
    @DeckUnknown val groupType: String = "",
    val rolesById: Dictionary<IntGenericId, RawRole>,
    @DeckUnknown val tournamentsRolesById: Unit? = null,
    @DeckUnsupported val createdByInfo: Unit? = null
)

@Serializable
data class RawPrivateChannel(
    val id: UniqueId,
    val type: RawChannelType,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val updatedAt: Timestamp,
    val name: String?,
    val contentType: RawChannelContentType,
    val archivedAt: Timestamp?,
    val autoArchiveAt: Timestamp?,
    val archivedBy: GenericId?,
    val parentChannelId: UniqueId?,
    val deletedAt: Timestamp?,
    val createdByWebhookId: UniqueId?,
    val archivedByWebhookId: UniqueId?,
    val dmType: String = "Default",
    val ownerId: GenericId,
    val voiceParticipants: List<RawUser>,
    val users: List<RawUser>
)

@Serializable(RawChannelType.Serializer::class)
enum class RawChannelType(val serialName: String) {
    Team("Team"), Private("DM");

    companion object Serializer: StringIdEnumSerializer<RawChannelType>(
        StringSerializationStrategy(values().associateBy { it.serialName })
    )
}

@Serializable(RawChannelContentType.Serializer::class)
enum class RawChannelContentType(val serialName: String) {
    Chat("chat"),
    Streaming("stream"),
    Voice("voice"),
    Calendar("event"),
    Scheduling("scheduling"),
    Announcements("announcements"),
    Forum("forum"),
    List("list"),
    Documentation("doc"),
    Media("media");

    companion object Serializer: StringIdEnumSerializer<RawChannelContentType>(
        StringSerializationStrategy(values().associateBy { it.serialName })
    )
}