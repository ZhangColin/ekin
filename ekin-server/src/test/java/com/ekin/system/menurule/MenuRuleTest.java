//package com.ekin.system.menurule;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static com.ekin.system.menurule.MenuFixture.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class MenuRuleTest {
//    private MenuRule menuRule;
//
//    @Before
//    public void setUp() {
//        menuRule = menuOf();
//    }
//
//    @Test
//    public void should_create_menu_success() {
//        assertThat(menuRule.getParentId()).isEqualTo(PARENT_ID);
//        assertThat(menuRule.getTitle()).isEqualTo(TITLE);
//        assertThat(menuRule.getIcon()).isEqualTo(ICON);
//        assertThat(menuRule.getHidden()).isEqualTo(HIDDEN);
//        assertThat(menuRule.getLevel()).isEqualTo(LEVEL);
//        assertThat(menuRule.getSort()).isEqualTo(SORT);
//    }
//
//    @Test
//    public void should_describe_menu_success() {
//        // when
//        menuRule.change(PARENT_ID + 1, TITLE + 1, NAME + 1, ICON + 1,
//                !HIDDEN, LEVEL + 1, SORT + 1);
//
//        // then
//        assertThat(menuRule.getParentId()).isEqualTo(PARENT_ID + 1);
//        assertThat(menuRule.getTitle()).isEqualTo(TITLE + 1);
//        assertThat(menuRule.getIcon()).isEqualTo(ICON + 1);
//        assertThat(menuRule.getHidden()).isEqualTo(!HIDDEN);
//        assertThat(menuRule.getLevel()).isEqualTo(LEVEL + 1);
//        assertThat(menuRule.getSort()).isEqualTo(SORT + 1);
//    }
//}