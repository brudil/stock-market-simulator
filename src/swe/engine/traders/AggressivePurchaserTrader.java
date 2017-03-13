package swe.engine.traders;

import swe.engine.Market;
import swe.engine.Portfolio;
import swe.engine.TradeSlip;

public class AggressivePurchaserTrader extends Trader {
    @Override
    public TradeSlip getRequestedTrades(Portfolio portfolio, Market market) {
        return null;
    }
}
