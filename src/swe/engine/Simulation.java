package swe.engine;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Houses everything needed to run an independent market simulation.
 */
public class Simulation {
    private Market market;
    private History history;
    private SimulationCalendar simulationCalendar;


    public Simulation(ArrayList<Company> companies, ArrayList<Portfolio> portfolios, SharesMap shares) {
        this.market = new Market(companies, portfolios, shares);
        this.simulationCalendar = new SimulationCalendar(new SimulationEvent[10]);
        this.history = new History(simulationCalendar.getTickCount());
    }

    /**
     * Runs the simulation for ticks in calender
     */
    public void runSimulation() {

        for(TickIdentifier tick : this.simulationCalendar.getAllTickIdentifiers()) {
            this.performTick(tick);
        }

        System.out.println(this.history.getStateForEndOfEachDay().length);
    }

    /**
     * @return History of market
     */
    public History getHistory() {
        return this.history;
    }

    /**
     * Performs a 15min trading period
     * @param tick TickIdentifier of tick to simulate
     */
    public void performTick(TickIdentifier tick) {
        // does everything
        // 15 min period
        Calendar calendar = tick.calendar;
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK) + " " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " @ " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

        if (tick.isStartOfDay) {
            this.market.getPortfolios().forEach(Portfolio::onNewDay);
        }

        // 1) get sale offers and sought purchases
        HashMap<Portfolio, TradeSlip> trades = this.market.getRequestedPortfolioTrades(this.history);

        // 2) determine what will be brought and sold
        // 3) do transactions
        this.market.performRequestedTradesBestAttempt(trades);

        // 4) adjust share prices
        // above via 'market.finishTrading()'
        // 6) done

        this.history.commitTick(tick, this.market);
    }
}
