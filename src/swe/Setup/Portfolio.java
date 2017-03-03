/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.Setup;

import java.util.ArrayList;

/**
 *
 * @author Fin
 */
class Portfolio {
    String Name;
    Double Cash;
    String Trader_Type;
    ArrayList<Share> Shares = new ArrayList<>();

    public Portfolio(String Name, Double Cash, String Trader_Type) {
        this.Name = Name;
        this.Cash = Cash;
        this.Trader_Type = Trader_Type;
    }

    public String getName() {
        return Name;
    }

    public Double getCash() {
        return Cash;
    }

    public String getTrader_Type() {
        return Trader_Type;
    }

    public ArrayList<Share> getShares() {
        return Shares;
    }
    
    public void addNewShare(Company c, int NoOfShares) {
        Shares.add(new Share(c, NoOfShares));
    }

    @Override
    public String toString() {
        String s = "Client{" + "Name=" + Name + ", Cash=" + Cash + ", Trader_Type=" + Trader_Type;
        String s2 = "\nShares:\n";
        
        for (int i = 0; i < Shares.size(); i++) {
            s2 = s2.concat("Company: "+Shares.get(i).getCompany().getName()+"\tNo Of Shares: "+Shares.get(i).getNoOfShares()+"\n");
        }
                
        return s+s2;
    }
}
