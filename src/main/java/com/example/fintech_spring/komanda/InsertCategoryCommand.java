package com.example.fintech_spring.komanda;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InsertCategoryCommand implements Command<Category> {

    private final Repository<Integer, Category> categoryRepository;

    @Override
    public void execute(Category entity) {
        categoryRepository.save(entity.getId(), entity);

    }
}
