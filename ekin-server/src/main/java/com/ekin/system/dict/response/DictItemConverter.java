package com.ekin.system.dict.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.dict.domain.DictItem;
import org.mapstruct.Mapper;

/**
 * @author colin
 */
@Mapper(componentModel = "spring")
public interface DictItemConverter extends Converter<DictItem, DictItemDto> {
}
