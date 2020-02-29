package com.ekin.system.menu;

public class MenuFixture {
    public static final Long PARENT_ID = 0L;
    public static final String TITLE = "系统管理";
    public static final String NAME = "system";
    public static final String ICON = "";
    public static final Boolean HIDDEN = false;
    public static final Integer LEVEL = 1;
    public static final Integer SORT = 10;

    public static Menu menuOf() {
        return new Menu(PARENT_ID, TITLE, NAME, ICON, HIDDEN, LEVEL, SORT);
    }

    public static MenuParam menuParamOf() {
        final MenuParam menuParam = new MenuParam();
        menuParam.setParentId(PARENT_ID);
        menuParam.setTitle(TITLE);
        menuParam.setName(NAME);
        menuParam.setIcon(ICON);
        menuParam.setHidden(HIDDEN);
        menuParam.setSort(SORT);
        return menuParam;
    }

    public static MenuDto menuDtoOf() {
        final MenuDto menuDto = new MenuDto();
        menuDto.setId("1");
        menuDto.setParentId(PARENT_ID.toString());
        menuDto.setTitle(TITLE);
        menuDto.setName(NAME);
        menuDto.setIcon(ICON);
        menuDto.setHidden(HIDDEN);
        menuDto.setSort(SORT);

        return menuDto;
    }
}
