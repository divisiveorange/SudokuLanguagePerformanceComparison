using System;
class Sudoku
{
    static void Main()
    {
        var game = new Sudoku();
    }
    class TwoInt
    {
        public int x;
        public int y;
        public TwoInt(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    Sudoku()
    {
        Console.WriteLine("type the board with blanks as zero as 9 rows of 9 seperated by spaces");
        setter();
        printer();
        //bool[] possibilities = new bool[10];
        Span<bool> possibilities = stackalloc bool[10];
        int x = 0;
        int y = 0;
        //TwoInt twoInt = nextTwoInt(x, y);
        //x = twoInt.x;
        //y = twoInt.y;
        nextCoords(ref x, ref y);
        allPosibilities(x, y, possibilities);
        for (int i = 1; i < 10; i++)
        {
            if (!(possibilities[i]))
            {
                meat(x, y, i);
                board[x, y] = 0;
            }
        }
        Console.WriteLine("Failure");
        printer();
    }

    void end()
    {
        Console.WriteLine("Finished successfully");
        printer();
        System.Environment.Exit(1);
    }

    void meat(int x, int y, int n)
    {
        board[x, y] = n;
        //bool[] possibilities = new bool[10];
        Span<bool> possibilities = stackalloc bool[10];
        nextCoords(ref x, ref y);
        //TwoInt twoInt = nextTwoInt(x, y);
        //x = twoInt.x;
        //y = twoInt.y;
        allPosibilities(x, y, possibilities);
        for (int i = 1; i < 10; i++)
        {
            if (!(possibilities[i]))
            {
                meat(x, y, i);
            }
        }
        board[x, y] = 0;
    }

    int[ , ] board = new int[9, 9];
    int[ , ] OgBoard;

    void printer()
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Console.ForegroundColor = ConsoleColor.Cyan;
                if (OgBoard[i, j] != 0)
                {
                    Console.ForegroundColor = ConsoleColor.Magenta;
                }
                Console.Write(" " + (Int32) board[i, j] + " ");
            }
            Console.WriteLine();
        }
    }

    void setter()
    {
        for (int i = 0; i < 9; i++)
        {
            string row = Console.ReadLine();
            string[] rows = row.Split(' ');
            for (int j = 0; j < 9; j++)
            {
                board[i, j] =  Int32.Parse(rows[j]);
            }
        }
        OgBoard = (int[ , ]) board.Clone();
    }

    void allPosibilities(int x, int y, Span<bool> possibilies)
    {
        for(int i = 0; i < 9; i++)
        {
            possibilies[board[x, i]] = true;
        }
        for(int i = 0; i < 9; i++)
        {
            possibilies[board[i, y]] = true;
        }
        int xBound =  (x / 3 * 3);
        int yBound =  (y / 3 * 3);
        for (int i = xBound; i < xBound + 3; i++)
        {
            for (int j = yBound; j < yBound + 3; j++)
            {
                possibilies[board[i, j]] = true;
            }
        }
    }

    void nextCoords(ref int x, ref int y)
    {
        //while (board[x, y] != 0)
        //{
        //    y = y + ((x >= 8) ? 1 : 0);
        //    x = ((x >= 8) ? 0 : x + 1);
        //    if (y >= 9)
        //    {
        //        end();
        //    }
        //}
        while (board[x, y] != 0)
        {
            y = (y + ((x + 1) / 9));
            x = ((x + 1) % 9);
            if (y >= 9)
            {
                end();
            }
        }
    }
    TwoInt nextTwoInt(int x, int y)
    {
        while (board[x, y] != 0)
        {
            y = (y + ((x + 1) / 9));
            x = ((x + 1) % 9);
            if (y >= 9)
            {
                end();
            }
        }
        return new TwoInt(x, y);
    }
}