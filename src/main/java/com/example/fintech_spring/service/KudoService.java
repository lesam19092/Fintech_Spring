package com.example.fintech_spring.service;


import com.example.fintech_spring.komanda.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KudoService {


    private final List<Command> commands;

    @EventListener(ApplicationReadyEvent.class)
    private void fetchingRepositories() {
        log.info("Application start fetching");
        commands.forEach(Command::execute);
    }


}






