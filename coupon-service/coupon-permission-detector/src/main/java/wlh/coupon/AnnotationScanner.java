package wlh.coupon;

import wlh.coupon.annotation.IgnorePermission;
import wlh.coupon.annotation.CouponPermission;
import wlh.coupon.vo.PermissionInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;
import java.util.*;

/**
 * permission information scanner
 */
@Slf4j
public class AnnotationScanner {

    private String pathPrefix;

    private static final String COUPON_PKG = "wlh.coupon";

    AnnotationScanner(String prefix) {
        this.pathPrefix = trimPath(prefix);
    }

    /**
     * Construct permission information of all Controllers
     * */
    List<PermissionInfo> scanPermission(
            Map<RequestMappingInfo, HandlerMethod> mappingMap
    ) {

        List<PermissionInfo> result = new ArrayList<>();
        mappingMap.forEach((mapInfo, method) ->
                result.addAll(buildPermission(mapInfo, method)));

        return result;
    }

    /**
     * Construct the permission information of the Controller
     * @param mapInfo {@link RequestMappingInfo} @RequestMapping correspond information
     * @param handlerMethod {@link HandlerMethod} @RequestMapping
     *                                           Details of the corresponding method,
     *                                           including method, class, parameter
     * */
    private List<PermissionInfo> buildPermission(
            RequestMappingInfo mapInfo, HandlerMethod handlerMethod
    ) {

        Method javaMethod = handlerMethod.getMethod();
        Class baseClass = javaMethod.getDeclaringClass();

        // Ignore mappings not under wlh.coupon
        if (!isCouponPackage(baseClass.getName())) {
            log.debug("ignore method: {}", javaMethod.getName());
            return Collections.emptyList();
        }

        // Determine whether this method needs to be ignored
        IgnorePermission ignorePermission = javaMethod.getAnnotation(
                IgnorePermission.class
        );
        if (null != ignorePermission) {
            log.debug("ignore method: {}", javaMethod.getName());
            return Collections.emptyList();
        }

        // Take permission annotation
        CouponPermission couponPermission = javaMethod.getAnnotation(
                CouponPermission.class
        );
        if (null == couponPermission) {
            // if there is no CouponPermission and no IgnorePermission, record it
            log.error("lack @CouponPermission -> {}#{}",
                    javaMethod.getDeclaringClass().getName(),
                    javaMethod.getName());
            return Collections.emptyList();
        }

        // take URL
        Set<String> urlSet = mapInfo.getPatternsCondition().getPatterns();

        // take method
        boolean isAllMethods = false;
        Set<RequestMethod> methodSet = mapInfo.getMethodsCondition().getMethods();
        if (CollectionUtils.isEmpty(methodSet)) {
            isAllMethods = true;
        }

        List<PermissionInfo> infoList = new ArrayList<>();

        for (String url : urlSet) {

            // support all http methods
            if (isAllMethods) {
                PermissionInfo info = buildPermissionInfo(
                        HttpMethodEnum.ALL.name(),
                        javaMethod.getName(),
                        this.pathPrefix + url,
                        couponPermission.readOnly(),
                        couponPermission.description(),
                        couponPermission.extra()
                );
                infoList.add(info);
                continue;
            }

            // support partial http methods
            for (RequestMethod method : methodSet) {
                PermissionInfo info = buildPermissionInfo(
                        method.name(),
                        javaMethod.getName(),
                        this.pathPrefix + url,
                        couponPermission.readOnly(),
                        couponPermission.description(),
                        couponPermission.extra()
                );
                infoList.add(info);
                log.info("permission detected: {}", info);
            }
        }

        return infoList;
    }

    /**
     * construct single api
     * */
    private PermissionInfo buildPermissionInfo(
            String reqMethod, String javaMethod, String path,
            boolean readOnly, String desp, String extra
    ) {

        PermissionInfo info = new PermissionInfo();
        info.setMethod(reqMethod);
        info.setUrl(path);
        info.setIsRead(readOnly);
        info.setDescription(
                // If there is no description in the annotation, use method name
                StringUtils.isEmpty(desp) ? javaMethod : desp
        );
        info.setExtra(extra);

        return info;
    }

    /**
     * Determine whether the current class is in the package we defined
     * */
    private boolean isCouponPackage(String className) {

        return className.startsWith(COUPON_PKG);
    }

    /**
     * ensure path start with /, and not use / as the end
     * if user -> /user, /user/ -> /user
     * */
    private String trimPath(String path) {

        if (StringUtils.isEmpty(path)) {
            return "";
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }
}
