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
public class FlowServiceTest {


    @MockBean
    public GameService gameService;

    @MockBean
    public MoveService moveService;

    public FlowService flowService;

    @Before
    public void setUp() throws Exception {
        flowService = new FlowService(gameService, moveService);

        Game game = new Game("p1");
        Optional<Game> optional = Optional.of(game);
        Mockito.when(gameService.getFirstByFinishedIsNull())
                .thenReturn(optional);
    }

    @Test
    public void joinFirstAvailableGameOrCreateOneTest() {
        String p1 = "p1";
        Game found = flowService.joinFirstAvailableGameOrCreateOne(p1);

        assertEquals(found.getPlayerOneId(), p1);
    }
}
