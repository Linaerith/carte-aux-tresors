package com.carbonit.carteauxtresors.mapper;

import com.carbonit.carteauxtresors.dto.AdventureDTO;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AdventureDTOMapperTest {

    @Test
    public void given_ListStringCorrectlyFormatted_when_mapMapDimensionsFromInfos_return_AdventureDTO() {
        List<String> infos = Arrays.asList("C", "3", "4");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapMapDimensionsFromInfos(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMapDimensions().getX()).isEqualTo(3L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMapDimensions().getY()).isEqualTo(4L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ListStringNotCorrectlyFormatted_when_mapMapDimensionsFromInfos_return_IllegalArgumentException() {
        List<String> infos = List.of("C");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapMapDimensionsFromInfos(adventureDTO, infos);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ListStringNotCorrectlyFormatted_when_mapMapDimensionsFromInfos_return_AdventureDTO() {
        List<String> infos = Arrays.asList("C", "test", "4");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapMapDimensionsFromInfos(adventureDTO, infos);
    }
}
