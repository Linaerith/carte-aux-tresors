package com.carbonit.carteauxtresors.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Treasure {
    Long number;
    Position position;
}
