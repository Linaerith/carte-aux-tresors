package com.carbonit.carteauxtresors.enums;

import com.carbonit.carteauxtresors.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Orientation {
    N("Nord") {
        @Override
        public Orientation turnRight() {
            return E;
        }

        @Override
        public Orientation turnLeft() {
            return O;
        }

        @Override
        public Position moveForward(Position currentPosition) {
            return currentPosition.toBuilder().y(currentPosition.getY() - 1).build();
        }
    },
    S("Sud")  {
        @Override
        public Orientation turnRight() {
            return O;
        }

        @Override
        public Orientation turnLeft() {
            return E;
        }

        @Override
        public Position moveForward(Position currentPosition) {
            return currentPosition.toBuilder().y(currentPosition.getY() + 1).build();
        }
    },
    O("Ouest")  {
        @Override
        public Orientation turnRight() {
            return N;
        }

        @Override
        public Orientation turnLeft() {
            return S;
        }

        @Override
        public Position moveForward(Position currentPosition) {
            return currentPosition.toBuilder().x(currentPosition.getX() - 1).build();
        }
    },
    E("Est")  {
        @Override
        public Orientation turnRight() {
            return S;
        }

        @Override
        public Orientation turnLeft() {
            return N;
        }

        @Override
        public Position moveForward(Position currentPosition) {
            return currentPosition.toBuilder().x(currentPosition.getX() + 1).build();
        }
    };

    @Getter
    private final String description;

    public abstract Orientation turnRight();
    public abstract Orientation turnLeft();
    public abstract Position moveForward(Position currentPosition);

}
