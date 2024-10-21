package com.example.fintech_spring.repository;

import com.example.fintech_spring.dto.entity.Event;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.HTMLDocument;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findAll(Specification<Event> specification);


    static Specification<Event> buildSpecification(String title, String place, LocalDate dateFrom, LocalDate toDate) {
        List<Specification<Event>> specifications = new ArrayList<>();
        if (title != null) {
            specifications.add((Specification<Event>) (event, query, cb) -> cb.equal(event.get("title"), title));
        }
        if (place != null) {
            specifications.add((Specification<Event>) (event, query, cb) -> cb.equal(event.get("location").get("name"), place));
        }
        if (dateFrom != null && toDate != null) {
            specifications.add((Specification<Event>)
                    (event, query, cb) -> cb.between(event.get("date"),
                            Timestamp.valueOf(dateFrom.atStartOfDay()), Timestamp.valueOf(toDate.atStartOfDay())));
        }
        return specifications.stream().reduce(Specification::and).orElse(null);
    }
}
