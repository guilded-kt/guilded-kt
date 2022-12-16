package io.github.srgaabriel.deck.core.event.calendar

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.CalendarEvent
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEvent
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayCalendarEventCreatedEvent

/**
 * Called when a [CalendarEvent] is created
 */
public data class CalendarEventCreateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val calendarEvent: CalendarEvent,
): DeckEvent {
    val server: StatelessServer by lazy { calendarEvent.server }
    val channel: StatelessCalendarChannel by lazy { calendarEvent.channel }
}

internal val EventService.calendarEventCreate: EventMapper<GatewayCalendarEventCreatedEvent, CalendarEventCreateEvent> get() = mapper { client, event ->
    CalendarEventCreateEvent(
        client = client,
        barebones = event,
        calendarEvent = DeckCalendarEvent.from(client, event.calendarEvent)
    )
}