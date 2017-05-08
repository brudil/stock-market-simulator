package swe.engine;

/**
 * Represents a collection of shares between a Company and a Portfolio
 */
public class Share {
    private int numberOfShares;

    public Share(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public int getNumberOfShares() {
        return this.numberOfShares;
    }

    @Override
    public String toString() {
        return "Share{" + "Company=" + "tbi" + ", no_of_shares=" + this.numberOfShares + '}';
    }


    public void deltaSharesBy(int amount) {
        this.numberOfShares = this.numberOfShares + amount;
    }
}
