package vn.iotstar.service.impl;

import java.util.List;
import vn.iotstar.dao.ICategoryDAO_24162121;
import vn.iotstar.dao.impl.CategoryDAOImpl_24162121;
import vn.iotstar.entity.Category_24162121;
import vn.iotstar.service.ICategoryService_24162121;

public class CategoryServiceImpl_24162121 implements ICategoryService_24162121 {
    private final ICategoryDAO_24162121 dao = new CategoryDAOImpl_24162121();

    @Override
    public List<Category_24162121> findAll() {
        return dao.findAll();
    }

    @Override
    public Category_24162121 findById(int categoryId) {
        return dao.findById(categoryId);
    }
}
