package swe.engine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Houses everything needed to run an independent market simulation.
 */
public class Simulation {

    Calendar calendar = new GregorianCalendar(2017, 1, 1, 9, 0);
    ArrayList<Portfolio> portfolios = new ArrayList<>();


    /**
     * Performs a 15min trading period
     */
    public void performTick() {
        // does everything
        // 15 min period

        this.updateDateForNextTick();
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

        // update date
        // logic to skip weekends, shut time, celebration days etc
        // essentially, on open days 28 ticks. 9am to 4pm
        // if new date, call onNewDay for all portfolios.

        // 1) get sale offers and sought purchases
        // 2) determine what will be brought and sold
        // 3) do transactions
        // 4) adjust share prices
        // 5) update share index
        // 6) done
    }

    private void updateDateForNextTick() {
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (currentHour >= 9 && currentHour < 16) {
            calendar.add(Calendar.MINUTE, 15);
        } else {
            // TODO: skip weekends and celebration days
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            // trigger onNewDay for portfolios
        }
    }


    public static void main(String args[]) {
        Simulation sim = new Simulation();
        sim.performTick();
        sim.performTick();
        sim.performTick();
        sim.performTick();
        sim.performTick();
        sim.performTick();
        sim.performTick();
    }
}
