package wlh.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * path request object definition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePathRequest {

    private List<PathInfo> pathInfos;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PathInfo {

        /** path pattern */
        private String pathPattern;

        /** HTTP method */
        private String httpMethod;

        /** path name */
        private String pathName;

        /** service name */
        private String serviceName;

        /** operation mode: READ, WRITE */
        private String operationMode;
    }
}
