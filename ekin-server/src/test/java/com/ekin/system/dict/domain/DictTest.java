package com.ekin.system.dict.domain;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DictTest {
    @Test
    public void should_add_and_update_dict_item() {
        final Dict sexDict = new Dict("sex");

        sexDict.submitItem("男", "1", 10);
        assertThat(sexDict.getDictItems().size()).isEqualTo(1);

        sexDict.submitItem("男", "1", 20);
        assertThat(sexDict.getDictItems().size()).isEqualTo(1);
        assertThat(sexDict.getDictItems().get(0).getSort()).isEqualTo(20);

        sexDict.removeItem("1");
        assertThat(sexDict.getDictItems().size()).isEqualTo(0);
    }
}