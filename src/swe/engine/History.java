package swe.engine;

import java.util.Arrays;
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

    public HistoryState[] getStateForEndOfEachDay() {
        return Arrays
                .stream(this.ticksState)
                .filter(historyState -> historyState.tickIdentifier.isEndOfDay)
                .toArray(HistoryState[]::new);
    }

    public Float[] getEndOfDayShareIndex() {
        return Arrays.stream(this.getStateForEndOfEachDay())
                .map(state -> (float) state.shareIndex)
                .toArray(Float[]::new);
    }
}
