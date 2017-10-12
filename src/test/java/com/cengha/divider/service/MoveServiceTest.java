package com.cengha.divider.service;

import com.cengha.divider.model.Game;
import com.cengha.divider.model.Move;
import com.cengha.divider.repository.GameRepository;
import com.cengha.divider.repository.MoveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class MoveServiceTest {


    @MockBean
    public MoveRepository moveRepository;

    public MoveService moveService;

    public List<Move> moves;

    public Game game;

    @Before
    public void setUp() throws Exception {
        moveService = new MoveService(moveRepository);

        game = new Game("p1");
        game.setId("game_id");
        Optional<Game> optional = Optional.of(game);
        moves = new LinkedList<>();
        moves.add(new Move("game_id", "p1", 1));
        Mockito.when(moveRepository.getAllByGameId(game.getId())).thenReturn(moves);
    }

    @Test
    public void getFirstByPlayerOneIdOrPlayerTwoIdTest() {
        List<Move> found = moveService.getAllByGameId("game_id");

        assertEquals(found.size(), moves.size());
    }
}
