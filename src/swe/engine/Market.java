package swe.engine;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.HashMap;

public class Market {
    private ArrayList<Company> companies;
    private ArrayList<Portfolio> portfolios;
    private HashMap<Company, HashMap<Portfolio, Share>> companyShares;
    private HashMap<Portfolio, HashMap<Company, Share>> portfolioShares;

    public Market(ArrayList<Company> companies, ArrayList<Portfolio> portfolios) {
        this.companies = companies;
        this.portfolios = portfolios;
    }

    public HashMap<Portfolio, Share> getSharesForCompany(Company company) {
        return this.companyShares.getOrDefault(company, null);
    }

    public HashMap<Company, Share> getSharesForPortfolio(Portfolio portfolio) {
        return this.portfolioShares.getOrDefault(portfolio, null);
    }

    public void serializeState() {}

    public ArrayList<Portfolio> getPortfolios() {
        return this.portfolios;
    }

    public MarketStatus getMarketStatus() {
        return MarketStatus.BEAR;
    }
}
