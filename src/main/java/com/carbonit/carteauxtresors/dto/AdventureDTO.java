package com.carbonit.carteauxtresors.dto;

import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.entity.Treasure;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class AdventureDTO {
    Position mapDimensions;
    List<Treasure> treasures;
    List<Position> mountains;
    List<Adventurer> adventurers;
}
