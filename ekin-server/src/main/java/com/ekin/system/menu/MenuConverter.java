package com.ekin.system.menu;

import com.cartisan.CartisanContext;
import com.cartisan.dtos.Converter;
import com.ekin.system.resource.application.ResourceCategoryAppService;
import com.ekin.system.resource.domain.Resource;
import com.ekin.system.resource.response.ResourceCategoryDto;
import com.ekin.system.resource.response.ResourceDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Mapper
public interface MenuConverter extends Converter<Menu, MenuDto> {
    MenuConverter CONVERTER = Mappers.getMapper(MenuConverter.class);

}