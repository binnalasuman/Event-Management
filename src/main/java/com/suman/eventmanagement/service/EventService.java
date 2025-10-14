package com.suman.eventmanagement.service;

import com.suman.eventmanagement.entity.Event;
import com.suman.eventmanagement.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Page<Event> searchEventsByTitle(String title, Pageable pageable) {
        return eventRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Event> searchEventsByLocation(String location, Pageable pageable) {
        return eventRepository.findByLocationContainingIgnoreCase(location, pageable);
    }

    public Page<Event> searchEventsByTitleAndLocation(String title, String location, Pageable pageable) {
        return eventRepository.findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(title, location, pageable);
    }

    public Page<Event> searchEventsByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return eventRepository.findByEventDateBetween(start, end, pageable);
    }

    public Page<Event> searchAll(String title, String location, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return eventRepository.findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndEventDateBetween(
                title, location, start, end, pageable
        );
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setTitle(eventDetails.getTitle());
                    event.setDescription(eventDetails.getDescription());
                    event.setLocation(eventDetails.getLocation());
                    event.setEventDate(eventDetails.getEventDate());
                    event.setAvailableSeats(eventDetails.getAvailableSeats());
                    return eventRepository.save(event);
                }).orElse(null);
    }

    public boolean deleteEvent(Long id) {
        return eventRepository.findById(id)
                .map(event -> {
                    eventRepository.delete(event);
                    return true;
                }).orElse(false);
    }
}
