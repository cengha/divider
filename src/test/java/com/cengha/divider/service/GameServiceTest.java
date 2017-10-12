package com.cengha.divider.service;

import com.cengha.divider.model.Game;
import com.cengha.divider.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class GameServiceTest {


    @MockBean
    public GameRepository gameRepository;

    public GameService gameService;

    @Before
    public void setUp() throws Exception {
        gameService=new GameService(gameRepository);

        Game game = new Game("p1");
        Optional<Game> optional = Optional.of(game);
        Mockito.when(gameRepository
                .getByPlayerOneIdOrPlayerTwoIdAndFinishedIsNull(game.getPlayerOneId(), game.getPlayerOneId()))
                .thenReturn(optional);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String p1 = "p1";
        Game found = gameService.getFirstByPlayerOneIdOrPlayerTwoId(p1);

        assertEquals(found.getPlayerOneId(), p1);
    }
}
