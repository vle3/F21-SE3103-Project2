package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import controller.KeyController;
import controller.TimerListener;
import model.EnemyComposite;
import model.GameElement;
import model.Shooter;
import model.ShooterElement;
import model.observerPattern.ShooterObserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;


public class GameBoard {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;
    public static final int CELL_SIZE = 20;
    
    public static final int FPS = 20;
    public static final int DELAY = 1000 / FPS;

    private JFrame window;
    private MyCanvas canvas;
    private Shooter shooter;
    private EnemyComposite enemyComposite;
    private Timer timer;
    private TimerListener timerListener;
    private JLabel scoreDisplay = new JLabel();
    private int score;

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

        JPanel northPanel = new JPanel();
        JLabel label = new JLabel("Score: ");
        northPanel.add(label);
        scoreDisplay.setText("" + score);
        scoreDisplay.setFocusable(false);
        label.setFocusable(false);
        northPanel.add(scoreDisplay);
        cp.add(BorderLayout.NORTH, northPanel);

        canvas.getGameElements().add(new TextDraw("Click <Start> to Play", 100 , 100 , Color.yellow, 30));

        timerListener = new TimerListener(this);
        timer = new Timer(DELAY, timerListener);
        
        enemyComposite = new EnemyComposite();

        ShooterObserver observers = new ShooterObserver(this);
        enemyComposite.addListener(observers);
        startButton.addActionListener(event -> {
            shooter = new Shooter(GameBoard.WIDTH / 2, GameBoard.HEIGHT - ShooterElement.SIZE );
            canvas.getGameElements().clear();
            canvas.getGameElements().add(shooter);
            enemyComposite = new EnemyComposite();
            canvas.getGameElements().add(enemyComposite);
            enemyComposite.addListener(observers);
            score = 0;
            timer.start();
        });
        
        
        
        // if(enemyComposite.getRows().isEmpty()){
        //     timer.stop();
        //     canvas.getGameElements().add(new TextDraw("You Win\n" + "Score: " + enemyComposite.getGameScore()
        //     , 100, 100, Color.YELLOW, 30));
        // }

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
    public JFrame getWindow() {
        return window;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public JLabel getScoreDisplay() {
        return scoreDisplay;
    }

}
