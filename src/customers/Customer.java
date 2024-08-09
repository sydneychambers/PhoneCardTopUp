package customers;

import filehandler.FileHandler;

import java.util.List;

public class Customer {
    private String customerId;  // TRN
    private String lastName;
    private String address;
    private String telephoneNumber;  // area code + prefix + serial number
    private double creditBalance;

    // Default Constructor
    public Customer() {
        customerId = "000-000-000";
        lastName = "Doe";
        address = "First Street";
        telephoneNumber = "301-000-0000";
        creditBalance = 100.0;
    }

    // Primary Constructor
    public Customer(String customerId, String lastName, String address, String telephoneNumber, double creditBalance) {
        this.customerId = customerId;
        this.lastName = lastName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.creditBalance = creditBalance;t
    }

    // Copy constructor
    public Customer(Customer obj) {
        this.customerId = obj.customerId;
        this.lastName = obj.lastName;
        this.address = obj.address;
        this.telephoneNumber = obj.telephoneNumber;
        this.creditBalance = obj.creditBalance;
    }

    // Getters and setters for encapsulation
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public double getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(double creditBalance) {
        this.creditBalance = creditBalance;
    }

    public void addCredit(double amount) {
        this.creditBalance += amount;
        System.out.println("Credit added. New balance: $" + this.creditBalance);
        updateCustomerFile();
    }

    private void updateCustomerFile() {
        String filePath;
        if (this.telephoneNumber.startsWith("301") || this.telephoneNumber.startsWith("302")
                || this.telephoneNumber.startsWith("303") || this.telephoneNumber.startsWith("304")) {
            filePath = FileHandler.getDigicelCustomersFilePath();
        } else if (this.telephoneNumber.startsWith("601") || this.telephoneNumber.startsWith("602")
        || this.telephoneNumber.startsWith("603") || this.telephoneNumber.startsWith("604")) {
            filePath = FileHandler.getFlowCustomersFilePath();
        } else {
            throw new IllegalStateException("Unknown provider for phone number: " + this.telephoneNumber);
        }

        List<Customer> customers = FileHandler.loadCustomers(filePath);

        boolean customerUpdated = false;
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(this.customerId)) {
                customer.setLastName(this.lastName);
                customer.setAddress(this.address);
                customer.setTelephoneNumber(this.telephoneNumber);
                customer.setCreditBalance(this.creditBalance);
                customerUpdated = true;
                break;
            }
        }

        if (customerUpdated) {
            FileHandler.saveCustomers(customers, filePath);
        } else {
            System.out.println("Customer with ID " + this.customerId + " not found.");
        }
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId +
               ", Last Name: " + lastName +
               ", Address: " + address +
               ", Telephone: " + telephoneNumber +
               ", Balance: $" + creditBalance;
    }
}
