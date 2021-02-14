import java.util.Arrays;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

    private TURN turn;
    private final char[] board;

    Game() {
        turn = TURN.X;
        board = new char[9];
        Arrays.fill(board, '-');
    }

    void play() {
        draw();
        // Main loop
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (isDraw()) {
                System.out.println("It's a draw!!");
                break;
            }

            System.out.print(turn + "'s turn: ");
            if (scanner.hasNext()) {
                String input = scanner.next();
                if (input.equals("q") || input.equals("quit")) {
                    break;
                }

                // Get move
                if (input.length() == 2) {
                    input = input.toUpperCase();

                    char first = input.charAt(0);
                    if (first >= 'A' && first <= 'C') {
                        char second = input.charAt(1);
                        if (second >= '0' && second <= '2') {
                            // Valid moves
                            int col = first - 'A';
                            int row = second - '0';

                            if (placeOnBoard(col, row, turn)) {
                                draw();
                                if (hasWinner()) {
                                    System.out.println(turn + " won!!!");
                                    break;
                                }
                                switchTurn();
                            }
                        }
                    }
                }
            }
        }
    }

    void draw() {
        clearScreen();
        System.out.println("    A     B     C");
        System.out.println("       |     |   ");
        System.out.println("0   " + board[0] + "  |  " + board[1] + "  |  " + board[2]);
        System.out.println("  _____|_____|_____");
        System.out.println("       |     |");
        System.out.println("1   " + board[3] + "  |  " + board[4] + "  |  " + board[5]);
        System.out.println("  _____|_____|_____");
        System.out.println("       |     |");
        System.out.println("2   " + board[6] + "  |  " + board[7] + "  |  " + board[8]);
        System.out.println("       |     |");
    }

    boolean isOccupied(int pos) {
        return board[pos] != '-';
    }

    boolean checkThree(int first, int second, int third) {
        return isOccupied(first) && isOccupied(second) && isOccupied(third) &&
                board[first] == board[second] && board[second] == board[third];
    }
    boolean hasWinner() {
        // 3 Horizontal
        boolean horizontal =
                checkThree(0, 1, 2) ||
                checkThree(3, 4, 5) ||
                checkThree(6, 7 , 8);
        // 3 Vertical
        boolean vertical =
                checkThree(0, 3, 6) ||
                checkThree(1, 4, 7) ||
                checkThree(2, 5 , 8);
        // 2 Diagonal
        boolean diagonal =
                checkThree(0, 4, 8) ||
                checkThree(2, 4 , 6);
        return horizontal || vertical || diagonal;
    }

    boolean isDraw() {
        int spaces = 0;
        for (char c : board) {
            if (c == '-') {
                ++spaces;
            }
        }
        return spaces == 0;
    }

    void switchTurn() {
        if(turn == TURN.O) {
            turn = TURN.X;
        } else {
            turn = TURN.O;
        }
    }

    void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    boolean placeOnBoard(int col, int row, TURN turn) {
        final int pos = row * 3 + col;

        if (isOccupied(pos)) {
            return false;
        }

        switch (turn) {
            case O -> board[pos] = 'O';
            case X -> board[pos] = 'X';
        }
        return true;
    }
}

enum TURN {
   X,
   O
}
