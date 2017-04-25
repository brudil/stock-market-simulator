package swe.engine;

import java.io.Serializable;
import java.util.HashMap;

public class SharesMap implements Serializable {
    private HashMap<Company, HashMap<Portfolio, Share>> companyShares;
    private HashMap<Portfolio, HashMap<Company, Share>> portfolioShares;

    public SharesMap() {
        companyShares = new HashMap<>();
        portfolioShares = new HashMap<>();
    }

    public void setShares(Company company, Portfolio portfolio, int shares) {
        Share share = new Share(shares);
        HashMap<Portfolio, Share> portfolioMap = this.companyShares.getOrDefault(company, new HashMap<>());
        portfolioMap.put(portfolio, share);
        this.companyShares.put(company, portfolioMap);

        HashMap<Company, Share> companyMap = this.portfolioShares.getOrDefault(portfolio, new HashMap<>());
        companyMap.put(company, share);
        this.portfolioShares.put(portfolio, companyMap);
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
        return this.companyShares.getOrDefault(company, new HashMap<>()).getOrDefault(portfolio, new Share(0));
    }
}
