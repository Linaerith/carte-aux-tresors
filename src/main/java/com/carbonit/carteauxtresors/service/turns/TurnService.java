package com.carbonit.carteauxtresors.service.turns;

import com.carbonit.carteauxtresors.enums.Orientation;

public interface TurnService {

    boolean isApplicableTo(Orientation currentOrientation);
    Orientation turnRight();
    Orientation turnLeft();

}
