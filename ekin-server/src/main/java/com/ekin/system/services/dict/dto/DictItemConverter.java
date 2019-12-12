package com.ekin.system.services.dict.dto;

import com.cartisan.dtos.Converter;
import com.ekin.system.domains.dict.DictItem;
import org.mapstruct.Mapper;

/**
 * @author colin
 */
@Mapper(componentModel = "spring")
public interface DictItemConverter extends Converter<DictItem, DictItemDTO> {
}
