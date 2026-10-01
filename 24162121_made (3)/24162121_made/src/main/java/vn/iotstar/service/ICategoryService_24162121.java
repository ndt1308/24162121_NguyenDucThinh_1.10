package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Category_24162121;

public interface ICategoryService_24162121 {
    List<Category_24162121> findAll();
    Category_24162121 findById(int categoryId);
}
