package com.cengha.divider.service;

import com.cengha.divider.exception.NoSuchGameException;
import com.cengha.divider.model.Game;
import com.cengha.divider.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    private final GameRepository repo;

    @Autowired
    public GameService(GameRepository repo) {
        this.repo = repo;
    }

    public Optional<Game> getFirstByFinishedIsNull(){
        return repo.getFirstByFinishedIsNull();
    }

    public Game retrieveById(String id) throws NoSuchGameException {
        Optional<Game> optGame = repo.getById(id);
        if(!optGame.isPresent()) throw new NoSuchGameException();
        Game game = optGame.get();
        return game;
    }

    public Game save(Game game){
        return repo.save(game);
    }

    public Game getFirstByPlayerOneIdOrPlayerTwoId(String userId){
        return repo.getByPlayerOneIdOrPlayerTwoIdAndFinishedIsNull(userId,userId).orElseThrow(NoSuchGameException::new);
    }


}
