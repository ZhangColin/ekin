package com.ekin.system.services.dict.condition;

import com.cartisan.repositories.Condition;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class DictCondition {
    @Condition(blurry = "name,code")
    private String blurry;
}
