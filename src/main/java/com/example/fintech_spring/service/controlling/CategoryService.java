package com.example.fintech_spring.service.controlling;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    //TODO add the missing implementation
    //TODO поменять названеие папки

    private final Repository<Integer, Category> categoryRepository;

    public void save(Integer id, Category category) {
        categoryRepository.save(id, category);
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    public boolean deleteById(Integer id) {
        return categoryRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void update(Integer id, Category category) {
        categoryRepository.update(id, category);
    }

    public int getTotalCount() {
        return categoryRepository.getTotalCount();
    }
}
