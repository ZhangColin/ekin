package com.ekin.system.dict;

import com.cartisan.exceptions.CartisanException;
import com.ekin.system.dict.domain.Dict;
import com.ekin.system.dict.request.DictParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class DictAppServiceTest {
    private DictRepository repository;
    private DictAppService service;
    private DictParam dictParam;
    private Dict dict;

    @Before
    public void setUp() {
        repository = mock(DictRepository.class);
        service = new DictAppService(repository);

        dictParam = new DictParam();
        dictParam.setName("性别");
        dictParam.setCode("gender");
        dictParam.setDescription("性别描述");

        dict = new Dict(dictParam.getCode());
        dict.describe(dictParam.getName(), dictParam.getDescription());
    }

    @Test
    public void when_add_code_already_exist_then_throw_exception() {
        // given
        when(repository.existsByCode(anyString())).thenReturn(true);

        // then
        assertThatThrownBy(() -> service.addDict(dictParam))
                .isInstanceOf(CartisanException.class)
                .hasMessage(DictAppService.ERR_CODE_EXISTS);
    }

    @Test
    public void should_add_success() {
        // given
        when(repository.existsByCode(anyString())).thenReturn(false);

        // when
        service.addDict(dictParam);

        // then
        verify(repository).save(any(Dict.class));
    }

    @Test
    public void when_edit_code_already_exist_then_throw_exception() {
        // given
        when(repository.existsByCodeAndIdNot(anyString(), anyLong())).thenReturn(true);

        // then
        assertThatThrownBy(() -> service.editDict(1L, dictParam))
                .isInstanceOf(CartisanException.class)
                .hasMessage(DictAppService.ERR_CODE_EXISTS);
    }

    @Test
    public void should_edit_success() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.of(dict));
        when(repository.existsByCodeAndIdNot(anyString(), anyLong())).thenReturn(false);

        // when
        service.editDict(1L, dictParam);

        // then
        verify(repository).save(any(Dict.class));
    }

    @Test
    public void should_remove_success() {
        // when
        service.removeDict(1L);

        // then
        verify(repository).deleteById(eq(1L));
    }
}