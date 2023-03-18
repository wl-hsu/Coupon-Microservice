package wlh.coupon;

import wlh.coupon.permission.PermissionClient;
import wlh.coupon.vo.PermissionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * Permission detection listener,
 * run automatically after the Spring container starts
 */
@Slf4j
@Component
public class PermissionDetectListener implements
        ApplicationListener<ApplicationReadyEvent> {

    private static final String KEY_SERVER_CTX = "server.servlet.context-path";
    private static final String KEY_SERVICE_NAME = "spring.application.name";

    @Override
    @SuppressWarnings("all")
    public void onApplicationEvent(ApplicationReadyEvent event) {

        ApplicationContext ctx = event.getApplicationContext();

        new Thread(() -> {

            // Scan permissions (annotations)
            List<PermissionInfo> infoList = scanPermission(ctx);

            // Registration authority
            registerPermission(infoList, ctx);

        }).start();
    }

    /**
     * register permission
     * */
    @SuppressWarnings("all")
    private void registerPermission(List<PermissionInfo> infoList,
                                    ApplicationContext ctx) {

        log.info("*************** register permission ***************");

        PermissionClient permissionClient = ctx.getBean(PermissionClient.class);
        if (null == permissionClient) {
            log.error("no permissionClient bean found");
            return;
        }

        // take service name
        String servName = ctx.getEnvironment().getProperty(KEY_SERVICE_NAME);

        log.info("serviceName: {}", servName);

        boolean result = new PermissionRegistry(
                permissionClient, servName
        ).register(infoList);

        if (result) {
            log.info("*************** done register ***************");
        }
    }

    /**
     * Scan the Controller permission information in the microservice
     * */
    private List<PermissionInfo> scanPermission(ApplicationContext ctx) {

        // Take out the context prefix
        String pathPrefix = ctx.getEnvironment().getProperty(KEY_SERVER_CTX);

        // Take out Spring's mapping bean
        RequestMappingHandlerMapping mappingBean =
                (RequestMappingHandlerMapping)
                        ctx.getBean("requestMappingHandlerMapping");

        // scan permissions
        List<PermissionInfo> permissionInfoList =
                new AnnotationScanner(pathPrefix).scanPermission(
                        mappingBean.getHandlerMethods()
                );

        permissionInfoList.forEach(p -> log.info("{}", p));
        log.info("{} permission found", permissionInfoList.size());
        log.info("*************** done scanning ***************");

        return permissionInfoList;
    }
}
