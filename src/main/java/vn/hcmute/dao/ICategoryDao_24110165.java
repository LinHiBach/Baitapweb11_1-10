package vn.hcmute.dao;

import java.util.List;
import vn.hcmute.entity.Category_24110165;

public interface ICategoryDao_24110165 {
    void insert(Category_24110165 cate);
    void update(Category_24110165 cate);
    void delete(int id);
    Category_24110165 findById(int id);
    Category_24110165 findByCategoryname(String name);
    List<Category_24110165> findAll();
    List<Category_24110165> searchByName(String keyword);
    List<Category_24110165> findAll(int page, int pagesize);
    int count();

    // Aliases for compatibility
    void edit(Category_24110165 category);
    Category_24110165 get(int id);
    List<Category_24110165> getAll();
    List<Category_24110165> search(String keyword);
}