//package com.ekin.system.menurule;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.data.domain.Sort;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.ekin.system.menurule.MenuFixture.menuOf;
//import static com.ekin.system.menurule.MenuFixture.menuParamOf;
//import static java.util.Collections.singletonList;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//public class MenuRuleAppServiceTest {
//    private MenuRuleRepository repository;
//    private MenuRuleAppService service;
//    private MenuRuleParam menuRuleParam;
//    private MenuRule menuRule;
//
//    @Before
//    public void setUp() {
//        repository = mock(MenuRuleRepository.class);
//        service = new MenuRuleAppService(repository);
//
//        menuRuleParam = menuParamOf();
//        menuRule = menuOf();
//    }
//
//    @Test
//    public void should_get_menu_tree_list() {
//        // given
//        when(repository.findAll(any(Sort.class))).thenReturn(singletonList(menuRule));
//
//        // when
//        final List<MenuRuleDto> menuRuleDtos = service.getMenuRuleTreeList();
//
//        // then
//        assertThat(menuRuleDtos.size()).isEqualTo(1);
//        assertThat(menuRuleDtos.get(0).getName()).isEqualTo(menuRule.getName());
//    }
//
//    @Test
//    public void should_add_success() {
//        // when
//        service.addMenuRule(menuRuleParam);
//
//        // then
//        verify(repository).save(any(MenuRule.class));
//    }
//
//    @Test
//    public void should_edit_success() {
//        // given
//        when(repository.findById(anyLong())).thenReturn(Optional.of(menuRule));
//
//        // when
//        service.editMenuRule(1L, menuRuleParam);
//
//        // then
//        verify(repository).save(any(MenuRule.class));
//    }
//
//    @Test
//    public void should_remove_success() {
//        // when
//        service.removeMenuRule(1L);
//
//        // then
//        verify(repository).deleteById(eq(1L));
//    }
//}