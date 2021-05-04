package model.strategyPattern;

import java.awt.Color;
import java.awt.Graphics2D;

import model.Shooter;

public class ShooterRenderDeadStrategy implements ShooterRenderStrategy {

    private Shooter shooter;

    public ShooterRenderDeadStrategy(Shooter shooter)
    {
        this.shooter = shooter;
    }
    

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        var composites = shooter.getComponents();
        for(var s: composites) 
        {
            s.color = Color.gray;
            s.filled = true;
            s.render(g2);
        }
    }
    
}
