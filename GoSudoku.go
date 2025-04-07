package main

import (
	"fmt"
	"os"
	"time"
)

func main() {
	var board = setter()
	printer(&board)
	var x int8 = 0
	var y int8 = 0
	nextSquare(&x, &y, &board)
	var possibilities [10]bool
	options(x, y, &board, &possibilities)
	//var copies [10][9][9]int8
	for i := 1; i <= 9; i++ {
		if possibilities[i] == false {
			//copies[i] = board
			go meatNewThread(x, y, int8(i), board)
		}
	}
	board[x][y] = 0
	time.Sleep(1 * time.Second)
	fmt.Println("Failure")
}

func meatNewThread(x int8, y int8, n int8, board [9][9]int8) {
	board[x][y] = n
	nextSquare(&x, &y, &board)
	var possibilities [10]bool
	options(x, y, &board, &possibilities)
	var copies [10][9][9]int8
	for i := 1; i <= 9; i++ {
		if possibilities[i] == false {
			copies[i] = board
			go meat(x, y, int8(i), &copies[i])
		}
	}
	board[x][y] = 0
}

func meat(x int8, y int8, n int8, board *[9][9]int8) {
	board[x][y] = n
	nextSquare(&x, &y, board)
	var possibilities [10]bool
	options(x, y, board, &possibilities)
	for i := 1; i <= 9; i++ {
		if !possibilities[i] {
			meat(x, y, int8(i), board)
		}
	}
	board[x][y] = 0
}

func setter() [9][9]int8 {
	var board [9][9]int8
	for i := 0; i < 9; i++ {
		for j := 0; j < 9; j++ {
			fmt.Scan(&board[i][j])
		}
	}
	return board
}

func printer(board *[9][9]int8) {
	for _, arr := range board {
		for _, n := range arr {
			fmt.Print(n)
			fmt.Print(" ")
		}
		fmt.Println()
	}
}

func options(x int8, y int8, board *[9][9]int8, possibilities *[10]bool) {
	for i := 0; i < 9; i++ {
		possibilities[board[x][i]] = true
		possibilities[board[i][y]] = true
	}
	var xBound = x / 3 * 3
	var yBound = y / 3 * 3
	for i := xBound; i < xBound+3; i++ {
		for j := yBound; j < yBound+3; j++ {
			possibilities[board[i][j]] = true
		}
	}
}

func nextSquare(x *int8, y *int8, board *[9][9]int8) {
	for board[*x][*y] != 0 {
		*y = *y + ((*x + 1) / 9)
		*x = (*x + 1) % 9
		if *y >= 9 {
			end(board)
		}
	}
}

func end(board *[9][9]int8) {
	fmt.Println("Success")
	printer(board)
	os.Exit(0)
}
