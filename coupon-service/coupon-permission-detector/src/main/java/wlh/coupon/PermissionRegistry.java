package wlh.coupon;

import wlh.coupon.permission.PermissionClient;
import wlh.coupon.vo.CommonResponse;
import wlh.coupon.vo.CreatePathRequest;
import wlh.coupon.vo.PermissionInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authorization Registration Component
 */
@Slf4j
public class PermissionRegistry {

    /** Authorization Service SDK Client*/
    private PermissionClient permissionClient;

    /** service name */
    private String serviceName;

    /**
     * Construction method
     * */
    PermissionRegistry(PermissionClient permissionClient, String serviceName) {

        this.permissionClient = permissionClient;
        this.serviceName = serviceName;
    }

    /**
     * <Permission registration
     * */
    boolean register(List<PermissionInfo> infoList) {

        if (CollectionUtils.isEmpty(infoList)) {
            return false;
        }

        List<CreatePathRequest.PathInfo> pathInfos = infoList.stream()
                .map(info -> CreatePathRequest.PathInfo.builder()
                .pathPattern(info.getUrl())
                        .httpMethod(info.getMethod())
                        .pathName(info.getDescription())
                        .serviceName(serviceName)
                        .operationMode(info.getIsRead() ? OperationModeEnum.READ.name() :
                                OperationModeEnum.WRITE.name())
                        .build()
                ).collect(Collectors.toList());

        CommonResponse<List<Integer>> response = permissionClient.createPath(
                new CreatePathRequest(pathInfos)
        );

        if (!CollectionUtils.isEmpty(response.getData())) {
            log.info("register path info: {}", response.getData());
            return true;
        }

        return false;
    }
}
