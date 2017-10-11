package com.cengha.divider.controller;

import com.cengha.divider.model.Game;
import com.cengha.divider.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final FlowService flowService;

    @Autowired
    public GameController(FlowService flowService) {
        this.flowService = flowService;
    }

    @PostMapping(value = "/join")
    public HttpEntity<Game> gameJoin(@RequestParam("playerId")String playerId){
        return new ResponseEntity<>(flowService.joinFirstAvailableGameOrCreateOne(playerId), HttpStatus.OK);
    }

    @PostMapping(value = "/move")
    public HttpEntity<Game> gameMove(@RequestParam("number")Integer number,
                                     @RequestParam("playerId")String playerId,
                                     @RequestParam("gameId")String gameId){
        return new ResponseEntity<>(flowService.makeMove(number, playerId, gameId), HttpStatus.OK);

    }


}
