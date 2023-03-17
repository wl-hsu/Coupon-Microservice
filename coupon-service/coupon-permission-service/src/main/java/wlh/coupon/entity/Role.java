package wlh.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * user role entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon_role")
public class Role {

    /** primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /** role name */
    @Basic
    @Column(name = "role_name", nullable = false)
    private String roleName;

    /** role tag */
    @Basic
    @Column(name = "role_tag", nullable = false)
    private String roleTag;
}
