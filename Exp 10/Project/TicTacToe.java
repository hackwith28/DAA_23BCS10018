import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.util.Timer;
import java.util.TimerTask;

class TicTacToe extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private GameBoard game;
    private boolean playWithAI;
    private String playerX, playerO;
    private Font gameFont;
    private WinningLinePanel linePanel;
    private JPanel boardPanel;

    public TicTacToe() {
        setTitle("Tic Tac Toe - DSA Project");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadFont();
        askMode();
        getPlayerNames();

        game = new GameBoard();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new OverlayLayout(mainPanel));

        boardPanel = new JPanel(new GridLayout(3, 3)) {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                return new Dimension(size.height, size.height);
            }
        };
        boardPanel.setBackground(new Color(201, 255, 223));

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton();
                btn.setFont(gameFont.deriveFont(48f));
                btn.setBackground(new Color(195, 139, 223));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                int r = i, c = j;
                btn.addActionListener(e -> handleMove(r, c));
                buttons[i][j] = btn;
                boardPanel.add(btn);
            }

        linePanel = new WinningLinePanel();
        mainPanel.add(linePanel);
        mainPanel.add(boardPanel);

        JPanel controls = new JPanel();
        JButton reset = new JButton("Reset");
        JButton undo = new JButton("Undo");

        for (JButton btn : new JButton[]{reset, undo}) {
            btn.setFont(gameFont.deriveFont(20f));
            btn.setBackground(new Color(195, 139, 223));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }

        reset.addActionListener(e -> resetGame());
        undo.addActionListener(e -> undoMove());

        controls.add(reset);
        controls.add(undo);
        controls.setBackground(new Color(90, 35, 15));

        add(mainPanel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        // Fix resizing issue
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                boardPanel.revalidate();
                boardPanel.repaint();
                linePanel.repaint();
            }
        });
    }

    private void loadFont() {
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("AlfaSlabOne-Regular.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(gameFont);
        } catch (Exception e) {
            gameFont = new Font("Serif", Font.BOLD, 28); // fallback
        }
    }

    private void askMode() {
        int choice = JOptionPane.showOptionDialog(this, "Choose game mode:",
                "Game Mode", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Play vs AI", "Multiplayer"}, "Play vs AI");
        playWithAI = (choice == 0);
    }

    private void getPlayerNames() {
        playerX = JOptionPane.showInputDialog(this, "Enter Player X name:");
        if (playWithAI) {
            playerO = "AI Bot";
        } else {
            playerO = JOptionPane.showInputDialog(this, "Enter Player O name:");
        }
        if (playerX == null || playerX.isEmpty()) playerX = "Player X";
        if (playerO == null || playerO.isEmpty()) playerO = "Player O";
    }

    private void handleMove(int row, int col) {
        if (game.makeMove(row, col)) {
            buttons[row][col].setText(String.valueOf(game.getCurrentPlayer()));
            processGameState();

            // If AI mode and it's AI's turn
            if (playWithAI && game.getCurrentPlayer() == 'O' && game.getWinner() == ' ') {
                int[] aiMove = game.getBestMove();
                if (aiMove[0] != -1) {
                    game.makeMove(aiMove[0], aiMove[1]);
                    buttons[aiMove[0]][aiMove[1]].setText("O");
                    processGameState();
                }
            }
        }
    }

    private void processGameState() {
        int[][] win = game.getWinningLine();
        if (win != null) {
            String winner = game.getCurrentPlayer() == 'X' ? playerX : playerO;
            linePanel.animateLine(win[0][1], win[0][0], win[2][1], win[2][0]);
            disableButtons();
            new Timer().schedule(new TimerTask() {
                public void run() {
                    JOptionPane.showMessageDialog(TicTacToe.this, winner + " wins!");
                }
            }, 800);
        } else if (game.isDraw()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            disableButtons();
        } else {
            game.switchPlayer();
        }
    }

    private void resetGame() {
        game.clearBoard();
        linePanel.clearLine();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
    }

    private void undoMove() {
        if (game.undo()) {
            refreshBoard();
            linePanel.clearLine();
        }
    }

    private void refreshBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText(game.getCell(i, j) == ' ' ? "" : String.valueOf(game.getCell(i, j)));
    }

    private void disableButtons() {
        for (JButton[] row : buttons)
            for (JButton b : row)
                b.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }

    // Drawing animation
    class WinningLinePanel extends JPanel {
        private int startRow, startCol, endRow, endCol;
        private double progress = 0.0;
        private Timer timer;

        public WinningLinePanel() {
            setOpaque(false);
        }

        public void animateLine(int col1, int row1, int col2, int row2) {
            this.startCol = col1;
            this.startRow = row1;
            this.endCol = col2;
            this.endRow = row2;
            this.progress = 0.0;

            if (timer != null) timer.cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    progress += 0.05;
                    if (progress >= 1.0) {
                        progress = 1.0;
                        timer.cancel();
                    }
                    repaint();
                }
            }, 0, 20);
        }

        public void clearLine() {
            if (timer != null) timer.cancel();
            progress = 0;
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (progress == 0) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(6));

            int w = getWidth() / 3;
            int h = getHeight() / 3;

            int x1 = startCol * w + w / 2;
            int y1 = startRow * h + h / 2;
            int x2 = endCol * w + w / 2;
            int y2 = endRow * h + h / 2;

            int dx = (int) ((x2 - x1) * progress);
            int dy = (int) ((y2 - y1) * progress);

            g2.drawLine(x1, y1, x1 + dx, y1 + dy);
        }
    }
}
