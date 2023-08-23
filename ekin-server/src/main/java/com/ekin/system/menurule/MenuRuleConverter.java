package com.ekin.system.menurule;

import com.cartisan.dto.Converter;
import com.ekin.system.user.domain.User;
import com.ekin.system.user.response.UserDetailDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface MenuRuleConverter extends Converter<MenuRule, MenuRuleDto> {
    MenuRuleConverter CONVERTER = Mappers.getMapper(MenuRuleConverter.class);

    @Override
    @InheritConfiguration
    @Mapping(target="childMenuRules", ignore = true)
    MenuRuleDto convert(MenuRule menuRule);
}