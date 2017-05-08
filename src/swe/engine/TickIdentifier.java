package swe.engine;

import java.util.Calendar;

/**
 * Represents a tick in the simulation
 * Holds date flags such as if it is the first or last tick of day
 */
public class TickIdentifier {
    public final Calendar calendar;
    public final int tickId;
    public final boolean isStartOfDay;
    public final boolean isEndOfDay;

    public TickIdentifier(Calendar calendar, int tickId) {
        this.calendar = calendar;
        this.tickId = tickId;
        this.isStartOfDay = (calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.HOUR_OF_DAY) == 9);
        this.isEndOfDay = (calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.HOUR_OF_DAY) == 16);
    }
}
