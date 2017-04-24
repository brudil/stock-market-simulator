package swe.engine;

import java.util.Calendar;

final public class HistoryState {
    final public TickIdentifier tickIdentifier;
    final public MarketStatus status;
    final int shareIndex;

    public HistoryState(TickIdentifier tickIdentifier, MarketStatus status, int shareIndex) {
        this.tickIdentifier = tickIdentifier;
        this.status = status;
        this.shareIndex = shareIndex;
    }
}
