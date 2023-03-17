package wlh.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * permissions service starter
 */
@EnableEurekaClient
@SpringBootApplication
public class PermissionApplication {

    public static void main(String[] args) {

        SpringApplication.run(PermissionApplication.class, args);
    }
}
