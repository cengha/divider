package com.cengha.divider.repository;

import com.cengha.divider.model.Move;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends CrudRepository<Move,String> {

    List<Move> getAllByGameId(String gameId);
}
