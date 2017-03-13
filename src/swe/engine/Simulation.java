package swe.engine;

import swe.engine.traders.RandomInnerTraders;
import swe.engine.traders.RandomTrader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Houses everything needed to run an independent market simulation.
 */
public class Simulation {
    private Calendar calendar = new GregorianCalendar(2017, 0, 1, 9, 0);
    private Market market;


    public Simulation(ArrayList<Company> companies, ArrayList<Portfolio> portfolios) {
        this.market = new Market(companies, portfolios);
    }

    /**
     * Performs a 15min trading period
     */
    public void performTick() {
        // does everything
        // 15 min period

        this.updateDateForNextTick();
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK) + " " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " @ " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));


        // 1) get sale offers and sought purchases
        HashMap<Portfolio, TradeSlip> trades = this.market.getRequestedPortfolioTrades();

        // 2) determine what will be brought and sold
        // 3) do transactions
        this.market.performRequestedTradesBestAttempt(trades);

        // 4) adjust share prices
        // 5) update share index
        // above via 'market.finishTrading()'
        // 6) done
    }


    /**
     * Move time forward for next tick
     */
    private void updateDateForNextTick() {
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
            this.ensureIsViableDay();


            // trigger onNewDay for portfolios
            for (Portfolio portfolio : this.market.getPortfolios()) {
                portfolio.onNewDay();
            }

        }
    }

    /**
     * Ensures the current internal date isn't a special day, moving to the next if so.
     * Recursively called internally when special days are found, to ensure the next day isn't special too.
     * Special days include: weekends, christmas + boxing day and good friday, easter sunday.
     */
    private void ensureIsViableDay() {
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // if we on sun, go to monday
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            this.ensureIsViableDay();
            return;
        }

        // if we on sat, go to monday
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            this.ensureIsViableDay();
            return;
        }

        // skip christmas + boxing day (25/12, 26/12)
        if ((month == Calendar.DECEMBER) && (dayOfMonth == 25 || dayOfMonth == 26)) {
            calendar.set(Calendar.DAY_OF_MONTH, 27);
            this.ensureIsViableDay();
            return;
        }

        // TODO: ensure only for year 2017, hardcoded date (https://github.com/brudil/stock-market-simulator/issues/3)
        // skip good friday (14/04)
        if (month == Calendar.APRIL && dayOfMonth == 14) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            this.ensureIsViableDay();
            return;
        }

        // TODO: ensure only for year 2017, hardcoded date (https://github.com/brudil/stock-market-simulator/issues/3)
        // skip easter monday (17/04)
        if (month == Calendar.APRIL && dayOfMonth == 17) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            this.ensureIsViableDay();
        }
    }


    public static void main(String args[]) {
        ArrayList<Company> companies = new ArrayList<>();
        companies.add(new Company("Monsters Inc.", StockType.HITECH, 300));

        ArrayList<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(new Portfolio("Dave's Stocks", 4844, new RandomTrader(RandomInnerTraders.BALANCED)));

        Simulation sim = new Simulation(companies, portfolios);
        for(int t = 0; t < 28 * 365; t++) {
            sim.performTick();
        }
    }
}
