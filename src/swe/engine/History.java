package swe.engine;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Contains historical market data
 * Holds immutable records of each tick
 * Public helper aggregation methods for data retrieval
 */
public class History {

    private int totalDays;
    private HistoryState[] ticksState;

    public History(int totalDays) {
        this.totalDays = totalDays;
        this.ticksState = new HistoryState[totalDays];
    }

    /**
     * Creates and stores a new HistoryState record in History
     * @param tick tick to store
     * @param market market instance to serialise
     */
    public void commitTick(TickIdentifier tick, Market market) {
        HistoryState state = new HistoryState(
                tick,
                calculateMarketStatus(market.getShareIndex(), tick.tickId),
                market.getShareIndex(),
                getCompanySharePrices(market),
                getPortfolioWorth(market)
        );

        ticksState[tick.tickId] = state;
    }

    /**
     * Helper method used in commitTick
     * @param market market instance
     * @return Company shares
     */
    private HashMap<Company,Double> getCompanySharePrices(Market market) {
        HashMap<Company, Double> sharePriceMap = new HashMap<>();
        for (Company company : market.getCompanies()) {
            sharePriceMap.put(company, company.getPrice());
        }

        return sharePriceMap;
    }

    /**
     * Helper method used in commitTick
     * @param market market instance
     * @return Portfolio worth
     */
    private HashMap<Portfolio,Double> getPortfolioWorth(Market market) {
        HashMap<Portfolio, Double> sharePriceMap = new HashMap<>();
        for (Portfolio portfolio : market.getPortfolios()) {
            sharePriceMap.put(portfolio, portfolio.getWorth());
        }

        return sharePriceMap;
    }

    /*     "*/
    /**
     * Helper method used in commitTick
     * Logic:  A bear market is defined as one where the
     *  share index has fallen for 3 consecutive trading days. A bull market is defined as one where the
     *  share index has risen for 3 consecutive trading days.
     * @param nowIndex current index
     * @param tickId Tick ID to work from
     * @return Market status for given tickId
     */
    private MarketStatus calculateMarketStatus(double nowIndex, int tickId) {
        if (tickId <= (28 * 3)) {
            return MarketStatus.STABLE;
        }
        double aBackIndex = ticksState[tickId - 28].shareIndex;
        double bBackIndex = ticksState[tickId - (28 * 2)].shareIndex;
        if (nowIndex > aBackIndex && aBackIndex > bBackIndex) {
            return MarketStatus.BULL;
        }

        if (nowIndex < aBackIndex && aBackIndex < bBackIndex) {
            return MarketStatus.BEAR;
        }

        return MarketStatus.STABLE;
    }

    /**
     * @return Total days expected in History
     */
    public int getTotalDays() {
        return totalDays;
    }

    /**
     * Returns imutalble HistoryState for given tick
     * @param i Tick ID
     * @return HistoryState of given Tick ID
     */
    public HistoryState getStateForTick(int i) {
        return ticksState[i];
    }

    /**
     * Returns array of HistoryStates that were marked as the last of the day
     * @return HistoryStates array
     */
    public HistoryState[] getStateForEndOfEachDay() {
        return Arrays
                .stream(this.ticksState)
                .filter(historyState -> historyState.tickIdentifier.isEndOfDay)
                .toArray(HistoryState[]::new);
    }

    /**
     * @return Share Index of market over each day
     */
    public Float[] getEndOfDayShareIndex() {
        return Arrays.stream(this.getStateForEndOfEachDay())
                .map(state -> (float) state.shareIndex)
                .toArray(Float[]::new);
    }

    /**
     * @return Highest Share Index stored in the History
     */
    public double getHighestShareIndex() {
        return Arrays.stream(ticksState).map(state -> state.shareIndex).max(Double::compare).get();
    }

    /**
     * @return Highest Client Worth stored in the History
     */
    public double getHighestClientWorth() {
        return Arrays.stream(ticksState).map(state -> Collections.max(state.portfolioWorth.values())).max(Double::compare).get();
    }
}
