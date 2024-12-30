package dao;

import databaseconnection.DatabaseConnection;
import entity.Customer;
import exception.CustomerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImplementation implements CustomerDao{
    @Override
    public Customer LoginCustomer(String customerName, String customerPassword, int customerAccountNumber) throws CustomerException {

        Customer customer = null;

        try(Connection con = DatabaseConnection.provideConnection()){
            PreparedStatement ps = con.prepareStatement("select * from customer cs inner join account ac on" +
                    " ac.cid=cs.cid where cs.customerName = ? and cs.customerPassword = ? and ac.customerAccountNumber = ?");

            ps.setString(1,customerName);
            ps.setString(2,customerPassword);
            ps.setInt(3,customerAccountNumber);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int a = rs.getInt("customerAccountNumber");
                String b = rs.getString("customerName");
                String c = rs.getString("customerPassword");
                int d = rs.getInt("customerBalance");
                String e = rs.getString("customerEmail");
                String f = rs.getString("customerMobile");
                String g = rs.getString("customerAddress");

                customer = new Customer(a,b,c,d,e,f,g);
            }
            else{
                throw new CustomerException("Invalid username or password or accountNumber");
            }
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            throw new CustomerException(ex.getMessage());
        }
        return customer;

    }

    @Override
    public int getBalance(int customerAccountNumber) throws CustomerException {

        int b = -1;
        try(Connection con = DatabaseConnection.provideConnection()){
            PreparedStatement ps = con.prepareStatement("select customerBalance from account where customerAccountNumber = ?");
            ps.setInt(1,customerAccountNumber);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                b = rs.getInt("customerBalance");
            }


        } catch (SQLException seq) {
            throw new CustomerException(seq.getMessage());
        }

        return b;
    }

    @Override
    public int Deposit(int customerAccountNumber,int amount) throws CustomerException {

        int b= -1;
        try(Connection con = DatabaseConnection.provideConnection()){

            PreparedStatement ps = con.prepareStatement("update account set customerBalance = customerBalance + ? where" +
                    " customerAccountNumber = ?");

            ps.setInt(1,amount);
            ps.setInt(2,customerAccountNumber);

            ps.executeUpdate();

        } catch (SQLException seq) {
            throw new CustomerException(seq.getMessage());
        }
        return b;
    }

    @Override
    public int WithDrawAmount(int customerAccountNumber, int amount) throws CustomerException {
        int b= -1;
        try(Connection con = DatabaseConnection.provideConnection()){

            PreparedStatement ps = con.prepareStatement("select customerBalance from account where customerAccountNumber = ?");
            ps.setInt(1,customerAccountNumber);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int balance = rs.getInt("customerBalance");
                if(balance-amount<0){
                    throw new CustomerException("Cannot withdraw amount less than the balance");
                }
            }

            PreparedStatement ps1 = con.prepareStatement("update account set customerBalance = customerBalance - ? where" +
                    " customerAccountNumber = ?");

            ps1.setInt(1,amount);
            ps1.setInt(2,customerAccountNumber);

            ps1.executeUpdate();

        } catch (SQLException seq) {
            throw new CustomerException(seq.getMessage());
        }
        return b;
    }

    public int TransferAmount(int customerAccountNumber, int amount, int customerAccountNumber2) throws CustomerException {
        try (Connection con = DatabaseConnection.provideConnection()) {
            // Start a transaction
            con.setAutoCommit(false);

            // Check balance of the sender
            PreparedStatement checkBalanceStmt = con.prepareStatement(
                    "select customerBalance from account where customerAccountNumber = ?");
            checkBalanceStmt.setInt(1, customerAccountNumber);
            ResultSet rs = checkBalanceStmt.executeQuery();

            if (!rs.next()) {
                throw new CustomerException("Sender account not found.");
            }

            int senderBalance = rs.getInt("customerBalance");
            if (senderBalance < amount) {
                throw new CustomerException("Insufficient balance in sender's account.");
            }

            // Deduct amount from the sender's account
            PreparedStatement deductStmt = con.prepareStatement(
                    "update account set customerBalance = customerBalance - ? where customerAccountNumber = ?");
            deductStmt.setInt(1, amount);
            deductStmt.setInt(2, customerAccountNumber);
            deductStmt.executeUpdate();

            // Check if the receiver's account exists
            PreparedStatement checkReceiverStmt = con.prepareStatement(
                    "select customerBalance from account where customerAccountNumber = ?");
            checkReceiverStmt.setInt(1, customerAccountNumber2);
            ResultSet rsReceiver = checkReceiverStmt.executeQuery();

            if (!rsReceiver.next()) {
                throw new CustomerException("Receiver account not found.");
            }

            // Add amount to the receiver's account
            PreparedStatement addStmt = con.prepareStatement(
                    "update account set customerBalance = customerBalance + ? where customerAccountNumber = ?");
            addStmt.setInt(1, amount);
            addStmt.setInt(2, customerAccountNumber2);
            addStmt.executeUpdate();

            // Commit the transaction
            con.commit();
            return 1; // Indicating success

        } catch (SQLException e) {
            throw new CustomerException("Transaction failed: " + e.getMessage());
        }
    }

}
