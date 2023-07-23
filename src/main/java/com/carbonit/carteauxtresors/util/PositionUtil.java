package com.carbonit.carteauxtresors.util;

import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;

import java.util.List;
import java.util.Objects;

public class PositionUtil {
    public static boolean isSamePosition(Position a, Position b) {
        if (a == null || b == null) {
            return false;
        }
        return Objects.equals(a.getX(), b.getX()) && Objects.equals(a.getY(), b.getY());
    }

    public static boolean isPositionOutOfBounds(Position mapDimension, Position position) {
        if (position == null || mapDimension == null) {
            throw new IllegalArgumentException("Map dimensions or adventurer position on map can not be null");
        }
        return position.getX() > mapDimension.getX() - 1 || position.getY() > mapDimension.getY() - 1 || position.getX() < 0 || position.getY() < 0;
    }

    public static boolean isAdventurerOnCase(List<Adventurer> adventurers, Adventurer currentAdventurer, Position position) {
        return adventurers.stream().filter(adventurer -> !Objects.equals(currentAdventurer.getId(), adventurer.getId()))
                .anyMatch(adventurer -> isSamePosition(position, adventurer.getPosition().getFirst()));
    }
}
