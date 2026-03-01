package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private boolean gameEnded = false;

    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private JButton replayButton;
    private JButton exitButton;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public Gameplay() {

        map = new MapGenerator(3, 7);

        setLayout(null);

        replayButton = new JButton("Replay");
        replayButton.setBounds(250, 350, 100, 30);
        replayButton.setVisible(false);
        add(replayButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(370, 350, 100, 30);
        exitButton.setVisible(false);
        add(exitButton);

        replayButton.addActionListener(e -> resetGame());
        exitButton.addActionListener(e -> System.exit(0));

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    // ✅ Correct Swing method
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Score: " + score, 550, 30);

        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (gameEnded) {
            g.setFont(new Font("serif", Font.BOLD, 30));

            if (ballposY > 570) {
                g.setColor(Color.red);
                g.drawString("Game Over, Score: " + score, 190, 300);
            } else {
                g.setColor(Color.green);
                g.drawString("You Won, Score: " + score, 190, 300);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (play) {

            if (new Rectangle(ballposX, ballposY, 20, 20)
                    .intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {

                    if (map.map[i][j] > 0) {

                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;

                        Rectangle rect = new Rectangle(
                                brickX, brickY,
                                map.brickWidth, map.brickHeight
                        );

                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(rect)) {

                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= rect.x ||
                                    ballposX + 1 >= rect.x + rect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0 || ballposX > 670)
                ballXdir = -ballXdir;

            if (ballposY < 0)
                ballYdir = -ballYdir;

            // 🔴 Game Over Check
            if (ballposY > 570) {
                endGame();
            }

            // 🟢 Win Check
            if (totalBricks <= 0) {
                endGame();
            }
        }

        repaint();
    }

    // ✅ Proper End Game Method
    private void endGame() {
        play = false;
        gameEnded = true;
        ballXdir = 0;
        ballYdir = 0;

        replayButton.setVisible(true);
        exitButton.setVisible(true);
    }

    public void resetGame() {

        play = false;
        gameEnded = false;

        ballposX = 120;
        ballposY = 350;
        ballXdir = -1;
        ballYdir = -2;
        playerX = 310;
        score = 0;
        totalBricks = 21;

        map = new MapGenerator(3, 7);

        replayButton.setVisible(false);
        exitButton.setVisible(false);

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!gameEnded) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && playerX < 600)
                moveRight();

            if (e.getKeyCode() == KeyEvent.VK_LEFT && playerX > 10)
                moveLeft();
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}