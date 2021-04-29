package model.strategyPattern;

import model.Shooter;

public class ShooterMoveAliveStrategy implements ShooterMoveStrategy 
{
    private Shooter shooter;

    public ShooterMoveAliveStrategy(Shooter shooter)
    {
        this.shooter = shooter;
    }

    @Override
    public void moveAlgorthim() {
    }
}
