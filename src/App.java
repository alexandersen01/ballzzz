import javax.swing.JFrame;

import controller.BallsController;
import view.BallsView;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Balls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BallsView view = new BallsView();
        BallsController controller = new BallsController(view);
        frame.add(view);
        frame.setSize(500, 500);
        frame.setVisible(true);
        
    }
}
