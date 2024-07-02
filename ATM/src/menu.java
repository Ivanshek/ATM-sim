import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class menu extends account {

	
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
		
	HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
	
	public void createConnection() throws ClassNotFoundException, SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			System.out.println("Connecting ... ");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "12345678");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM atm_user");
			while (rs.next()) {
				
				int CustomerNumber = rs.getInt("account");
				
				if(getCustomerNumber() == CustomerNumber) {
					int Pin = rs.getInt("pin");
					data.put(CustomerNumber, Pin);
				}
			}
			System.out.println("Connection Created" + "\n");
			
			rs.close();
			stmt.close();
			con.close();
			
		}catch (ClassNotFoundException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}catch (SQLException e) {
			Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, e);
		}
		        
	}
	
	public void getLogin() throws IOException, ClassNotFoundException, SQLException{
		int x = 1;
		
		do {
			try {				
				
				
				System.out.println("Welcome to the ATM");
				System.out.println("Enter your number");
				setCustomerNumber(menuInput.nextInt());
				
				System.out.println("Enter your PIN");
				setPin(menuInput.nextInt());
				
			} catch (Exception e) {
				System.out.println("\n" + "Invalid" + "\n");
				x = 2;
			}
			
			int cn = getCustomerNumber();
			int pn = getPin();
			
			createConnection();
			
			if(data.containsKey(cn) && data.get(cn) == pn) {
				getAccountType();
				
			}
			else {
				System.out.println("\n" + "Wrong number or  PIN" + "\n");
			}
		} while (x == 1);
	}
		
	public void getAccountType() throws ClassNotFoundException, SQLException {
		System.out.println("Select the Account: ");
		System.out.println("Type 1 - checking account");
		System.out.println("Type 2 - saving account");
		System.out.println("Type 3 - Exit");
		
		int select = menuInput.nextInt();
		
		switch (select) {
		case 1: 
			getchecking();
			break;
		
		case 2:
			getsaving();
			break;
			
		case 3:
			System.out.println("Thank you for using ATM");
			break;
			
		default:
			System.out.println("Invalid");
			getAccountType();
		}
		
	}
	public void getchecking() throws ClassNotFoundException, SQLException {
		System.out.println("Checking Account");
		System.out.println("Type 1 - View Balance");
		System.out.println("Type 2 - Withdraw Funds");
		System.out.println("Type 3 - Deposit Funds");
		System.out.println("Type 4 - Back to Select Accout Type");
		System.out.println("Type 5 - Exit");
		System.out.println("Choice: ");
		
		int select = menuInput.nextInt();
		
		switch (select) {
		case 1: 
			System.out.println("Account Balance: " + moneyFormat.format(getcheckingbalance()));
			getchecking();
			break;
		
		case 2:
			getcheckingwithdrawinput();
			getchecking();
			break;
			
		case 3:
			getcheckingdepositinput();
			getchecking();			
			break;
		
		case 4:
			getAccountType();
			
		case 5:
			System.out.println("Thank you for using ATM");
			break;
			
		default:
			System.out.println("Invalid");
			getchecking();
		}
	}
	
	public void getsaving() throws ClassNotFoundException, SQLException {
		System.out.println("Saving Account");
		System.out.println("Type 1 - View Balance");
		System.out.println("Type 2 - Withdraw Funds");
		System.out.println("Type 3 - Deposit Funds");
		System.out.println("Type 4 - Back to Select Accout Type");
		System.out.println("Type 5 - Exit");
		System.out.println("Choice: ");
		
		int select = menuInput.nextInt();
		
		switch (select) {
		case 1: 
			System.out.println("Account Balance: " + moneyFormat.format(getsavingbalance()));
			getsaving();
			break;
		
		case 2:
			getsavingwithdrawinput();
			getsaving();
			break;
			
		case 3:
			getsavingdepositinput();
			getsaving();			
			break;
		
		case 4:
			getAccountType();
			
		case 5:
			System.out.println("Thank you for using ATM");
			break;
			
		default:
			System.out.println("Invalid");
			getsaving();
		}
	}

}
