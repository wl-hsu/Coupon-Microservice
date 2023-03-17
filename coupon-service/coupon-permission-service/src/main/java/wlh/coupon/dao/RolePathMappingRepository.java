package wlh.coupon.dao;

import wlh.coupon.entity.RolePathMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>RolePathMapping Dao</h1>
 */
public interface RolePathMappingRepository
        extends JpaRepository<RolePathMapping, Integer> {

    /**
     * Find data records by role id + path id
     * */
    RolePathMapping findByRoleIdAndPathId(Integer roleId, Integer pathId);
}
