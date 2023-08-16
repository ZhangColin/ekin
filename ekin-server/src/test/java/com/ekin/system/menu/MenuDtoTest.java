//package com.ekin.system.menu;
//
//import org.junit.Test;
//
//import java.util.List;
//
//import static com.ekin.system.menu.MenuFixture.menuDtoOf;
//import static java.util.Arrays.asList;
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class MenuDtoTest {
//    @Test
//    public void buildMenuTreeList() {
//        // given
//        final MenuDto menuDto = menuDtoOf();
//
//        final MenuDto chileMenu = new MenuDto();
//        chileMenu.setName(menuDto.getName() + "-1");
//        chileMenu.setParentId(menuDto.getId());
//
//        // when
//        final List<MenuDto> menuTreeList = MenuDto.buildMenuTreeList(asList(menuDto, chileMenu));
//
//        // then
//        assertThat(menuTreeList.size()).isEqualTo(1);
//        assertThat(menuTreeList.get(0).getName()).isEqualTo(menuDto.getName());
//
//        assertThat(menuTreeList.get(0).getChildMenus().size()).isEqualTo(1);
//        assertThat(menuTreeList.get(0).getChildMenus().get(0).getName()).isEqualTo(menuDto.getName() + "-1");
//    }
//}