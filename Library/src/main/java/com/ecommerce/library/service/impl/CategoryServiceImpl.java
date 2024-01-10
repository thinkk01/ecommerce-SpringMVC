package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        try {
            Category categorySave = new Category(category.getName());
            return categoryRepository.save(categorySave);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category update(Category category) {
        try{
            Category categoryUpdate = categoryRepository.getById(category.getId());
            categoryUpdate.setName(category.getName());
            return categoryRepository.save(categoryUpdate);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getById(id);
        categoryRepository.delete(category);
    }

    //loi
    @Override
    public void enableById(Long id) {
        Category category = categoryRepository.getById(id);
        category.setDeleted(true);
        category.setActivated(false);
        categoryRepository.save(category);
    }
    @Override
    public void unableId(Long id){
        Category category = categoryRepository.getById(id);
        category.setDeleted(false);
        category.setActivated(true);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllByActivatedTrue() {
        return categoryRepository.findAllByActivatedTrue();
    }

    @Override
    public List<Category> getCategoriesAndSize() {
        return null;
    }

    @Override
    public List<Category> findAllByActivatedIs() {
        return categoryRepository.findAllByActivatedIs();
    }

    @Override
    public List<CategoryDto> listCategoryAndProduct() {
        return categoryRepository.getCategoryAndProduct();
    }
}
