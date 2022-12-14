import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten, appleX, appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(new Color(52, 146, 235));
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    public void startGame(){
        applePopulate();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running) {
           // for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
             //   g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
               // g.drawLine(0, i * UNIT_SIZE, SCREEN_HEIGHT, i * UNIT_SIZE);
            //}

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.white);
            g.setFont(new Font("Bebas Neue", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        }else
            gameOver(g);

    }

    public void applePopulate(){
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void move(){

        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;

            applePopulate();
        }
    }

    public void checkCollisions(){
        //checks if head collides with body
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //check if head touches left border
        if(x[0] < 0){
            running = false;
        }
        //touches right
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //touches top
        if(y[0] < 0){
            running = false;
        }
        //touches bottom
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        //Game over text

        g.setColor(Color.white);
        g.setFont(new Font("Bebas Neue", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT/2);
        g.setFont(new Font("Bebas Neue", Font.BOLD, 50));
        g.drawString("FINAL SCORE: " + applesEaten, 180, (SCREEN_HEIGHT / 2) + (UNIT_SIZE * 4));

    }

    JButton button = new JButton();

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }

}
