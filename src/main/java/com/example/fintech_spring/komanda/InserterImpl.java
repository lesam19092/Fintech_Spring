package com.example.fintech_spring.komanda;

import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class InserterImpl implements Inserter {

    private Command insertLocationCommand;
    private Command insertCategoryCommand;


    public void insertLocation(Location location) {
        insertLocationCommand.execute(location);
    }

    public void insertCategory(Category category) {
        insertCategoryCommand.execute(category);
    }

}
