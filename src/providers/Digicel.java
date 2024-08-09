package providers;

import customers.Customer;
import credit.PhoneCredit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import filehandler.FileHandler;

public class Digicel extends ServiceProvider {
    private int numBranches;
    private List<Customer> customers = new ArrayList<>();
    private List<PhoneCredit> phoneCredits = new ArrayList<>();

    // Default Constructor
    public Digicel() {
        super("DefaultDigicelId", "DefaultDigicelAddress");
        this.numBranches = 3;
        initializeData();
    }

    // Primary Constructor
    public Digicel(String companyId, String address, int numBranches) {
        super(companyId, address);
        this.numBranches = numBranches;
        initializeData();
    }

    // Copy constructor
    public Digicel(Digicel obj) {
        super(obj.getCompanyId(), obj.getAddress());
        this.numBranches = obj.numBranches;
        setCustomers(obj.getCustomers());
        setPhoneCredits(obj.getPhoneCredits());
    }

    private void initializeData() {
        List<Customer> customers = FileHandler.loadCustomers(FileHandler.getDigicelCustomersFilePath());
        List<PhoneCredit> phoneCredits = FileHandler.loadPhoneCredits(FileHandler.getDigicelCreditFilePath());
        setCustomers(customers);
        setPhoneCredits(phoneCredits);
    }

    @Override
    public void addCustomer(Customer customer) {
        // Validate phone number format
        if (!isValidPhoneNumber(customer.getTelephoneNumber())) {
            System.out.println("Invalid phone number format for Digicel.");
            return;
        }

        // Validate TRN format
        if (!isValidTRN(customer.getCustomerId())) {
            System.out.println("Invalid TRN format.");
            return;
        }

        List<Customer> existingCustomers = FileHandler.loadCustomers(FileHandler.getDigicelCustomersFilePath());

        // Check if the customer already exists
        boolean customerExists = existingCustomers.stream()
            .anyMatch(c -> c.getCustomerId().equals(customer.getCustomerId()));
        if (customerExists) {
            System.out.println("Customer with this TRN already exists.");
            return;
        }

        existingCustomers.add(customer);
        setCustomers(existingCustomers);
        FileHandler.saveCustomers(existingCustomers, FileHandler.getDigicelCustomersFilePath());
        System.out.println("Customer added to Digicel.");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for Phone# format: prefix-000-0000
        String phonePattern = "^(301|302|303|304)-\\d{3}-\\d{4}$";
        Pattern pattern = Pattern.compile(phonePattern);
        return pattern.matcher(phoneNumber).matches();
    }

    private boolean isValidTRN(String trn) {
        // Regular expression for TRN format: 000-000-000 where each digit is between 1 and 9
        String trnPattern = "\\d{3}-\\d{3}-\\d{3}";
        Pattern pattern = Pattern.compile(trnPattern);
        return pattern.matcher(trn).matches();
    }

    @Override
    public void createPhoneCredit() {
        Random random = new Random();

        // Generate a random 13-digit card number
        String cardNumber = String.format("%013d", Math.abs(random.nextLong() % 10000000000000L));

        // Select a random denomination
        double[] denominations = {100.0, 200.0, 500.0, 1000.0};
        double denomination = denominations[random.nextInt(denominations.length)];

        PhoneCredit newCredit = new PhoneCredit(cardNumber, denomination);

        List<PhoneCredit> credits = FileHandler.loadPhoneCredits(FileHandler.getDigicelCreditFilePath());
        credits.add(newCredit);
        FileHandler.savePhoneCredits(credits, FileHandler.getDigicelCreditFilePath());

        System.out.println("Phone credit created for Digicel.");
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public void setPhoneCredits(List<PhoneCredit> phoneCredits) {
        this.phoneCredits = phoneCredits;
    }

    @Override
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        numCustomers = customers.size();
    }

    @Override
    public List<PhoneCredit> getPhoneCredits() {
        List<PhoneCredit> credits = FileHandler.loadPhoneCredits(FileHandler.getDigicelCreditFilePath());
        System.out.println("Digicel Phone Credits:");
        for (PhoneCredit credit : credits) {
            System.out.println(credit);
        }
        return credits;
    }

    @Override
    public void displayCompanyInfo() {
        System.out.println("Company ID: " + getCompanyId());
        System.out.println("Address: " + getAddress());
        System.out.println("Number of Branches: " + numBranches);
        System.out.println("Total Customers: " + numCustomers);
    }

    public int getNumBranches() {
        return numBranches;
    }

    public void setNumBranches(int numBranches) {
        this.numBranches = numBranches;
    }
}
