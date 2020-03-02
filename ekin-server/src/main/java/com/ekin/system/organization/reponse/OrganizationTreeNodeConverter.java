package com.ekin.system.organization.reponse;

import com.cartisan.dtos.Converter;
import com.ekin.system.organization.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface OrganizationTreeNodeConverter extends Converter<Organization, OrganizationTreeNode> {
    OrganizationTreeNodeConverter CONVERTER = Mappers.getMapper(OrganizationTreeNodeConverter.class);

}