package com.cengha.divider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/{gameId}/game.start")
    public void notifyGameRoomStartGame(@DestinationVariable String gameId) {
        messagingTemplate.convertAndSend("/ws/channel/game/" + gameId, "Connected and game Started!");
    }

    @MessageMapping("/{gameId}/game.created/{playerId}")
    public void notifyPlayerGameCreated(@DestinationVariable String playerId,
                                        @DestinationVariable String gameId) {
        messagingTemplate.convertAndSend("/ws/channel/game/" + gameId + "/player/" + playerId,
                "Connected and waiting for an opponent!");
    }

    @MessageMapping("/{gameId}/game.turn/{turnPlayerId}")
    public void notifyPlayerOpponentTurn(@DestinationVariable String turnPlayerId,
                                         @DestinationVariable String gameId) {
        messagingTemplate.convertAndSend("/ws/channel/game/" + gameId + "/turn", turnPlayerId);
    }

    @MessageMapping("/{gameId}/game.move/{number}")
    public void notifyPlayerOpponentMove(@DestinationVariable String number,
                                         @DestinationVariable String gameId) {
        messagingTemplate.convertAndSend("/ws/channel/game/" + gameId + "/number", number);
    }

    @MessageMapping("/{gameId}/game.finish/{winnerPlayerId}")
    public void notifyPlayerOpponentFinished(@DestinationVariable String winnerPlayerId,
                                         @DestinationVariable String gameId) {
        messagingTemplate.convertAndSend("/ws/channel/game/" + gameId + "/finish", winnerPlayerId);
    }


}