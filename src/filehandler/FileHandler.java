package filehandler;

import customers.Customer;
import credit.PhoneCredit;
import providers.Digicel;
import providers.Flow;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DIGICEL_CUSTOMERS_FILE = "src/files/Digicel_Customers.txt";
    private static final String FLOW_CUSTOMERS_FILE = "src/files/Flow_Customers.txt";
    private static final String DIGICEL_CREDIT_FILE = "src/files/Digicel_CardInformation.txt";
    private static final String FLOW_CREDIT_FILE = "src/files/Flow_CardInformation.txt";

    // Ensure files exist or create them
    public static void checkForFiles() {
        createFiles(DIGICEL_CUSTOMERS_FILE);
        createFiles(FLOW_CUSTOMERS_FILE);
        createFiles(DIGICEL_CREDIT_FILE);
        createFiles(FLOW_CREDIT_FILE);
    }

    private static void createFiles(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile(); // Create the file
                System.out.println("Created missing file: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + filePath);
            e.printStackTrace();
        }
    }

    public static void initializeProviderData(Digicel digicel, Flow flow) {
        List<Customer> digicelCustomers = loadCustomers(DIGICEL_CUSTOMERS_FILE);
        List<PhoneCredit> digicelCredits = loadPhoneCredits(DIGICEL_CREDIT_FILE);
        digicel.setCustomers(digicelCustomers);
        digicel.setPhoneCredits(digicelCredits);

        List<Customer> flowCustomers = loadCustomers(FLOW_CUSTOMERS_FILE);
        List<PhoneCredit> flowCredits = loadPhoneCredits(FLOW_CREDIT_FILE);
        flow.setCustomers(flowCustomers);
        flow.setPhoneCredits(flowCredits);
    }

    public static List<Customer> loadCustomers(String filePath) {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Customer customer = new Customer(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        Double.parseDouble(parts[4])
                    );
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customer file: " + e.getMessage());
        }
        return customers;
    }

    public static void saveCustomers(List<Customer> customers, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Customer customer : customers) {
                writer.write(customer.getCustomerId() + "," +
                             customer.getLastName() + "," +
                             customer.getAddress() + "," +
                             customer.getTelephoneNumber() + "," +
                             customer.getCreditBalance());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing customer file: " + e.getMessage());
        }
    }

    public static List<PhoneCredit> loadPhoneCredits(String filePath) {
        List<PhoneCredit> credits = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    PhoneCredit credit = new PhoneCredit(parts[0], Double.parseDouble(parts[1]));
                    credit.setStatus(parts[2]);
                    credits.add(credit);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading phone credit file: " + e.getMessage());
        }
        return credits;
    }

    public static void savePhoneCredits(List<PhoneCredit> credits, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (PhoneCredit credit : credits) {
                writer.write(credit.getCardNumber() + "," +
                             credit.getDenomination() + "," +
                             credit.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing phone credit file: " + e.getMessage());
        }
    }
    
    public static void saveProviderData(Digicel digicel, Flow flow) {
        saveCustomers(digicel.getCustomers(), DIGICEL_CUSTOMERS_FILE);
        savePhoneCredits(digicel.getPhoneCredits(), DIGICEL_CREDIT_FILE);
        saveCustomers(flow.getCustomers(), FLOW_CUSTOMERS_FILE);
        savePhoneCredits(flow.getPhoneCredits(), FLOW_CREDIT_FILE);
    }

    public static String getDigicelCustomersFilePath() {
        return DIGICEL_CUSTOMERS_FILE;
    }

    public static String getFlowCustomersFilePath() {
        return FLOW_CUSTOMERS_FILE;
    }

    public static String getDigicelCreditFilePath() {
        return DIGICEL_CREDIT_FILE;
    }

    public static String getFlowCreditFilePath() {
        return FLOW_CREDIT_FILE;
    }
}
