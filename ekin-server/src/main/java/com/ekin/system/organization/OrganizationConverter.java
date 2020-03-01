package com.ekin.system.organization;

import com.cartisan.dtos.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface OrganizationConverter extends Converter<Organization, OrganizationDto> {
    OrganizationConverter CONVERTER = Mappers.getMapper(OrganizationConverter.class);

}