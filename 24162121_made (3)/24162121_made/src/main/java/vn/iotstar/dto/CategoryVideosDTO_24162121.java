package vn.iotstar.dto;

import java.util.Map;
import vn.iotstar.entity.Category_24162121;
import vn.iotstar.entity.Videos_24162121;
import vn.iotstar.util.PageResult_24162121;

/** Dữ liệu 1 khối "Category" trên trang Home: tên + số video (Câu 5) + video của trang hiện tại (Câu 4). */
public class CategoryVideosDTO_24162121 {
    private final Category_24162121 category;
    private final long videoCount;                              // Câu 5: Category Name (20)
    private final PageResult_24162121<Videos_24162121> videos;  // Câu 4: 3 video / trang
    private final Map<String, Long> shareCounts;                // key = VideoId
    private final Map<String, Long> likeCounts;                 // key = VideoId
    private final String otherParams;                           // trang hiện tại của các category khác (để giữ nguyên khi chuyển trang)

    public CategoryVideosDTO_24162121(Category_24162121 category, long videoCount,
            PageResult_24162121<Videos_24162121> videos, Map<String, Long> shareCounts,
            Map<String, Long> likeCounts, String otherParams) {
        this.category = category;
        this.videoCount = videoCount;
        this.videos = videos;
        this.shareCounts = shareCounts;
        this.likeCounts = likeCounts;
        this.otherParams = otherParams;
    }

    public Category_24162121 getCategory() { return category; }
    public long getVideoCount() { return videoCount; }
    public PageResult_24162121<Videos_24162121> getVideos() { return videos; }
    public Map<String, Long> getShareCounts() { return shareCounts; }
    public Map<String, Long> getLikeCounts() { return likeCounts; }
    public String getOtherParams() { return otherParams; }
}
