package swe.engine;

import java.util.Arrays;
import java.util.HashMap;

public class History {

    private int totalDays;
    private HistoryState[] ticksState;

    public History(int totalDays) {
        this.totalDays = totalDays;
        this.ticksState = new HistoryState[totalDays];
    }

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

    private HashMap<Company,Double> getCompanySharePrices(Market market) {
        HashMap<Company, Double> sharePriceMap = new HashMap<>();
        for (Company company : market.getCompanies()) {
            sharePriceMap.put(company, company.getPrice());
        }

        return sharePriceMap;
    }

    private HashMap<Portfolio,Double> getPortfolioWorth(Market market) {
        HashMap<Portfolio, Double> sharePriceMap = new HashMap<>();
        for (Portfolio portfolio : market.getPortfolios()) {
            sharePriceMap.put(portfolio, portfolio.getWorth());
        }

        return sharePriceMap;
    }

    /*      A bear market is defined as one where the
            share index has fallen for 3 consecutive trading days. A bull market is defined as one where the
            share index has risen for 3 consecutive trading days."*/
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

    public int getTotalDays() {
        return totalDays;
    }

    public HistoryState getStateForTick(int i) {
        return ticksState[i];
    }

    public HistoryState[] getStateForEndOfEachDay() {
        return Arrays
                .stream(this.ticksState)
                .filter(historyState -> historyState.tickIdentifier.isEndOfDay)
                .toArray(HistoryState[]::new);
    }

    public Float[] getEndOfDayShareIndex() {
        return Arrays.stream(this.getStateForEndOfEachDay())
                .map(state -> (float) state.shareIndex)
                .toArray(Float[]::new);
    }

    public double getHighestShareIndex() {
        return Arrays.stream(ticksState).map(state -> state.shareIndex).max(Double::compare).get();
    }
}
