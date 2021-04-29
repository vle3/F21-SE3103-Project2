package model.observerPattern;

import java.awt.Color;

import model.Shooter;
import model.strategyPattern.ShooterMoveDeadStrategy;
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
    }

    @Override
    public void bombHitShooter() {
        //let's do --10 per hit         
    }

    @Override
    public void enemyReachBottom() {
        //Game Over        
    }

    @Override
    public void enemyTouchShooter() {
        // Game Over     
        gameBoard.getCanvas().getGameElements().add(new TextDraw("Game Over", 100, 200, Color.red, 30));
        Shooter shooter = gameBoard.getShooter();
        shooter.setMoveStrategy(new ShooterMoveDeadStrategy(shooter));
        shooter.setRenderStrategy(new ShooterRenderDeadStrategy(shooter));
    }


    @Override
    public void bulletHitEnemy() {
        //increase score by 10
        int score = gameBoard.getScore();
        ++score;
        gameBoard.setScore(score);    
        gameBoard.getScoreDisplay().setText("" + score);
    
    }


    
}
