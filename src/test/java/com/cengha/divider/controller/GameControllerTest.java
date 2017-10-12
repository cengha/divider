package com.cengha.divider.controller;

import com.cengha.divider.model.Game;
import com.cengha.divider.service.FlowService;
import com.cengha.divider.service.GameService;
import com.cengha.divider.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlowService flowService;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        game.setId("game_id");
        game.setPlayerOneId("p1");
        game.setPlayerTwoId("p2");
    }

    @Test
    public void gameJoinTest() throws Exception {
        given(flowService.joinFirstAvailableGameOrCreateOne("p1")).willReturn(game);
        mockMvc.perform(post("/game/join?playerId=p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOneId", is(game.getPlayerOneId())))
                .andExpect(jsonPath("$.playerTwoId", is(game.getPlayerTwoId())));
    }
}
