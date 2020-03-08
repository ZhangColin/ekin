package com.ekin.system.dict.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.dict.domain.Dict;
import com.ekin.system.organization.reponse.OrganizationConverter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface DictConverter extends Converter<Dict, DictDto> {
    DictConverter CONVERTER = Mappers.getMapper(DictConverter.class);
}
