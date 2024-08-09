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

    // Primary Construtctor
    public ServiceProvider(String companyId, String address) {
        this.companyId = companyId;
        this.address = address;
    }

    public abstract void addCustomer(Customer customer);
    public abstract List<Customer> getCustomers();
    public abstract void setCustomers(List<Customer> customers);
    public abstract void createPhoneCredit();
    public abstract void displayCompanyInfo();
    public abstract void setPhoneCredits(List<PhoneCredit> phoneCredits);

    public List<PhoneCredit> getPhoneCredits() {
        return phoneCredits;
    }

    public static int getTotalCustomers() {
        return numCustomers;
    }

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
