//package com.ekin.system.organization;
//
//import com.ekin.system.organization.reponse.OrganizationDto;
//
//public class OrganizationFixture {
//    public static final Long ID = 1L;
//    public static final Long PARENT_ID = 0L;
//    public static final String NAME = "研发中心";
//    public static final String DESCRIPTION = "研发中心描述";
//    public static final Integer SORT = 10;
//
//    public static Organization organizationOf() {
//        final Organization organization = new Organization(ID, PARENT_ID, NAME);
//        organization.describe(DESCRIPTION, SORT);
//        return organization;
//    }
//
//    public static OrganizationParam organizationParamOf() {
//        final OrganizationParam organizationParam = new OrganizationParam();
//        organizationParam.setParentId(PARENT_ID);
//        organizationParam.setName(NAME);
//        organizationParam.setDescription(DESCRIPTION);
//        organizationParam.setSort(SORT);
//        return organizationParam;
//    }
//
//    public static OrganizationDto organizationDtoOf() {
//        final OrganizationDto organizationDto = new OrganizationDto();
//        organizationDto.setId(ID);
//        organizationDto.setParentId(PARENT_ID);
//        organizationDto.setName(NAME);
//        organizationDto.setSort(SORT);
//
//        return organizationDto;
//    }
//}
