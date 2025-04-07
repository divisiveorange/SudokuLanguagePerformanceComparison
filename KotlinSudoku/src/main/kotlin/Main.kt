import java.util.Arrays
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val board = setter()
    printer(board)
    var (x, y) = nextSquare(0, 0, board)
    val possibilities = allowed(x, y, board)
    for (i in 1..9) {
        if (!possibilities[i]) {
            meat(x, y, i, board)
        }
    }
    board[x][y] = 0
    println("failure")
}

fun meat(xIn: Int, yIn: Int, n: Int, board: Array<IntArray>) {
    var x = xIn
    var y = yIn
    board[x][y] = n
    val coord = nextSquare(x, y, board)
    x = coord.first
    y = coord.second
    val possibilities = allowed(x, y, board)
    for (i in 1..9) {
        if (!possibilities[i]) {
            meat(x, y, i, board)
        }
    }
    board[x][y] = 0
}

fun setter() : Array<IntArray> {
    println("Type out board")
    val board = Array(9) {IntArray(9)}
    for (i in 0..8) {
        val line = readln()
        val chars = line.split(" ")
        for (j in 0..8) {
            board[i][j] = chars[j].toInt()
        }
    }
    return board
}

fun printer(board : Array<IntArray>) {
    for (row in board) {
        for (num in row) {
            print(num)
            print(" ")
        }
        println()
    }
}

fun allowed(x: Int, y:Int, board: Array<IntArray>): BooleanArray {
    val possibilities = BooleanArray(10)
    for (i in 0..8) {
        possibilities[board[x][i]] = true
        possibilities[board[i][y]] = true
    }
    val xBound = x / 3 * 3
    val yBound = y / 3 * 3
    for (i in xBound..xBound + 2) {
        for (j in yBound..yBound + 2) {
            possibilities[board[i][j]] = true
        }
    }
    return possibilities
}

fun nextSquare(xIn: Int, yIn: Int, board: Array<IntArray>): Pair<Int, Int> {
    var x = xIn
    var y = yIn
    while (y < 9 && board[x][y] != 0) {
        y += ((x + 1) / 9)
        x = (x + 1) % 9
    }
    if (y >= 9) {
        end(board)
    }
    return Pair(x, y)
}

fun end(board: Array<IntArray>) {
    println("Success")
    printer(board)
    exitProcess(0)
}