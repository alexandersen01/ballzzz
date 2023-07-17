package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import view.BallsView;

public class BallsController {
    public Timer timer;
    private BallsView view;

    public BallsController(BallsView ballView) {
        this.view = ballView;
        int delay = 1;
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                view.update(0.01);
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                view.update(0.01);
            }
        }, 0, delay);


}
}
