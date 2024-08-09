package sections;

import providers.Digicel;
import providers.Flow;
import providers.ServiceProvider;
import customers.Customer;

import java.util.List;
import java.util.Scanner;

public class AdministrationSection {
    private Digicel digicel;
    private Flow flow;

    // Primary Constructor
    public AdministrationSection() {
        this.digicel = new Digicel();
        this.flow = new Flow();
        filehandler.FileHandler.initializeProviderData(digicel, flow);
    }

    public void handleAdminSection() {
        Scanner scanner = new Scanner(System.in);
        boolean adminMenuActive = true;

        while (adminMenuActive) {
            System.out.println("Select Service Provider:");
            System.out.println("1. Digicel");
            System.out.println("2. Flow");
            System.out.println("3. Exit to Main Menu");
            int providerChoice = scanner.nextInt();
            scanner.nextLine();

            ServiceProvider selectedProvider = null;
            String password = "";

            switch (providerChoice) {
                case 1:
                    selectedProvider = digicel;
                    password = "TheBiggerBetterNetwork2021";
                    break;
                case 2:
                    selectedProvider = flow;
                    password = "TheWayIFlow2021";
                    break;
                case 3:
                    adminMenuActive = false;
                    continue; // Exit to Main Menu
                default:
                    System.out.println("Invalid selection.");
                    continue; // Stay in the loop
            }

            // Password verification
            System.out.print("Enter password: ");
            String enteredPassword = scanner.nextLine();

            if (!enteredPassword.equals(password)) {
                System.out.println("Incorrect password.");
                continue; // Return to provider selection
            }

            // Administrative tasks menu
            boolean adminTasksActive = true;

            while (adminTasksActive) {
                System.out.println("Select an option:");
                System.out.println("a) Display company information");
                System.out.println("b) Add customer");
                System.out.println("c) View customer base");
                System.out.println("d) Create phone credit");
                System.out.println("e) View all phone credit");
                System.out.println("f) Save data and exit to Main Menu");
                char choice = scanner.nextLine().charAt(0);

                switch (choice) {
                    case 'a':
                        selectedProvider.displayCompanyInfo();
                        break;
                    case 'b':
                        addCustomer(selectedProvider, scanner);
                        break;
                   case 'c':
                        List<Customer> customers = selectedProvider.getCustomers();
                        if (((List<?>) customers).isEmpty()) {
                            System.out.println("No customers found.");
                        } else {
                            System.out.println("Customer base:");
                            for (Customer customer : customers) {
                                System.out.println(customer);
                            }
                        }
                        break;
                    case 'd':
                        selectedProvider.createPhoneCredit();
                        break;
                    case 'e':
                        selectedProvider.getPhoneCredits();
                        break;
                    case 'f':
                        filehandler.FileHandler.saveProviderData(digicel, flow);
                        System.out.println("Data saved.");
                        adminTasksActive = false; // Exit to provider selection
                        break;
                    default:
                        System.out.println("Invalid selection.");
                }
            }
        }
    }

    private void addCustomer(ServiceProvider provider, Scanner scanner) {
        System.out.print("Enter Customer ID (TRN): ");
        String customerId = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Telephone Number: ");
        String telephoneNumber = scanner.nextLine();

        Customer newCustomer = new Customer(customerId, lastName, address, telephoneNumber, 100.0);
        provider.addCustomer(newCustomer);
    }
}
