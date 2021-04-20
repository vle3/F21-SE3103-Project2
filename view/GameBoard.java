package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.KeyController;
import controller.TimerListener;
import model.EnemyComposite;
import model.Shooter;
import model.ShooterElement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;


public class GameBoard {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;
    
    public static final int FPS = 20;
    public static final int DELAY = 1000 / FPS;

    private JFrame window;
    private MyCanvas canvas;
    private Shooter shooter;
    private EnemyComposite enemyComposite;
    private Timer timer;
    private TimerListener timerListener;

    public GameBoard(JFrame window)
    {
        this.window = window;
    }

    public void init()
    {
        Container cp = window.getContentPane();

        canvas = new MyCanvas(this, WIDTH, HEIGHT);
        cp.add(BorderLayout.CENTER, canvas);
        canvas.addKeyListener(new KeyController(this));
        canvas.requestFocusInWindow();
        canvas.setFocusable(true);;

        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");
        startButton.setFocusable(false);
        quitButton.setFocusable(false);

        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(quitButton);
        cp.add(BorderLayout.SOUTH, southPanel);

        canvas.getGameElements().add(new TextDraw("Click <Start> to Play", 100 , 100 , Color.yellow, 30));

        timerListener = new TimerListener(this);
        timer = new Timer(DELAY, timerListener);

        startButton.addActionListener(event -> {
            shooter = new Shooter(GameBoard.WIDTH / 2, GameBoard.HEIGHT - ShooterElement.SIZE );
            enemyComposite = new EnemyComposite();
            canvas.getGameElements().clear();
            canvas.getGameElements().add(shooter);
            canvas.getGameElements().add(enemyComposite);
            timer.start();
        });

        quitButton.addActionListener(event -> System.exit(0));
    }

    public MyCanvas getCanvas() {
        return canvas;
    }
    public Timer getTimer() {
        return timer;
    }
    public TimerListener getTimerListener() {
        return timerListener;
    }
    public Shooter getShooter() {
        return shooter;
    }
    public EnemyComposite getEnemyComposite() {
        return enemyComposite;
    }

}
