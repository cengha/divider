'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#number-page');
var winPage = document.querySelector('#win-page');
var lostPage = document.querySelector('#lost-page');
var usernameButton = document.querySelector('#usernameButton');
var moveButton = document.querySelector('#moveButton');
var plusOneButton = document.querySelector('#btnPlus');
var minusOneButton = document.querySelector('#btnMinus');
var simulate = document.querySelector('#simulate');
var alertMove = document.querySelector('#alert-move');
var alertMove2 = document.querySelector('#alert-move2');

var stompClient = null;
var username = null;
var gameId = null;
var isGameCreated = false;
var newNumber = Number(0);
var oldNumber = Number(0);
var min = Number(0);
var max = Number(0);
var one = Number(1);
var tpid = null;
var winnerPlayerId = null;
var finished = false;


var cnnct = function connect(event) {

    username = $('#username').val();

    if (username) {

        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/dws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
};

function onConnected() {
    console.log('connected dws');

    //join a game
    joinGame();

}

function subscribeChannels() {
    // Subscribe to the Private Personal Channel
    stompClient.subscribe('/ws/channel/game/' + gameId, onMessageReceived);

    //Subscribe to the Private Game Channel
    stompClient.subscribe('/ws/channel/game/' + gameId + '/player/' + username, onMessageReceived);

    //Subscribe to the Number Channel
    stompClient.subscribe('/ws/channel/game/' + gameId + '/number', onNumberReceived);

    //Subscribe to the Finish Channel
    stompClient.subscribe('/ws/channel/game/' + gameId + '/finish', onFinishReceived);

    //Subscribe to the Turn Channel
    stompClient.subscribe('/ws/channel/game/' + gameId + '/turn', onTurnReceived);

    console.log('isGameCreated: ' + isGameCreated);
    if (isGameCreated) {
        stompClient.send("/ws/divider/" + gameId + "/game.start");
        stompClient.send("/ws/divider/" + gameId + "/game.move/" + newNumber);
        stompClient.send("/ws/divider/" + gameId + "/game.turn/" + tpid);
    } else {
        stompClient.send("/ws/divider/" + gameId + "/game.created/" + username);
    }
}

function joinGame() {
    console.log('rest join call, username-->' + username);
    $.ajax({
        url: '/game/join',
        type: "POST",
        data: {
            playerId: username
        },
        dataType: 'json',
        success: function (data) {
            gameId = data.id;
            tpid = data.turnPlayerId;
            console.log('connected game id -->' + gameId);
            alertMove.classList.add('hidden');
            alertMove2.classList.add('hidden');
            if (data.lastMove !== null) {
                newNumber = data.lastMove.number;
                oldNumber = data.lastMove.number;
                min = Number(data.lastMove.number) - 1;
                max = Number(data.lastMove.number) + 1;
                stompClient.send("/ws/divider/" + gameId + "/game.turn/" + data.turnPlayerId);
            }
            if (data.created !== null) {
                isGameCreated = true;
            }
            subscribeChannels();
        },
        error: function () {
            alertMove.classList.remove('hidden');
            alertMove2.classList.remove('hidden');
        }
    });
}

var mv = function move() {
    var number = document.getElementById("inp-number").value;
    var numberNumb = Number(document.getElementById("inp-number").value);
    if (!(numberNumb === oldNumber || numberNumb + one === oldNumber || numberNumb - one === oldNumber)) {
        document.getElementById("inp-number").value = oldNumber;
        alert("check number, plus 1 or minus 1");
    } else {
        $.ajax({
            url: '/game/move',
            type: "POST",
            data: {
                number: number,
                playerId: username,
                gameId: gameId
            },
            dataType: 'json',
            success: function (data) {
                console.log("make a move: " + number);
                alertMove.classList.add('hidden');
                alertMove2.classList.add('hidden');
                if (data.finished !== null) {
                    stompClient.send("/ws/divider/" + gameId + "/game.finish/" + data.winnerPlayerId);
                } else {
                    stompClient.send("/ws/divider/" + gameId + "/game.move/" + data.lastMove.number);
                    stompClient.send("/ws/divider/" + gameId + "/game.turn/" + data.turnPlayerId);
                }
            },
            error: function handleError() {
                alertMove.classList.remove('hidden');
                alertMove2.classList.remove('hidden');
            }
        });
    }
};

function terminateGame() {
    $.ajax({
        url: '/game/terminate',
        type: "POST",
        data: {
            playerId: username,
            gameId: gameId
        },
        dataType: 'json',
        success: function (data) {
            console.log("game terminated: " + gameId + " " + data.id
            )
            ;
        },
        error: function handleError() {
            alertMove.classList.remove('hidden');
            alertMove2.classList.remove('hidden');
        }
    });
}

var plsN = function plusOne() {
    console.log('plusOne');
    console.log(max);
    alertMove.classList.add('hidden');
    alertMove2.classList.add('hidden');
    var numberNumb = Number(document.getElementById("inp-number").value);
    if (numberNumb + one > max) {
        alert('plus one minus one');
    } else {
        document.getElementById("inp-number").value = numberNumb + one;
    }
};

var mnsN = function minusOne() {
    console.log('minusOne');
    console.log(min);
    alertMove.classList.add('hidden');
    alertMove2.classList.add('hidden');
    var numberNumb = Number(document.getElementById("inp-number").value);
    if (numberNumb - one < min) {
        alert('plus one minus one');
    } else {
        document.getElementById("inp-number").value = numberNumb - one;
    }
};


function onError(error) {
    terminateGame();
    console.log('not connected dws ' + error);
    stompClient.disconnect();
}


function onMessageReceived(payload) {
    console.log(payload.body);
}

function onNumberReceived(payload) {
    console.log(payload.body);
    console.log('newNumber -->' + payload.body);
    document.getElementById("inp-number").value = payload.body;
    newNumber = Number(payload.body);
    oldNumber = Number(payload.body);
    min = Number(payload.body) - 1;
    max = Number(payload.body) + 1;
}

function onFinishReceived(payload) {
    finished = true;
    $("#moveButton").prop("disabled", true).off('click');
    console.log(payload.body);
    console.log('newNumber -->' + payload.body);
    winnerPlayerId = payload.body;
    chatPage.classList.add('hidden');
    if (winnerPlayerId === username) {
        winPage.classList.remove('hidden');
    } else {
        lostPage.classList.remove('hidden');
    }
}

function onTurnReceived(payload) {
    console.log(payload.body);
    console.log('newNumber -->' + payload.body);
    tpid = payload.body;
    if (tpid === username) {
        $("#moveButton").prop("disabled", false).off('click');
        if (!finished) {
            if (simulate.checked) {
                document.getElementById("inp-number").value = (newNumber + one) % 3 === 0 ?
                    newNumber + one : (newNumber - one) % 3 === 0 ? newNumber - one : newNumber;
                mv();
            }
        }
    } else {
        $("#moveButton").prop("disabled", true).off('click');
    }
}


usernameButton.addEventListener('click', cnnct, true);
moveButton.addEventListener('click', mv, true);
plusOneButton.addEventListener('click', plsN, true);
minusOneButton.addEventListener('click', mnsN, true);