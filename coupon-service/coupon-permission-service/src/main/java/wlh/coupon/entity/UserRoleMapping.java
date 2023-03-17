package wlh.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * User and Role mapping relationship entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon_user_role_mapping")
public class UserRoleMapping {

    /** primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /** User primary key*/
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** Role primary key*/
    @Basic
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
}
