package swe.engine;

import org.junit.Before;
import org.junit.Test;
import swe.engine.traders.RandomInnerTraders;
import swe.engine.traders.RandomTrader;

import static org.junit.Assert.*;

public class SharesMapTest {
    SharesMap shares;
    Company companyA;
    Company companyMissing;
    Portfolio portA;
    Portfolio portMissing;

    @Before
    public void setup() {
        this.shares = new SharesMap();
        this.companyA = new Company("Daves Company", StockType.HARD, (float) 44.0);
        this.companyMissing = new Company("Missing", StockType.HARD, (float) 3.0);
        this.portA = new Portfolio("Bee's ", 3382.33, new RandomTrader(RandomInnerTraders.AGGRESSIVE_PURCHASER));
        this.portMissing = new Portfolio("Missing Port ", 32.33, new RandomTrader(RandomInnerTraders.AGGRESSIVE_PURCHASER));

    }

    @Test
    public void setShares() throws Exception {
        shares.setShares(companyA, portA, 443);
    }

    @Test
    public void getSharesForCompanyInPortfolio() {
        shares.setShares(companyA, portA, 443);

        assertEquals(443, shares.getSharesForCompanyInPortfolio(companyA, portA).getNumberOfShares());
        assertEquals(0, shares.getSharesForCompanyInPortfolio(companyA, portMissing).getNumberOfShares());
        assertEquals(0, shares.getSharesForCompanyInPortfolio(companyMissing, portA).getNumberOfShares());
        assertEquals(0, shares.getSharesForCompanyInPortfolio(companyMissing, portMissing).getNumberOfShares());

        shares.setShares(companyA, portA, 100);

        assertEquals(100, shares.getSharesForCompanyInPortfolio(companyA, portA).getNumberOfShares());

    }

}
