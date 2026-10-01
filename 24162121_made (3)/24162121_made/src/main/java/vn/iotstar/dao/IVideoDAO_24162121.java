package vn.iotstar.dao;

import java.util.List;
import java.util.Map;
import vn.iotstar.entity.Videos_24162121;

public interface IVideoDAO_24162121 {
    List<Videos_24162121> findPage(int page, int size);
    long count();
    Videos_24162121 findById(String videoId);
    void insert(Videos_24162121 video);
    void update(Videos_24162121 video);
    void delete(String videoId);
    String nextId();
    long countShares(String videoId);
    long countLikes(String videoId);

    // ===== Câu 4 + 5: trang Home theo Category =====
    List<Videos_24162121> findPageByCategory(int categoryId, int page, int size);
    long countByCategory(int categoryId);
    /** Câu 5: đếm số video theo từng category (key = CategoryId). */
    Map<Integer, Long> countGroupByCategory();
}
