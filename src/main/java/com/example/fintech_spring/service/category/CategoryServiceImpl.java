package com.example.fintech_spring.service.category;

import com.example.fintech_spring.data_source.Repository;
import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.snapshot.CategorySnapshotService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final Repository<Integer, Category> categoryRepository;

    private final CategorySnapshotService categorySnapshotService;


    @Override
    public void save(Integer id, Category category) {
        categorySnapshotService.saveSnapshot(id, category);
        categoryRepository.save(id, category);
    }

    @Override
    public Category findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            return category.get();

        } else throw new EntityNotFoundException("Category not found");
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categorySnapshotService.saveSnapshot(id, category.get());
            return categoryRepository.deleteById(id);
        }
        return false;
    }


    @Override
    public boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void update(Integer id, Category category) {
        categorySnapshotService.saveSnapshot(id, category);
        categoryRepository.update(id, category);
    }

    @Override
    public int getTotalCount() {
        return categoryRepository.getTotalCount();
    }

}
