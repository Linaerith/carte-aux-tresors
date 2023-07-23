package com.carbonit.carteauxtresors.service;

import com.carbonit.carteauxtresors.entity.Position;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.carbonit.carteauxtresors.util.PositionUtil.isSamePosition;

@Service
public class MountainService {

    public boolean isThereMountain(List<Position> existingMountains, Position position) {
        return existingMountains.stream().anyMatch(existingMountain -> isSamePosition(position, existingMountain));
    }
}
