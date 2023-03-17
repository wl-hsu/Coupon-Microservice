package wlh.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * URL Path information entity class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon_path")
public class Path {

    /** primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /** path pattern */
    @Basic
    @Column(name = "path_pattern", nullable = false)
    private String pathPattern;

    /** HTTP method */
    @Basic
    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    /** path name */
    @Basic
    @Column(name = "path_name", nullable = false)
    private String pathName;

    /** 服务名称 */
    @Basic
    @Column(name = "service_name", nullable = false)
    private String serviceName;

    /** operationMode: READ, WRITE */
    @Basic
    @Column(name = "op_mode", nullable = false)
    private String opMode;

    /**
     * Constructor without primary key
     * Let permission detector to detect controller api of microservice
     * After detect the api, need to register path without primary key.
     * */
    public Path(String pathPattern, String httpMethod, String pathName,
                String serviceName, String opMode) {

        this.pathPattern = pathPattern;
        this.httpMethod = httpMethod;
        this.pathName = pathName;
        this.serviceName = serviceName;
        this.opMode = opMode;
    }
}
