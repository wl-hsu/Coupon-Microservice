package wlh.coupon.dao;

import wlh.coupon.entity.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRoleMapping Dao
 */
public interface UserRoleMappingRepository
        extends JpaRepository<UserRoleMapping, Long> {

    /**
     * use userId to find the record
     * */
    UserRoleMapping findByUserId(Long userId);
}
