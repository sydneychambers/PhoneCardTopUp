package sections;

import customers.Customer;
import providers.Digicel;
import providers.Flow;
import providers.ServiceProvider;
import credit.PhoneCredit;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CustomerSection {
    private Digicel digicel;
    private Flow flow;

    // PRimary Constructor
    public CustomerSection() {
        this.digicel = new Digicel();
        this.flow = new Flow();
        filehandler.FileHandler.initializeProviderData(digicel, flow);
    }

    public void handleCustomerSection() {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Select an option:");
            System.out.println("1) Add Credit");
            System.out.println("2) Check Balance");
            System.out.println("3) Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleAddCredit(scanner);
                    break;
                case 2:
                    handleCheckBalance(scanner);
                    break;
                case 3:
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }

    private void handleAddCredit(Scanner scanner) {
    System.out.print("Enter your TRN (format: 000-000-000): ");
    String trn = scanner.nextLine();

    if (!isValidTRN(trn)) {
        System.out.println("Invalid TRN format.");
        return;
    }

    System.out.print("Enter your telephone number (format: 301-000-0000): ");
    String telephoneNumber = scanner.nextLine();

    if (!isValidPhoneNumber(telephoneNumber)) {
        System.out.println("Invalid phone number format.");
        return;
    }

    ServiceProvider provider = getProviderByPhoneNumber(telephoneNumber);
    if (provider == null) {
        System.out.println("Invalid phone number.");
        return;
    }

    Customer customer = getCustomerByPhoneNumber(provider, telephoneNumber);
    if (customer == null) {
        System.out.println("Customer not found.");
        return;
    }

    System.out.println("Found customer: " + customer);

    if (!customer.getCustomerId().equals(trn)) {
        System.out.println("TRN does not match.");
        return;
    }

    System.out.print("Enter card number (13 digits): ");
    String cardNumber = scanner.nextLine();

    if (!isValidCardNumber(cardNumber)) {
        System.out.println("Invalid card number format.");
        return;
    }

    PhoneCredit credit = getCreditByCardNumber(provider, cardNumber);
    if (credit != null && credit.getStatus().equals("available")) {
        customer.addCredit(credit.getDenomination());
        credit.markAsUsed();
        System.out.println("Credit successfully added.");
    } else {
        System.out.println("Invalid or already used card number.");
    }
}


    // Validate TRN format
    private boolean isValidTRN(String trn) {
        return trn.matches("\\d{3}-\\d{3}-\\d{3}");
    }

    // Validate card number format
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{13}");
    }

    private void handleCheckBalance(Scanner scanner) {
        System.out.print("Enter your telephone number (format: 301-000-0000): ");
        String telephoneNumber = scanner.nextLine();

        if (!isValidPhoneNumber(telephoneNumber)) {
            System.out.println("Invalid phone number format.");
            return;
        }

        ServiceProvider provider = getProviderByPhoneNumber(telephoneNumber);
        if (provider == null) {
            System.out.println("Invalid phone number.");
            return;
        }

        Customer customer = getCustomerByPhoneNumber(provider, telephoneNumber);
        if (customer != null) {
            System.out.println("Your balance is: $" + customer.getCreditBalance());
        } else {
            System.out.println("Customer not found.");
        }
    }

    // Validate phone number format
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^(301|302|303|304|601|602|603|604)-\\d{3}-\\d{4}$";
        Pattern pattern = Pattern.compile(phonePattern);
        return pattern.matcher(phoneNumber).matches();
    }

    private ServiceProvider getProviderByPhoneNumber(String phoneNumber) {
        String prefix = phoneNumber.substring(0, 3);
        switch (prefix) {
            case "301":
            case "302":
            case "303":
            case "304":
                return digicel;
            case "601":
            case "602":
            case "603":
            case "604":
                return flow;
            default:
                return null;
        }
    }

    private Customer getCustomerByPhoneNumber(ServiceProvider provider, String phoneNumber) {
        List<Customer> customers = provider.getCustomers();
        for (Customer customer : customers) {
            System.out.println("Checking customer: " + customer);
            if (customer.getTelephoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    private PhoneCredit getCreditByCardNumber(ServiceProvider provider, String cardNumber) {
        List<PhoneCredit> credits = provider.getPhoneCredits();
        for (PhoneCredit credit : credits) {
            if (credit.getCardNumber().equals(cardNumber)) {
                return credit;
            }
        }
        return null;
    }
}
