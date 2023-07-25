package com.carbonit.carteauxtresors.service;

import com.carbonit.carteauxtresors.dto.AdventureDTO;
import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.entity.Treasure;
import com.carbonit.carteauxtresors.enums.Action;
import com.carbonit.carteauxtresors.enums.Orientation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static com.carbonit.carteauxtresors.util.PositionUtil.isSamePosition;

@Slf4j
@AllArgsConstructor
@Service
public class ActionService {

    MoveService moveService;

    public AdventureDTO handleAdventurersTurns(AdventureDTO adventureDTO) {
        while (!isGameFinished(adventureDTO)) {
            AtomicReference<AdventureDTO> tempAdventureDTO = new AtomicReference<>(adventureDTO);
            for (int i = 0; i < tempAdventureDTO.get().getAdventurers().size(); i++) {
                {
                    List<Action> adventurerActions = new ArrayList<>(tempAdventureDTO.get().getAdventurers().get(i).getActions());
                    if (!adventurerActions.isEmpty()) {
                        Pair<AdventureDTO, Adventurer> pair = executeActions(tempAdventureDTO.get(), tempAdventureDTO.get().getAdventurers().get(i), adventurerActions.get(0));
                        adventurerActions.remove(0);
                        Adventurer adventurer = pair.getSecond().toBuilder()
                                .actions(adventurerActions)
                                .build();
                        pair.getFirst().getAdventurers().set(i, adventurer);
                        tempAdventureDTO.set(
                                pair.getFirst().toBuilder()
                                        .adventurers(pair.getFirst().getAdventurers())
                                        .build()
                        );
                    }
                }
            }
            adventureDTO = tempAdventureDTO.get();
        }
        return adventureDTO;
    }

    public boolean isGameFinished(AdventureDTO adventureDTO) {
        return adventureDTO.getAdventurers().stream().allMatch(adventurer -> CollectionUtils.isEmpty(adventurer.getActions()));
    }

    public Pair<AdventureDTO, Adventurer> executeActions(AdventureDTO adventure, Adventurer currentAdventurer, Action currentAction) {
        if (Objects.isNull(currentAction)) {
            return Pair.of(adventure, currentAdventurer);
        }
        Pair<Position, Orientation> newPosition = moveService.updatePosition(adventure.getMapDimensions(), adventure.getMountains(), adventure.getAdventurers(), currentAdventurer, currentAction);
        Pair<List<Treasure>, Long> treasuresAndCollectedTreasures = collectTreasure(adventure.getTreasures(), newPosition.getFirst(), currentAdventurer.getNumberOfCollectedTreasures(), currentAdventurer.getPosition().getFirst());

        currentAdventurer = currentAdventurer.toBuilder()
                .position(newPosition)
                .numberOfCollectedTreasures(treasuresAndCollectedTreasures.getSecond())
                .build();
        adventure = adventure.toBuilder()
                .treasures(treasuresAndCollectedTreasures.getFirst())
                .build();

        return Pair.of(adventure, currentAdventurer);
    }

    public Pair<List<Treasure>, Long> collectTreasure(List<Treasure> treasures, Position adventurerPosition, Long numberOfCollectedTreasures, Position lastPosition) {
        List<Treasure> updatedTreasures = new ArrayList<>();
        AtomicLong numberOfCollectedTreasure = new AtomicLong(numberOfCollectedTreasures);

        if(isSamePosition(lastPosition, adventurerPosition)) {
            return Pair.of(treasures, numberOfCollectedTreasures);
        }
        treasures.forEach(treasure -> {
            if (isSamePosition(treasure.getPosition(), adventurerPosition) && treasure.getNumber() > 0) {
                log.info("Adventurer collected one treasure on case {} {}", adventurerPosition.getX(), adventurerPosition.getY());
                if(treasure.getNumber() - 1 > 0) {
                    updatedTreasures.add(Treasure.builder()
                            .position(treasure.getPosition())
                            .number(treasure.getNumber() - 1)
                            .build());
                }
                numberOfCollectedTreasure.addAndGet(1);
            } else {
                updatedTreasures.add(treasure);
            }
        });
        return Pair.of(updatedTreasures, numberOfCollectedTreasure.get());
    }
}
