package com.ekin.system.appservice.dict.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.domain.dict.DictItem;
import org.mapstruct.Mapper;

/**
 * @author colin
 */
@Mapper(componentModel = "spring")
public interface DictItemConverter extends Converter<DictItem, DictItemDto> {
}
