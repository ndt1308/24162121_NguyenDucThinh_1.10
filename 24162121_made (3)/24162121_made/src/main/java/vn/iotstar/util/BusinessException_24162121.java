package vn.iotstar.util;

/** Lỗi nghiệp vụ (hết hàng, vượt tồn kho...) - message hiển thị thẳng cho người dùng. */
public class BusinessException_24162121 extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public BusinessException_24162121(String message) { super(message); }
}
