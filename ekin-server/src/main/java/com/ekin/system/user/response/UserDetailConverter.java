package com.ekin.system.user.response;

import com.cartisan.dto.Converter;
import com.ekin.system.user.domain.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface UserDetailConverter extends Converter<User, UserDetailDto>{
    UserDetailConverter CONVERTER = Mappers.getMapper(UserDetailConverter.class);

    @Override
    @InheritConfiguration
//    @Mapping(source="roles", target="roles")
//    @Mapping(source="organizations", target="organization")
    UserDetailDto convert(User user);

//    default List<RoleDto> userRoles2RoleDtos(List<UserRole> userRoles){
//        final RoleAppService roleAppService =
//                CartisanContext.getBean(RoleAppService.class);
//
//        final List<String> roleIds = userRoles.stream()
//                .map(userRole -> userRole.getRoleId().toString()).collect(toList());
//        return roleAppService.getAllEnableRoles().stream()
//                .filter(roleDto -> roleIds.contains(roleDto.getId()))
//                .collect(toList());
//    }
//
//    default OrganizationDto userOrganization2OrganizationDto(List<UserOrganization> userOrganizations){
//        final OrganizationAppService organizationAppService =
//                CartisanContext.getBean(OrganizationAppService.class);
//
//        final List<String> organizationIds = userOrganizations.stream()
//                .map(userOrganization -> userOrganization.getOrganizationId().toString()).collect(toList());
//        return organizationAppService.getAllOrganizations().stream()
//                .filter(organizationDto -> organizationIds.contains(organizationDto.getId().toString()))
//                .findFirst()
//                .orElse(null);
//    }
}
