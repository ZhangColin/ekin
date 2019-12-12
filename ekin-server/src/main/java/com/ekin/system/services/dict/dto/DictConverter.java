package com.ekin.system.services.dict.dto;

import com.cartisan.dtos.Converter;
import com.ekin.system.domains.dict.Dict;
import org.mapstruct.Mapper;

/**
 * @author colin
 */
@Mapper(componentModel = "spring")
public interface DictConverter extends Converter<Dict, DictDTO> {
}
