package swe.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Market contains all market state ensuring consistency
 */
public class Market {
    private ArrayList<Company> companies;
    private ArrayList<Portfolio> portfolios;
    private SharesMap shares;

    public Market(ArrayList<Company> companies, ArrayList<Portfolio> portfolios, SharesMap sharesMap) {
        this.companies = companies;
        this.portfolios = portfolios;
        this.shares = sharesMap;

        for (Portfolio portfolio : portfolios) {
            portfolio.setMarket(this);
        }
    }

    public ArrayList<Portfolio> getPortfolios() {
        return this.portfolios;
    }

    public SharesMap getShares() {
        return this.shares;
    }

    public HashMap<Portfolio, TradeSlip> getRequestedPortfolioTrades() {
        HashMap<Portfolio, TradeSlip> trades = new HashMap<>();
        for (Portfolio portfolio : this.portfolios) {
            TradeSlip tradeSlip = portfolio.getRequestedTrades();
            if (tradeSlip != null) {
                trades.put(portfolio, tradeSlip);
            }
        }

        return trades;
    }

    /**
     * Performs requested portfolio trades using best-attempt method, sharing at over-buying & selling
     * @param trades Map of portfolios to a TradeSlip with their intended trades
     */
    public void performRequestedTradesBestAttempt(HashMap<Portfolio, TradeSlip> trades) {
        // merge all requested trades in to buy & sell (with deltas)
        HashMap<Company, HashMap<Portfolio, Integer>> tradesByCompany = new HashMap<>();

        // for each company
        // TODO: refactor, ugly
        for (Map.Entry<Portfolio, TradeSlip> tradeSlip : trades.entrySet()) {
            for(Map.Entry<Company, Integer> trade : tradeSlip.getValue().entrySet()) {
                HashMap<Portfolio, Integer> comp = tradesByCompany.getOrDefault(trade.getKey(), new HashMap<>());
                comp.put(tradeSlip.getKey(), trade.getValue());
                tradesByCompany.put(trade.getKey(), comp);
            }
        }

        for (Map.Entry<Company, HashMap<Portfolio, Integer>> companyTrades : tradesByCompany.entrySet()) {
            this.performBestAttemptTradesForCompany(companyTrades.getKey(), companyTrades.getValue());
        }
    }

    /**
     * Sells shares in a company
     * Does not ensure consistency
     * @param company Company of shares to sell
     * @param portfolio Portfolio selling shares
     * @param amount Quantity of shares to sell. larger than 0
     */
    private void sellSharesInPortfolio(Company company, Portfolio portfolio, int amount) {
        Share share = this.shares.getSharesForCompanyInPortfolio(company, portfolio);
        share.deltaSharesBy(-amount);
    }


    /**
     * Buys shares in a company
     * Does not ensure consistency
     * @param company Company of shares to buy
     * @param portfolio Portfolio buying shares
     * @param amount Quantity of shares to buy. larger than 0
     */
    private void buySharesInPortfolio(Company company, Portfolio portfolio, int amount) {
        Share share = this.shares.getSharesForCompanyInPortfolio(company, portfolio);
        share.deltaSharesBy(amount);
    }

    public void moveShares(Company company, Portfolio portfolioFrom, Portfolio portfolioTo, int amount) {
        this.sellSharesInPortfolio(company, portfolioFrom, amount);
        this.buySharesInPortfolio(company, portfolioTo, amount);
    }

    private static Stream<Map.Entry<Portfolio, Integer>> getBuyers(HashMap<Portfolio, Integer> portfolioDeltas) {
        return portfolioDeltas.entrySet().stream().filter(set -> set.getValue() > 0);
    }

    private static Stream<Map.Entry<Portfolio, Integer>> getSellers(HashMap<Portfolio, Integer> portfolioDeltas) {
        return portfolioDeltas.entrySet().stream()
                .filter(set -> set.getValue() < 0);
    }

    /**
     * Does best attempt trades for portfolios trading a certain company
     * @param company Company of shares to be traded
     * @param portfolioDeltas Map of delta change of shares to be traded
     */
    private void performBestAttemptTradesForCompany(Company company, HashMap<Portfolio, Integer> portfolioDeltas) {

        // reduce those lists to total sell and buy amount
        int totalBuy = getBuyers(portfolioDeltas).map(Map.Entry::getValue).reduce(0, Integer::sum);
        int totalSell = getSellers(portfolioDeltas).map(Map.Entry::getValue).reduce(0, Integer::sum) * -1;

        // if we have no buyers OR sellers abort
        if (totalBuy <= 0 || totalSell <= 0) {
            return;
        }

        if (totalBuy >= totalSell) {
            Map<Portfolio, Integer> possibleBuy = getBuyers(portfolioDeltas).collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> totalSell * (e.getValue() / totalBuy)
            ));

            // sell all
            int totalSold = getSellers(portfolioDeltas).map(seller -> {
                this.sellSharesInPortfolio(company, seller.getKey(), Math.abs(seller.getValue()));
                return Math.abs(seller.getValue());
            }).reduce(0, Integer::sum);

            // buy what we can
            int totalBrought = possibleBuy.entrySet().stream().map(buyer -> {
                this.buySharesInPortfolio(company, buyer.getKey(), buyer.getValue());
                return buyer.getValue();
            }).reduce(0, Integer::sum);

            double excess = (double) totalBuy - (double) totalSell;
            double div = excess / (double) shares.getTotalSharesForCompany(company);
            company.setPrice(company.getPrice() + (div * company.getPrice()));
        } else {
            Map<Portfolio, Integer> possibleSell = getSellers(portfolioDeltas).collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> totalBuy * (Math.abs(e.getValue()) / totalSell)
            ));

            // buy all
            int totalBrought = getBuyers(portfolioDeltas).map(seller -> {
                this.buySharesInPortfolio(company, seller.getKey(), seller.getValue());
                return seller.getValue();
            }).reduce(0, Integer::sum);

            // sell what we can
            int totalSold = possibleSell.entrySet().stream().map(seller -> {
                this.sellSharesInPortfolio(company, seller.getKey(), Math.abs(seller.getValue()));
                return Math.abs(seller.getValue());
            }).reduce(0, Integer::sum);

            double excess = (double) totalSell - (double) totalBuy;
            double div = excess / (double) shares.getTotalSharesForCompany(company);
            company.setPrice(company.getPrice() - (div * company.getPrice()));
        }
        // TODO: ensure what we have sold equals what was brought
    }

    public double getShareIndex() {
        // Get total of all companies share prices and then average them
        double totalPrice =  companies.stream()
                .mapToDouble(Company::getPrice)
                .reduce(0.0, Double::sum);

        return totalPrice / (double) companies.size();
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }
}
