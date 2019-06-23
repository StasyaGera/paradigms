"use strict";

var letters = {a : 0, b : 1, c : 2, d : 3, e : 4, f : 5, g : 6, h : 7};

function Cell(color) {
    this.color = color;
}
var EMPTY = new Cell(0);
var WHITE = new Cell(1);
var BLACK = new Cell(2);

function BoardConfiguration() {
    this.blackMoves = [{x : 3, y : 2}, {x : 2, y : 3}, {x : 4, y : 5}, {x : 5, y : 4}];
    this.whiteMoves = [{x : 3, y : 4}, {x : 4, y : 3}, {x : 2, y : 5}, {x : 5, y : 2}];

    this.config = [
        [EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY],
        [EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY],
        [EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY],
        [EMPTY, EMPTY, EMPTY, WHITE, BLACK, EMPTY, EMPTY, EMPTY],
        [EMPTY, EMPTY, EMPTY, BLACK, WHITE, EMPTY, EMPTY, EMPTY],
        [EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY],
        [EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY],
        [EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY],
    ]
}

function GameError(message) {
    this.name = "GameError";
    this.message = message;
}
GameError.prototype = Error.prototype;

function HumanPlayer(name, color) {
    this.name = name;
    this.color = color;
    this.count = 0;
}
HumanPlayer.prototype.makeMove = function (config) {
    var x, y;
    for (var i = 0; i < game.board.length; i++) {
        for (var j = 0; j < game.board[i].length; j++) {
            if (game.board[i][j] != config[i][j]) {
                x = i; y = j;
                break;
            }
        }
        if (x != undefined){
            break;
        }
    }

    game.board = config;

    var i = 1;
    while (x + i < 9 && game.board[x + i][y] != this.color && game.board[x + i][y] != EMPTY) {
        i++;
    }
    if (game.board[x + i][y] == this.color) {
        for (var p = x + 1; p < x + i; p++) {
            game.board[p][y] = this.color;
        }
        this.count += i - 1;
    }
    i = 0;
    while (x - i >= 0 &&game.board[x - i][y] != this.color &&game.board[x - i][y] != EMPTY) {
        i++;
    }
    if (board[x - i][y] == this.color) {
        for (var p = x - 1; p > x - i; p--) {
            game.board[p][y] = this.color;
        }
        this.count += i - 1;
    }
    i = 0;

    while (y + i < 9 &&game.board[x][y + i] != this.color &&game.board[x][y + i] != EMPTY) {
        i++;
    }
    if (board[x][y + i] == this.color) {
        for (var p = y + 1; p < y + i; p++) {
            game.board[x][p] = this.color;
        }
        this.count += i - 1;
    }
    i = 0;
    while (y - i >= 0 && game.board[x][y - i] != this.color && game.board[x][y - i] != EMPTY) {
        i++;
    }
    if (board[x][y - i] == this.color) {
        for (var p = y - 1; p > y - i; p--) {
            game.board[x][p] = this.color;
        }
        this.count += i - 1;
    }
    i = 0;

    while (x + i < 9 && y + i < 9 &&game.board[x + i][y + i] != this.color &&game.board[x + i][y + i] != EMPTY) {
        i++;
    }
    if (board[x + i][y + i] == this.color) {
        for (var p = 1; p < i; p++) {
            game.board[x + p][y + p] = this.color;
        }
        this.count += i - 1;
    }
    i = 0;
    while (y - i >= 0 && x - i >= 0 &&game.board[x - i][y - i] != this.color &&game.board[x - i][y - i] != EMPTY) {
        i++;
    }
    if (board[x - i][y - i] == this.color) {
        for (var p = 1; p < i; p++) {
            game.board[x - p][y - p] = this.color;
        }
        this.count += i - 1;
    }
    i = 0;
}

function GameServer(players){
    this.moves = true;
    this.players = players;
    this.board = new BoardConfiguration();
}
GameServer.prototype.checkState = function () {
    return false;
};

function readline() {
    return ["d3", "c3", "c4"];
}

var players = [new HumanPlayer("First", BLACK), new HumanPlayer("Second", WHITE)];

var game = new GameServer(players);
var turn = 0, w = 0, pos = readline();

while (w < pos.length) {
//    if (players[turn].color == BLACK) {
//        var t = 0;
//        for (i in game.board.blackMoves) {
//            if (i.x == pos.charAt(0) - 1 && i.y == letters[pos.charAt(1)]) {
//                game.board.blackMoves.splice(t, 1);
//            }
//            t++:
//        }
//    }
    game.board.config[pos[w].charAt(1) - 1][letters[pos[w].charAt(0)]] = players[turn].color;
    game.players[turn].makeMove(game.board);
//    if (!checkState()) {
//        break;
//    }
    turn = turn ? 0 : 1;
    w++;
}

println("first player: " + game.players[0].count);
println("second player: " + game.players[1].count);
