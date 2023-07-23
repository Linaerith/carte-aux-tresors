package com.carbonit.carteauxtresors.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Position {
    private Long x;
    private Long y;
}
