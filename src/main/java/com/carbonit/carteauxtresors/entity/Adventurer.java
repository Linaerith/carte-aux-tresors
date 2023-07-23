package com.carbonit.carteauxtresors.entity;

import org.springframework.data.util.Pair;
import com.carbonit.carteauxtresors.enums.Action;
import com.carbonit.carteauxtresors.enums.Orientation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class Adventurer {
    Long id;
    Pair<Position, Orientation> position;
    List<Action> actions;
    String name;
    Long numberOfCollectedTreasures;
}
