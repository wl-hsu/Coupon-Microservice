package wlh.coupon.dao;

import wlh.coupon.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Role Dao
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
