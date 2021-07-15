package com.kyanite.dingtalk.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.dingtalk.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrivateDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrivateData.class);
        PrivateData privateData1 = new PrivateData();
        privateData1.setId(1L);
        PrivateData privateData2 = new PrivateData();
        privateData2.setId(privateData1.getId());
        assertThat(privateData1).isEqualTo(privateData2);
        privateData2.setId(2L);
        assertThat(privateData1).isNotEqualTo(privateData2);
        privateData1.setId(null);
        assertThat(privateData1).isNotEqualTo(privateData2);
    }
}
