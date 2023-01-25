package net.htlgkr.game;

public enum Difficulty {
    EASY(2000, 5000, 0)
    ,MEDIUM(1000, 3000, 0.2)
    ,HARD(500, 2000, 0.4),
    MASOCHIST(0, 1000, 0.6);

    private final int battlePrepareTime;
    private final int battleTime;
    private final double enemySpawnChance;

    Difficulty(int battlePrepareTime, int battleTime, double enemySpawnChance) {
        this.battlePrepareTime = battlePrepareTime;
        this.battleTime = battleTime;
        this.enemySpawnChance = enemySpawnChance;
    }

    public int getBattlePrepareTime() {
        return battlePrepareTime;
    }

    public int getBattleTime() {
        return battleTime;
    }

    public double getEnemySpawnChance() {
        return enemySpawnChance;
    }
}
