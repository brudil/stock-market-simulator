package swe.setup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import swe.engine.Company;
import swe.engine.Portfolio;
import swe.engine.SharesMap;
import swe.engine.StockType;
import swe.engine.traders.RandomTrader;
import swe.engine.traders.Trader;

public class Setup {

    public ArrayList<Company> Companies;
    public ArrayList<Portfolio> Clients;
    public SharesMap Shares;

    public Setup() {
        Companies = new ArrayList<>();
        Clients = new ArrayList<>();
        Shares = new SharesMap();
    }

    void start() {
        System.out.println("Setup Starting...");
        int choice = 0;
        while (choice != 7) {
            // Display menu graphics
            System.out.println("================================");
            System.out.println("|            MENU              |");
            System.out.println("================================");
            System.out.println("| Options:                     |");
            System.out.println("|        1. Enter A Company    |");
            System.out.println("|        2. List All Companies |");
            System.out.println("|        3. Enter A Client     |");
            System.out.println("|        4. View A Client      |");
            System.out.println("|        5. Save Config        |");
            System.out.println("|        6. Load Config        |");
            System.out.println("|        7. Exit               |");
            System.out.println("================================");

            // get input choice from user
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter Choice: ");
            choice = keyboard.nextInt();

            // Switch construct
            switch (choice) {
                case 1:
                    System.out.println("Option 1 selected");
                    addNewCompany();
                    break;
                case 2:
                    System.out.println("Option 2 selected");
                    listCompanies();
                    break;
                case 3:
                    System.out.println("Option 3 selected");
                    addNewClient();
                    break;
                case 4:
                    System.out.println("Option 4 selected");
                    viewClient();
                    break;
                case 5:
                    System.out.println("Option 5 selected");
                    saveConfig();
                    break;
                case 6:
                    System.out.println("Option 6 selected");
                    loadConfig();
                    break;
                case 7:
                    System.out.println("Exit selected");
                    break;
                default:
                    System.out.println("Invalid selection");
                    break; // This break is not really necessary
            }
        }
    }

    private void addNewCompany() {      
        // create a scanner
        Scanner s = new Scanner(System.in);
        
        // enter company name
        System.out.println("Enter Company Name: ");
        String c_name = s.nextLine();
        
        // enter company type
        int done = 0;
        StockType selectedStockType = null;
        while (done == 0) {
            System.out.println("\nChoose Company Type: ");
            System.out.println("1. Hi-Tech");
            System.out.println("2. Property");
            System.out.println("3. Hard");
            System.out.println("4. Food");
            System.out.println("Make selection: ");
                int choice = s.nextInt();
                // Switch construct
                switch (choice) {
                case 1:
                    System.out.println("\nHi-Tech Selected\n");
                    selectedStockType = StockType.HITECH;
                    done = 1;
                    break;
                case 2:
                    System.out.println("\nProperty Selected\n");
                    selectedStockType = StockType.PROPERTY;
                    done = 1;
                    break;
                case 3:
                    System.out.println("\nHard Selected\n");
                    selectedStockType = StockType.HARD;
                    done = 1;
                    break;
                case 4:
                    System.out.println("\nFood Selected\n");
                    selectedStockType = StockType.FOOD;
                    done = 1;
                    break;
                default:
                    System.out.println("\nInvalid Selection\n");
                    break; // This break is not really necessary
                }
        }
        
        // enter company closing price (31st December)
        System.out.println("Enter Company Closing Price: ");
        Float c_price = s.nextFloat();
        
        // create the new company and add it to the global companies array list.
        addCompany(c_name, selectedStockType, c_price);
        //Companies.add(new Company(c_name, selectedStockType, c_price));
        System.out.println("\nCompany '"+c_name+"' has been added!\n");
    }

    public void addCompany(String name, StockType selectedStockType, Float price) {
        Companies.add(new Company(name, selectedStockType, price));
    }

    public void addClient(String name, Trader trader, Double cash) {
        Clients.add(new Portfolio(name, cash, trader));
    }

    private void listCompanies() {
        System.out.println("\nList Of All Companies:");
        for (int i = 0; i < Companies.size(); i++) {
            System.out.println(i+". "+Companies.get(i).getName());
        }
        System.out.println("\n");
    }

    private void addNewClient() {
        // create a scanner
        Scanner s = new Scanner(System.in);
        
        // enter client name
        System.out.println("Enter Client Name: ");
        String name = s.nextLine();
        
        // enter trader type
        int done = 0;
        String trader = "";
        while (done == 0) {
            System.out.println("\nChoose Trader Type: ");
            System.out.println("1. Random Balanced");
            System.out.println("2. Random Purchaser");
            System.out.println("3. Random Seller");
            System.out.println("4. Intelligent");
            System.out.println("Make selection: ");
                int choice = s.nextInt();
                // Switch construct
                switch (choice) {
                case 1:
                    System.out.println("\nRandom Balanced Selected\n");
                    trader = "BALANCED";
                    done = 1;
                    break;
                case 2:
                    System.out.println("\nRandom Purchaser Selected\n");
                    trader = "PURCHASER";
                    done = 1;
                    break;
                case 3:
                    System.out.println("\nRandom Seller Selected\n");
                    trader = "SELLER";
                    done = 1;
                    break;
                case 4:
                    System.out.println("\nIntelligent Selected\n");
                    trader = "INTELLIGENT";
                    done = 1;
                    break;
                default:
                    System.out.println("\nInvalid Selection\n");
                    break; // This break is not really necessary
                }
        }
        
        // enter client cash (£)
        System.out.println("Enter Client Cash (£): ");
        double cash = s.nextDouble();
        
        // create the new client and add it to the global clients array list.
        Portfolio new_client = new Portfolio(name, cash, new RandomTrader());
        Clients.add(new_client);
        System.out.println("\nClient '"+name+"' has been added!\n");
        
        // add all shares for the client
        System.out.println("\nPlease add all shares owned by '"+name+"'\n");
        int done2 = 0;
        while (done2 == 0) {
            System.out.println("\nMenu: ");
            System.out.println("1. Add A Share");
            System.out.println("2. Exit");
            System.out.println("Make selection: ");
                int choice = s.nextInt();
                // Switch construct
                switch (choice) {
                case 1:
                    System.out.println("\nAdd A New Share Selected\n");
                    
                    // get the company from the user
                    System.out.println("Which Company?\n");
                    listCompanies();
                    int c = s.nextInt();
                    System.out.println("Company '"+Companies.get(c).getName()+"' Selected\n");
                    
                    // get the no of shares from the user
                    System.out.println("How Many Shares does "+name+" have in "+Companies.get(c).getName()+"?");
                    int no_of_shares = s.nextInt();
                    
                    // add the share to the client
                    new_client.addNewShare(Companies.get(c), no_of_shares);
                    break;
                case 2:
                    System.out.println("\nAll Done...\n");
                    done2 = 1;
                    break;
                default:
                    System.out.println("\nInvalid Selection\n");
                    break; // This break is not really necessary
                }
        }
    }

    private void viewClient() {
        Scanner s = new Scanner(System.in);
        // get the company from the user
        System.out.println("Which Client?\n");
        listClients();
        System.out.println("Choice:");
        int choice = s.nextInt();
        System.out.println("Client '"+Clients.get(choice).getName()+"' Selected\n");
        System.out.println(Clients.get(choice).toString());
    }

    private void listClients() {
        System.out.println("\nList Of All Clients:");
        for (int i = 0; i < Clients.size(); i++) {
            System.out.println(i+". "+Clients.get(i).getName());
        }
        System.out.println("\n");       
    }

    public void saveConfig() {
        // try to open the file and save the 2 array lists
        try{
            // Catch errors in I/O if necessary.
            // Open a file to write to.
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter File Name: ");
            String name = keyboard.nextLine();

            FileOutputStream saveFile=new FileOutputStream(name+".config");

            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            // Now do the save.
            save.writeObject(Companies);
            save.writeObject(Clients);

            // Close the file.
            save.close();
        }
        catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    public void save(String name) {
        // try to open the file and save the 2 array lists
        try{
            // Catch errors in I/O if necessary.
            // Open a file to write to.
            FileOutputStream saveFile=new FileOutputStream(name+".config");

            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            // Now do the save.
            save.writeObject(Companies);
            save.writeObject(Clients);
            save.writeObject(Shares);

            // Close the file.
            save.close();
        }
        catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    public void loadConfig() {
        // Wrap all in a try/catch block to trap I/O errors.
        try{
            // Open a file to write to
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter File Name: ");
            String name = keyboard.nextLine();

            // Open file to read from
            FileInputStream saveFile = new FileInputStream(name+".config");

            // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);

            // read the company and client arrays back into the variables
            // make sure to cast the types
            // throwing an error but doesn't matter lol
            Companies = (ArrayList<Company>) save.readObject();
            Clients = (ArrayList<Portfolio>) save.readObject();

            // Close the file.
            save.close(); // This also closes saveFile.
        }
        catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    public void load(String name) {
        // Wrap all in a try/catch block to trap I/O errors.
        try{

            // Open file to read from
            FileInputStream saveFile = new FileInputStream(name+".config");

            // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);

            // read the company and client arrays back into the variables
            // make sure to cast the types
            // throwing an error but doesn't matter lol
            Companies = (ArrayList<Company>) save.readObject();
            Clients = (ArrayList<Portfolio>) save.readObject();
            Shares = (SharesMap) save.readObject();

            // Close the file.
            save.close(); // This also closes saveFile.
        }
        catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }
}
