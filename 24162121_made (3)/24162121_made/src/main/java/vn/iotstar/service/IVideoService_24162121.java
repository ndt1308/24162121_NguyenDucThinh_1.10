package vn.iotstar.service;

import java.util.Map;
import vn.iotstar.entity.Videos_24162121;
import vn.iotstar.util.PageResult_24162121;

public interface IVideoService_24162121 {
    PageResult_24162121<Videos_24162121> findPage(int page, int size);
    Videos_24162121 findById(String videoId);
    void insert(Videos_24162121 video);
    void update(Videos_24162121 video);
    void delete(String videoId);
    long countShares(String videoId);
    long countLikes(String videoId);

    // ===== Câu 4 + 5 =====
    PageResult_24162121<Videos_24162121> findPageByCategory(int categoryId, int page, int size);
    Map<Integer, Long> countGroupByCategory();
}
