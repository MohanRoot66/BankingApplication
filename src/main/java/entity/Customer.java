package entity;

public class Customer {

    private int customerAccountNumber;
    private String customerName;
    private String customerPassword;
    private int customerBalance;
    private String customerEmail;
    private String customerMobile;
    private String customerAddress;

    public Customer(int customerAccountNumber, String customerName, String customerPassword, int customerBalance, String customerEmail, String customerMobile, String customerAddress) {
        this.customerAccountNumber = customerAccountNumber;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customerBalance = customerBalance;
        this.customerEmail = customerEmail;
        this.customerMobile = customerMobile;
        this.customerAddress = customerAddress;
    }

    public Customer() {

    }

    public int getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(int customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public int getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(int customerBalance) {
        this.customerBalance = customerBalance;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerAccountNumber=" + customerAccountNumber +
                ", customerName='" + customerName + '\'' +
                ", customerPassword='" + customerPassword + '\'' +
                ", customerBalance=" + customerBalance +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerMobile='" + customerMobile + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                '}';
    }

}
