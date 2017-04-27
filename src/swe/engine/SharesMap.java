package swe.engine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (!this.portfolioShares.containsKey(portfolio)) {
            this.portfolioShares.put(portfolio, new HashMap<>());
        }
        return this.portfolioShares.get(portfolio);
    }

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
     * @param company
     * @param portfolio
     * @return Mutable share object
     */
    public Share getSharesForCompanyInPortfolio(Company company, Portfolio portfolio) {
        return this.companyShares.getOrDefault(company, new HashMap<>()).getOrDefault(portfolio, new Share(0));
    }
}
