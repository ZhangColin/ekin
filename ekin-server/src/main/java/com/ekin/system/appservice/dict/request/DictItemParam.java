package com.ekin.system.appservice.dict.request;

import lombok.Data;

/**
 * @author colin
 */
@Data
public class DictItemParam {
    private String label;
    private String value;
    private Integer sort;
}
