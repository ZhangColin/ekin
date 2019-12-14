package com.ekin.system.appservice.dict.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.domain.dict.Dict;
import org.mapstruct.Mapper;

/**
 * @author colin
 */
@Mapper(componentModel = "spring")
public interface DictConverter extends Converter<Dict, DictDto> {
}
