package wlh.coupon.dao;

import wlh.coupon.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Path Dao
 */
public interface PathRepository extends JpaRepository<Path, Integer> {

    /**
     * Find the path record based on the service name
     * */
    List<Path> findAllByServiceName(String serviceName);

    /**
     * Find data records based on path pattern + request type
     * */
    Path findByPathPatternAndHttpMethod(String pathPattern, String httpMethod);
}
