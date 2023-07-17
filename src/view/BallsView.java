package view;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import model.BallsModel;



public class BallsView extends JPanel {

    public static List<BallsModel> balls;
    public static final int FLOOR = 450;
    public static final int WALL_LEFT = 0;
    public static final int WALL_RIGHT = 500;
    public static final int WALL_TOP = 0;
    public static final int WALL_BOTTOM = 500;
    private static final int MAX_BALLS = 20;
    private int ballCount = 0;

    public BallsView() {
        balls = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                createBalls(e.getX(), e.getY());
            }
        });
    }

    private void createBalls(int x, int y) {
        double radius = 20;
        balls.add(new BallsModel(x, y, radius));
        repaint();
        ballCount++;
    }

    public void update(double deltaTime) {
        for (BallsModel ball : balls) {
            ball.update(deltaTime);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        float strokeWidth = 20.0f;  // Desired thickness of the line
        g2.setStroke(new BasicStroke(strokeWidth));

        //draw floor line
        g2.drawLine(0, FLOOR - 10, 500, FLOOR - 10);
        g2.setColor(Color.BLACK);

        //draw wall lines
        g2.drawLine(0, 0, 0, 500);
        g2.drawLine(500, 0, 500, 500);

        for (BallsModel ball : balls) {
            ball.draw(g2);
        }

        if(ballCount > MAX_BALLS) {
            //remove first ball
            balls.remove(0);
            ballCount--;

        }

        //draw ball counter in top left corner
        g2.setColor(Color.BLACK);
        g2.drawString("Balls: " + ballCount, 20, 20);

    }

    
    
}
