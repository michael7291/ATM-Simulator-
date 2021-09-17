package atm;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {

    // User's first name
    private String firstName;

    // User's last name
    private String lastName;

    // User's ID number
    private String uuid;

    // The MD5 hash of the user's pin number
    private byte pinHash[];

    // List of accounts for user
    private ArrayList<Account> accounts;


    /**
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param pin user's account pin number
     * @param bank the bank object that user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank bank){

        // Set the user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // Store the MD5 hash rather than the original user pin
        // for security purposes.
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        }catch (NoSuchAlgorithmException e){
            System.out.println("Error: NoSuchElementAlgorithm");
            e.printStackTrace();
            System.exit(1);
        }

        // Get a new unique universal user ID for the user
        this.uuid = bank.getNewUserUUID();

        // Create an empty list of accounts
        this.accounts = new ArrayList<Account>();

        // Print out message log for creating a new account
        System.out.printf("New user %s, %s with ID %s created. \n", lastName, firstName, this.uuid);
    }

    /**
     * Add an account for user
     * @param anAccount The account to add
     */
    public void addAccount(Account anAccount){
        this.accounts.add(anAccount);
    }


    /**
     * Get the User's uuid
     * @return the uuid
     */
    public String getuuID(){
        return this.uuid;
    }


    /**
     * Check whether a given pin matches the true user pin.
     * @param aPin Pin to check (user enters)
     * @return Whether the pin is valid or not
     */
    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e){
            System.out.println("Error: NoSuchElementAlgorithm");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    public String getFirstName(){
        return this.firstName;
    }


    /**
     * Print out summaries for accounts of this user
     */
    public void printAccountsSummary(){
        System.out.printf("%s's account summary: ", this.getFirstName());
        System.out.println();
        for(int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("  %d) %s", i+1, this.accounts.get(i).getSummaryLine());
            System.out.println();
        }

        System.out.println();
    }


    /**
     * Get the number of accounts this user has
     * @return number of accounts
     */
    public int numAccounts(){
        return this.accounts.size();
    }


    /**
     * Print the transaction history for a particular account.
     * @param acctIndex the index of the account to use
     */
    public void printAccountTransHistory(int acctIndex){
        this.accounts.get(acctIndex).printTransHistory();
    }


    /**
     * Get the account balance
     * @param acctIndex index of account to use
     * @return the account balance
     */
    public double getAccountBalance(int acctIndex){
        return this.accounts.get(acctIndex).getBalance();
    }


    /**
     * Get UUID of a particular account
     * @param acctIndex the index of the account to use
     * @return the UUID of the account
     */
    public String getAcctUUID(int acctIndex){
        return this.accounts.get(acctIndex).getUUID();
    }


    public void addAccountTransaction(int acctIdex, double amount, String memo){
        this.accounts.get(acctIdex).addTransaction(amount, memo);

    }
}
