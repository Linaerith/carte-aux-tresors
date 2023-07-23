package com.carbonit.carteauxtresors.service;

import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.enums.Action;
import com.carbonit.carteauxtresors.enums.Orientation;
import com.carbonit.carteauxtresors.service.turns.MoveService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class MoveServiceTest {

    @InjectMocks
    private MoveService moveService;

    @Mock
    private MountainService mountainService;

    private List<Position> existingMountains;
    private Position mapDimensions;
    private List<Adventurer> adventurers;
    private Adventurer adventurerSamePosition;

    @Before
    public void setUp() {
        this.mapDimensions = Position.builder().x(4L).y(3L).build();
        Position firstMountain = Position.builder().x(1L).y(1L).build();
        Position secondMountain = Position.builder().x(0L).y(2L).build();
        this.existingMountains = Arrays.asList(firstMountain, secondMountain);
        Adventurer adventurer1 = Adventurer.builder().id(2L).position(Pair.of(Position.builder().x(1L).y(1L).build(), Orientation.N)).build();
        this.adventurerSamePosition = Adventurer.builder().id(3L).position(Pair.of(Position.builder().x(1L).y(0L).build(), Orientation.N)).build();
        this.adventurers = Collections.singletonList(adventurer1);
        doReturn(false).when(mountainService).isThereMountain(any(), any());
    }

    @Test
    public void given_AdventurerPositionWithOrientationNordAndActionG_when_turnLeft_return_O() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.N);

        Pair<Position, Orientation> newPosition = moveService.turnLeft(adventurerPosition);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.O);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_AdventurerPositionWithOrientationSud_when_turnLeft_return_E() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.S);

        Pair<Position, Orientation> newPosition = moveService.turnLeft(adventurerPosition);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.E);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_AdventurerPositionWithOrientationOuest_when_turnLeft_return_S() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.O);

        Pair<Position, Orientation> newPosition = moveService.turnLeft(adventurerPosition);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.S);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_AdventurerPositionWithOrientationEst_when_turnLeft_return_N() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);

        Pair<Position, Orientation> newPosition = moveService.turnLeft(adventurerPosition);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.N);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_OrientationNord_when_turnRight_return_E() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.N);

        Pair<Position, Orientation> newPosition = moveService.turnRight(adventurerPosition);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.E);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_AdventurerPositionWithOrientationSud_when_turnRight_return_O() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.S);
        Pair<Position, Orientation> newPosition = moveService.turnRight(adventurerPosition);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.O);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_AdventurerPositionWithOrientationOuest_when_turnRight_return_N() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.O);
        Pair<Position, Orientation> newPosition = moveService.turnRight(adventurerPosition);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.N);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_AdventurerPositionWithOrientationEst_when_turnRight_return_S() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);
        Pair<Position, Orientation> newPosition = moveService.turnRight(adventurerPosition);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.S);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_AdventurerPositionWithOrientationNord_when_moveForward_return_NAndYMinus1() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(1L).build(), Orientation.N);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.moveForward(mapDimensions, existingMountains, adventurers, currentAdventurer);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.N);
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getX()).isEqualTo(adventurerPosition.getFirst().getX());
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getY()).isEqualTo(adventurerPosition.getFirst().getY() - 1);
    }

    @Test
    public void given_AdventurerPositionWithOrientationSud_when_moveForward_return_SAndYPlus1() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.S);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.moveForward(mapDimensions, existingMountains, adventurers, currentAdventurer);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.S);
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getX()).isEqualTo(adventurerPosition.getFirst().getX());
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getY()).isEqualTo(adventurerPosition.getFirst().getY() + 1);
    }

    @Test
    public void given_AdventurerPositionWithOrientationOuest_when_moveForward_return_O() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.O);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.moveForward(mapDimensions, existingMountains, adventurers, currentAdventurer);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.O);
        AssertionsForClassTypes.assertThat(newPosition.getFirst()).isEqualTo(adventurerPosition.getFirst());
    }

    @Test
    public void given_AdventurerPositionWithOrientationEst_when_moveForward_return_EAndXPlus1() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.moveForward(mapDimensions, existingMountains, adventurers, currentAdventurer);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.E);
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getX()).isEqualTo(adventurerPosition.getFirst().getX() + 1);
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getY()).isEqualTo(adventurerPosition.getFirst().getY());
    }

    @Test
    public void given_AdventurerPositionWithOrientationEstAndAdventurersSamePosition_when_moveForward_return_SamePosition() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.moveForward(mapDimensions, existingMountains, Collections.singletonList(adventurerSamePosition), currentAdventurer);

        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.E);
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getX()).isEqualTo(adventurerPosition.getFirst().getX());
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getY()).isEqualTo(adventurerPosition.getFirst().getY());
    }

    @Test
    public void given_ActionD_when_updatePosition_return_updatedOrientation() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.updatePosition(mapDimensions, existingMountains, Collections.singletonList(currentAdventurer), currentAdventurer, Action.D);

        AssertionsForClassTypes.assertThat(newPosition.getFirst().getX()).isEqualTo(adventurerPosition.getFirst().getX());
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getY()).isEqualTo(adventurerPosition.getFirst().getY());
        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.S);
    }

    @Test
    public void given_ActionG_when_updatePosition_return_updatedOrientation() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.updatePosition(mapDimensions, existingMountains, Collections.singletonList(currentAdventurer), currentAdventurer, Action.G);

        AssertionsForClassTypes.assertThat(newPosition.getFirst().getX()).isEqualTo(adventurerPosition.getFirst().getX());
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getY()).isEqualTo(adventurerPosition.getFirst().getY());
        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(Orientation.N);
    }

    @Test
    public void given_ActionA_when_updatePosition_return_updatedPosition() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.updatePosition(mapDimensions, existingMountains, Collections.singletonList(currentAdventurer), currentAdventurer, Action.A);

        AssertionsForClassTypes.assertThat(newPosition.getFirst().getX()).isEqualTo(adventurerPosition.getFirst().getX() + 1);
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getY()).isEqualTo(adventurerPosition.getFirst().getY());
        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(currentAdventurer.getPosition().getSecond());
    }

    @Test
    public void given_NullAction_when_updatePosition_return_SamePosition() {
        Pair<Position, Orientation>  adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        Pair<Position, Orientation> newPosition = moveService.updatePosition(mapDimensions, existingMountains, Collections.singletonList(currentAdventurer), currentAdventurer, null);

        AssertionsForClassTypes.assertThat(newPosition.getFirst().getX()).isEqualTo(adventurerPosition.getFirst().getX());
        AssertionsForClassTypes.assertThat(newPosition.getFirst().getY()).isEqualTo(adventurerPosition.getFirst().getY());
        AssertionsForClassTypes.assertThat(newPosition.getSecond()).isEqualTo(currentAdventurer.getPosition().getSecond());
    }
}
