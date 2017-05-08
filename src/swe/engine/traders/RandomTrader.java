package swe.engine.traders;

import swe.engine.Company;
import swe.engine.Market;
import swe.engine.Portfolio;
import swe.engine.TradeSlip;

import java.util.HashMap;
import java.util.Random;

// using composition this directs out our calls to the trader of the day (being either balanced, aggressive purchaser and aggressive seller)

/**
 * Implements a Random style trader, as described in spec
 */
public class RandomTrader extends Trader {

    private RandomInnerTraders currentInnerTrader = RandomInnerTraders.BALANCED;
    private Trader balancedTrader;
    private Trader aggressivePurchaserTrader;
    private Trader aggressiveSellerTrader;

    public RandomTrader() {
        this.balancedTrader = new BalancedTrader();
        this.aggressivePurchaserTrader = new AggressivePurchaserTrader();
        this.aggressiveSellerTrader = new AggressiveSellerTrader();
    }

    public RandomTrader(RandomInnerTraders initialInnerTrader) {
        this.balancedTrader = new BalancedTrader();
        this.aggressivePurchaserTrader = new AggressivePurchaserTrader();
        this.aggressiveSellerTrader = new AggressiveSellerTrader();
    }

    public void onNewDay() {
        // update inner trader
        // seller = 0.4:seller, 0.6:balanced | bal = 0.1:seller, 0.8:bal, 0.1:pur | pur = 0.7:bal, 0.3:pur
        float randomNumber = new Random().nextFloat();

        switch (this.currentInnerTrader) {
            case BALANCED:
                if (randomNumber >= 0.0 && randomNumber <= 0.1) {
                    this.currentInnerTrader = RandomInnerTraders.AGGRESSIVE_SELLER;
                } else if (randomNumber > 0.1 && randomNumber <= 0.2) {
                    this.currentInnerTrader = RandomInnerTraders.AGGRESSIVE_PURCHASER;
                } else {
                    this.currentInnerTrader = RandomInnerTraders.BALANCED;
                }
                break;

            case AGGRESSIVE_PURCHASER:
                if (randomNumber >= 0.0 && randomNumber <= 0.7) {
                    this.currentInnerTrader = RandomInnerTraders.BALANCED;
                } else {
                    this.currentInnerTrader = RandomInnerTraders.AGGRESSIVE_PURCHASER;
                }
                break;

            case AGGRESSIVE_SELLER:
                if (randomNumber >= 0.0 && randomNumber <= 0.4) {
                    this.currentInnerTrader = RandomInnerTraders.AGGRESSIVE_SELLER;
                } else {
                    this.currentInnerTrader = RandomInnerTraders.BALANCED;
                }
                break;

        }
    }

    @Override
    public TradeSlip getRequestedTrades(Portfolio portfolio, Market market) {
        return this.getCurrentTrader().getRequestedTrades(portfolio, market);
    }

    private Trader getCurrentTrader() {
        switch (this.currentInnerTrader) {
            case BALANCED:
                return this.balancedTrader;

            case AGGRESSIVE_PURCHASER:
                return this.aggressivePurchaserTrader;

            case AGGRESSIVE_SELLER:
                return this.aggressiveSellerTrader;
        }

        return this.balancedTrader;
    }

    @Override
    public String toString() {
        return "Random";
    }
}
