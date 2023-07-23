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

import static com.carbonit.carteauxtresors.util.PositionUtil.isPositionOutOfBounds;
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
        if(infos.size() != 3) {
            throw new IllegalArgumentException(String.format("Mountain is not correctly initialized : %s", String.join(" - ", infos)));
        }
        List<Position> mountains = adventureDTO.getMountains() == null ? new ArrayList<>() : new ArrayList<>(adventureDTO.getMountains());
        Position mountain = Position.builder()
                .x(parseLong(infos.get(1)))
                .y(parseLong(infos.get(2)))
                .build();

        if(isPositionOutOfBounds(adventureDTO.getMapDimensions(), mountain)) {
            throw new IllegalArgumentException(String.format("Mountain's position is out of bounds : %s", String.join(" - ", infos)));
        }

        mountains.add(mountain);

        return adventureDTO.toBuilder()
                .mountains(mountains)
                .build();
    }

    public static AdventureDTO mapTreasuresFromInfos(AdventureDTO adventureDTO, List<String> infos) {
        if(infos.size() != 4) {
            throw new IllegalArgumentException(String.format("Treasure is not correctly initialized : %s", String.join(" - ", infos)));
        }
        List<Treasure> treasures = adventureDTO.getTreasures() == null ? new ArrayList<>() : new ArrayList<>(adventureDTO.getTreasures());
        Position treasurePosition = Position.builder()
                .x(parseLong(infos.get(1)))
                .y(parseLong(infos.get(2)))
                .build();

        if(isPositionOutOfBounds(adventureDTO.getMapDimensions(), treasurePosition)) {
            throw new IllegalArgumentException(String.format("Treasure's position is out of bounds : %s", String.join(" - ", infos)));
        }

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
        if(infos.size() != 6) {
            throw new IllegalArgumentException(String.format("Adventurer is not correctly initialized : %s", String.join(" - ", infos)));
        }
        List<Adventurer> adventurers = adventureDTO.getAdventurers() == null ? new ArrayList<>() : new ArrayList<>(adventureDTO.getAdventurers());
        Position adventurerInitialPosition = Position.builder()
                .x(parseLong(infos.get(2)))
                .y(parseLong(infos.get(3)))
                .build();

        if(isPositionOutOfBounds(adventureDTO.getMapDimensions(), adventurerInitialPosition)) {
            throw new IllegalArgumentException(String.format("Adventurer's position is out of bounds : %s", String.join(" - ", infos)));
        }

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

    public static List<String> mapAdventureDTOToString(AdventureDTO adventureDTO) {
        List<String> results = new ArrayList<>();
        results.add(String.format("C - %d - %d", adventureDTO.getMapDimensions().getX(), adventureDTO.getMapDimensions().getY()));
        adventureDTO.getMountains().forEach(mountain -> {
            results.add(String.format("M - %d - %d", mountain.getX(), mountain.getY()));
        });
        adventureDTO.getTreasures().forEach(treasure -> {
            if(treasure.getNumber() > 0) {
                results.add(String.format("T - %d - %d - %d", treasure.getPosition().getX(), treasure.getPosition().getY(), treasure.getNumber()));
            }
        });
        adventureDTO.getAdventurers().forEach(adventurer -> {
            results.add(String.format("A - %s - %d - %d - %s - %d",
                    adventurer.getName(),
                    adventurer.getPosition().getFirst().getX(),
                    adventurer.getPosition().getFirst().getY(),
                    adventurer.getPosition().getSecond().name(),
                    adventurer.getNumberOfCollectedTreasures()));
        });
        return results;
    }
}
