package com.elraj.palaeotrek.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HikeMapperTest {

    private HikeMapper hikeMapper;

    @BeforeEach
    public void setUp() {
        hikeMapper = new HikeMapperImpl();
    }
}
