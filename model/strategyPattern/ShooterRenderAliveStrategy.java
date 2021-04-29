package model.strategyPattern;

import java.awt.Graphics2D;

import model.Shooter;

public class ShooterRenderAliveStrategy implements ShooterRenderStrategy{
    public Shooter shooter;

    public ShooterRenderAliveStrategy(Shooter shooter)
    {
        this.shooter = shooter;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        var composites = shooter.getComponents();
        boolean filled = true;
        for(var s: composites)        
        {
            s.filled = filled;
            filled = !filled;
            s.render(g2);
        }
    }
}
