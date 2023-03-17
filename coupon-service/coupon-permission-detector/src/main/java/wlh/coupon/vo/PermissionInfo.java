package wlh.coupon.vo;

import lombok.Data;

/**
 * When our permission detector detect controller,
 * it will create permission info object.
 */
@Data
public class PermissionInfo {

    /** Controller  URL */
    private String url;

    /** method type */
    private String method;

    /** is read ? */
    private Boolean isRead;

    /** method description */
    private String description;

    /** extra attribute */
    private String extra;

    @Override
    public String toString() {

        return "url = " + url
                + ", method = " + method
                + ", isRead = " + isRead
                + ", description = " + description;
    }
}
