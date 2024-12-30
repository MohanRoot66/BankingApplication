package dao;

import entity.Customer;
import exception.CustomerException;

public interface CustomerDao {

    public Customer LoginCustomer(String customerName, String customerPassword , int customerAccountNumber) throws CustomerException;

    public int getBalance(int customerAccountNumber) throws CustomerException;

    public int Deposit(int customerAccountNumber, int amount) throws CustomerException;

    public int WithDrawAmount(int customerAccountNumber, int amount) throws CustomerException;

    public int TransferAmount(int customerAccountNumber , int amount , int customerAccountNumber2) throws  CustomerException;
}
