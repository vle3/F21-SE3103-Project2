package model.observerPattern;

import model.Enemy;
import model.EnemyComposite;
import model.GameElement;
import model.Shooter;

public interface Subject {
    void removeListener(Observer o);
    void addListener(Observer o);
    void notifyObserver(Shooter.Event event);
    void notifyObserver(EnemyComposite.Event event);
    
}
