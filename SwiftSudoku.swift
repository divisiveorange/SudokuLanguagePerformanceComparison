import Foundation
print("Type input with a row being a line, characters seperated by spaces and 0 being blank")
var board = setter()
let OgBoard = boardCloner(board: board)
printer(array: &board)
var (x, y) = nextSquare(xIn: 0, yIn: 0, board: &board)
var impossibilities = allPossibilities(x: x, y: y, board: &board)
for i in 1...9 {
    if (!impossibilities[i]) {
        meat(xIn: x, yIn: y, n: i, board: &board)
    }
}
board[x][y] = 0
print("Failure")
printer(array: &board)

func boardCloner(board: [[Int]]) -> [[Int]] {
    var clone = [[Int]]()
    var i = 0
    for arr in board {
        clone.append([Int]())
        for item in arr {
            clone[i].append(item)
        }
        i += 1
    }
    return clone
}

func end(board: inout [[Int]]) {
    print("Sucess!")
    printer(array: &board)
    exit(0)
}

func nextSquare(xIn: Int, yIn: Int, board: inout [[Int]]) -> (Int, Int) {
    var x = xIn
    var y = yIn
    while y < 9 && board[x][y] != 0 {
        y = y + ((x + 1) / 9)
        x = (x + 1) % 9
    }
    if y >= 9 {
        end(board: &board)
    }
    return (x, y)
}

func meat(xIn: Int, yIn: Int, n: Int, board: inout [[Int]]) {
    var x = xIn
    var y = yIn
    board[x][y] = n
    (x, y) = nextSquare(xIn: x, yIn: y, board: &board)
    let impossibilities = allPossibilities(x: x, y: y, board: &board)
    for i in 1...9 {
        if (!impossibilities[i]) {
            meat(xIn: x, yIn: y, n: i, board: &board)
        }
    }
    board[x][y] = 0
}

func allPossibilities(x: Int, y: Int, board: inout [[Int]]) -> [Bool] {
    var impossibilities = [Bool](repeating: false, count: 10)
    for i in 0...8 {
        impossibilities[board[x][i]] = true
        impossibilities[board[i][y]] = true
    }
    let xBound = x / 3 * 3
    let yBound = y / 3 * 3
    for i in xBound...xBound + 2 {
        for j in yBound...yBound + 2 {
            impossibilities[board[i][j]] = true
        }
    }
    return impossibilities
}

func setter() -> [[Int]] {
    var array = [[Int]]();
    for i in 0...8 {
        array.append([Int]())
        let line = readLine()
        let strings = line!.split(separator: " ", maxSplits: 1000, omittingEmptySubsequences: true)
        for j in 0...8 {
            array[i].append(Int(strings[j]) ?? 0)
        }
    }
    return array
}

func printer(array: inout [[Int]]) {
    for i in 0...8 {
        for j in 0...8 {
            print("\u{001B}[0;36m", terminator: "")
            if OgBoard[i][j] > 0 {
                print("\u{001B}[0;35m", terminator: "")
            }
            print(array[i][j], terminator: " ")
        }
        print()
    }
}

