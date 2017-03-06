package swe.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void commitBestCaseTrades(HashMap<Portfolio, TradeSlip> trades) {
        // merge all requested trades in to buy & sell (with deltas)

        HashMap<Company, HashMap<Portfolio, Integer>> tradesByCompany = new HashMap<>();

        // for each company
        for (Map.Entry<Portfolio, TradeSlip> tradeSlip : trades.entrySet()) {
            for(Map.Entry<Company, Integer> trade : tradeSlip.getValue().entrySet()) {
                tradesByCompany.getOrDefault(trade.getKey(), new HashMap<>()).put(tradeSlip.getKey(), trade.getValue());
            }
        }

        for (Map.Entry<Company, HashMap<Portfolio, Integer>> companyTrades : tradesByCompany.entrySet()) {
            this.commitTradeForCompany(companyTrades.getKey(), companyTrades.getValue());
        }
    }

    private void commitTradeForCompany(Company company, HashMap<Portfolio, Integer> portfolioDeltas) {
        System.out.println(company.getName());

        // separate out our buyers and sellers
        Stream<Map.Entry<Portfolio, Integer>> buyers = portfolioDeltas.entrySet().stream().filter(set -> set.getValue() > 0);
        Stream<Map.Entry<Portfolio, Integer>> sellers = portfolioDeltas.entrySet().stream().filter(set -> set.getValue() < 0);

        // reduce those lists to total sell and buy amount
        int totalBuy = buyers.map(Map.Entry::getValue).reduce(0, Integer::sum);
        int totalSell = sellers.map(Map.Entry::getValue).reduce(0, Integer::sum) * -1;


        // if we have no buyers OR sellers abort
        if (totalBuy <= 0 || totalSell <= 0) {
            return;
        }

        if (totalBuy >= totalSell) {
            // TODO: ensure rounding is correct
            Map<Portfolio, Integer> possibleBuy = buyers.collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> totalSell * (e.getValue() / totalBuy)
            ));

            // sell all
            // buy possibleBuy
        } else {
            // TODO: ensure rounding is correct
            Map<Portfolio, Integer> possibleSell = sellers.collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> totalSell * (e.getValue() / totalBuy)
            ));

            // buy all
            // sell possibleSell
        }

    }
}
