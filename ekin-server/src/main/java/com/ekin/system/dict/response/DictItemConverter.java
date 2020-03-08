package com.ekin.system.dict.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.dict.domain.DictItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface DictItemConverter extends Converter<DictItem, DictItemDto> {
    DictItemConverter CONVERTER = Mappers.getMapper(DictItemConverter.class);
}
