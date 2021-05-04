package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import model.strategyPattern.ShooterMoveAliveStrategy;
import model.strategyPattern.ShooterMoveStrategy;
import model.strategyPattern.ShooterRenderAliveStrategy;
import model.strategyPattern.ShooterRenderStrategy;

public class Shooter extends GameElement {
    public static final int UNIT_MOVE = 10;
    public static final int MAX_BULLET = 3;

    private ArrayList<GameElement> components = new ArrayList<>();
    private ArrayList<GameElement> weapons = new ArrayList<>();

    private ShooterMoveStrategy moveStrategy;
    private ShooterRenderStrategy renderStrategy;

    public enum Direction{
        LEFT, RIGHT, UP, DOWN;
    }

    public enum Event{
        BombHit, EnemyTouch;
    }

    public Shooter(int x , int y)
    {
        super(x, y , 0 , 0);

        var size = ShooterElement.SIZE;
        var s1 = new ShooterElement(x-size, y-size, Color.white, false);
        var s2 = new ShooterElement(x, y-size, Color.white, false);
        var s3 = new ShooterElement(x-size, y, Color.white, false);
        var s4 = new ShooterElement(x, y, Color.white, false);
        components.add(s1);
        components.add(s2);
        components.add(s3);
        components.add(s4);
        moveStrategy = new ShooterMoveAliveStrategy(this);
        renderStrategy = new ShooterRenderAliveStrategy(this);
    }

    public void moveRight()
    {
        super.x += UNIT_MOVE;
        for(var c: components){
            c.x += UNIT_MOVE;
        }
    }

    public void moveLeft()
    {
        super.x -= UNIT_MOVE;
        for(var c: components){
            c.x -= UNIT_MOVE;
        }
    }

    public boolean canFireMoreBullet(){
        return weapons.size() < MAX_BULLET;
    }

    public void removeBulletOutOfBound(){
        var remove = new ArrayList<GameElement>();
        for (var w: weapons){
            if(w.y < 0) remove.add(w);
        }
        weapons.removeAll(remove);
    }

    public ArrayList<GameElement> getWeapons() {
        return weapons;
    }

    @Override
    public void render(Graphics2D g2) {
        for(var c: components)
        {
            c.render(g2);
        }
        for(var w: weapons){
            w.render(g2);
        }
    }

    @Override
    public void animate() {
        for(var w: weapons){
            w.animate();
        }
    }

    public ArrayList<GameElement> getComponents() {
        return components;
    }
    public void setMoveStrategy(ShooterMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
    public void setRenderStrategy(ShooterRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }
}
