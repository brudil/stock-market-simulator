package swe.engine;

import java.util.Calendar;

final public class HistoryState {
    final public TickIdentifier tickIdentifier;
    final public MarketStatus status;
    final public Float shareIndex;

    public HistoryState(TickIdentifier tickIdentifier, MarketStatus status, Float shareIndex) {
        this.tickIdentifier = tickIdentifier;
        this.status = status;
        this.shareIndex = shareIndex;
    }
}
