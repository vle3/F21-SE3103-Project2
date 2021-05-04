package controller;

import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextArea;

import model.Bullet;
import model.EnemyComposite;
import model.Shooter;
import view.GameBoard;
import view.TextDraw;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class TimerListener implements ActionListener{
    public enum EventType{
        KEY_RIGHT, KEY_LEFT, KEY_SPACE
    }

    private GameBoard gameBoard;
    private LinkedList<EventType> eventQueue;
    private final int BOMB_DROP_FREQ = 20;
    private int frameCounter = 0;
    private int score = 0;

    public TimerListener(GameBoard gameBoard)
    {
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ++frameCounter;
        update();
        processEventQueue();
        processCollision();
        gameBoard.getCanvas().repaint();
        // gameBoard.getCanvas().getGameElements().add(new TextDraw("Score: " + score , 200, 300 , Color.yellow, 30));
    }

    private void processEventQueue()
    {
        while(!eventQueue.isEmpty()){
            var e = eventQueue.getFirst();
            eventQueue.removeFirst();
            Shooter shooter = gameBoard.getShooter();
            if(shooter == null) return;

            switch(e){
                case KEY_LEFT:
                    shooter.moveLeft();
                    break;
                case KEY_RIGHT:
                    shooter.moveRight();
                    break;
                case KEY_SPACE:
                    if(shooter.canFireMoreBullet())
                        shooter.getWeapons().add(new Bullet(shooter.x, shooter.y));
                    break;
            }
        }

        if(frameCounter == BOMB_DROP_FREQ){
            gameBoard.getEnemyComposite().dropBomb();
            frameCounter = 0 ;
        }
    }

    // private void detectCollision()
    // {
    //     var figures = gameBoard.getCanvas().getGameElements();
        
        
    // }


    private void processCollision()
    {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();
        
        shooter.removeBulletOutOfBound();
        enemyComposite.removeBombsOutOfBound();
        enemyComposite.processCollision(shooter);
    }

    private void update()
    {
        for(var e: gameBoard.getCanvas().getGameElements()){
            e.animate();
        }
    }

    public LinkedList<EventType> getEventQueue() {
        return eventQueue;
    }
    public int getScore() {
        return score;
    }
    
}
