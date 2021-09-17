package atm;

import java.util.Date;

public class Transaction {

    // The transaction amount.
    private double amount;

    // The time and date of the transaction.
    private Date timeStamp;

    // A memo for this transaction.
    private String memo;

    // The account where the transaction was performed.
    private Account inAccount;


    /**
     * Creates a new transaction.
     * @param amount The amount transacted.
     * @param memo The memo for the transaction.
     * @param inAccount The account the transaction belongs to.
     */
    public Transaction(double amount, String memo, Account inAccount){

        // Calls two-argument constructor
        this(amount, inAccount);

        // Sets the memo
        this.memo = memo;
    }

    /**
     * Creates a new transaction.
     * @param amount The amount transacted.
     * @param inAccount The account the transaction belongs to.
     */
    public Transaction(double amount, Account inAccount){

        // Sets the amount
        this.amount = amount;
        this.inAccount = inAccount;
        this.memo = "";
        this.timeStamp = new Date();
    }


    /**
     * Get the amount of the transaction
     * @return transaction amount
     */
    public double getAmount(){
        return this.amount;
    }


    /**
     * Get a string summarizing the transaction
     * @return the summary string
     */
    public String getSummaryLine(){
        if(this.amount >= 0){
            return String.format("%s : +$%.02f : %s", this.timeStamp.toString(),
                    this.amount, this.memo);
        } else{
            return String.format("%s : -$%.02f : %s", this.timeStamp.toString(),
                    -this.amount, this.memo);
        }



    }

}
