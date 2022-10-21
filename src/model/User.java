package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.Connect;

public class User {
	private String userId, user_name;
	private double rupiah_balance;
	
	private Connect con = Connect.getConnection();
	
	public User() {}

	public User(String userId, String user_name, double rupiah_balance) {
		super();
		this.userId = userId;
		this.user_name = user_name;
		this.rupiah_balance = rupiah_balance;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public double getRupiah_balance() {
		return rupiah_balance;
	}

	public void setRupiah_balance(double rupiah_balance) {
		this.rupiah_balance = rupiah_balance;
	}

	public User get(String id) {
		String query = String.format("SELECT * FROM User WHERE userId=?");
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			User User = null;
			if(rs.next()) {
				User = map(rs);
			}	
			return User;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private User map(ResultSet rs) {
		String userId, user_name;
		double rupiah_balance;
		
		try {
			userId = rs.getString("userId");
			user_name = rs.getString("user_name");
			rupiah_balance = rs.getDouble("rupiah_balance");

			return new User(userId, user_name, rupiah_balance);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<User> getAll(){
		String query = "SELECT * FROM User";
		ResultSet rs = con.executeQuery(query);
		Vector<User> user = new Vector<>();
		try {
			while(rs.next()) {
				User User = map(rs);
				user.add(User);
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void updateData() {
		String query = "update User set rupiah_balance = ? where userId = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setDouble(1, rupiah_balance);
			ps.setString(2, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
