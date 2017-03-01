package swe.engine.traders;

public abstract class Trader {

    /**
     * Called before trading begins at the start of each new day
     */
    public void onNewDay() {}

    public void getTradesForTick() {}

}
