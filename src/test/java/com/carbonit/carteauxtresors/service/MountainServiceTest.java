package com.carbonit.carteauxtresors.service;

import com.carbonit.carteauxtresors.entity.Position;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MountainServiceTest {

    @InjectMocks
    private MountainService mountainService;


    @Test
    public void given_multipleMountainsAndPositionWithinMountains_whenIsThereMountain_returnTrue() {
        Position adventurerPosition = Position.builder().x(1L).y(1L).build();
        boolean isThereMountain = mountainService.isThereMountain(getMultipleMountains(), adventurerPosition);
        AssertionsForClassTypes.assertThat(isThereMountain).isTrue();
    }

    @Test
    public void given_multipleMountainsAndPositionNotWithinMountains_whenIsThereMountain_returnFalse() {
        Position adventurerPosition = Position.builder().x(1L).y(2L).build();
        boolean isThereMountain = mountainService.isThereMountain(getMultipleMountains(), adventurerPosition);
        AssertionsForClassTypes.assertThat(isThereMountain).isFalse();
    }

    @Test
    public void given_noMountainsAndAdventurerPosition_whenIsThereMountain_returnFalse() {
        Position adventurerPosition = Position.builder().x(1L).y(2L).build();
        boolean isThereMountain = mountainService.isThereMountain(new ArrayList<>(), adventurerPosition);
        AssertionsForClassTypes.assertThat(isThereMountain).isFalse();
    }

    private List<Position> getMultipleMountains() {
        Position firstMountain = Position.builder().x(1L).y(1L).build();
        Position secondMountain = Position.builder().x(0L).y(2L).build();
        return Arrays.asList(firstMountain, secondMountain);
    }
}
