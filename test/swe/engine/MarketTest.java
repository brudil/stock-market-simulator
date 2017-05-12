package swe.engine;

import org.junit.Before;
import org.junit.Test;
import swe.engine.traders.RandomInnerTraders;
import swe.engine.traders.RandomTrader;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MarketTest {
    @Test
    public void getMarketStatus() throws Exception {
    }

    @Test
    public void getShareIndex() throws Exception {
    }

    Market market;
    Company aInc;
    Company bInc;
    Portfolio fooPort;
    Portfolio barPort;

    @Before
    public void setUp() throws Exception {
        aInc = new Company("A inc.", StockType.HITECH, (double) 300.0);
        bInc = new Company("B inc.", StockType.HITECH, (double) 300.0);

        fooPort = new Portfolio("Bar", 200, new RandomTrader(RandomInnerTraders.BALANCED));
        barPort = new Portfolio("Foo", 400, new RandomTrader(RandomInnerTraders.BALANCED));

        ArrayList<Company> companies = new ArrayList<>();
        companies.add(aInc);
        companies.add(bInc);

        ArrayList<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(fooPort);
        portfolios.add(barPort);

        market = new Market(companies, portfolios, new SharesMap());
    }
}
