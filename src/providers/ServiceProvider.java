package providers;

import credit.PhoneCredit;
import customers.Customer;

import java.util.ArrayList;
import java.util.List;

public abstract class ServiceProvider {
    private String companyId;
    private String address;
    protected static int numCustomers = 0;
    protected List<PhoneCredit> phoneCredits = new ArrayList<>();
    protected List<Customer> customers = new ArrayList<>();

    // Constructor to initialize common attributes
    public ServiceProvider(String companyId, String address) {
        this.companyId = companyId;
        this.address = address;
    }

    // Abstract methods to be implemented by subclasses
    public abstract void addCustomer(Customer customer);
    public abstract List<Customer> getCustomers();
    public abstract void setCustomers(List<Customer> customers);
    public abstract void createPhoneCredit();
    public abstract void displayCompanyInfo();
    public abstract void setPhoneCredits(List<PhoneCredit> phoneCredits);

    // Concrete method to view all phone credits
    public List<PhoneCredit> getPhoneCredits() {
        return phoneCredits;
    }

    // Static method to get total number of customers across all providers
    public static int getTotalCustomers() {
        return numCustomers;
    }

    // Getter and Setter methods for encapsulation
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
