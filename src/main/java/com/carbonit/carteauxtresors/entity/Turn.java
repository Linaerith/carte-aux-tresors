package com.carbonit.carteauxtresors.entity;

import com.carbonit.carteauxtresors.enums.Action;
import lombok.Builder;

@Builder
public class Turn {
    Long adventurerId;
    Action action;
}
