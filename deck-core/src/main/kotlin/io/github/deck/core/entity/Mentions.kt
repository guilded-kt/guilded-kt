package io.github.deck.core.entity

import io.github.deck.common.entity.RawMessageMentions
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.mapToBuiltin
import java.util.*

public data class Mentions(
    val users: List<GenericId>,
    val channels: List<UUID>,
    val roles: List<IntGenericId>,
    val here: Boolean,
    val everyone: Boolean,
) {
    public companion object {
        public fun from(raw: RawMessageMentions): Mentions = Mentions(
            users = raw.users.map { it.id },
            channels = raw.channels.map { it.id.mapToBuiltin() },
            roles = raw.roles.map { it.id },
            here = raw.here,
            everyone = raw.everyone,
        )
    }
}