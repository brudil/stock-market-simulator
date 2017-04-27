package swe.engine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SimulationCalendar {
    private final int tickCount;
    private final TickIdentifier[] ticks;

    public SimulationCalendar(SimulationEvent[] events) {
        ArrayList<TickIdentifier> ticksGenerated = this.generateDays();
        this.tickCount = ticksGenerated.size();
        this.ticks = ticksGenerated.toArray(new TickIdentifier[0]);
    }

    public TickIdentifier[] getAllTickIdentifiers() {
        return this.ticks;
    }

    private ArrayList<TickIdentifier> generateDays() {
        ArrayList<TickIdentifier> flexibleTicks = new ArrayList<>();
        int tickId = 0;
        // Set sim start time
        Calendar calendar = new GregorianCalendar(2017, 0, 1, 9, 0);
        Calendar endDate = new GregorianCalendar(2018, Calendar.JANUARY, 1, 0, 0);

        while (calendar.before(endDate)) {
            flexibleTicks.add(new TickIdentifier((Calendar) calendar.clone(), tickId));

            SimulationCalendar.updateDateForNextTick(calendar);
            tickId += 1;
        }

        return flexibleTicks;
    }

    public int getTickCount() {
        return this.tickCount;
    }


    /**
     * Move time forward for next tick
     */
    static private void updateDateForNextTick(Calendar calendar) {
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (currentHour >= 9 && currentHour < 16) {
            // still within working time of 9am to 4pm
            calendar.add(Calendar.MINUTE, 15);
        } else {
            // reset time to 9:00am
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 0);

            // go to tomorrow
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            // check tomorrow isn't a special day
            SimulationCalendar.ensureIsViableDay(calendar);
        }
    }

    /**
     * Ensures the current internal date isn't a special day, moving to the next if so.
     * Recursively called internally when special days are found, to ensure the next day isn't special too.
     * Special days include: weekends, christmas + boxing day and good friday, easter sunday.
     */
    static private void ensureIsViableDay(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // if we on sun, go to monday
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            SimulationCalendar.ensureIsViableDay(calendar);
            return;
        }

        // if we on sat, go to monday
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            SimulationCalendar.ensureIsViableDay(calendar);
            return;
        }

        // skip christmas + boxing day (25/12, 26/12)
        if ((month == Calendar.DECEMBER) && (dayOfMonth == 25 || dayOfMonth == 26)) {
            calendar.set(Calendar.DAY_OF_MONTH, 27);
            SimulationCalendar.ensureIsViableDay(calendar);
            return;
        }

        // TODO: ensure only for year 2017, hardcoded date (https://github.com/brudil/stock-market-simulator/issues/3)
        // skip good friday (14/04)
        if (month == Calendar.APRIL && dayOfMonth == 14) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            SimulationCalendar.ensureIsViableDay(calendar);
            return;
        }

        // TODO: ensure only for year 2017, hardcoded date (https://github.com/brudil/stock-market-simulator/issues/3)
        // skip easter monday (17/04)
        if (month == Calendar.APRIL && dayOfMonth == 17) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            SimulationCalendar.ensureIsViableDay(calendar);
        }
    }


}
