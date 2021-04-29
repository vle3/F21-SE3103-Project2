package model.observerPattern;

import model.GameElement;

public interface Subject {
    void removeListener(Observer o);
    void addListener(Observer o);
    void notifyObserver();
    
}
