const prompt = require('prompt-sync')();
const fs = require('fs');
const { exit } = require('process');

var globalBoard;
var kounter = 0;
var speed = 1;
var totalCount = 0;

speed = process.argv[2];
let board = scanner();
printer(board);
let x = firstX(board);
let y = firstY(board);
for (let i = 1; i <= 9; i++) {
    if (allowed(board, i, x, y)) {
        meat(board, i);
    }
}

function clone(board) {
    return JSON.parse(JSON.stringify(board));
}

function meat(board, n) {
    let x0 = firstX(board);
    let y0 = firstY(board);
    board[x0][y0] = n;
    let x = firstX(board);
    let y = firstY(board);
    kounter++;
    totalCount++;
    if (kounter == speed) {
        printer(board);
        console.log("x = " + x);
        console.log("y = " + y);
        kounter = 0;
    }
    if (y >= 9) {
        printer(board);
        complete(board);
        console.log("SUCCESS");
        console.log("Took " + totalCount + " guesses");
        exit(1);
    }
    for (let i = 1; i <= 9; i++) {
        if (allowed(board, i, x, y)) {
            meat(board, i);
        }
    }
    board[x0][y0] = 0;
    return;
}

function allowed(board, n, x, y) {
    for (let i = 0; i < 9; i++) {
        if (board[x][i] == n) {
            return false;
        }
    }
    for (let i = 0; i < 9; i++) {
        if (board[i][y] == n) {
            return false;
        }
    }
    let cellX = (Math.floor(x / 3) * 3);
    let cellY = (Math.floor(y / 3) * 3);
    if (cellX == undefined) {
        console.log("cellX  undefined");
        exit(0);
    }
    if (cellY == undefined) {
        console.log("cellY  undefined");
        exit(0);
    }
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            if (board[cellX + j][cellY + i] == n) {
                return false;
            }
        }
    }
    return true;
}

function printer(board) {
    process.stdout.write("\033[H\033[2J");
    for (let i = 0; i < 9; i++) {
        if (i % 3 == 0) console.log("-------------------------");
        for (let j = 0; j < 9; j++) {
            if (j % 3 == 0) process.stdout.write("| ");
            if (globalBoard[j][i] != 0) process.stdout.write("\033[0;32m");
            if (board[j][i] == 0) process.stdout.write("  ");
            else process.stdout.write(board[j][i] + ' ');
            if (globalBoard[j][i] != 0) process.stdout.write("\033[0m");
        }
        process.stdout.write('|' + '\n');
    }
    process.stdout.write('\n');
}

function complete(board) {
    for (let i = 0; i < 9; i++) {
        let count = [];
        for (let j = 0; j < 9; j++) {
            if (board[j][i] == 0) return 0;
            count[board[j][i]]++;
        }
        for (let j = 1; j <= 9; j++) {
            if (count[j] != 1) {
                return 0;
            }
        }
    }
    for (let i = 0; i < 9; i++) {
        let count = [];
        for (let j = 0; j < 9; j++) {
            if (board[i][j] == 0) return 0;
            count[board[i][j]]++;
        }
        for (let j = 1; j <= 9; j++) {
            if (count[j] != 1) {
                return 0;
            }
        }
    }
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            let count = [];
            for (let y = 3 * i; y < 3 * i + 3; y++) {
                for (let x = 3 * j; x < 3 * j + 3; x++) {
                    if (board[x][y] == 0) return 0;
                    count[board[x][y]]++;
                }
            }
            for (let x = 1; x <= 9; x++) {
                if (count[x] != 1) {
                    return 0;
                }
            }
        }
    }
    return 1;
}

function scanner() {
    console.log(process.argv[3]);
    console.log("This is what is being searched for " + './' + process.argv[3]);

    let file = fs.readFileSync('./' + process.argv[3], 'utf8');
    console.log(file);
    let board = [];
    for (let i = 0; i < 9; i++) {
        board[i] = [];
    }
    let p = 0;
    console.log("Enter board with blanks as 0 and with numbers seperated by spaces or newlines left to right top to bottom");
    for (let i = 0; i < 9; i++) {
        for (let j = 0; j < 9; j++) {
            board[j][i] = Number(file[p]);
            p += 2;
            //console.log(board);
            //board[j][i] = prompt();
        }
    }
    //console.log(board);
    globalBoard = clone(board);
    //console.log(globalBoard);
    //console.log(board);
    return board;
}

function firstX(board) {
    for (let i = 0; i < 9; i++) {
        for (let j = 0; j < 9; j++) {
            if (board[i][j] == 0) {
                return i;
            }
        }
    }
    return 9;
}
function firstY(board) {
    for (let i = 0; i < 9; i++) {
        for (let j = 0; j < 9; j++) {
            if (board[i][j] == 0) {
                return j;
            }
        }
    }
    return 9;
}
