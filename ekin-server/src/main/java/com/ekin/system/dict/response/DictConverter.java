package com.ekin.system.dict.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.dict.domain.Dict;
import org.mapstruct.Mapper;

/**
 * @author colin
 */
@Mapper(componentModel = "spring")
public interface DictConverter extends Converter<Dict, DictDto> {
}
