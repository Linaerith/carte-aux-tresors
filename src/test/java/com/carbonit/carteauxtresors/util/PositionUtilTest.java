package com.carbonit.carteauxtresors.util;

import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.enums.Orientation;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PositionUtilTest {

    @Test
    public void given_SamePosition_when_isSamePosition_return_True() {
        Position a = Position.builder().x(1L).y(2L).build();
        Position b = Position.builder().x(1L).y(2L).build();

        boolean isSamePosition = PositionUtil.isSamePosition(a, b);

        AssertionsForClassTypes.assertThat(isSamePosition).isTrue();

    }

    @Test
    public void given_SameYAndDifferentX_when_isSamePosition_return_False() {
        Position a = Position.builder().x(1L).y(2L).build();
        Position b = Position.builder().x(5L).y(2L).build();

        boolean isSamePosition = PositionUtil.isSamePosition(a, b);

        AssertionsForClassTypes.assertThat(isSamePosition).isFalse();

    }

    @Test
    public void given_NullPositions_when_isSamePosition_return_False() {
        boolean isSamePosition = PositionUtil.isSamePosition(null, null);

        AssertionsForClassTypes.assertThat(isSamePosition).isFalse();

    }

    @Test
    public void given_NullA_when_isSamePosition_return_False() {
        Position a = Position.builder().x(1L).y(2L).build();

        boolean isSamePosition = PositionUtil.isSamePosition(a, null);

        AssertionsForClassTypes.assertThat(isSamePosition).isFalse();

    }

    @Test
    public void given_NullB_when_isSamePosition_return_False() {
        Position b = Position.builder().x(5L).y(2L).build();

        boolean isSamePosition = PositionUtil.isSamePosition(null, b);

        AssertionsForClassTypes.assertThat(isSamePosition).isFalse();

    }

    @Test
    public void given_SameXAndDifferentY_when_isSamePosition_return_False() {
        Position a = Position.builder().x(1L).y(2L).build();
        Position b = Position.builder().x(1L).y(1L).build();

        boolean isSamePosition = PositionUtil.isSamePosition(a, b);

        AssertionsForClassTypes.assertThat(isSamePosition).isFalse();
    }

    @Test
    public void given_DifferencePosition_when_isSamePosition_return_False() {
        Position a = Position.builder().x(1L).y(2L).build();
        Position b = Position.builder().x(3L).y(4L).build();

        boolean isSamePosition = PositionUtil.isSamePosition(a, b);

        AssertionsForClassTypes.assertThat(isSamePosition).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_NullMapDimensions_when_isPositionOutOfBounds_return_IllegalArgumentException() {
        Position b = Position.builder().x(0L).y(0L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(null, b);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isFalse();

    }

    @Test(expected = IllegalArgumentException.class)
    public void given_NullPosition_when_isPositionOutOfBounds_return_IllegalArgumentException() {
        Position b = Position.builder().x(0L).y(0L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(b, null);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isFalse();

    }

    @Test(expected = IllegalArgumentException.class)
    public void given_Null_when_isPositionOutOfBounds_return_IllegalArgumentException() {
        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(null, null);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isFalse();

    }

    @Test
    public void given_PositionWithinBounds_when_isPositionOutOfBounds_return_False() {
        Position mapDimension = Position.builder().x(3L).y(4L).build();
        Position b = Position.builder().x(0L).y(0L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(mapDimension, b);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isFalse();

    }

    @Test
    public void given_NegativePositionOnX_when_isPositionOutOfBounds_return_True() {
        Position mapDimension = Position.builder().x(1L).y(2L).build();
        Position b = Position.builder().x(-1L).y(1L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(mapDimension, b);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isTrue();
    }

    @Test
    public void given_NegativePositionOnY_when_isPositionOutOfBounds_return_True() {
        Position mapDimension = Position.builder().x(3L).y(4L).build();
        Position b = Position.builder().x(1L).y(-1L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(mapDimension, b);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isTrue();
    }

    @Test
    public void given_NegativePositionOnBothAxis_when_isPositionOutOfBounds_return_True() {
        Position mapDimension = Position.builder().x(1L).y(2L).build();
        Position b = Position.builder().x(-1L).y(-1L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(mapDimension, b);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isTrue();
    }

    @Test
    public void given_PositionOutOfBoundOnX_when_isPositionOutOfBounds_return_True() {
        Position mapDimension = Position.builder().x(3L).y(4L).build();
        Position b = Position.builder().x(3L).y(2L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(mapDimension, b);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isTrue();
    }

    @Test
    public void given_PositionOutOfBoundOnY_when_isPositionOutOfBounds_return_True() {
        Position mapDimension = Position.builder().x(3L).y(4L).build();
        Position b = Position.builder().x(1L).y(4L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(mapDimension, b);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isTrue();
    }

    @Test
    public void given_PositionOutOfBoundOnBothAxis_when_isPositionOutOfBounds_return_True() {
        Position mapDimension = Position.builder().x(3L).y(4L).build();
        Position b = Position.builder().x(3L).y(4L).build();

        boolean isPositionOutOfBounds = PositionUtil.isPositionOutOfBounds(mapDimension, b);

        AssertionsForClassTypes.assertThat(isPositionOutOfBounds).isTrue();
    }

    @Test
    public void given_AdventurerOnSamePosition_when_isAdventurerOnCase_return_True() {
        Pair<Position, Orientation> adventurerPosition = Pair.of(Position.builder().x(1L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();
        Adventurer adventurerSamePosition = Adventurer.builder().id(3L).position(Pair.of(Position.builder().x(1L).y(0L).build(), Orientation.N)).build();
        List<Adventurer> adventurers = Arrays.asList(adventurerSamePosition, currentAdventurer);

        boolean isAdventurerOnCase = PositionUtil.isAdventurerOnCase(adventurers, currentAdventurer, adventurerPosition.getFirst());

        AssertionsForClassTypes.assertThat(isAdventurerOnCase).isTrue();
    }

    @Test
    public void given_AdventurerNotSamePosition_when_isAdventurerOnCase_return_False() {
        Pair<Position, Orientation> adventurerPosition = Pair.of(Position.builder().x(2L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();
        Adventurer adventurerSamePosition = Adventurer.builder().id(3L).position(Pair.of(Position.builder().x(1L).y(0L).build(), Orientation.N)).build();
        List<Adventurer> adventurers = Arrays.asList(adventurerSamePosition, currentAdventurer);

        boolean isAdventurerOnCase = PositionUtil.isAdventurerOnCase(adventurers, currentAdventurer, adventurerPosition.getFirst());

        AssertionsForClassTypes.assertThat(isAdventurerOnCase).isFalse();
    }

    @Test
    public void given_emptyList_when_isAdventurerOnCase_return_False() {
        Pair<Position, Orientation> adventurerPosition = Pair.of(Position.builder().x(2L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();

        boolean isAdventurerOnCase = PositionUtil.isAdventurerOnCase(Collections.emptyList(), currentAdventurer, adventurerPosition.getFirst());

        AssertionsForClassTypes.assertThat(isAdventurerOnCase).isFalse();
    }

    @Test
    public void given_onlyCurrentAdventurerOnList_when_isAdventurerOnCase_return_False() {
        Pair<Position, Orientation> adventurerPosition = Pair.of(Position.builder().x(2L).y(0L).build(), Orientation.E);
        Adventurer currentAdventurer = Adventurer.builder().id(1L).position(adventurerPosition).build();
        List<Adventurer> adventurers = Collections.singletonList(currentAdventurer);

        boolean isAdventurerOnCase = PositionUtil.isAdventurerOnCase(adventurers, currentAdventurer, adventurerPosition.getFirst());

        AssertionsForClassTypes.assertThat(isAdventurerOnCase).isFalse();
    }
}
