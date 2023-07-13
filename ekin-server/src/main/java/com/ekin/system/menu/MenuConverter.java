package com.ekin.system.menu;

import com.cartisan.dto.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface MenuConverter extends Converter<Menu, MenuDto> {
    MenuConverter CONVERTER = Mappers.getMapper(MenuConverter.class);

}