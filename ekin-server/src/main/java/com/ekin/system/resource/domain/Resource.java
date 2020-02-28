package com.ekin.system.resource.domain;

import com.cartisan.constants.CodeMessage;
import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import com.cartisan.exceptions.CartisanException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_resources")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Resource extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "code")
    private String code;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "sort")
    private Integer sort;

    private Resource() {
    }

    public Resource(String name, String code, String url, Long categoryId) {
        this.name = name;
        this.code = ensureCode(code);
        this.url = url;
        this.categoryId = categoryId;
        this.description = "";
        this.sort = 0;
    }

    public void configPermission(String code, String url) {
        this.code = ensureCode(code);
        this.url = url;
    }

    public void describe(String name, Long categoryId, String description, Integer sort){
        this.name = name;
        this.categoryId = categoryId;
        this.description = description;
        this.sort = sort;
    }

    static final String ERR_CODE_NOT_EMPTY = "权限编码不能为空";

    private String ensureCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new CartisanException(CodeMessage.FAIL.fillArgs(ERR_CODE_NOT_EMPTY));
        }
        return code;
    }
}