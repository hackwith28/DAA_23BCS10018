import java.util.*;

class GameBoard {
    private char[][] board;
    private char currentPlayer;
    private Stack<int[]> moveHistory;

    public GameBoard() {
        board = new char[3][3];
        moveHistory = new Stack<>();
        currentPlayer = 'X';
        clearBoard();
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            moveHistory.push(new int[]{row, col});
            return true;
        }
        return false;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public boolean undo() {
        if (!moveHistory.isEmpty()) {
            int[] last = moveHistory.pop();
            board[last[0]][last[1]] = ' ';
            switchPlayer();
            return true;
        }
        return false;
    }

    public boolean isDraw() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == ' ') return false;
        return getWinner() == ' ';
    }

    public char getWinner() {
        for (int i = 0; i < 3; i++) {
            if (same(board[i][0], board[i][1], board[i][2])) return board[i][0];
            if (same(board[0][i], board[1][i], board[2][i])) return board[0][i];
        }
        if (same(board[0][0], board[1][1], board[2][2])) return board[0][0];
        if (same(board[0][2], board[1][1], board[2][0])) return board[0][2];
        return ' ';
    }

    private boolean same(char a, char b, char c) {
        return a != ' ' && a == b && b == c;
    }

    public int[][] getWinningLine() {
        for (int i = 0; i < 3; i++) {
            if (same(board[i][0], board[i][1], board[i][2]))
                return new int[][]{{i, 0}, {i, 1}, {i, 2}};
            if (same(board[0][i], board[1][i], board[2][i]))
                return new int[][]{{0, i}, {1, i}, {2, i}};
        }
        if (same(board[0][0], board[1][1], board[2][2]))
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};
        if (same(board[0][2], board[1][1], board[2][0]))
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};
        return null;
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void clearBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
        moveHistory.clear();
        currentPlayer = 'X';
    }

    // -------------------------------
    // AI Section: Minimax Algorithm
    // -------------------------------

    public int[] getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') {
                    board[i][j] = 'O'; // AI is 'O'
                    int score = minimax(false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }

        return bestMove;
    }

    private int minimax(boolean isMaximizing) {
        char winner = getWinner();
        if (winner == 'O') return 1;
        if (winner == 'X') return -1;
        if (isDraw()) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') {
                    board[i][j] = isMaximizing ? 'O' : 'X';
                    int score = minimax(!isMaximizing);
                    board[i][j] = ' ';
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }

        return bestScore;
    }
}
