package com.ekin.system.dict.domain;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DictTest {
    @Test
    public void should_add_and_update_dict_item() {
        final Dict genderDict = new Dict("gender");

        genderDict.submitItem("男", "1", 10);
        assertThat(genderDict.getDictItems().size()).isEqualTo(1);

        genderDict.submitItem("男", "1", 20);
        assertThat(genderDict.getDictItems().size()).isEqualTo(1);
        assertThat(genderDict.getDictItems().get(0).getSort()).isEqualTo(20);

        genderDict.removeItem("1");
        assertThat(genderDict.getDictItems().size()).isEqualTo(0);
    }
}