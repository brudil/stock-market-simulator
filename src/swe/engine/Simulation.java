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
    private Market market;
    private History history;
    private SimulationCalendar simulationCalendar;


    public Simulation(ArrayList<Company> companies, ArrayList<Portfolio> portfolios) {
        this.market = new Market(companies, portfolios);
        this.simulationCalendar = new SimulationCalendar(new SimulationEvent[10]);
        this.history = new History(simulationCalendar.getTickCount());

        // TODO: Gen date to tick mapping, then run ticks
    }

    public void runSimulation() {

        for(TickIdentifier tick : this.simulationCalendar.getAllTickIdentifiers()) {
            this.performTick(tick);
        }
    }

    public History getHistory() {
        return this.history;
    }

    /**
     * Performs a 15min trading period
     */
    public void performTick(TickIdentifier tick) {
        // does everything
        // 15 min period
        Calendar calendar = tick.calendar;
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK) + " " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " @ " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));


        // 1) get sale offers and sought purchases
        HashMap<Portfolio, TradeSlip> trades = this.market.getRequestedPortfolioTrades();

        // 2) determine what will be brought and sold
        // 3) do transactions
        this.market.performRequestedTradesBestAttempt(trades);

        // 4) adjust share prices
        // above via 'market.finishTrading()'
        // 6) done

        this.history.commitTick(tick, this.market);
    }

    public static void main(String args[]) {
        ArrayList<Company> companies = new ArrayList<>();
        companies.add(new Company("Monsters Inc.", StockType.HITECH, 300));

        ArrayList<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(new Portfolio("Dave's Stocks", 4844, new RandomTrader(RandomInnerTraders.BALANCED)));

        Simulation sim = new Simulation(companies, portfolios);
        sim.runSimulation();
    }
}
