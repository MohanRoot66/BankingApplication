package dao;

import entity.Accountant;
import entity.Customer;
import exception.AccountantException;
import exception.CustomerException;
import java.util.*;

public interface AccountDao {

    public Accountant LoginAccountant(String accountantUsername,String accountantPassword) throws AccountantException;

    public int addCustomer(String customerName,String customerEmail,String customerPassword,
                            String customerMobile,String customerAddress) throws CustomerException;

    public String addAccount(int customerBalance,int cid) throws CustomerException;

    public String updateCustomer(int customerAccountNumber,String customerAddress) throws  CustomerException;

    public String deleteCustomer(int customerAccountNumber) throws CustomerException;

    public Customer viewCustomer(int customerAccountNumber) throws  CustomerException;

    public List<Customer> viewAllCustomers() throws CustomerException;
}
