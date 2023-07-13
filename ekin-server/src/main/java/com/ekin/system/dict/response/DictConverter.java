package com.ekin.system.dict.response;

import com.cartisan.dto.Converter;
import com.ekin.system.dict.domain.Dict;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface  DictConverter extends Converter<Dict, DictDto> {
    DictConverter CONVERTER = Mappers.getMapper(DictConverter.class);
}
