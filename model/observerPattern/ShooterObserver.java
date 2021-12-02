package model.observerPattern;

import java.awt.Color;

import model.Shooter;
import model.strategyPattern.ShooterMoveAliveStrategy;
import model.strategyPattern.ShooterMoveDeadStrategy;
import model.strategyPattern.ShooterRenderAliveStrategy;
import model.strategyPattern.ShooterRenderDeadStrategy;
import view.GameBoard;
import view.TextDraw;

public class ShooterObserver implements Observer{
    private GameBoard gameBoard;

    public ShooterObserver(GameBoard gameBoard)
    {
        this.gameBoard = gameBoard;
    }
    

    @Override
    public void allEnemyDestroy() {
        //Win
        gameBoard.getCanvas().getGameElements().add(new TextDraw("You Won!, Your score: " + gameBoard.getScore() , 100, 200, Color.red, 30));
        Shooter shooter = gameBoard.getShooter();
        shooter.setMoveStrategy(new ShooterMoveAliveStrategy(shooter));
        shooter.setRenderStrategy(new ShooterRenderAliveStrategy(shooter));
        // gameBoard.getTimer().stop();
        // gameBoard.getCanvas().repaint();     

    }

    @Override
    public void bombHitShooter() {
        //let's do -20 per hit
        int score = gameBoard.getScore();
        if(score > 10)
        {score-=20;}
        else if(score <= 10 )
        {
            score = 0;
        }
        gameBoard.setScore(score);    
        gameBoard.getScoreDisplay().setText("" + score);         
    }

    @Override
    public void enemyReachBottom() {
        //Game Over
        gameBoard.getCanvas().getGameElements().add(new TextDraw("Game Over, Your score: " + gameBoard.getScore() , 100, 200, Color.red, 30));
        Shooter shooter = gameBoard.getShooter();
        shooter.setMoveStrategy(new ShooterMoveDeadStrategy(shooter));
        shooter.setRenderStrategy(new ShooterRenderDeadStrategy(shooter));
        gameBoard.getTimer().stop();
        gameBoard.getCanvas().repaint();        
    }

    @Override
    public void enemyTouchShooter() {
        // Game Over     
        gameBoard.getCanvas().getGameElements().add(new TextDraw("Game Over, Your score: " + gameBoard.getScore() , 100, 200, Color.red, 30));
        Shooter shooter = gameBoard.getShooter();
        shooter.setMoveStrategy(new ShooterMoveDeadStrategy(shooter));
        shooter.setRenderStrategy(new ShooterRenderDeadStrategy(shooter));
        gameBoard.getTimer().stop();
        gameBoard.getCanvas().repaint();
    }


    @Override
    public void bulletHitEnemy() {
        //increase score by 10
        int score = gameBoard.getScore();
        score+=10;
        gameBoard.setScore(score);    
        gameBoard.getScoreDisplay().setText("" + score);
    
    }


    @Override
    public void shooterDestroyed() {
        //Game Over
        gameBoard.getCanvas().getGameElements().add(new TextDraw("Game Over, Your score: " + gameBoard.getScore() , 100, 200, Color.red, 30));
        Shooter shooter = gameBoard.getShooter();
        shooter.setMoveStrategy(new ShooterMoveDeadStrategy(shooter));
        shooter.setRenderStrategy(new ShooterRenderDeadStrategy(shooter));
        gameBoard.getTimer().stop();
        gameBoard.getCanvas().repaint();        
    }


    
}
