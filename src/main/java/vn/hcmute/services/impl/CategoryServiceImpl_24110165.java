package vn.hcmute.services.impl;

import java.io.File;
import java.util.List;
import vn.hcmute.dao.ICategoryDao_24110165;
import vn.hcmute.dao.impl.CategoryDaoImpl_24110165;
import vn.hcmute.entity.Category_24110165;
import vn.hcmute.services.ICategoryService_24110165;
import vn.hcmute.utils.Constant_24110165;

public class CategoryServiceImpl_24110165 implements ICategoryService_24110165 {
    private final ICategoryDao_24110165 categoryDao = new CategoryDaoImpl_24110165();

    @Override
    public void insert(Category_24110165 category) {
        categoryDao.insert(category);
    }

    @Override
    public void update(Category_24110165 newCategory) {
        Category_24110165 oldCategory = categoryDao.findById(newCategory.getCategoryId());
        if (oldCategory != null) {
            oldCategory.setCategoryname(newCategory.getCategoryname());
            oldCategory.setStatus(newCategory.getStatus());
            if (newCategory.getImages() != null && !newCategory.getImages().isEmpty()) {
                // Xóa ảnh cũ nếu người dùng cập nhật ảnh mới
                String oldFileName = oldCategory.getImages();
                if (oldFileName != null && !oldFileName.isEmpty()) {
                    File file = new File(Constant_24110165.DIR + File.separator + oldFileName);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                oldCategory.setImages(newCategory.getImages());
            }
            categoryDao.update(oldCategory);
        } else {
            categoryDao.update(newCategory);
        }
    }

    @Override
    public void edit(Category_24110165 category) {
        update(category);
    }

    @Override
    public void delete(int id) {
        Category_24110165 oldCategory = categoryDao.findById(id);
        if (oldCategory != null && oldCategory.getImages() != null) {
            File file = new File(Constant_24110165.DIR + File.separator + oldCategory.getImages());
            if (file.exists()) {
                file.delete();
            }
        }
        categoryDao.delete(id);
    }

    @Override
    public Category_24110165 get(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public Category_24110165 findById(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public Category_24110165 findByCategoryname(String name) {
        return categoryDao.findByCategoryname(name);
    }

    @Override
    public List<Category_24110165> getAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category_24110165> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category_24110165> search(String keyword) {
        return categoryDao.searchByName(keyword);
    }

    @Override
    public List<Category_24110165> searchByName(String keyword) {
        return categoryDao.searchByName(keyword);
    }

    @Override
    public List<Category_24110165> findAll(int page, int pagesize) {
        return categoryDao.findAll(page, pagesize);
    }

    @Override
    public int count() {
        return categoryDao.count();
    }
}