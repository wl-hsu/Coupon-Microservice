package wlh.coupon.dao;

import wlh.coupon.entity.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CouponTemplate Dao interface
 */
public interface CouponTemplateDao extends JpaRepository<CouponTemplate, Integer> {

    /**
     * Query templates by template name
     * where name = ...
     * */
    CouponTemplate findByName(String name);

    /**
     * Find template records based on available and expired tags
     * where available = ... and expired = ...
     * */
    List<CouponTemplate> findAllByAvailableAndExpired(
            Boolean available, Boolean expired
    );

    /**
     * Find template records based on the expired tag
     * where expired = ...
     * */
    List<CouponTemplate> findAllByExpired(Boolean expired);
}
