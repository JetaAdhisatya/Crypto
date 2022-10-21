package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.Connect;

public class Transaction {
	private int transactionId;
	private String  userId, coinId, transaction;
	private float num_of_coins;
	
	private Connect con = Connect.getConnection();
	
	
	public Transaction() {}

	public Transaction(int transactionId, String userId, String coinId, String transaction, float num_of_coins) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.coinId = coinId;
		this.transaction = transaction;
		this.num_of_coins = num_of_coins;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserID() {
		return userId;
	}

	public void setUserID(String userId) {
		this.userId = userId;
	}

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public float getNum_of_coins() {
		return num_of_coins;
	}

	public void setNum_of_coins(float num_of_coins) {
		this.num_of_coins = num_of_coins;
	}

	public boolean insert() {
		String query = String.format("INSERT INTO Transaction VALUES ('0', ?, ?, ?, ?)");
		PreparedStatement ps = con.prepareStatement(query);
			
		try {
			ps.setString(1, userId);
			ps.setString(2, coinId);
			ps.setString(3, transaction);
			ps.setFloat(4, num_of_coins);
			
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Transaction get(String id) {
		String query = String.format("SELECT * FROM Transaction WHERE transactionId=?");
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			Transaction transaction = null;
			if(rs.next()) {
				transaction = map(rs);
			}	
			return transaction;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean delete() {
		String query = String.format("DELETE FROM Transaction WHERE transactionId = ?");
		PreparedStatement ps = con.prepareStatement(query);
			
		try {
			ps.setInt(1, transactionId);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private Transaction map(ResultSet rs) {
		int transactionId;
		String  userId, coinId, transaction;
		float num_of_coins;
		
		try {
			transactionId = rs.getInt("transactionId");
			userId = rs.getString("userId");
			coinId = rs.getString("coinId");
			transaction = rs.getString("transaction");
			num_of_coins = rs.getFloat("num_of_coins");

			return new Transaction(transactionId, userId, coinId, transaction, num_of_coins);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Transaction> getAll(){
		String query = "SELECT * FROM Transaction";
		ResultSet rs = con.executeQuery(query);
		Vector<Transaction> transactions = new Vector<>();
		try {
			while(rs.next()) {
				Transaction transaction = map(rs);
				transactions.add(transaction);
			}
			return transactions;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
