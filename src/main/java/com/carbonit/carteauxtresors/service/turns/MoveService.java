package com.carbonit.carteauxtresors.service.turns;

import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.enums.Action;
import com.carbonit.carteauxtresors.enums.Orientation;
import com.carbonit.carteauxtresors.service.MountainService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.carbonit.carteauxtresors.util.PositionUtil.*;

@Component
@AllArgsConstructor
public class MoveService {

    MountainService mountainService;

    public Pair<Position, Orientation> updatePosition(
            Position mapDimension,
            List<Position> existingMountains,
            List<Adventurer> adventurers,
            Adventurer currentAdventurer,
            Action action
    ) {
        if (Objects.equals(action, Action.G)) {
            return turnLeft(currentAdventurer.getPosition());
        }
        if (Objects.equals(action, Action.D)) {
            return turnRight(currentAdventurer.getPosition());
        }
        if (Objects.equals(action, Action.A)) {
            return moveForward(mapDimension, existingMountains, adventurers, currentAdventurer);
        }
        return currentAdventurer.getPosition();
    }

    public Pair<Position, Orientation> turnLeft(Pair<Position, Orientation> currentPosition) {
        return Pair.of(currentPosition.getFirst(), currentPosition.getSecond().turnLeft());
    }

    public Pair<Position, Orientation> turnRight(Pair<Position, Orientation> currentPosition) {
        return Pair.of(currentPosition.getFirst(), currentPosition.getSecond().turnRight());
    }

    public Pair<Position, Orientation> moveForward(
            Position mapDimension,
            List<Position> existingMountains,
            List<Adventurer> adventurers,
            Adventurer currentAdventurer) {
        Pair<Position, Orientation> currentPosition = currentAdventurer.getPosition();
        Position nextPosition = currentPosition.getSecond().moveForward(currentPosition.getFirst());
        if (
                !isPositionOutOfBounds(mapDimension, nextPosition) &&
                        !mountainService.isThereMountain(existingMountains, nextPosition) &&
                        !isAdventurerOnCase(adventurers, currentAdventurer, nextPosition)
        ) {
            return Pair.of(nextPosition, currentPosition.getSecond());
        }
        return currentPosition;
    }
}
