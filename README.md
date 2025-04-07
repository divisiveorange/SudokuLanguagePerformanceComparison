# SudokuLanguagePerformanceComparison
A collection of implementations of Sudoku solvers written in different languages. 
For my own exploration hence a lack of user friendliness.

Sudoku boards comes from the boards folder. 

For most of the implementations, they are run like

./sudokuExecutable < boards/board.brd

But the javascript implementation requires 

node Sudoku.js boards/board.brd


Either board6.brd or board8.brd is the hardest to solve (depending on the order in which the programs go through the grid)

Algorithmically, the implementations should all be the same.


Interesting findings, Rust performs quite poorly in this test (similar to Java, behind C# and well behind C++), C++ and Rust's ability to treat values as booleans without any instructions mean they can avoid a division where other languages with non-no-op casting are faster when a division is used.

