package com.carbonit.carteauxtresors.service;

import com.carbonit.carteauxtresors.dto.AdventureDTO;
import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.enums.Action;
import com.carbonit.carteauxtresors.enums.Orientation;
import com.carbonit.carteauxtresors.service.turns.MoveService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ActionServiceTest {
    @InjectMocks
    ActionService actionService;

    @Mock
    MoveService moveService;

    @Test
    public void given_AdventureWithAllNotEmptyActionList_when_isGameFinished_return_False() {
        Adventurer adventurerNotFinished1 = Adventurer.builder().actions(Arrays.asList(Action.A, Action.A)).build();
        Adventurer adventurerNotFinished2 = Adventurer.builder().actions(Collections.singletonList(Action.G)).build();
        AdventureDTO adventureDTO = AdventureDTO.builder().adventurers(Arrays.asList(adventurerNotFinished2, adventurerNotFinished1)).build();

        boolean isGameFinished = actionService.isGameFinished(adventureDTO);

        AssertionsForClassTypes.assertThat(isGameFinished).isFalse();
    }

    @Test
    public void given_AdventureWithOneNotEmptyActionList_when_isGameFinished_return_False() {
        Adventurer adventurerNotFinished = Adventurer.builder().actions(Arrays.asList(Action.A, Action.A)).build();
        Adventurer adventurerFinished = Adventurer.builder().actions(Collections.emptyList()).build();
        AdventureDTO adventureDTO = AdventureDTO.builder().adventurers(Arrays.asList(adventurerFinished, adventurerNotFinished)).build();

        boolean isGameFinished = actionService.isGameFinished(adventureDTO);

        AssertionsForClassTypes.assertThat(isGameFinished).isFalse();
    }

    @Test
    public void given_AdventureWithAdventurersWithEmptyActionList_when_isGameFinished_return_True() {
        Adventurer adventurerNotFinished1 = Adventurer.builder().actions(Collections.emptyList()).build();
        Adventurer adventurerNotFinished2 = Adventurer.builder().actions(Collections.emptyList()).build();
        AdventureDTO adventureDTO = AdventureDTO.builder().adventurers(Arrays.asList(adventurerNotFinished2, adventurerNotFinished1)).build();

        boolean isGameFinished = actionService.isGameFinished(adventureDTO);

        AssertionsForClassTypes.assertThat(isGameFinished).isTrue();
    }

    @Test
    public void given_AdventureWithNoAdventurers_when_isGameFinished_return_True() {
        AdventureDTO adventureDTO = AdventureDTO.builder().adventurers(Collections.emptyList()).build();

        boolean isGameFinished = actionService.isGameFinished(adventureDTO);

        AssertionsForClassTypes.assertThat(isGameFinished).isTrue();
    }

    @Test
    public void given_AdventureWithTwoAdventurers_when_handleAdventurersTurns_return_UpdatedAdventureDTO() {
        Pair<Position, Orientation> adventurerPosition = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.E);
        Adventurer adventurerNotFinished = Adventurer.builder()
                .position(adventurerPosition)
                .actions(Arrays.asList(Action.G, Action.A))
                .build();
        Adventurer adventurerFinished = Adventurer.builder()
                .position(Pair.of(Position.builder().x(1L).y(1L).build(), Orientation.E))
                .actions(Collections.emptyList())
                .build();
        AdventureDTO adventureDTO = AdventureDTO.builder()
                .mapDimensions(Position.builder().x(4L).y(3L).build())
                .treasures(Collections.emptyList())
                .mountains(Collections.emptyList())
                .adventurers(Arrays.asList(adventurerFinished, adventurerNotFinished))
                .build();
        Pair<Position, Orientation> nextAdventurerPosition1 = Pair.of(Position.builder().x(0L).y(0L).build(), Orientation.N);
        Pair<Position, Orientation> nextAdventurerPosition2 = Pair.of(Position.builder().x(0L).y(1L).build(), Orientation.N);

        doReturn(nextAdventurerPosition1).when(moveService).updatePosition(any(), any(), any(), any(), eq(Action.G));
        doReturn(nextAdventurerPosition2).when(moveService).updatePosition(any(), any(), any(), any(), eq(Action.A));

        AdventureDTO finishedAdventure = actionService.handleAdventurersTurns(adventureDTO);

        Assertions.assertThat(finishedAdventure.getAdventurers()).hasSize(2);
        Assertions.assertThat(finishedAdventure.getAdventurers().get(0).getActions()).isEmpty();
        Assertions.assertThat(finishedAdventure.getAdventurers().get(1).getActions()).isEmpty();
        Assertions.assertThat(finishedAdventure.getAdventurers().get(0).getPosition().getFirst().getX()).isEqualTo(adventurerFinished.getPosition().getFirst().getX());
        Assertions.assertThat(finishedAdventure.getAdventurers().get(1).getPosition().getFirst().getX()).isEqualTo(adventurerNotFinished.getPosition().getFirst().getX());
        Assertions.assertThat(finishedAdventure.getAdventurers().get(0).getPosition().getFirst().getY()).isEqualTo(adventurerFinished.getPosition().getFirst().getY());
        Assertions.assertThat(finishedAdventure.getAdventurers().get(1).getPosition().getFirst().getY()).isEqualTo(adventurerNotFinished.getPosition().getFirst().getY() + 1);
        Assertions.assertThat(finishedAdventure.getAdventurers().get(0).getPosition().getSecond()).isEqualTo(adventurerFinished.getPosition().getSecond());
        Assertions.assertThat(finishedAdventure.getAdventurers().get(1).getPosition().getSecond()).isEqualTo(Orientation.N);
    }
}
