package model.strategyPattern;

import model.Shooter;
import view.GameBoard;

public class ShooterMoveDeadStrategy implements ShooterMoveStrategy {
    private Shooter shooter;
    
    public ShooterMoveDeadStrategy(Shooter shooter)
    {
        this.shooter = shooter;
    }

    @Override
    public void moveAlgorthim() {
        var composites = shooter.getComponents();
        for(var s: composites)        
        {
            s.y += 20;
        }

        shooter.y = composites.get(0).y;

        if(shooter.y > GameBoard.HEIGHT){
            for(var s: composites){
                s.y = 0 ;
            }
        }
        shooter.y = 0;
    }


}
