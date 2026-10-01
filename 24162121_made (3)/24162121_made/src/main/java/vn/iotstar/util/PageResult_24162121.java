package vn.iotstar.util;

import java.util.List;

/** Kết quả phân trang: danh sách của trang hiện tại + thông tin trang. */
public class PageResult_24162121<T> {
    private final List<T> items;
    private final int page;
    private final int size;
    private final long totalItems;
    private final int totalPages;

    public PageResult_24162121(List<T> items, int page, int size, long totalItems) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / size);
    }

    public List<T> getItems() { return items; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }

    /** Trang bắt đầu của thanh phân trang (hiện tối đa 5 số trang quanh trang hiện tại). */
    public int getBeginPage() {
        int end = getEndPage();
        return Math.max(1, end - 4);
    }

    public int getEndPage() {
        return Math.min(totalPages, Math.max(page + 2, 5));
    }
}
