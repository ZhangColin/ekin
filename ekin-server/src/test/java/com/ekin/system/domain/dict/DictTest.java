package com.ekin.system.domain.dict;


import com.cartisan.exceptions.CartisanException;
import com.ekin.system.repository.DictRepository;
import com.ekin.system.appservice.dict.DictAppService;
import com.ekin.system.appservice.dict.request.DictParam;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DictTest {
    @Test(expected = CartisanException.class)
    public void should_be_dict_code_unique() {
        // given
        final DictRepository dictRepository = mock(DictRepository.class);
        when(dictRepository.existsByCode(anyString())).thenReturn(true);

        final DictAppService dictAppService = new DictAppService(dictRepository, null, null);

        final DictParam dictParam = new DictParam();
        dictParam.setCode("test");

        // when
        dictAppService.addDict(dictParam);
    }

    @Test
    public void should_add_and_update_dict_item() {
        final Dict sexDict = new Dict("性别", "sex");

        sexDict.submitItem("男", "1", 10);
        assertThat(sexDict.getDictItems().size()).isEqualTo(1);

        sexDict.submitItem("男", "1", 20);
        assertThat(sexDict.getDictItems().size()).isEqualTo(1);
        assertThat(sexDict.getDictItems().get(0).getSort()).isEqualTo(20);

        sexDict.removeItem("1");
        assertThat(sexDict.getDictItems().size()).isEqualTo(0);
    }
}