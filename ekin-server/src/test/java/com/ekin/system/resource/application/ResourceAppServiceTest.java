//package com.ekin.system.resource.application;
//
//import com.cartisan.CartisanContext;
//import com.cartisan.dto.PageResult;
//import com.cartisan.exception.CartisanException;
//import com.ekin.system.resource.domain.Resource;
//import com.ekin.system.resource.repository.ResourceRepository;
//import com.ekin.system.resource.request.ResourceParam;
//import com.ekin.system.resource.request.ResourceQuery;
//import com.ekin.system.resource.response.ResourceCategoryDto;
//import com.ekin.system.resource.response.ResourceDto;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.List;
//import java.util.Optional;
//
//import static java.util.Collections.singletonList;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//public class ResourceAppServiceTest {
//    private ResourceRepository repository;
//    private ResourceAppService service;
//    private ResourceParam resourceParam;
//    private Resource resource;
//
//    private ApplicationContext applicationContext;
//    private ResourceCategoryAppService resourceCategoryAppService;
//
//    @Before
//    public void setUp() {
//        repository = mock(ResourceRepository.class);
//        service = new ResourceAppService(repository);
//        resourceParam = new ResourceParam();
//        resourceParam.setName("用户管理");
//        resourceParam.setCategoryId(1L);
//        resourceParam.setCode("system:user");
//        resourceParam.setUrl("/system/users/**");
//        resourceParam.setDescription("用户管理描述");
//        resourceParam.setSort(10);
//
//        resource = new Resource(resourceParam.getName(), resourceParam.getCode(), resourceParam.getUrl(),
//                resourceParam.getCategoryId());
//        resource.describe(resourceParam.getName(), resourceParam.getCategoryId(),
//                resourceParam.getDescription(), resourceParam.getSort());
//    }
//
//    @Test
//    public void should_get_all_resources() {
//        // given
//        resourceCategoryAppService = mock(ResourceCategoryAppService.class);
//
//        applicationContext = mock(ApplicationContext.class);
//        when(applicationContext.getBean(ResourceCategoryAppService.class))
//                .thenReturn(resourceCategoryAppService);
//        CartisanContext.setAppContext(applicationContext);
//
//        ResourceCategoryDto resourceCategoryDto = new ResourceCategoryDto();
//        resourceCategoryDto.setId("1");
//
//        when(resourceCategoryAppService.getAllResourceCategories())
//                .thenReturn(singletonList(resourceCategoryDto));
//
//        when(repository.findAll(any(Sort.class))).thenReturn(singletonList(resource));
//
//        // when
//        final List<ResourceDto> resources = service.getAllResources();
//
//        // then
//        assertThat(resources.size()).isEqualTo(1);
//        assertThat(resources.get(0).getId()).isEqualTo(resource.getId());
//        assertThat(resources.get(0).getName()).isEqualTo(resource.getName());
//    }
//
//    @Test
//    public void should_search_resources() {
//        // given
//        resourceCategoryAppService = mock(ResourceCategoryAppService.class);
//
//        applicationContext = mock(ApplicationContext.class);
//        when(applicationContext.getBean(ResourceCategoryAppService.class))
//                .thenReturn(resourceCategoryAppService);
//        CartisanContext.setAppContext(applicationContext);
//
//        ResourceCategoryDto resourceCategoryDto = new ResourceCategoryDto();
//        resourceCategoryDto.setId("1");
//
//        when(resourceCategoryAppService.getAllResourceCategories())
//                .thenReturn(singletonList(resourceCategoryDto));
//
//        final PageRequest pageable = PageRequest.of(1, 10);
//        when(repository.findAll(any(Specification.class), any(Pageable.class)))
//                .thenReturn(new PageImpl<>(singletonList(resource), pageable, 10L));
//
//        // when
//        final PageResult<ResourceDto> resources =
//                service.searchResources(new ResourceQuery(), pageable);
//
//        // then
//        assertThat(resources.getRows().size()).isEqualTo(1);
//        assertThat(resources.getRows().get(0).getId()).isEqualTo(resource.getId());
//        assertThat(resources.getRows().get(0).getName()).isEqualTo(resource.getName());
//    }
//
//    @Test
//    public void when_add_name_already_exist_then_throw_exception() {
//        // given
//        when(repository.existsByName(anyString())).thenReturn(true);
//
//        // then
//        assertThatThrownBy(() -> service.addResource(resourceParam))
//                .isInstanceOf(CartisanException.class)
//                .hasMessage(ResourceAppService.ERR_NAME_EXISTS);
//    }
//
//    @Test
//    public void should_add_success() {
//        // given
//        when(repository.existsByName(anyString())).thenReturn(false);
//
//        // when
//        service.addResource(resourceParam);
//
//        // then
//        verify(repository).save(any(Resource.class));
//    }
//
//    @Test
//    public void when_edit_name_already_exist_then_throw_exception() {
//        // given
//        when(repository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);
//
//        // then
//        assertThatThrownBy(() -> service.editResource(1L, resourceParam))
//                .isInstanceOf(CartisanException.class)
//                .hasMessage(ResourceAppService.ERR_NAME_EXISTS);
//    }
//
//    @Test
//    public void should_edit_success() {
//        // given
//        when(repository.findById(anyLong())).thenReturn(Optional.of(resource));
//        when(repository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
//
//        // when
//        service.editResource(1L, resourceParam);
//
//        // then
//        verify(repository).save(any(Resource.class));
//    }
//
//    @Test
//    public void should_remove_success() {
//        // when
//        service.removeResource(1L);
//
//        // then
//        verify(repository).deleteById(eq(1L));
//    }
//}