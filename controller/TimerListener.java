package controller;

import java.awt.event.ActionListener;

import view.GameBoard;

import java.awt.event.ActionEvent;

public class TimerListener implements ActionListener{

    private GameBoard gameBoard;

    public TimerListener(GameBoard gameBoard)
    {
        this.gameBoard = gameBoard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameBoard.getCanvas().repaint();
    }
    
}
