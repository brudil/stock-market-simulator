package swe.engine;

public class Share {
    Company company;
    int numberOfShares;

    public Share(Company company, int numberOfShares) {
        this.company = company;
        this.numberOfShares = numberOfShares;
    }

    public Company getCompany() {
        return this.company;
    }

    public int getNoOfShares() {
        return this.numberOfShares;
    }

    @Override
    public String toString() {
        return "Share{" + "Company=" + this.company + ", no_of_shares=" + this.numberOfShares + '}';
    }
    
    
}
