package com.kyanite.dingtalk.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.dingtalk.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicData.class);
        PublicData publicData1 = new PublicData();
        publicData1.setId(1L);
        PublicData publicData2 = new PublicData();
        publicData2.setId(publicData1.getId());
        assertThat(publicData1).isEqualTo(publicData2);
        publicData2.setId(2L);
        assertThat(publicData1).isNotEqualTo(publicData2);
        publicData1.setId(null);
        assertThat(publicData1).isNotEqualTo(publicData2);
    }
}
