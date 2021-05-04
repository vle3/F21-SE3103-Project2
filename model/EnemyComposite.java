package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import model.observerPattern.Observer;
import model.observerPattern.ShooterObserver;
import model.observerPattern.Subject;
import model.strategyPattern.ShooterMoveStrategy;
import model.strategyPattern.ShooterRenderStrategy;
import view.GameBoard;

public class EnemyComposite extends GameElement implements Subject{

    public static final int NROWS = 2;
    public static final int NCOLS = 10;
    public static final int ENEMY_SIZE = 20;
    public static final int UNIT_MOVE = 5;

    public enum Event{
        BulletHit, ReachBottom, TouchShooter, AllDestroyed, ShooterDestroyed, BombHitShooter;
    }
    private ArrayList<Observer> observers = new ArrayList<>();

    private ShooterMoveStrategy moveStrategy;
    private ShooterRenderStrategy renderStrategy;

    private ArrayList<ArrayList<GameElement>> rows;
    private ArrayList<GameElement> bombs;
    private int gameScore = 0 ;
    private boolean movingToRight = true;
    private Random random = new Random();

    public EnemyComposite(){
        rows = new ArrayList<>();
        bombs = new ArrayList<>();

        for(int r = 0; r < NROWS; r++ )
        {
            var oneRow = new ArrayList<GameElement>();
            rows.add(oneRow);
            for(int c = 0 ; c < NCOLS ; c++){
                oneRow.add(new Enemy(c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2, ENEMY_SIZE, Color.YELLOW, true));
            }
        }
    }

    @Override
    public void render(Graphics2D g2) {
        //render enemy array
        for(var r : rows){
            for (var c: r){
                c.render(g2);
            }
        }
        //render bombs
        for(var b: bombs){
            b.render(g2);
        }
    }

    @Override
    public void animate() {
        int dx = UNIT_MOVE ;
        int dy = ENEMY_SIZE;
        if(movingToRight){
            if(rightEnd() >= GameBoard.WIDTH){
                dx = -dx;
                movingToRight = false;
                for(var row: rows)
                {
                    for(var e: row){
                        e.y += dy;
                    }
                }
            }
        }
        else{
            dx = -dx;
            if(leftEnd() <= 0){
                dx = -dx;
                movingToRight = true;
                for(var row: rows)
                {
                    for(var e: row){
                        e.y += dy;
                    }
                }
            }
        }

        //update x loc
        for(var row: rows){
            for(var e: row){
                e.x += dx;
            }
        }

        for(var b: bombs){
            b.animate();
        }
    }

    private int rightEnd(){
        int xEnd = -100;
        for(var row: rows){
            if(row.size() == 0) continue;
            int x = row.get(row.size()-1).x + ENEMY_SIZE;
            if (x > xEnd) xEnd = x;
        }
        return xEnd ;
    }

    private int leftEnd()
    {
        int xEnd = 9000;
        for(var row: rows){
            if(row.size() == 0) continue;
            int x = row.get(0).x;
            if(x < xEnd) xEnd = x;
        }
        return xEnd;
    }
    
    public void dropBomb(){
        for(var row: rows){
            for(var e: row){
                if(random.nextFloat() < 0.1F){
                    bombs.add(new Bomb(e.x, e.y));
                }
            }
        }
    }

    public void removeBombsOutOfBound(){
        var remove = new ArrayList<GameElement>();
        for(var b: bombs){
            if(b.y >= GameBoard.HEIGHT){
                remove.add(b);
            }
        }
        bombs.removeAll(remove);
    }


    public void processCollision(Shooter shooter){
        var removeBullets = new ArrayList<GameElement>();

        // bullets vs enemies
        for(var row: rows){
            var removeEnemies = new ArrayList<GameElement>();
            for(var enemy: row){
                for(var bullet: shooter.getWeapons()){
                    if(enemy.collideWith(bullet)){
                        removeBullets.add(bullet);
                        removeEnemies.add(enemy);
                        notifyObserver(Event.BulletHit);
                        System.out.println(row.size());
                    }
                }
            }
            row.removeAll(removeEnemies);
        }
        shooter.getWeapons().removeAll(removeBullets);

        //bullet vs bombs
        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();

        for(var b: bombs){
            for(var bullet: shooter.getWeapons()){
                if(b.collideWith(bullet))
                {
                    removeBombs.add(b);
                    removeBullets.add(bullet);
                }
            }
        }

        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);

        //bombs vs shooter 
        var removeShooter = new ArrayList<GameElement>();
        for(var b: bombs){
            for(var c: shooter.getComponents()){
                if(b.collideWith(c)){
                    removeBombs.add(b);
                    removeShooter.add(c);
                    notifyObserver(Event.BombHitShooter);
                }
            }
        }
        if(shooter.getComponents().size() == 0)
        {
            notifyObserver(Event.ShooterDestroyed);
        }
        shooter.getComponents().removeAll(removeShooter);
        bombs.removeAll(removeBombs);

        //enemy touch shooter
        for(var row: rows)
        {
            for(var enemy: row)
            {
                if(enemy.collideWith(shooter))
                {
                    notifyObserver(Event.TouchShooter);
                }
                if(enemy.y >= 300){
                    notifyObserver(Event.ReachBottom);
                }
            }            
        }

        //all enemy destroy
        if(rows.get(0).size() == 0 && rows.get(1).size() == 0) notifyObserver(Event.AllDestroyed);
        
    }
    public int getGameScore() {
        return gameScore;
    }

    public ArrayList<ArrayList<GameElement>> getRows() {
        return rows;
    } 
  
    @Override
    public void addListener(Observer o) {
        observers.add(o);
    } 

    @Override
    public void removeListener(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver(Event event){
        switch(event){
            case BulletHit:
                for(var o: observers){
                    o.bulletHitEnemy();
                }
                break;
            case ReachBottom:
                for(var o: observers){
                    o.enemyReachBottom();
                }
                break;
            case TouchShooter:
                for(var o: observers)
                {
                    o.enemyTouchShooter();
                }
                break;
            case AllDestroyed:
                for(var o: observers)
                {
                    o.allEnemyDestroy();
                }
                break;
            case ShooterDestroyed:
                for(var o: observers){
                    o.shooterDestroyed();
                }
                break;
            case BombHitShooter:
                for(var o: observers)
                {
                    o.bombHitShooter();
                }
                break;
        }
    }

  
    @Override
    public void notifyObserver(model.Shooter.Event event) {
    }

    public void setMoveStrategy(ShooterMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
    public void setRenderStrategy(ShooterRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }


}
