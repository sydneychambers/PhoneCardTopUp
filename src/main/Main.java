package main;

import sections.AdministrationSection;
import sections.CustomerSection;
import providers.Digicel;
import providers.Flow;
import filehandler.FileHandler;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize service providers
        Digicel digicel = new Digicel();
        Flow flow = new Flow();

        // Ensure files exist
        FileHandler.checkForFiles();

        // Load data from files
        FileHandler.initializeProviderData(digicel, flow);

        // Initialize sections
        AdministrationSection adminSection = new AdministrationSection();
        CustomerSection customerSection = new CustomerSection();

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select a section:");
            System.out.println("1. Administration");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminSection.handleAdminSection();
                    break;
                case 2:
                    customerSection.handleCustomerSection();
                    break;
                case 3:
                    // Save data before exiting
                    FileHandler.saveProviderData(digicel, flow);
                    System.out.println("Data saved. Exiting...");
                    return; // Exit the application
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }
}
