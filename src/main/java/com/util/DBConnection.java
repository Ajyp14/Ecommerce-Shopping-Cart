
package com.util;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {

//    private static final String URL = "jdbc:mysql://trolley.proxy.rlwy.net:53635/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
//    private static final String USER = "root";
//    private static final String PASS = "dvMMDdeTYbZoveQiEwBnbiSjdtRRSXSV";

//Mysql
/*	private static final String HOST = System.getenv("MYSQLHOST");
	private static final String PORT = System.getenv("MYSQLPORT");
	private static final String DATABASE = System.getenv("MYSQLDATABASE");
	private static final String USER = System.getenv("MYSQLUSER");
	private static final String PASS = System.getenv("MYSQLPASSWORD");

	
	
	private static final String URL =
	        "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE +
	        "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
*/
	
		private static final String DATABASE_URL = System.getenv("DATABASE_URL");


	    private static final String HOST = System.getenv("DB_HOST");
	    private static final String PORT = System.getenv("DB_PORT");
	    private static final String DATABASE = System.getenv("DB_NAME");
	    private static final String USER = System.getenv("DB_USER");
	    private static final String PASS = System.getenv("DB_PASS");

	    private static final String URL="jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE + "?sslmode=require";

	    public static Connection getConnection() {
	        try {
	        	System.out.println("Hey ajay");
	            Class.forName("org.postgresql.Driver");

	            // Try DATABASE_URL first (Render's preferred method)
	            if (DATABASE_URL != null && !DATABASE_URL.isEmpty()) {
	                System.out.println("✅ Using DATABASE_URL");
	                
	                URI uri = new URI(DATABASE_URL);

	                String username = uri.getUserInfo().split(":")[0];
	                String password = uri.getUserInfo().split(":")[1];

	                String jdbcUrl = "jdbc:postgresql://" 
	                        + uri.getHost() + ":" 
	                        + uri.getPort() 
	                        + uri.getPath()
	                        + "?sslmode=require";

	                return DriverManager.getConnection(jdbcUrl, username, password);
	                
	            }
	            
	            System.out.println("HOST: " + HOST);
	            System.out.println("PORT: " + PORT);
	            System.out.println("DB: " + DATABASE);
	            System.out.println("USER: " + USER);

	            return DriverManager.getConnection(URL, USER, PASS);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	

	
    // Get MySQL connection
/*    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            System.out.println("posrt1;;"+PORT);
            System.out.println("HOST: " + HOST);
            System.out.println("PORT: " + PORT);
            System.out.println("DB: " + DATABASE);
            System.out.println("USER: " + USER);
            
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 */   
    
	
   // ------------------------------
    // CARD PAYMENT METHODS
    // ------------------------------

    // Validate card details
    public boolean validateCard(String cardNumber, String holderName,
                                String expMonth, String expYear, String cvv) {

        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "SELECT * FROM card_details WHERE card_number=? AND holder_name=? AND exp_month=? AND exp_year=? AND cvv=?"
            );

            pst.setString(1, cardNumber);
            pst.setString(2, holderName);
            pst.setString(3, expMonth);
            pst.setString(4, expYear);
            pst.setString(5, cvv);

            ResultSet rs = pst.executeQuery();
            return rs.next(); // TRUE if card exists

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Get card balance
    public int getCardBalance(String cardNumber) {
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "SELECT balance FROM card_details WHERE card_number=?"
            );
            pst.setString(1, cardNumber);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // card not found
    }


    // Update card balance
    public void updateCardBalance(String cardNumber, int newBalance) {
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "UPDATE card_details SET balance=? WHERE card_number=?"
            );

            pst.setInt(1, newBalance);
            pst.setString(2, cardNumber);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ------------------------------
    // 🟢 UPI PAYMENT METHODS
    // ------------------------------

    // Check UPI balance
    public int checkUPIBalance(String upiId) {
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "SELECT balance FROM bank_upi WHERE upi_id=?"
            );

            pst.setString(1, upiId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // not found
    }


    // Deduct UPI balance
    public void updateUPIBalance(String upiId, int newBalance) {
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "UPDATE bank_upi SET balance=? WHERE upi_id=?"
            );

            pst.setInt(1, newBalance);
            pst.setString(2, upiId);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
