package model.strategyPattern;

import model.Shooter;
import view.GameBoard;

public class ShooterMoveAliveStrategy implements ShooterMoveStrategy 
{
    private Shooter shooter;

    public ShooterMoveAliveStrategy(Shooter shooter)
    {
        this.shooter = shooter;
    }

    @Override
    public void moveAlgorthim() {
        var composite = shooter.getComponents();
        for (int i = composite.size() - 1 ; i > 0 ; i--)        
        {
            composite.get(i).x = composite.get(i - 1).x;
            composite.get(i).y = composite.get(i - 1).y;
            composite.get(0).x -= GameBoard.CELL_SIZE;
        }

        
    }
}
