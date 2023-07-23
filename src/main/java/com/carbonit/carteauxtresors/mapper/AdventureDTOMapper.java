package com.carbonit.carteauxtresors.mapper;

import com.carbonit.carteauxtresors.dto.AdventureDTO;
import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.entity.Treasure;
import com.carbonit.carteauxtresors.enums.Action;
import com.carbonit.carteauxtresors.enums.Orientation;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

public class AdventureDTOMapper {
    public static AdventureDTO mapAdventureDTO(AdventureDTO adventureDTO, List<String> infos) {
        if (Objects.equals(infos.get(0), "C")) {
            adventureDTO = mapMapDimensionsFromInfos(adventureDTO, infos);
        }
        if (Objects.equals(infos.get(0), "M")) {
            adventureDTO = mapMountainFromInfos(adventureDTO, infos);
        }
        if (Objects.equals(infos.get(0), "T")) {
            adventureDTO = mapTreasuresFromInfos(adventureDTO, infos);
        }
        if (Objects.equals(infos.get(0), "A")) {
            adventureDTO = mapAdventurersFromInfos(adventureDTO, infos);
        }
        return adventureDTO;
    }
    public static AdventureDTO mapMountainFromInfos(AdventureDTO adventureDTO, List<String> infos) {
        List<Position> mountains = adventureDTO.getMountains() == null ? new ArrayList<>() : adventureDTO.getMountains();
        Position mountain = Position.builder()
                .x(parseLong(infos.get(1)))
                .y(parseLong(infos.get(2)))
                .build();
        mountains.add(mountain);

        return adventureDTO.toBuilder()
                .mountains(mountains)
                .build();
    }

    public static AdventureDTO mapTreasuresFromInfos(AdventureDTO adventureDTO, List<String> infos) {
        List<Treasure> treasures = adventureDTO.getTreasures() == null ? new ArrayList<>() : adventureDTO.getTreasures();
        Position treasurePosition = Position.builder()
                .x(parseLong(infos.get(1)))
                .y(parseLong(infos.get(2)))
                .build();
        Treasure treasure = Treasure.builder()
                .position(treasurePosition)
                .number(parseLong(infos.get(3)))
                .build();
        treasures.add(treasure);

        return adventureDTO.toBuilder()
                .treasures(treasures)
                .build();
    }

    public static AdventureDTO mapAdventurersFromInfos(AdventureDTO adventureDTO, List<String> infos) {
        List<Adventurer> adventurers = adventureDTO.getAdventurers() == null ? new ArrayList<>() : adventureDTO.getAdventurers();
        Position adventurerInitialPosition = Position.builder()
                .x(parseLong(infos.get(2)))
                .y(parseLong(infos.get(3)))
                .build();
        Orientation adventurerInitialOrientation = Enum.valueOf(Orientation.class, infos.get(4));
        Adventurer adventurer = Adventurer.builder()
                .position(Pair.of(adventurerInitialPosition, adventurerInitialOrientation))
                .id((long) adventurers.size() + 1)
                .name(infos.get(1))
                .actions(mapStringToListOfActions(infos.get(5)))
                .numberOfCollectedTreasures(0L)
                .build();
        adventurers.add(adventurer);

        return adventureDTO.toBuilder()
                .adventurers(adventurers)
                .build();
    }

    public static AdventureDTO mapMapDimensionsFromInfos(AdventureDTO adventureDTO, List<String> infos) {
        if(infos.size() == 3) {
            return adventureDTO.toBuilder()
                    .mapDimensions(Position.builder().x(parseLong(infos.get(1))).y(parseLong(infos.get(2))).build())
                    .build();
        }
        throw new IllegalArgumentException(String.format("Map dimensions are not correctly initialized %s", String.join(" - ", infos)));
    }

    public static List<Action> mapStringToListOfActions(String stringActions) {
        List<Action> actions = new ArrayList<>();
        char[] characters = stringActions.toCharArray();
        for(char character : characters) {
            actions.add(Enum.valueOf(Action.class, String.valueOf(character)));
        }
        return actions;
    }
}
