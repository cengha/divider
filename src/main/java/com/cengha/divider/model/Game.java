package com.cengha.divider.model;

import lombok.Data;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "game")
public class Game{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String playerOneId;
    private String playerTwoId;
    private String winnerPlayerId;
    private String turnPlayerId;
    private LocalDateTime created=LocalDateTime.now();
    private LocalDateTime finished;

    @Transient
    private Move lastMove;

    @Transient
    private List<Move> moves;

    public Game(){ }
    public Game(String playerOneId){
        this.playerOneId=playerOneId;
    }


}
