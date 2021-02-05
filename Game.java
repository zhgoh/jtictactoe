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
            if (hasWinner() || !hasMoves()) {
                System.out.println("Game Ended");
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
                        if (second >= '1' && second <= '3') {
                            // Valid moves
                            int x = first - 'A';
                            int y = second - '1';

                            if (setBoard(x, y, turn)) {
                                switchTurn();
                                draw();
                            }
                        }
                    }
                }
            }
        }
    }

    void draw() {
        clearScreen();
        System.out.println("    a     b     c");
        System.out.println("       |     |   ");
        System.out.println("1   " + board[0] + "  |  " + board[1] + "  |  " + board[2]);
        System.out.println("  _____|_____|_____");
        System.out.println("       |     |");
        System.out.println("2   " + board[3] + "  |  " + board[4] + "  |  " + board[5]);
        System.out.println("  _____|_____|_____");
        System.out.println("       |     |");
        System.out.println("3   " + board[6] + "  |  " + board[7] + "  |  " + board[8]);
        System.out.println("       |     |");
    }

    boolean isNotEmpty(int one) {
        return board[one] != '-';
    }

    boolean checkThree(int one, int two, int three) {
        return isNotEmpty(one) && isNotEmpty(two) && isNotEmpty(three) &&
                board[one] == board[two] && board[two] == board[three];
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

    boolean hasMoves() {
        for (char c : board) {
            if (c == '-') {
                return true;
            }
        }
        return false;
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

    boolean setBoard(int x, int y, TURN turn) {
        if (board[y * 3 + x] != '-') {
            return false;
        }
        switch (turn) {
            case O -> board[y * 3 + x] = 'O';
            case X -> board[y * 3 + x] = 'X';
        }
        return true;
    }
}

enum TURN {
   X,
   O
}