package swe.engine;

import java.util.HashMap;

public class SharesMap {
    private HashMap<Company, HashMap<Portfolio, Share>> companyShares;
    private HashMap<Portfolio, HashMap<Company, Share>> portfolioShares;

    public void setShares(Company company, Portfolio portfolio, int shares) {
        Share share = new Share(shares);
        HashMap<Portfolio, Share> portfolioMap = this.companyShares.getOrDefault(company, new HashMap<>());
        portfolioMap.put(portfolio, share);

        HashMap<Company, Share> companyMap = this.portfolioShares.getOrDefault(portfolio, new HashMap<>());
        companyMap.put(company, share);
    }


    public HashMap<Portfolio, Share> getSharesForCompany(Company company) {
        return this.companyShares.getOrDefault(company, null);
    }

    public HashMap<Company, Share> getSharesForPortfolio(Portfolio portfolio) {
        return this.portfolioShares.getOrDefault(portfolio, null);
    }

    /**
     * @param company
     * @param portfolio
     * @return Mutable share object
     */
    public Share getSharesForCompanyInPortfolio(Company company, Portfolio portfolio) {
        return this.companyShares.get(company).get(portfolio);
    }
}
