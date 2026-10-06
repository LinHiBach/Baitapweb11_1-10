package vn.hcmute.services;

import java.util.List;
import vn.hcmute.entity.Category_24110165;

public interface ICategoryService_24110165 {
    void insert(Category_24110165 category);
    void update(Category_24110165 category);
    void edit(Category_24110165 category);
    void delete(int id);
    Category_24110165 get(int id);  
    Category_24110165 findById(int id);
    Category_24110165 findByCategoryname(String name);
    List<Category_24110165> getAll();
    List<Category_24110165> findAll();
    List<Category_24110165> search(String keyword);
    List<Category_24110165> searchByName(String keyword);
    List<Category_24110165> findAll(int page, int pagesize);
    int count();
}