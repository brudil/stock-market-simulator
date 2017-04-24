package swe.engine;

public class HistoryReporter {
    private History history;

    public HistoryReporter(History history) {
        this.history = history;
    }

    public HistoryState[] getEndOfDayTicks() {
        HistoryState[] days = new HistoryState[history.getTotalDays()];

        for(int day = 0; day <= history.getTotalDays(); day++) {
            days[day] = history.getStateForTick((day * 7) - 7);
        }

        return days;
    }
}
