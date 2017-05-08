package swe.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Houses shares and their relationship between Companies shares and the portfolios that own them
 */
public class SharesMap {
    private HashMap<Company, HashMap<Portfolio, Share>> companyShares;
    private HashMap<Portfolio, HashMap<Company, Share>> portfolioShares;

    public SharesMap() {
        companyShares = new HashMap<>();
        portfolioShares = new HashMap<>();
    }

    /**
     * Sets shares in a company that a portfolio owns
     * @param company company of shares
     * @param portfolio portfolio that will own shares
     * @param shares quantity of shares
     */
    public void setShares(Company company, Portfolio portfolio, int shares) {
        Share share = new Share(shares);
        HashMap<Portfolio, Share> portfolioMap = this.companyShares.getOrDefault(company, new HashMap<>());
        portfolioMap.put(portfolio, share);
        this.companyShares.put(company, portfolioMap);

        HashMap<Company, Share> companyMap = this.portfolioShares.getOrDefault(portfolio, new HashMap<>());
        companyMap.put(company, share);
        this.portfolioShares.put(portfolio, companyMap);
    }

    /**
     * @param portfolio desired portfolio
     * @return map of companies and shares owned by given portfolio
     */
    public HashMap<Company, Share> getSharesForPortfolio(Portfolio portfolio) {
        if (!this.portfolioShares.containsKey(portfolio)) {
            this.portfolioShares.put(portfolio, new HashMap<>());
        }
        return this.portfolioShares.get(portfolio);
    }

    /**
     * @param company desired company
     * @return map of portfolios and shares owned for given company
     */
    public HashMap<Portfolio, Share> getSharesForCompany(Company company) {
        if (!this.companyShares.containsKey(company)) {
            this.companyShares.put(company, new HashMap<>());
        }
        return this.companyShares.get(company);
    }

    /**
     * @param company company of shares quantity
     * @return total shares within given company
     */
    public int getTotalSharesForCompany(Company company) {
        return getSharesForCompany(company).entrySet().stream()
                .mapToInt(entry -> entry.getValue().getNumberOfShares())
                .reduce(0, Integer::sum);
    }

    /**
     * @param portfolio given portfolio
     * @param percentage percentage between 0 - 100 of random shares
     * @return map of random companies and random shares of percentage given
     */
    public Map<Company, Integer> getRandomSharePercentageOfPortfolio(Portfolio portfolio, double percentage) {
        HashMap<Company, Share> sharesInCompanies = this.getSharesForPortfolio(portfolio);

        int totalCompanies = sharesInCompanies.size();

        Map<Company, Integer> numericShares = sharesInCompanies
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> (int) Math.floor(e.getValue().getNumberOfShares() * (percentage / 100))
            ));

        // int totalShares = numericShares.entrySet().stream().map(Map.Entry::getValue).reduce(0, Integer::sum);

        return numericShares;
    }

    /**
     * @param company given company
     * @param portfolio given portfolio
     * @return Mutable share object
     */
    public Share getSharesForCompanyInPortfolio(Company company, Portfolio portfolio) {
        return this.companyShares.getOrDefault(company, new HashMap<>()).getOrDefault(portfolio, new Share(0));
    }
}
