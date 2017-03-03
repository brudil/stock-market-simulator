/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.Setup;

/**
 *
 * @author Fin
 */
class Share {
    Company c;
    int no_of_shares;

    public Share(Company c, int no_of_shares) {
        this.c = c;
        this.no_of_shares = no_of_shares;
    }

    public Company getCompany() {
        return c;
    }

    public int getNoOfShares() {
        return no_of_shares;
    }

    @Override
    public String toString() {
        return "Share{" + "Company=" + c + ", no_of_shares=" + no_of_shares + '}';
    }
    
    
}
