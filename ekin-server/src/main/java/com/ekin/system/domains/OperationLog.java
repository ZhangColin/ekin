package com.ekin.system.domains;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_operation_logs")
@Getter
@EqualsAndHashCode
public class OperationLog extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Convert(converter = OperationLogType.Converter.class)
    private OperationLogType type;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ip")
    private String ip;

    @Column(name = "url")
    private String url;

    @Column(name = "method")
    private String method;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "success")
    private Boolean isSuccess;

    private OperationLog() {

    }

    public OperationLog(OperationLogType type, Long userId, String ip, String url,
                        String method, String parameters, Boolean isSuccess) {
        this.type = type;
        this.userId = userId;
        this.ip = ip;
        this.url = url;
        this.method = method;
        this.parameters = parameters;
        this.isSuccess = isSuccess;
    }
}
