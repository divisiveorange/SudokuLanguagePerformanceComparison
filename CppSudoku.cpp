#include <iostream>
#include <stdlib.h>
#include <cstdio>

using namespace std;

typedef struct sudokuBoard {
    char b[9][9];
} board;
struct twoChar {
    char x;
    char y;
};

board globalBoard;
int kounter = 0;
int speed = 1;
int totalCount = 0;

board scanner();
void printer(board *board);
char allowed(board *board, char n, char x, char y);
void meat(board *board, char n, char x, char y);
char complete(board board);
char firstX(board board);
char firstY(board board);
void end(board *board);
void allPossibilites(board *board, char x, char y, char possibilites[9]);

char twoToOne(char x, char y) {
    return y * 9 + x;
}

void nextSquare(board *board, char *x, char *y) {
    while (board->b[*x][*y] != 0) {
        *y = *y + (*x >= 8);
        *x = (*x + 1) * (*x < 8);
        if (*y >= 9) {
            end(board);
        }
    }
}

void end(board *board) {
    printer(board);
    //complete(*board);
    cout << "SUCCESS" << endl;
    cout << "Took " << totalCount << " guesses" << endl;
    exit(1);
}



int main(int argc, char* argv[]) {
    board board = scanner();
    printer(&board);
    char x = 0;
    char y = 0;
    
    nextSquare(&board, &x, &y);
    for (int i = 1; i <= 9; i++) {
        if (allowed(&board, i, x, y)) {
            meat(&board, i, x, y);
        }
    }
}

void meat(board *board, char n, char x, char y) {
    // char x = firstX(board);
    // char y = firstY(board);
    totalCount++;
    board->b[x][y] = n;
    nextSquare(board, &x, &y);
    char possibilites[10] = {0};
    allPossibilites(board, x, y, possibilites);
    for (char i = 1; i <= 9; i++) {
        if (!possibilites[i]) {
            meat(board, i, x, y);
        }
    }
    board->b[x][y] = 0;
    return;
}

void allPossibilites(board *board, char x, char y, char possibilites[10]) {
    for (int i = 0; i < 9; i++) {
        possibilites[board->b[x][i]]++;
        possibilites[board->b[i][y]]++;
    }
    char cellX = x / 3 * 3;
    char cellY = y / 3 * 3;
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            possibilites[board->b[cellX + j][cellY + i]]++;
        }
    }
}

char allowed(board *board, char n, char x, char y) {
    for (int i = 0; i < 9; i++) {
        if (board->b[x][i] == n) {
            //cout << n << " at " << x << ", " << i << endl;
            return 0;
        }
    }
    for (int i = 0; i < 9; i++) {
        if (board->b[i][y] == n) {
            //cout << n << " at " << i << ", " << y << endl;
            return 0;
        }
    }
    char cellX = x / 3 * 3;
    char cellY = y / 3 * 3;
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (board->b[cellX + j][cellY + i] == n) {
                //cout << n << " at " << cellX + j << ", " << cellY + i << endl;
                return 0;
            }
        }
    }
    return 1;
}

void printer(board *board) {
    system("clear");
    cout << flush;
    for (int i = 0; i < 9; i++) {
        if (i % 3 == 0) cout << "-------------------------" << '\n';
        for (int j = 0; j < 9; j++) {
            if (j % 3 == 0) cout << "| ";
            if (globalBoard.b[j][i] != 0) cout << "\033[0;32m";
            if (board->b[j][i] == 0) cout << "  ";
            else cout << (int)board->b[j][i] << ' ';
            if (globalBoard.b[j][i] != 0) cout << "\033[0m";
        }
        cout << '|' << '\n';
    }
    cout << '\n';
}

char complete(board board) {
    for (int i = 0; i < 9; i++) {
        char count[9] = {0};
        for (int j = 0; j <=9; j++) {
            if (board.b[j][i] == 0) return 0;
            count[board.b[j][i]]++;
        }
        for (int j = 1; j <= 9; j++) {
            if (count[j] != 1) {
                return 0;
            }
        }
    }
    for (int i = 0; i < 9; i++) {
        char count[9] = {0};
        for (int j = 0; j < 9; j++) {
            if (board.b[i][j] == 0) return 0;
            count[board.b[i][j]]++;
        }
        for (int j = 1; j <= 9; j++) {
            if (count[j] != 1) {
                return 0;
            }
        }
    }
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            char count[9] = {0};
            for (int y = 3 * i; y < 3 * i + 3; y++) {
                for (int x = 3 * j; x < 3 * j + 3; x++) {
                    if (board.b[x][y] == 0) return 0;
                    count[board.b[x][y]]++;
                }
            }
            for (int x = 1; x <= 9; x++) {
                if (count[x] != 1) {
                    return 0;
                }
            }
        }
    }
    return 1;
}

board scanner() {
    board board;
    cout << "Enter board with blanks as 0 and with numbers seperated by spaces or newlines left to right top to bottom" << endl;
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            int temp;
            scanf("%d", &temp);
            board.b[j][i] = temp;
        }
    }
    globalBoard = board;
    return board;
}

char firstX(board board) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board.b[i][j] == 0) {
                return i;
            }
        }
    }
    return 9;
}
char firstY(board board) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board.b[i][j] == 0) {
                return j;
            }
        }
    }
    return 9;
}