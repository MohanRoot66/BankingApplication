package org.example;

import dao.AccountDao;
import dao.AccountDaoImplementation;
import dao.CustomerDao;
import dao.CustomerDaoImplementation;
import databaseconnection.DatabaseConnection;
import entity.Accountant;
import entity.Customer;
import exception.CustomerException;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        boolean f = true;

        if (f){
            System.out.println("---------------------Welcome to Online Banking System--------------------");
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("1. ADMIN LOGIN PORTAL \r\n2. Customer");

            System.out.println("Choose your Option ");

            int r = sc.nextInt();

            switch (r){

                case 1:
                    System.out.println("Admin Login Credentials -------------------------------Accountant");
                    System.out.println("Enter Username");
                    String username = sc.next();
                    System.out.println("Enter Password");
                    String password = sc.next();

                    AccountDao ad = new AccountDaoImplementation();

                    try{
                        Accountant act = ad.LoginAccountant(username,password);
                        if(act==null){
                            System.out.println("wrong Credentials");
                            break;
                        }
                        System.out.println("Login Successfully");
                        System.out.println("Welcome to :"+ act.getAccountantUsername()+" Admin of Online Banking System");

                        boolean y= true;
                        while (y){
                            System.out.println("-------------------------\r\n" +
                                    "1. Add New Customer \r\n" +
                                    "2. Update Customer  \r\n" +
                                    "3. Delete Customer  \r\n" +
                                    "4. View Particular Account Details \r\n" +
                                    "5. View All Customer Account Details");

                            int x= sc.nextInt();
                            if(x==1){
                                System.out.println("----------------New Customer-----------------------");
                                System.out.println("Enter Customer Name : ");
                                String a1 = sc.next();
                                System.out.println("Enter Account Opening Balance : ");
                                int bal = sc.nextInt();
                                System.out.println("Enter Customer Email : ");
                                String a2 = sc.next();
                                System.out.println("Enter Customer Password : ");
                                String a3 = sc.next();
                                System.out.println("Enter Customer MobileNumber : ");
                                String a4 = sc.next();
                                System.out.println("Enter Customer Address : ");
                                String a5 = sc.next();

                                int s1=-1;

                                try{
                                    s1 = ad.addCustomer(a1,a2,a3,a4,a5);

                                    try{
                                        ad.addAccount(bal,s1);
                                    }
                                    catch (Exception e){
                                        System.out.println("not added");
                                    }

                                } catch (CustomerException e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            else if(x==2){
                                System.out.println("Update Customer Address-----------------");
                                System.out.println("Enter Customer Account Number");
                                int u = sc.nextInt();
                                System.out.println("Enter New Address");
                                String u2 = sc.next();

                                try{
                                    String res = ad.updateCustomer(u,u2);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            else if(x==3){
                                System.out.println("Delete Customer-----------------");
                                System.out.println("Enter Customer Account Number");
                                int u = sc.nextInt();

                                try{
                                    String res = ad.deleteCustomer(u);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            else if(x==4){
                                System.out.println("View Customer Details-----------------");
                                System.out.println("Enter Customer Account Number");
                                int u = sc.nextInt();

                                try{
                                    Customer cus = ad.viewCustomer(u);
                                    if(cus!=null){
                                        System.out.println("------------------------------------------------");
                                        System.out.println("Account Number :"+cus.getCustomerAccountNumber());
                                        System.out.println("Name :"+cus.getCustomerName());
                                        System.out.println("Balance :"+cus.getCustomerBalance());
                                        System.out.println("Email :"+cus.getCustomerEmail());
                                        System.out.println("Mobile :"+cus.getCustomerMobile());
                                        System.out.println("Address :"+cus.getCustomerAddress());
                                    }
                                    else{
                                        System.out.println("Account does not Exists");
                                    };
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            else if(x==5){
                                System.out.println("View ALL Customer Details-----------------");
                                List<Customer> customers = ad.viewAllCustomers();
                                try{
                                    customers.forEach((cus)->{
                                        System.out.println("------------------------------------------------");
                                        System.out.println("Account Number :"+cus.getCustomerAccountNumber());
                                        System.out.println("Name :"+cus.getCustomerName());
                                        System.out.println("Balance :"+cus.getCustomerBalance());
                                        System.out.println("Email :"+cus.getCustomerEmail());
                                        System.out.println("Mobile :"+cus.getCustomerMobile());
                                        System.out.println("Address :"+cus.getCustomerAddress());
                                    });
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }

                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    break;

                case 2:
                    System.out.println("Customer Login Credentials -------------------------------");
                    System.out.println("Enter Username");
                    String c_username = sc.next();
                    System.out.println("Enter Password");
                    String c_password = sc.next();
                    System.out.println("Enter Account Number");
                    int c_acno= sc.nextInt();

                    CustomerDao cusd = new CustomerDaoImplementation();

                    try{
                        Customer cus = cusd.LoginCustomer(c_username,c_password,c_acno);
                        System.out.println("Welcome : "+ cus.getCustomerName());

                        boolean m = true;

                        while (true){
                            System.out.println("--------------------------\r\n" +
                                    "1. View Balance \r\n" +
                                    "2. Deposite Money \r\n" +
                                    "3. Withdraw Money \r\n" +
                                    "4. Transfer Money");

                            int x = sc.nextInt();
                            if(x==1){
                                System.out.println("--------------------Balance-------------------------");
                                System.out.println("Current Account Balance ----------------------------");
                                System.out.println(cusd.getBalance(c_acno));
                                System.out.println("----------------------------------------------------");
                            }
                            else if (x==2) {
                                System.out.println("---------------------Deposit------------------------");
                                System.out.println("Enter amount to be deposited .......................");
                                int am = sc.nextInt();
                                cusd.Deposit(c_acno,am);
                                System.out.println("Your Balance After Deposit ");
                                System.out.println(cusd.getBalance(c_acno));
                                System.out.println("-----------------------------------------------------");
                            }
                            else if (x==3) {
                                System.out.println("---------------------WithDraw------------------------");
                                System.out.println("Enter amount to be Withdrawed .......................");
                                int am = sc.nextInt();
                                cusd.WithDrawAmount(c_acno,am);
                                System.out.println("Your Balance After WithDraw ");
                                System.out.println(cusd.getBalance(c_acno));
                                System.out.println("-----------------------------------------------------");
                            }
                            else if(x==4){
                                System.out.println("---------------------Transfer Amount------------------------");
                                System.out.println("Enter From Account Number");
                                int fr_acno= sc.nextInt();
                                System.out.println("Enter amount to be Transfer .......................");
                                int am = sc.nextInt();
                                System.out.println("Enter To Account Number");
                                int to_acno= sc.nextInt();
                                cusd.TransferAmount(fr_acno,am,to_acno);
                                System.out.println("Amount Trasfered");
                            }
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        throw new RuntimeException(e);
                    }

                default:

            }

        }

    }
}