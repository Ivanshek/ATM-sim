import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.exceptions.RSAException;

public class account {
	
	private int CustomerNumber;
	private int Pin;
	private double checkingbalance = 0;
	private double savingbalance = 0;
	
	Scanner input = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00 ");
	
	public int getCustomerNumber() {
		return this.CustomerNumber;
	}
	
	public void setCustomerNumber(int x) {
		this.CustomerNumber = x;
	}
	
	public int getPin() {
		return this.Pin;
	}
	
	public void setPin(int x) {
		this.Pin = x;
	}
	
	public void updatecheckingbalance(double amount) throws ClassNotFoundException, SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			System.out.println(" Connecting ... ");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "12345678");
			Statement stmt = con.createStatement();
			String amountString = String.format("%f", amount);
			double rs = stmt.executeUpdate("UPDATE atm_user SET checkingbalance = " + amountString + " WHERE account = " + getCustomerNumber() );		
			System.out.println("Chceking Balance Updated");
			
			stmt.close();
			con.close();
		}catch (ClassNotFoundException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch (SQLException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch(Exception e) {
			
		}
		        
	}
	
	public void updatesavingbalance(double amount) throws ClassNotFoundException, SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			System.out.println(" Connecting ... ");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "12345678");
			Statement stmt = con.createStatement();
			String amountString = String.format("%f", amount);
			double rs = stmt.executeUpdate("UPDATE atm_user SET savingbalance = " + amountString + " WHERE account = " + getCustomerNumber() );			
			System.out.println("Saving Balance Updated");
			
			
			stmt.close();
			con.close();
			
		}catch (ClassNotFoundException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch (SQLException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch(Exception e) {
			
		}
		        
	}
	
	public double getcheckingbalance() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			System.out.println(" Connecting ... ");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "12345678");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT checkingbalance FROM atm_user WHERE account = " +  getCustomerNumber());
			while (rs.next()) {
				checkingbalance = rs.getDouble(1);
			}
			System.out.println("Get Checking Balance");
			
			rs.close();
			stmt.close();
			con.close();
		}catch (ClassNotFoundException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch (SQLException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch(Exception e) {
			
		}
		return this.checkingbalance;
	}
	
	public double getsavingbalance() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			System.out.println(" Connecting ... ");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "12345678");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT savingbalance FROM atm_user WHERE account = " +  getCustomerNumber());
			while (rs.next()) {
				savingbalance = rs.getDouble(1);
			}
			System.out.println("Get Saving Balance");
			
			rs.close();
			stmt.close();
			con.close();
		}catch (ClassNotFoundException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch (SQLException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch(Exception e) {
			
		}	
		return this.savingbalance;
	}
	
	public void calcCheckingWihdraw(double amount) throws ClassNotFoundException, SQLException {
		checkingbalance = (checkingbalance - amount);
		updatecheckingbalance(checkingbalance);
	}
	
	public void calcSavingWihdraw(double amount) throws ClassNotFoundException, SQLException {
		savingbalance = (savingbalance - amount);
		updatesavingbalance(checkingbalance);
		
	}
	
	public void calcCheckingDeposit(double amount) throws ClassNotFoundException, SQLException {
		checkingbalance = (checkingbalance + amount);
		updatecheckingbalance(checkingbalance);
	}
	
	public void calcSavingDeposit(double amount) throws ClassNotFoundException, SQLException {
		savingbalance = (savingbalance + amount);
		updatesavingbalance(checkingbalance);
	}
	
	public void getcheckingwithdrawinput() throws ClassNotFoundException, SQLException {
		System.out.println("Checking account balance: " + moneyFormat.format(checkingbalance) );
		System.out.println("Amount you want to withdraw from Checking Account");
		double amount = input.nextDouble();
		
		if (checkingbalance - amount >= 0) {
			calcCheckingWihdraw(amount);
			System.out.println("New Checking account balance: " + moneyFormat.format(checkingbalance));
			
		}else {
			System.out.println("Balance cannot be negative");
		}
	}
	
	public void getsavingwithdrawinput() throws ClassNotFoundException, SQLException {
		System.out.println("Saving account balance: " + moneyFormat.format(savingbalance) );
		System.out.println("Amount you want to withdraw from Saving Account");
		double amount = input.nextDouble();
		
		if (savingbalance - amount >= 0) {
			calcSavingWihdraw(amount);
			System.out.println("New Saving account balance: " + moneyFormat.format(savingbalance));
			
		}else {
			System.out.println("Balance cannot be negative");
		}
	}
	
	public void getcheckingdepositinput() throws ClassNotFoundException, SQLException {
		System.out.println("Checking account balance: " + moneyFormat.format(checkingbalance) );
		System.out.println("Amount you want to withdraw from Checking Account");
		double amount = input.nextDouble();
		
		if (checkingbalance + amount >= 0) {
			calcCheckingDeposit(amount);
			System.out.println("New Checking account balance: " + moneyFormat.format(checkingbalance));
			
		}else {
			System.out.println("Balance cannot be negative");
		}
	}
	
	public void getsavingdepositinput() throws ClassNotFoundException, SQLException {
		System.out.println("Saving account balance: " + moneyFormat.format(savingbalance) );
		System.out.println("Amount you want to withdraw from Saving Account");
		double amount = input.nextDouble();
		
		if (savingbalance + amount >= 0) {
			calcSavingDeposit(amount);
			System.out.println("New Saving account balance: " + moneyFormat.format(savingbalance));
			
		}else {
			System.out.println("Balance cannot be negative");
		}
	}
	
}
