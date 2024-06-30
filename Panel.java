import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    static final int panel_height = 600;
    static final int panel_width = 600;
    final static int unit = 25;
    // static final int game_unit=(panel_width*panel_height)/unit;
    static int[] x = new int[unit];
    static int[] y = new int[unit];
    Random random;
    int bodyParts = 6;
    int appleX;
    int appleY;
    static final int delay = 100;
    Timer timer;
    int appleEaten = 0;
    char dir = 'D';
    boolean running = false;

    Panel() {
        random = new Random();
        this.setPreferredSize(new Dimension(panel_width, panel_height));
        this.setFocusable(true);
        this.setBackground(new Color(0));
        this.setOpaque(true);
        this.addKeyListener(new My_key());
        startGame();

    }

    public void startGame() {
        running = true;
        timer = new Timer(delay, this);
        timer.start();
        new_apple();
    }

    public void new_apple() {
        appleX = random.nextInt((int) (panel_width / unit)) * unit;
        appleY = random.nextInt((int) (panel_height / unit)) * unit;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {
            // for (int i = 0; i < panel_height / unit; i++) {
            // g.drawLine(0, i * unit, panel_width, i * unit);
            // g.drawLine(i * unit, 0, i * unit, panel_height);
            // }
            g.setColor(new Color(0xFFEF1E37, true));
            g.fillOval(appleX, appleY, unit, unit);

            // for body parts
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0xFF00C5EC, true));
                    g.fillRect(x[i], y[i], unit, unit);
                } else {
                    g.setColor(new Color(0xECFFFFFE, true));
                    g.fillRect(x[i], y[i], unit, unit);
                }

            }
            //
            g.setColor(new Color(0xB92323));
            g.setFont(new Font("MV Boli", Font.BOLD, 30));
            FontMetrics mat = getFontMetrics(g.getFont());
            g.drawString("SCORE  " + appleEaten, (panel_width - mat.stringWidth("SCORE" + appleEaten)) / 2, 30);
            // g.drawString("game OVER",60,300);

        } else {
            font(g);
        }
        if (!running) {
            timer.stop();
            // g.setColor(new Color(0xB92323));
            // g.setFont(new Font("MV Boli",Font.BOLD,30));
            // FontMetrics mat=getFontMetrics(g.getFont());
            // g.drawString("SCORE
            // "+appleEaten,(panel_width-mat.stringWidth("SCORE"+appleEaten))/2,30);
            // // g.drawString("game OVER",60,300);

        }
    }

    public void font(Graphics g) {
        g.setColor(new Color(0xB92323));
        g.setFont(new Font("MV Boli", Font.BOLD, 70));
        FontMetrics mat = getFontMetrics(g.getFont());
        g.drawString("GAME...OVER...", (panel_width - mat.stringWidth("GAME...OVER...")) / 2, panel_height / 2);
        // g.drawString("game OVER",60,300);
        g.setColor(new Color(0xB92323));
        g.setFont(new Font("MV Boli", Font.BOLD, 30));
        FontMetrics mat1 = getFontMetrics(g.getFont());
        g.drawString("SCORE  " + appleEaten, (panel_width - mat1.stringWidth("SCORE" + appleEaten)) / 2, 30);
        // g.drawString("game OVER",60,300);
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (dir) {
            case 'U':
                y[0] = y[0] - unit;
                break;
            case 'D':
                y[0] = y[0] + unit;
                break;
            case 'L':
                x[0] = x[0] - unit;
                break;
            case 'R':
                x[0] = x[0] + unit;
                break;

        }
    }

    public void checkCollision() {
        if (x[0] == panel_width) {
            running = false;
        }
        if (x[0] < 0) {
            running = false;
        }
        if (y[0] < 0) {
            running = false;
        }
        if (y[0] == panel_height) {
            running = false;
        }
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

    }

    public void checkapple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            appleEaten++;
            new_apple();
        }
    }

    public class My_key extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (dir != 'R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'L') {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (dir != 'D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir != 'U') {
                        dir = 'D';
                    }
                    break;

            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (running) {

            move();
            checkapple();
            checkCollision();

        }
        repaint();
    }
}
