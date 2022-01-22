package com.deck.core.cache

import com.deck.common.util.GenericId
import com.deck.core.entity.Channel
import com.deck.core.entity.User
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.*

public class DeckCacheManager() : CacheManager {
    override val channels: Cache<UUID, Channel> = Caffeine.newBuilder().build()
    override val users: Cache<GenericId, User> = Caffeine.newBuilder().build()

    override fun updateChannel(id: UUID, channel: Channel?) {
        channels.put(id, channel)
    }

    override fun retrieveChannel(id: UUID): Channel? {
        return channels.getIfPresent(id)
    }

    override fun updateUser(id: GenericId, user: User?) {
        users.put(id, user)
    }

    override fun retrieveUser(id: GenericId): User? {
        return users.getIfPresent(id)
    }
}
