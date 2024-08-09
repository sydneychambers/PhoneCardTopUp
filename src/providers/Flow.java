package providers;

import customers.Customer;
import credit.PhoneCredit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import filehandler.FileHandler;

public class Flow extends ServiceProvider {
    private String parentCompanyName;
    private List<Customer> customers = new ArrayList<>();

    // Default constructor
    public Flow() {
        super("DefaultFlowId", "DefaultFlowAddress");
        this.parentCompanyName = "DefaultParentCompany";
        initializeData();
    }

    // Primary Constructor
    public Flow(String companyId, String address, String parentCompanyName) {
        super(companyId, address);
        this.parentCompanyName = parentCompanyName;
        initializeData();
    }

    // Copy constructor
    public Flow(Flow obj) {
        super(obj.getCompanyId(), obj.getAddress());
        this.parentCompanyName = obj.parentCompanyName;
        setCustomers(obj.getCustomers());
        setPhoneCredits(obj.getPhoneCredits());
    }

    private void initializeData() {
        List<Customer> customers = FileHandler.loadCustomers(FileHandler.getFlowCustomersFilePath());
        List<PhoneCredit> phoneCredits = FileHandler.loadPhoneCredits(FileHandler.getFlowCreditFilePath());
        setCustomers(customers);
        setPhoneCredits(phoneCredits);
    }

    @Override
   public void addCustomer(Customer customer) {
    if (!isValidPhoneNumber(customer.getTelephoneNumber())) {
        System.out.println("Invalid phone number format for Flow.");
        return;
    }

    if (!isValidTRN(customer.getCustomerId())) {
        System.out.println("Invalid TRN format.");
        return;
    }

    List<Customer> existingCustomers = FileHandler.loadCustomers(FileHandler.getFlowCustomersFilePath());

    // Check if the customer already exists
    boolean customerExists = existingCustomers.stream()
        .anyMatch(c -> c.getCustomerId().equals(customer.getCustomerId()));
    if (customerExists) {
        System.out.println("Customer with this TRN already exists.");
        return;
    }

    existingCustomers.add(customer);
    setCustomers(existingCustomers);
    FileHandler.saveCustomers(existingCustomers, FileHandler.getFlowCustomersFilePath());
    System.out.println("Customer added to Flow.");
}


    private boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for Phone# format: prefix-000-000
        String phonePattern = "^(601|602|603|604)-\\d{3}-\\d{4}$";
        Pattern pattern = Pattern.compile(phonePattern);
        return pattern.matcher(phoneNumber).matches();
    }


    private boolean isValidTRN(String trn) {
        // Regular expression for TRN format: 000-000-000
        String trnPattern = "\\d{3}-\\d{3}-\\d{3}";
        Pattern pattern = Pattern.compile(trnPattern);
        return pattern.matcher(trn).matches();
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        numCustomers = customers.size(); // Update numCustomers based on the list size
    }

    @Override
    public void setPhoneCredits(List<PhoneCredit> phoneCredits) {
        this.phoneCredits = phoneCredits;
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

        List<PhoneCredit> credits = FileHandler.loadPhoneCredits(FileHandler.getFlowCreditFilePath());
        credits.add(newCredit);
        FileHandler.savePhoneCredits(credits, FileHandler.getFlowCreditFilePath());

        System.out.println("Phone credit created for Flow.");
    }

    @Override
    public List<PhoneCredit> getPhoneCredits() {
        List<PhoneCredit> credits = FileHandler.loadPhoneCredits(FileHandler.getFlowCreditFilePath());
        System.out.println("Flow Phone Credits:");
        for (PhoneCredit credit : credits) {
            System.out.println(credit);
        }
        return credits;
    }

    @Override
    public void displayCompanyInfo() {
        System.out.println("Company ID: " + getCompanyId());
        System.out.println("Address: " + getAddress());
        System.out.println("Parent Company: " + parentCompanyName);
        System.out.println("Total Customers: " + numCustomers);
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }
}
