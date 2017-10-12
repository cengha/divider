package com.cengha.divider.service;

import com.cengha.divider.exception.GameFinishedException;
import com.cengha.divider.exception.IllegalNumberException;
import com.cengha.divider.exception.NotYourTurnException;
import com.cengha.divider.model.Game;
import com.cengha.divider.model.Move;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FlowService {

    private final GameService gameService;
    private final MoveService moveService;

    @Autowired
    public FlowService(GameService gameService, MoveService moveService) {
        this.gameService = gameService;
        this.moveService = moveService;
    }

    @Transactional
    public Game joinFirstAvailableGameOrCreateOne(String playerId) {
        Game game;
        Optional<Game> optGame = gameService.getFirstByFinishedIsNull();
        if (optGame.isPresent()) {
            game = optGame.get();
            List<Move> allMoves = moveService.getAllByGameId(game.getId());
            Random random = new Random();
            int i = random.nextInt(1000);
            Move move = moveService.save(i, playerId, game.getId());
            allMoves.add(move);
            game.setLastMove(move);
            game.setMoves(allMoves);
            game.setPlayerTwoId(playerId);
            game.setTurnPlayerId(game.getPlayerOneId());
            game.setCreated(LocalDateTime.now());
        } else {
            game = new Game(playerId);
        }
        gameService.save(game);
        return game;
    }


    @Transactional
    public Game makeMove(Integer number, String playerId, String gameId) {
        Game game = gameService.retrieveById(gameId);
        if (game.getFinished() != null) {
            throw new GameFinishedException();
        }
        if (!game.getTurnPlayerId().equalsIgnoreCase(playerId)) {
            throw new NotYourTurnException();
        }
        List<Move> allByGameId = moveService.getAllByGameId(gameId);
        Integer lastMove = allByGameId.get(allByGameId.size() - 1).getNumber();
        Integer plusOne = lastMove + 1;
        Integer minusOne = lastMove - 1;
        if (number % 3 != 0 || !(number.equals(lastMove) || number.equals(plusOne) || number.equals(minusOne))) {
            throw new IllegalNumberException();
        }
        Move move = moveService.save(number / 3, playerId, gameId);
        if (move.getNumber().equals(1)) {
            game.setFinished(LocalDateTime.now());
            game.setWinnerPlayerId(playerId);
        }
        allByGameId.add(move);
        game.setMoves(allByGameId);
        game.setLastMove(move);
        String tpid = game.getTurnPlayerId().equalsIgnoreCase(game.getPlayerOneId()) ? game.getPlayerTwoId() : game.getPlayerOneId();
        game.setTurnPlayerId(tpid);
        gameService.save(game);
        return game;
    }


    public Game terminateGame(String playerId, String gameId) {
        Game game = gameService.retrieveById(gameId);
        if (game.getFinished() == null) {
            game.setFinished(LocalDateTime.now());
            String winnerId = playerId.equalsIgnoreCase(game.getPlayerOneId()) ? game.getPlayerTwoId() : game.getPlayerOneId();
            game.setWinnerPlayerId(winnerId);
        }
        return gameService.save(game);
    }
}
