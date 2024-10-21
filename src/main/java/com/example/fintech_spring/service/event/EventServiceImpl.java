package com.example.fintech_spring.service.event;

import com.example.fintech_spring.dto.EventDto;
import com.example.fintech_spring.dto.entity.Event;
import com.example.fintech_spring.repository.EventRepository;
import com.example.fintech_spring.service.location.LocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventSerivce {

    private final EventRepository eventRepository;
    private final LocationService locationService;

    @Override
    public void save(EventDto eventDto) {
        eventRepository.save(getEvent(eventDto));
    }

    @Override
    public EventDto findById(Integer id) {

        Optional<Event> optional = eventRepository.findById(id);

        if (optional.isPresent()) {
            Event event = optional.get();

            return new EventDto(
                    event.getTitle(),
                    event.getDate(),
                    event.getPrice(),
                    event.getLocation().getId());
        }

        throw new EntityNotFoundException("Event with id " + id + " not found");

    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, EventDto eventDto) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            eventRepository.save(getEvent(eventDto));
        } else {
            throw new EntityNotFoundException("Location with id " + id + " not found");
        }

    }


    private Event getEvent(EventDto eventDto) {
        Event event = new Event();
        event.setDate(eventDto.getDate());
        event.setTitle(eventDto.getTitle());
        event.setPrice(eventDto.getPrice());
        event.setLocation(locationService.findById(eventDto.getLocationId()));
        return event;
    }
}
