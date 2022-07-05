package io.github.deck.core.entity

import io.github.deck.common.entity.RawListItemNote
import io.github.deck.common.util.*
import io.github.deck.core.DeckClient
import io.github.deck.core.stateless.StatelessListItem
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.rest.builder.UpdateListItemRequestBuilder
import kotlinx.datetime.Instant

public interface ListItem: StatelessListItem {
    public val authorId: GenericId
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    public val label: String
    public val note: ListItemNote?

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val editorId: GenericId?
    public val editor: StatelessUser? get() = editorId?.let { BlankStatelessUser(client, it) }

    @DeckDelicateApi
    @Deprecated("This does not infer this list item's note unless it (the note) is complete")
    public suspend fun patch(builder: UpdateListItemRequestBuilder.() -> Unit): ListItem = update {
        label = this@ListItem.label
        note = this@ListItem.note?.content()
        builder()
    }
}

public sealed interface ListItemNote {
    public val client: DeckClient

    public val authorId: GenericId
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val editorId: GenericId?
    public val editor: StatelessUser? get() = editorId?.let { BlankStatelessUser(client, it) }

    public data class Complete(
        override val client: DeckClient,
        override val authorId: GenericId,
        override val createdAt: Instant,
        override val updatedAt: Instant?,
        override val editorId: GenericId?,
        public val content: String,
        public val mentions: Mentions?
    ): ListItemNote

    public data class Summary(
        override val client: DeckClient,
        override val authorId: GenericId,
        override val createdAt: Instant,
        override val updatedAt: Instant?,
        override val editorId: GenericId?
    ): ListItemNote

    /**
     * Returns the note's content if it is present (was handed out by guilded), otherwise
     * returns null.
     *
     * **Note: returning null does NOT mean that this note does not exist or it doesn't have a content**
     *
     * @return null if we don't have this note' s content, otherwise returns the value
     */
    @DeckDelicateApi
    public fun content(): String? =
        (this as? Complete)?.content

    public companion object {
        public fun from(client: DeckClient, raw: RawListItemNote): ListItemNote {
            return if (raw.content.isAbsent()) {
                Summary(
                    client = client,
                    authorId = raw.createdBy,
                    createdAt = raw.createdAt,
                    updatedAt = raw.updatedAt.asNullable(),
                    editorId = raw.updatedBy.asNullable()
                )
            } else {
                Complete(
                    client = client,
                    authorId = raw.createdBy,
                    createdAt = raw.createdAt,
                    updatedAt = raw.updatedAt.asNullable(),
                    editorId = raw.updatedBy.asNullable(),
                    content = raw.content.getValue(),
                    mentions = raw.mentions.asNullable()?.let { Mentions.from(it) }
                )
            }
        }
    }
}