package com.cengha.divider.repository;

import com.cengha.divider.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GameRepository gameRepository;

    private Game game1;
    private Game game2;

    @Before
    public void setUp() throws Exception {
        game1 = new Game("game_id1");
        testEntityManager.persist(game1);

        game2 = new Game("game_id2");
        testEntityManager.persist(game2);

        testEntityManager.flush();
    }

    @Test
    public void getByPlayerOneIdOrPlayerTwoIdAndFinishedIsNullTest() {


        Optional<Game> found = gameRepository.getByPlayerOneIdOrPlayerTwoIdAndFinishedIsNull
                (game1.getPlayerOneId(), game1.getPlayerOneId());

        assertTrue(found.isPresent());
        assertEquals(game1.getPlayerOneId(), found.get().getPlayerOneId());
        assertNull(found.get().getFinished());

    }

    @Test
    public void getFirstByFinishedIsNullTest() throws Exception {
        Optional<Game> found = gameRepository.getFirstByFinishedIsNull();
        assertTrue(found.isPresent());
        assertEquals(game1.getPlayerOneId(), found.get().getPlayerOneId());
        assertNull(found.get().getFinished());
    }
}
