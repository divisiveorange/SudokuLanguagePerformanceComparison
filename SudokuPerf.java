import java.util.Scanner;

public class SudokuPerf {
    public class board {
        public byte[][] b = new byte[9][9];
        board() {
            b = new byte[9][9];
        }
        @Override
        public board clone() {
            board newBoard = new board();
            for (byte i = 0; i < 9; i++) {
                newBoard.b[i] = b[i].clone();
            }
            return newBoard;
        }
    }
    public class twobyte {
        public byte x;
        public byte y;
    }
twobyte nextSquare(board board, byte x, byte y) {
    while (board.b[x][y] != 0) {
//        y = (byte) (y + ((x >= 8) ? 1 : 0));
//        x = (byte) ((x + 1) * ((x < 8) ? 1 : 0));
//        if (y >= 9) {
//            end(board);
//        }
        y = (byte)(y + ((x + 1) / 9));
        x = (byte)((x + 1) % 9);
         if (y >= 9) {
             end(board);
         }
    }
    twobyte returnable = new twobyte();
    returnable.x = x;
    returnable.y = y;
    return returnable;
}

board globalBoard;
byte kounter = 0;
byte speed = 1;
int totalCount = 0;

SudokuPerf() {
    board board = scanner();
    printer(board);
    twobyte coord = nextSquare(board, (byte)0, (byte)0);
    byte x = coord.x;
    byte y = coord.y;

    for (byte i = 1; i <= 9; i++) {
        if (allowed(board, i, x, y)) {
            meat(board, i, x, y);
        }
    }

}

public static void main(String[] args) {
    new SudokuPerf();
}

void end(board board) {
    printer(board);
    complete(board);
    System.out.println("SUCCESS");
    System.out.println("Took " + totalCount + " guesses");
    System.exit(1);
}

void meat(board board, byte n, byte x, byte y) {
    board.b[x][y] = n;
    twobyte coord = nextSquare(board, x, y);
    byte x2 = coord.x;
    byte y2 = coord.y;
    totalCount++;
    if (y2 >= 9) {
        end(board);
    }
    byte[] possibilites = new byte[10];
    allPossibilites(board, (byte)x2, (byte)y2, possibilites);
    for (byte i = 1; i <= 9; i++) {
        //cout << "checking " << i << endl;
        //if (allowed(board, i, x2, y2)) {
        if (possibilites[i] == 0) {
            //cout << i << " is valid" << endl;
            meat(board, i, x2, y2);
        }
    }
    board.b[x][y] = 0;
    return;
}

void allPossibilites(board board, byte x, byte y, byte[] possibilites) {
    for (byte i = 0; i < 9; i++) {
        possibilites[board.b[x][i]]++;
    }
    for (byte i = 0; i < 9; i++) {
        possibilites[board.b[i][y]]++;
            //cout << n << " at " << i << ", " << y << endl;
    }
    byte cellX = (byte)(x / 3 * 3);
    byte cellY = (byte)(y / 3 * 3);
    for (byte i = 0; i < 3; i++) {
        for (byte j = 0; j < 3; j++) {
            possibilites[board.b[cellX + j][cellY + i]]++;
        }
    }
}

boolean allowed(board board, byte n, byte x, byte y) {
    for (byte i = 0; i < 9; i++) {
        if (board.b[x][i] == n) {
            //cout << n << " at " << x << ", " << i << endl;
            return false;
        }
    }
    for (byte i = 0; i < 9; i++) {
        if (board.b[i][y] == n) {
            //cout << n << " at " << i << ", " << y << endl;
            return false;
        }
    }
    byte cellX = (byte) (x / 3 * 3);
    byte cellY = (byte) (y / 3 * 3);
    for (byte i = 0; i < 3; i++) {
        for (byte j = 0; j < 3; j++) {
            if (board.b[cellX + j][cellY + i] == n) {
                //cout << n << " at " << cellX + j << ", " << cellY + i << endl;
                return false;
            }
        }
    }
    return true;
}

void printer(board board) {
    //System.out.print("\033[H\033[2J");
    for (byte i = 0; i < 9; i++) {
        //if (i % 3 == 0) System.out.println("-------------------------");
        for (byte j = 0; j < 9; j++) {
            if (j % 3 == 0) System.out.print("| ");
            if (globalBoard.b[j][i] != 0) System.out.print("\033[0;32m");
            //System.out.print(board.b[i][j] + " ");
            if (board.b[j][i] == 0) {
                System.out.print("  ");
            } else {
                System.out.print(board.b[j][i] + " ");
            }
            if (globalBoard.b[j][i] != 0) System.out.print("\033[0m");
        }
        System.out.println('|');
    }
    System.out.println();
}

byte complete(board board) {
    for (byte i = 0; i < 9; i++) {
        byte[] count = new byte[10];
        for (byte j = 0; j < 9; j++) {
            if (board.b[j][i] == 0) return 0;
            count[board.b[j][i]]++;
        }
        for (byte j = 1; j <= 9; j++) {
            if (count[j] != 1) {
                return 0;
            }
        }
    }
    for (byte i = 0; i < 9; i++) {
        byte[] count = new byte[10];
        for (byte j = 0; j < 9; j++) {
            if (board.b[i][j] == 0) return 0;
            count[board.b[i][j]]++;
        }
        for (byte j = 1; j <= 9; j++) {
            if (count[j] != 1) {
                return 0;
            }
        }
    }
    for (byte i = 0; i < 3; i++) {
        for (byte j = 0; j < 3; j++) {
            byte[] count = new byte[10];
            for (byte y = (byte)((byte)3 * i); y < (byte)3 * i + (byte)3; y++) {
                for (byte x = (byte)(3 * j); x < 3 * j + 3; x++) {
                    if (board.b[x][y] == 0) return 0;
                    count[board.b[x][y]]++;
                }
            }
            for (byte x = 1; x <= 9; x++) {
                if (count[x] != 1) {
                    return 0;
                }
            }
        }
    }
    return 1;
}

board scanner() {
    board board = new board();
    System.out.println("Enter board with blanks as 0 and with numbers seperated by spaces or newlines left to right top to bottom");
    Scanner scanner = new Scanner(System.in);
    for (byte i = 0; i < 9; i++) {
        for (byte j = 0; j < 9; j++) {
            board.b[j][i] = (byte)scanner.nextInt();
        }
    }
    scanner.close();
    globalBoard = board.clone();
    return board;
}

byte firstX(board board) {
    for (byte i = 0; i < 9; i++) {
        for (byte j = 0; j < 9; j++) {
            if (board.b[i][j] == 0) {
                return (byte)i;
            }
        }
    }
    return 9;
}
byte firstY(board board) {
    for (byte i = 0; i < 9; i++) {
        for (byte j = 0; j < 9; j++) {
            if (board.b[i][j] == 0) {
                return (byte)j;
            }
        }
    }
    return 9;
}
}

