package swe.engine;

import java.util.Calendar;

public class History {

    private int totalDays;
    private HistoryState[] ticksState;

    public History(int totalDays) {
        this.totalDays = totalDays;
        this.ticksState = new HistoryState[totalDays];
    }

    public void commitTick(TickIdentifier tick, Market market) {
        HistoryState state = new HistoryState(
                tick,
                market.getMarketStatus(),
                market.getShareIndex()
        );

        ticksState[tick.tickId] = state;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public HistoryState getStateForTick(int i) {
        return ticksState[i];
    }
}
