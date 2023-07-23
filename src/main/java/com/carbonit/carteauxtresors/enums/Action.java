package com.carbonit.carteauxtresors.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum  Action {
    A("Avancer"),
    D("Tourner à droite"),
    G("Tourner à gauche");

    @Getter
    private final String description;
}
