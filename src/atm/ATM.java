package atm;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        // Prompt user for name and to create a PIN.
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();
        System.out.print("Create a pin code: ");
        String pin = sc.nextLine();
        System.out.println();
        System.out.println();


        // init bank
        Bank theBank = new Bank("Bank of Michael");

        // Add a user which also creates a savings account
        User aUser = theBank.addUser(firstName, lastName, pin);

        // Add a checking account for the user and to the bank
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            // Stay in login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);

            // Stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    /**
     * Print the ATM's login menu
     *
     * @param theBank the Bank's object whose accounts to use
     * @param sc      the scanner object to use for user input
     * @return the authenticated user object
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;

        // Prompt User for user/id pin combo until a correct one is
        // reached.
        do {
            System.out.printf("\n\nWelcome to %s", theBank.getName());
            System.out.println();
            System.out.println();
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();
            System.out.println();
            System.out.println();


            // Try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination. " +
                        "Please try again. ");
            }
        } while (authUser == null);  // continue looping until successful login

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {

        // print a summary of the user's accounts
        theUser.printAccountsSummary();

        int choice;


        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println();
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if(choice < 1 || choice > 5){
                System.out.println("Invalid choice. Please choose 1-5");
            }

        } while (choice < 1 || choice > 5);


        // Process the choice
        switch (choice){

            case 1:
                ATM.showTransactionHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }

        // redisplay menu unless user chooses to quit
        if(choice != 5){
            printUserMenu(theUser, sc);
        }
    }


    /**
     * Show the transaction history for an account
     * @param theUser the logged-in user object
     * @param sc the Scanner object used for user input
     */
    public static void showTransactionHistory(User theUser, Scanner sc){
        int theAcct;

        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    " whose transactions you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if(theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        // print transaction history
        theUser.printAccountTransHistory(theAcct);
    }


    /**
     * Process transferring funds from one account to another
     * @param theUser the logged-in User object
     * @param sc      the Scanner object used for user input
     */
    public static void transferFunds(User theUser, Scanner sc){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);


        // get the account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());


        //get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if(amount > acctBal){
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);


        // do the transfer
        theUser.addAccountTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAccountTransaction(toAcct, amount, String.format("Transfer from account %s", theUser.getAcctUUID(fromAcct)));

    }


    /**
     * Process a fund withdraw from an account
     * @param theUser the logged-in User object
     * @param sc      the scanner object for user input
     */
    public static void withdrawFunds(User theUser, Scanner sc){
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);


        //get the amount to transfer
        do{
            System.out.printf("Enter the amount to withdraw (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if(amount > acctBal){
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);


        //take the rest of previous input
        sc.nextLine();

        // Get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        System.out.println();
        System.out.println();

        // Do the withdraw
        theUser.addAccountTransaction(fromAcct, -1*amount, memo);
    }


    /**
     * Process a deposit of funds to an account
     * @param theUser  logged-in User object
     * @param sc  scanner object for user input
     */
    public static void depositFunds(User theUser, Scanner sc){
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(toAcct);


        //get the amount to deposit
        do{
            System.out.print("Enter the amount to deposit: $");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);


        //take the rest of previous input
        sc.nextLine();

        // Get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        System.out.println();
        System.out.println();

        // Do the withdraw
        theUser.addAccountTransaction(toAcct, amount, memo);

    }


}
