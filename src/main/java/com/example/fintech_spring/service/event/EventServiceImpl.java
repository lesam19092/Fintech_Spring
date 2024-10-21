package com.example.fintech_spring.service.event;

import com.example.fintech_spring.dto.EventDto;
import com.example.fintech_spring.dto.entity.Event;
import com.example.fintech_spring.repository.EventRepository;
import com.example.fintech_spring.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    @Override
    public void save(EventDto eventDto) {
        eventRepository.save(getEventFromDto(eventDto));
    }

    @Override
    public EventDto findById(Integer id) {
        Event event = eventRepository.findByIdWithLocations(id)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + id + " not found"));
        return convertToDto(event);
    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, EventDto eventDto) {
        Event event = eventRepository.findByIdWithLocations(id)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + id + " not found"));
        updateEventFromDto(event, eventDto);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findEventsByFilter(String title, String place, LocalDate dateFrom, LocalDate toDate) {
        return eventRepository.findAll(EventRepository.buildSpecification(title, place, dateFrom, toDate))
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void deleteAll() {
        eventRepository.deleteAll();
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }


    private void updateEventFromDto(Event event, EventDto eventDto) {
        event.setDate(eventDto.getDate());
        event.setTitle(eventDto.getTitle());
        event.setPrice(eventDto.getPrice());
        event.setLocation(locationRepository.findById(eventDto.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Location with id " + eventDto.getLocationId() + " not found")));
    }


    private Event getEventFromDto(EventDto eventDto) {
        Event event = new Event();
        event.setDate(eventDto.getDate());
        event.setTitle(eventDto.getTitle());
        event.setPrice(eventDto.getPrice());
        event.setLocation(locationRepository.findById(eventDto.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Location with id " + eventDto.getLocationId() + " not found")));
        return event;
    }

    private EventDto convertToDto(Event event) {
        return new EventDto(
                event.getTitle(),
                event.getDate(),
                event.getPrice(),
                event.getLocation().getId()
        );
    }
}
