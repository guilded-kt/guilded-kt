package com.deck.gateway.event.type

import com.deck.common.entity.RawServerBan
import com.deck.common.entity.RawServerMember
import com.deck.common.util.GenericId
import com.deck.gateway.entity.RawTeamMemberInfo
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamMemberJoined")
public data class GatewayTeamMemberJoinedEvent(
    val serverId: GenericId,
    val member: RawServerMember
): GatewayEvent()

@Serializable
@SerialName("TeamMemberUpdated")
public data class GatewayTeamMemberUpdatedEvent(
    val serverId: GenericId,
    val userInfo: RawTeamMemberInfo
): GatewayEvent()

@Serializable
@SerialName("TeamMemberRemoved")
public data class GatewayTeamMemberRemovedEvent(
    val serverId: GenericId,
    val userId: GenericId,
    val isKick: Boolean,
    val isBan: Boolean
): GatewayEvent()

@Serializable
@SerialName("TeamMemberBanned")
public data class GatewayTeamMemberBannedEvent(
    val serverId: GenericId,
    val serverMemberBan: RawServerBan
): GatewayEvent()

@Serializable
@SerialName("TeamMemberUnbanned")
public data class GatewayTeamMemberUnbannedEvent(
    val serverId: GenericId,
    val serverMemberBan: RawServerBan
): GatewayEvent()