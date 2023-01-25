package net.htlgkr.game;

public enum Difficulty {
    EASY(2000, 5000), MEDIUM(1000, 3000), HARD(500, 2500), MASOCHIST(0, 1500);

    private final int battlePrepareTime;
    private final int battleTime;

    Difficulty(int battlePrepareTime, int battleTime) {
        this.battlePrepareTime = battlePrepareTime;
        this.battleTime = battleTime;
    }

    public int getBattlePrepareTime() {
        return battlePrepareTime;
    }

    public int getBattleTime() {
        return battleTime;
    }
}
