package com.cengha.divider.service;

import com.cengha.divider.model.Move;
import com.cengha.divider.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveService {

    private final MoveRepository repo;

    @Autowired
    public MoveService(MoveRepository repo) {
        this.repo = repo;
    }

    public Move save(Integer number, String playerId, String gameId) {
        Move move=new Move(gameId,playerId,number);
        return repo.save(move);
    }

    public List<Move> getAllByGameId(String gameId){
        return repo.getAllByGameId(gameId);
    }

}
