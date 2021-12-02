package model.statePattern;

import javax.swing.JFrame;

import view.GameBoard;


public class StopState implements State{
    
    private JFrame window;
    public void doAction(Context context)
    {
        System.out.println("Game is in stop state");
        context.setState(this);
        GameBoard gameBoard = new GameBoard(window);
        gameBoard.init();
    }
    public String toString()
    {
        return "Stop State";
    }
}
