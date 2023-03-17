package wlh.coupon.service;

import wlh.coupon.constant.RoleEnum;
import wlh.coupon.dao.PathRepository;
import wlh.coupon.dao.RolePathMappingRepository;
import wlh.coupon.dao.RoleRepository;
import wlh.coupon.dao.UserRoleMappingRepository;
import wlh.coupon.entity.Path;
import wlh.coupon.entity.Role;
import wlh.coupon.entity.RolePathMapping;
import wlh.coupon.entity.UserRoleMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * authority verification
 */
@Slf4j
@Service
public class PermissionService {

    private final PathRepository pathRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;
    private final RolePathMappingRepository rolePathMappingRepository;

    @Autowired
    public PermissionService(PathRepository pathRepository,
                             RoleRepository roleRepository,
                             UserRoleMappingRepository userRoleMappingRepository,
                             RolePathMappingRepository rolePathMappingRepository) {
        this.pathRepository = pathRepository;
        this.roleRepository = roleRepository;
        this.userRoleMappingRepository = userRoleMappingRepository;
        this.rolePathMappingRepository = rolePathMappingRepository;
    }

    /**
     * User access permission verification
     * @param userId user id
     * @param uri access uri
     * @param httpMethod request type
     * @return true/false
     * */
    public Boolean checkPermission(Long userId, String uri, String httpMethod) {

        UserRoleMapping userRoleMapping = userRoleMappingRepository
                .findByUserId(userId);

        // If the user role mapping table cannot find a record,
        // directly return false
        if (null == userRoleMapping) {
            log.error("userId not exist is UserRoleMapping: {}", userId);
            return false;
        }

        // If the corresponding Role record cannot be found,
        // return false.
        Optional<Role> role = roleRepository.findById(userRoleMapping.getRoleId());
        if (!role.isPresent()) {
            log.error("roleId not exist in Role: {}",
                    userRoleMapping.getRoleId());
            return false;
        }

        // If the user role is a super administrator,
        // return true.
        if (role.get().getRoleTag().equals(RoleEnum.SUPER_ADMIN.name())) {
            return true;
        }

        // If the path is not registered (ignored), return true.
        Path path = pathRepository.findByPathPatternAndHttpMethod(
                uri, httpMethod
        );
        if (null == path) {
            return true;
        }

        RolePathMapping rolePathMapping = rolePathMappingRepository
                .findByRoleIdAndPathId(
                        role.get().getId(), path.getId()
                );

        return rolePathMapping != null;
    }
}
