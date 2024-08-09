package credit;

import filehandler.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class PhoneCredit {
    private String cardNumber;  // Unique 13-digit card number
    private double denomination;  // $100, $200, $500, or $1000
    private String status;  // "available" or "used"

    public PhoneCredit() {
        cardNumber = "0000000000000";
        denomination = 100.00;
        this.status = "available";  // Default status is "available"
    }

    // Primary Constructor
    public PhoneCredit(String cardNumber, double denomination) {
        this.cardNumber = cardNumber;
        this.denomination = denomination;
        this.status = "available";  // Default status is "available"
    }

    // Copy constructor
    public PhoneCredit(PhoneCredit obj) {
        this.cardNumber = obj.cardNumber;
        this.denomination = obj.denomination;
        this.status = obj.status;
    }

    // Getters and setters for encapsulation
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getDenomination() {
        return denomination;
    }

    public void setDenomination(double denomination) {
        this.denomination = denomination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void markAsUsed() {
        this.status = "used";
        System.out.println("Phone credit marked as used.");
        updateCreditFile();
    }

    @Override
    public String toString() {
        return "Card Number: " + cardNumber +
               ", Denomination: $" + denomination +
               ", Status: " + status;
    }

    private void updateCreditFile() {
        String digicelFilePath = FileHandler.getDigicelCreditFilePath();
        String flowFilePath = FileHandler.getFlowCreditFilePath();

        List<PhoneCredit> phoneCreditList = new ArrayList<>();
        boolean found = false;

        // Check Digicel file
        phoneCreditList.addAll(FileHandler.loadPhoneCredits(digicelFilePath));
        for (PhoneCredit credit : phoneCreditList) {
            if (credit.getCardNumber().equals(this.cardNumber)) {
                credit.setStatus("used");
                found = true;
                break;
            }
        }
        if (found) {
            FileHandler.savePhoneCredits(phoneCreditList, digicelFilePath);
            return;
        }

        // Check Flow file
        phoneCreditList.clear();
        phoneCreditList.addAll(FileHandler.loadPhoneCredits(flowFilePath));
        for (PhoneCredit credit : phoneCreditList) {
            if (credit.getCardNumber().equals(this.cardNumber)) {
                credit.setStatus("used");
                found = true;
                break;
            }
        }
        if (found) {
            FileHandler.savePhoneCredits(phoneCreditList, flowFilePath);
        } else {
            System.out.println("Card number not found in any file.");
        }
    }
}
