package com.carbonit.carteauxtresors.mapper;

import com.carbonit.carteauxtresors.dto.AdventureDTO;
import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.entity.Treasure;
import com.carbonit.carteauxtresors.enums.Action;
import com.carbonit.carteauxtresors.enums.Orientation;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AdventureDTOMapperTest {

    @Test
    public void given_ListStringCorrectlyFormattedWithUnknownObject_when_mapAdventureDTO_return_AdventureDTO() {
        List<String> infos = Arrays.asList("R", "3", "4");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapAdventureDTO(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures()).isNull();
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers()).isNull();
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains()).isNull();
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMapDimensions()).isNull();
    }

    @Test
    public void given_ListStringCorrectlyFormattedMapDimensions_when_mapAdventureDTO_return_AdventureDTO() {
        List<String> infos = Arrays.asList("C", "3", "4");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapAdventureDTO(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMapDimensions().getX()).isEqualTo(3L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMapDimensions().getY()).isEqualTo(4L);
    }

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
    public void given_ElementInListStringNotCorrectlyFormatted_when_mapMapDimensionsFromInfos_return_IllegalArgumentException() {
        List<String> infos = Arrays.asList("C", "test", "4");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapMapDimensionsFromInfos(adventureDTO, infos);
    }

    @Test
    public void given_ListStringCorrectlyFormattedWithAdventurerInfos_when_mapAdventureDTO_return_AdventureDTO() {
        List<String> infos = Arrays.asList("A", "Jim", "5", "4", "O", "ADA");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapAdventureDTO(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().size()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getId()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getName()).isEqualTo("Jim");
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getActions().size()).isEqualTo(3);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getPosition().getSecond()).isEqualTo(Orientation.O);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getPosition().getFirst().getY()).isEqualTo(4L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getPosition().getFirst().getX()).isEqualTo(5L);
    }

    @Test
    public void given_ListStringCorrectlyFormatted_when_mapAdventurersFromInfos_return_AdventureDTO() {
        List<String> infos = Arrays.asList("A", "Jim", "5", "4", "O", "ADA");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapAdventurersFromInfos(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().size()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getId()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getName()).isEqualTo("Jim");
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getActions().size()).isEqualTo(3);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getPosition().getSecond()).isEqualTo(Orientation.O);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getPosition().getFirst().getY()).isEqualTo(4L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getPosition().getFirst().getX()).isEqualTo(5L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ListStringCorrectlyFormattedButOutOfBound_when_mapAdventurersFromInfos_return_IllegalArgumentException() {
        List<String> infos = Arrays.asList("A", "Jim", "6", "4", "O", "ADA");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTOMapper.mapAdventurersFromInfos(adventureDTO, infos);
    }

    @Test
    public void given_ListStringCorrectlyFormattedWithAlreadyOneAdventurerInList_when_mapAdventurersFromInfos_return_AdventureDTO() {
        List<String> infos = Arrays.asList("A", "Jim", "5", "4", "O", "ADA");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .adventurers(Collections.singletonList(Adventurer.builder().id(1L).build()))
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapAdventurersFromInfos(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().size()).isEqualTo(2);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(0).getId()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(1).getId()).isEqualTo(2L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(1).getName()).isEqualTo("Jim");
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(1).getActions().size()).isEqualTo(3);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(1).getPosition().getSecond()).isEqualTo(Orientation.O);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(1).getPosition().getFirst().getY()).isEqualTo(4L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getAdventurers().get(1).getPosition().getFirst().getX()).isEqualTo(5L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ListStringNotCorrectlyFormatted_when_mapAdventurersFromInfos_return_IllegalArgumentException() {
        List<String> infos = List.of("A", "Jim", "5", "4", "O");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapMapDimensionsFromInfos(adventureDTO, infos);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ElementInListStringNotCorrectlyFormatted_when_mapAdventurersFromInfos_return_IllegalArgumentException() {
        List<String> infos = Arrays.asList("A", "Jim", "5", "4", "O", "test");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapMapDimensionsFromInfos(adventureDTO, infos);
    }

    @Test
    public void given_ListStringCorrectlyFormattedWithTreasureInfos_when_mapAdventureDTO_return_AdventureDTO() {
        List<String> infos = Arrays.asList("T", "1", "2", "3");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapAdventureDTO(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().size()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(0).getNumber()).isEqualTo(3L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(0).getPosition().getX()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(0).getPosition().getY()).isEqualTo(2L);
    }

    @Test
    public void given_ListStringCorrectlyFormatted_when_mapTreasuresFromInfos_return_AdventureDTO() {
        List<String> infos = Arrays.asList("T", "1", "2", "3");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapTreasuresFromInfos(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().size()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(0).getNumber()).isEqualTo(3L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(0).getPosition().getX()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(0).getPosition().getY()).isEqualTo(2L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ListStringCorrectlyFormattedButOutOfBound_when_mapTreasuresFromInfos_return_IllegalArgumentException() {
        List<String> infos = Arrays.asList("T", "3", "5", "3");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTOMapper.mapTreasuresFromInfos(adventureDTO, infos);
    }

    @Test
    public void given_ListStringCorrectlyFormattedWithAlreadyOneTreasureInList_when_mapTreasuresFromInfos_return_AdventureDTO() {
        List<String> infos = Arrays.asList("T", "1", "2", "3");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .treasures(Collections.singletonList(Treasure.builder().number(4L).build()))
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapTreasuresFromInfos(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().size()).isEqualTo(2);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(0).getNumber()).isEqualTo(4L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(1).getNumber()).isEqualTo(3L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(1).getPosition().getX()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getTreasures().get(1).getPosition().getY()).isEqualTo(2L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ListStringNotCorrectlyFormatted_when_mapTreasuresFromInfos_return_IllegalArgumentException() {
        List<String> infos = List.of("A", "Jim", "5", "4", "O");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapTreasuresFromInfos(adventureDTO, infos);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ElementInListStringNotCorrectlyFormatted_when_mapTreasuresFromInfos_return_IllegalArgumentException() {
        List<String> infos = Arrays.asList("T", "Jim", "1", "1");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapMapDimensionsFromInfos(adventureDTO, infos);
    }

    @Test
    public void given_ListStringCorrectlyFormattedWithMountainInfos_when_mapAdventureDTO_return_AdventureDTO() {
        List<String> infos = Arrays.asList("M", "1", "2");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapAdventureDTO(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().size()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().get(0).getX()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().get(0).getY()).isEqualTo(2L);
    }

    @Test
    public void given_ListStringCorrectlyFormatted_when_mapMountainsFromInfos_return_AdventureDTO() {
        List<String> infos = Arrays.asList("M", "1", "2");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapMountainFromInfos(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().size()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().get(0).getX()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().get(0).getY()).isEqualTo(2L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ListStringCorrectlyFormattedButOutOfBound_when_mapMountainFromInfos_return_IllegalArgumentException() {
        List<String> infos = Arrays.asList("M", "3", "5");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .build();
        AdventureDTOMapper.mapMountainFromInfos(adventureDTO, infos);
    }

    @Test
    public void given_ListStringCorrectlyFormattedWithAlreadyOneMountainInList_when_mapMountainFromInfos_return_AdventureDTO() {
        List<String> infos = Arrays.asList("M", "1", "2");
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(6L).y(5L).build())
                .mountains(Collections.singletonList(Position.builder().x(4L).y(4L).build()))
                .build();
        AdventureDTO updatedAdventureDTO = AdventureDTOMapper.mapMountainFromInfos(adventureDTO, infos);

        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().size()).isEqualTo(2);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().get(0).getX()).isEqualTo(4L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().get(0).getY()).isEqualTo(4L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().get(1).getX()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(updatedAdventureDTO.getMountains().get(1).getY()).isEqualTo(2L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ListStringNotCorrectlyFormatted_when_mapMountainFromInfos_return_IllegalArgumentException() {
        List<String> infos = List.of("A", "Jim", "5", "4", "O");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapTreasuresFromInfos(adventureDTO, infos);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_ElementInListStringNotCorrectlyFormatted_when_mapMountainFromInfos_return_IllegalArgumentException() {
        List<String> infos = Arrays.asList("M", "Jim", "1");
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        AdventureDTOMapper.mapMountainFromInfos(adventureDTO, infos);
    }

    @Test
    public void given_StringOfActionsCorrectlyFormatted_when_mapStringToListOfActions_return_ListOfActions() {
        String actions = "ADG";
        List<Action> newActions = AdventureDTOMapper.mapStringToListOfActions(actions);
        Assertions.assertThat(newActions).hasSize(3);
        Assertions.assertThat(newActions.get(0)).isEqualTo(Action.A);
        Assertions.assertThat(newActions.get(1)).isEqualTo(Action.D);
        Assertions.assertThat(newActions.get(2)).isEqualTo(Action.G);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_StringOfActionsNotCorrectlyFormatted_when_mapStringToListOfActions_return_IllegalArgumentException() {
        String actions = "AtestG";
        AdventureDTOMapper.mapStringToListOfActions(actions);
    }
}
