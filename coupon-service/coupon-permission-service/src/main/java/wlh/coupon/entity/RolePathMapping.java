package wlh.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Role -> path
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon_role_path_mapping")
public class RolePathMapping {

    /** primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /** Role primary key */
    @Basic
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    /** Path primary key */
    @Basic
    @Column(name = "path_id", nullable = false)
    private Integer pathId;
}
