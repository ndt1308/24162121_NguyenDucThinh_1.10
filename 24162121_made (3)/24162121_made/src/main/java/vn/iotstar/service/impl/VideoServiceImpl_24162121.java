package vn.iotstar.service.impl;

import java.util.List;
import java.util.Map;
import vn.iotstar.dao.IVideoDAO_24162121;
import vn.iotstar.dao.impl.VideoDAOImpl_24162121;
import vn.iotstar.entity.Videos_24162121;
import vn.iotstar.service.IVideoService_24162121;
import vn.iotstar.util.PageResult_24162121;

public class VideoServiceImpl_24162121 implements IVideoService_24162121 {
    private final IVideoDAO_24162121 dao = new VideoDAOImpl_24162121();

    @Override
    public PageResult_24162121<Videos_24162121> findPage(int page, int size) {
        long total = dao.count();
        int totalPages = (int) Math.ceil((double) total / size);
        if (page > totalPages) page = totalPages;   // ví dụ vừa xóa video cuối của trang cuối
        if (page < 1) page = 1;
        List<Videos_24162121> items = dao.findPage(page, size);
        return new PageResult_24162121<>(items, page, size, total);
    }

    @Override
    public Videos_24162121 findById(String videoId) {
        return videoId == null ? null : dao.findById(videoId);
    }

    @Override
    public void insert(Videos_24162121 video) {
        video.setVideoId(dao.nextId());          // ID tự sinh
        if (video.getViews() == null) video.setViews(0);
        if (video.getActive() == null) video.setActive(true);
        dao.insert(video);
    }

    @Override
    public void update(Videos_24162121 video) {
        dao.update(video);
    }

    @Override
    public void delete(String videoId) {
        dao.delete(videoId);
    }

    @Override
    public long countShares(String videoId) {
        return dao.countShares(videoId);
    }

    @Override
    public long countLikes(String videoId) {
        return dao.countLikes(videoId);
    }

    @Override
    public PageResult_24162121<Videos_24162121> findPageByCategory(int categoryId, int page, int size) {
        long total = dao.countByCategory(categoryId);
        int totalPages = (int) Math.ceil((double) total / size);
        if (page > totalPages) page = totalPages;
        if (page < 1) page = 1;
        List<Videos_24162121> items = total == 0
                ? new java.util.ArrayList<>()
                : dao.findPageByCategory(categoryId, page, size);
        return new PageResult_24162121<>(items, page, size, total);
    }

    @Override
    public Map<Integer, Long> countGroupByCategory() {
        return dao.countGroupByCategory();
    }
}
