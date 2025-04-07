use std::{process::exit};

fn main() {
    println!("Type in board");
    let mut board = setter();
    printer(&board);
    let mut x = 0;
    let mut y= 0;
    next_square(&mut x, &mut y, &mut board);
    let mut options = [true; 10];
    possibilities(x, y, &board, &mut options);
    for i in 1..10 {
        if options[i] {
            meat(x, y, i as i8, &mut board);
        }
    }
    board[x][y] = 0;
}

fn meat(mut x: usize, mut y: usize, n: i8, board: &mut [[i8; 9]; 9]) {
    unsafe {
        *board.get_unchecked_mut(x).get_unchecked_mut(y) = n;
        next_square(&mut x, &mut y, board);
        let mut options = [true; 10];
        possibilities(x, y, board, &mut options);
        for i in 1..10 {
            if *options.get_unchecked(i) {
                meat(x, y, i as i8, board);
            }
        }
        board[x][y] = 0;
    }
}


fn setter() -> [[i8; 9]; 9] {
    let mut board = [[0i8; 9]; 9];
    for i in 0..9 {
        let mut line = String::new();
        std::io::stdin().read_line(&mut line).unwrap();
        line = line.trim().to_string();
        let rows_str = line.split(" ");
        let mut j = 0;
        for num in rows_str {
           board[i][j] = num.parse::<i8>().unwrap();
           j += 1;
        }
    }
    return board;
}

fn printer(board: &[[i8; 9]; 9]) {
    for i in 0..9 {
        for j in 0..9 {
            print!("{}", board[i][j]);
            print!(" ");
        }
        println!();
    }
}

fn possibilities(x: usize, y: usize, board: &[[i8; 9]; 9], possibilities: &mut[bool; 10]) {
    unsafe {
        for i in 0..9 {
            *possibilities.get_unchecked_mut(*board.get_unchecked(i).get_unchecked(y) as usize) = false;
            *possibilities.get_unchecked_mut(*board.get_unchecked(x).get_unchecked(i) as usize) = false;
        }
        let x_bound = x / 3 * 3;
        let y_bound = y / 3 * 3;
        for i in x_bound..x_bound + 2 {
            for j in y_bound..y_bound + 2 {
                *possibilities.get_unchecked_mut(*board.get_unchecked(i).get_unchecked(j) as usize) = false;
            }
        }
    }
}

fn next_square(x: &mut usize, y: &mut usize, board: &[[i8; 9]; 9]) {
    unsafe {
        while *board.get_unchecked(*x).get_unchecked(*y) != 0 {
            *y = *y + ((*x >= 8) as usize);
            *x = (*x + 1) * (*x < 8) as usize;
            if *y >= 9 {
                end(board);
            }
        }
    }
}

fn end(board: &[[i8; 9]; 9]) {
    println!("Success");
    printer(board);
    exit(0);
}

