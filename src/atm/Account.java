package atm;

import java.util.ArrayList;

public class Account {

    // Account name
    private String name;

    // The user who is associated with the account
    private User accountHolder;

    // The account's ID
    private String uuID;

    // List of transactions for this account
    private ArrayList<Transaction> transactions;


    /**
     *
     * @param name name of the account
     * @param accountHolder the User object that holds this account
     * @param bank the bank that issues the account
     */
    public Account(String name, User accountHolder, Bank bank){

        // Set the account name and holder
        this.name = name;
        this.accountHolder = accountHolder;

        // Get new account UUID
        this.uuID = bank.getNewAccountUUID();

        // Create empty list of transactions for this account
        this.transactions = new ArrayList<Transaction>();

    }

    /**
     * Get the account ID
     * @return the uuid
     */
    public String getUUID(){
        return this.uuID;
    }


    /**
     * Get summary line for the account
     * @return the string summary
     */
    public String getSummaryLine(){
        double balance = this.getBalance();

        if(balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuID, balance
            , this.name);
        } else{
            return String.format("%s : $(%.02f) : %s", this.uuID, balance
                    , this.name);
        }
    }


    /**
     * Get balance for this account
     * @return account balance
     */
    public double getBalance(){
        double balance = 0;

        for(Transaction t : transactions){
            balance += t.getAmount();
        }

        return balance;
    }


    /**
     * Print the transaction history
     */
    public void printTransHistory(){
        System.out.printf("\nTransaction history for account %s:\n", this.uuID);
        for(int i = this.transactions.size()-1; i >= 0; i--){
            System.out.println(this.transactions.get(i).getSummaryLine());

        }

        System.out.println();
    }

    public void addTransaction(double amount, String memo){

        // create new transaction object and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }

}
