package model.observerPattern;

public interface Observer {
    void bulletHitEnemy();
    void allEnemyDestroy();
    void bombHitShooter();
    void enemyReachBottom();
    void enemyTouchShooter();
    void shooterDestroyed();
    void shooterWin();
}
