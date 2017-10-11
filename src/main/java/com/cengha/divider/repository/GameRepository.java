package com.cengha.divider.repository;

import com.cengha.divider.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game,String> {

    Optional<Game> getFirstByFinishedIsNull();
    Optional<Game> getById(String id);
    Optional<Game> getByPlayerOneIdOrPlayerTwoIdAndFinishedIsNull(String playerOneId,String playerTwoId);

}
