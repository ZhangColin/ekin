package com.ekin.system.dict.request;

import com.cartisan.repositories.Condition;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class SearchDict {
    @Condition(blurry = "name,code")
    private String blurry;
}
