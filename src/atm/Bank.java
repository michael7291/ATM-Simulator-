package atm;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;


    /**
     * Create new Bank object with empty lists of users and accounts
     * @param name name of bank
     */
    public Bank(String name){
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.users = new ArrayList<User>();
    }

    /**
     * Generate a new universally unique ID for user
     * @return the uuID
     */
    public String getNewUserUUID(){

        String uuID;
        Random rand = new Random();
        int len = 6;
        boolean nonUnique;

        // Continue looping until we get unique uuID
        do {

            // Generate the random number
            uuID = "";
            for(int i = 0; i < len; i++){
                uuID += ((Integer)rand.nextInt(10)).toString();
            }


            // Check to make sure number is unique.
            // If there is already an identical number in the list,
            // it is not a unique number so set nonUnique to true
            // and break out of for-loop.
            nonUnique = false;
            for(User u : this.users){
                if(uuID.compareTo(u.getuuID()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuID;
    }


    /**
     * Generate new universally unique ID for an account.
     * @return uuID
     */
    public String getNewAccountUUID(){

        String uuID;
        Random rand = new Random();
        int len = 10;
        boolean nonUnique;

        // Continue looping until we get unique uuID
        do {

            // Generate the random number
            uuID = "";
            for(int i = 0; i < len; i++){
                uuID += ((Integer)rand.nextInt(10)).toString();
            }


            // Check to make sure number is unique.
            // If there is already an identical number in the list,
            // it is not a unique number so set nonUnique to true
            // and break out of for-loop.
            nonUnique = false;
            for(Account a : this.accounts){
                if(uuID.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuID;

    }

    /**
     * Add an account to bank
     * @param anAccount The account to add
     */
    public void addAccount(Account anAccount){
        this.accounts.add(anAccount);
    }

    public User addUser(String firstName, String lastName, String pin){

        //Create a new User object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // Create a savings account for user
        Account newAccount = new Account("Savings", newUser, this);

        // Add this account (the constructor) to the holder's (User) list of accounts
        // and to the bank's list of accounts.
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    /**
     * Get the User object associated with a particular userID and pin,
     * if they are valid.
     * @param uuID the UUID of the user to log in
     * @param pin the user's pin
     * @return The User object if the login is successful, or null if
     *  it is not.
     */
    public User userLogin(String uuID, String pin){

        // Loop through list of users
        for(User u: this.users){

            // Check if user ID is correct
            if(u.getuuID().compareTo(uuID) == 0 && u.validatePin(pin)){
                return u;
            }
        }

        // If we could not find user or have incorrect pin
        return null;
    }

    /**
     * Get the name of the bank
     * @return name of bank
     */
    public String getName(){
        return this.name;
    }
}
