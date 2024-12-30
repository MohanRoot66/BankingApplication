package dao;

import databaseconnection.DatabaseConnection;
import entity.Accountant;
import entity.Customer;
import exception.AccountantException;
import exception.CustomerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImplementation implements AccountDao {

    @Override
    public Accountant LoginAccountant(String accountantUsername, String accountantPassword)
            throws AccountantException
    {

        Accountant ac = null;
        try (Connection con = DatabaseConnection.provideConnection()) {

            PreparedStatement ps = con.prepareStatement("select * from accountant where accountantUsername=? and " +
                    "accountantPassword = ?");

            ps.setString(1, accountantUsername);
            ps.setString(2, accountantPassword);

            ResultSet res = ps.executeQuery();

            if(res.next()){
                String n=res.getString("accountantUsername");
                String e=res.getString("accountantEmail");
                String p=res.getString("accountantPassword");

                ac = new Accountant(n,e,p);

            }

        } catch (SQLException e) {
            throw new AccountantException("Invalid username and password");

        }

        return  ac;
    }


    public int addCustomer(String customerName, String customerEmail, String customerPassword, String customerMobile, String customerAddress)
            throws CustomerException
    {
        int cid = -1;

        try(Connection con = DatabaseConnection.provideConnection()){

            PreparedStatement ps = con.prepareStatement("insert into customer(customerName,customerEmail," +
                    "customerPassword,customerMobile,customerAddress) values(?,?,?,?,?)");

            ps.setString(1,customerName);
            ps.setString(2,customerEmail);
            ps.setString(3,customerPassword);
            ps.setString(4,customerMobile);
            ps.setString(5,customerAddress);

            int x = ps.executeUpdate();
            if(x>0){

                PreparedStatement ps2 = con.prepareStatement("select cid from customer where customerEmail = ? and " +
                        "customerPassword = ?");
                ps2.setString(1,customerEmail);
                ps2.setString(2,customerPassword);

                ResultSet rs= ps2.executeQuery();

                if(rs.next()){
                    cid = rs.getInt("cid");
                }
                else{
                    System.out.println("Inserted data is Incorrect");
                }

                System.out.println("Customer Added Successfully");
            }
            else{
                System.out.println("Customer Not Added");
            }

        } catch (Exception e) {
            System.out.println("Sql query Related Error"+e);
            throw new CustomerException(e.getMessage());
        }

        return cid;
    }

    @Override
    public String addAccount(int customerBalance, int cid) throws CustomerException {

        String message = null;

        try(Connection con = DatabaseConnection.provideConnection()){

            PreparedStatement ps = con.prepareStatement("insert into account(customerBalance,cid) values(?,?)");
            ps.setInt(1,customerBalance);
            ps.setInt(2,cid);

            int x= ps.executeUpdate();
            if(x>0){
                System.out.println("Account Added Successfully");
            }
            else{
                System.out.println("Account not inserted");
            }

        } catch (SQLException e) {
            System.out.println("Sql Related Error"+e);
            throw new CustomerException(e.getMessage());
        }
        return  message;
    }

    @Override
    public String updateCustomer(int customerAccountNumber, String customerAddress) throws CustomerException {

        String message = null;
        try(Connection con = DatabaseConnection.provideConnection()){
            PreparedStatement ps = con.prepareStatement("update customer cs inner join account ac on cs.cid=ac.cid " +
                    "and ac.customerAccountNumber=? set cs.customerAddress = ?");
            ps.setInt(1,customerAccountNumber);
            ps.setString(2,customerAddress);

            int x = ps.executeUpdate();
            if(x>0){
                System.out.println("Address updated Successfully");
            }
            else{
                System.out.println("Customer updation not success");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomerException(e.getMessage());
        }
        return message;
    }

    @Override
    public String deleteCustomer(int customerAccountNumber) throws CustomerException {

        String message = null;
        try(Connection con = DatabaseConnection.provideConnection()){

            PreparedStatement ps = con.prepareStatement("DELETE cs FROM customer cs INNER JOIN account ac ON cs.cid = ac.cid " +
                    "WHERE ac.customerAccountNumber = ?");
            ps.setInt(1,customerAccountNumber);

            int x= ps.executeUpdate();

            if(x>0){
                System.out.println("Customer deleted Successfully");
            }
            else{
                System.out.println("Customer not deleted");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomerException(e.getMessage());
        }
        return  message;

    }

    @Override
    public Customer viewCustomer(int customerAccountNumber) throws CustomerException {
        Customer cus =  null;

        try(Connection con = DatabaseConnection.provideConnection()){
            PreparedStatement ps = con.prepareStatement("select * from customer cs inner join account ac " +
                    "on cs.cid=ac.cid where ac.customerAccountNumber= ?");

            ps.setInt(1,customerAccountNumber);

            ResultSet rs = ps.executeQuery();


            if(rs.next()){
                int a = rs.getInt("customerAccountNumber");
                String b = rs.getString("customerName");
                String c = rs.getString("customerPassword");
                int d = rs.getInt("customerBalance");
                String e = rs.getString("customerEmail");
                String f = rs.getString("customerMobile");
                String g = rs.getString("customerAddress");

                cus = new Customer(a,b,c,d,e,f,g);
            }
            else{
                throw new CustomerException("Invalid account number");
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new CustomerException(ex.getMessage());
        }
        return cus;
    }

    @Override
    public List<Customer> viewAllCustomers() throws CustomerException {

        List<Customer> customers = new ArrayList<>();

        try(Connection con = DatabaseConnection.provideConnection()){

            PreparedStatement ps = con.prepareStatement("select * from customer cs inner join account ac " +
                    "on ac.cid=cs.cid");

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int a = rs.getInt("customerAccountNumber");
                String b = rs.getString("customerName");
                String c = rs.getString("customerPassword");
                int d = rs.getInt("customerBalance");
                String e = rs.getString("customerEmail");
                String f = rs.getString("customerMobile");
                String g = rs.getString("customerAddress");

                customers.add(new Customer(a,b,c,d,e,f,g));

            }

        }
        catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }

        return customers;
    }

}
