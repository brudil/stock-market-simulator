package swe.engine.traders;

import java.util.Random;

// using composition this directs out our calls to the trader of the day (being either balanced, aggressive purchaser and aggressive seller)

public class RandomTrader extends Trader {

    RandomInnerTraders currentInnerTrader;
    Trader balancedTrader;
    Trader aggressivePurchaserTrader;
    Trader aggressiveSellerTrader;

    public RandomTrader() {
        this.balancedTrader = new BalancedTrader();
        this.aggressivePurchaserTrader = new BalancedTrader();
        this.aggressiveSellerTrader = new BalancedTrader();
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
    public void getTradesForTick() {
        this.getCurrentTrader().getTradesForTick();
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

}
