package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.Connect;

public class Assets {
	private int assetId;
	private String userId, coinId;
	private float sum_of_assets;
	
	private Connect con = Connect.getConnection();
	
	public Assets() {}

	public Assets(int assetId, String userId, String coinId, float sum_of_assets) {
		super();
		this.assetId = assetId;
		this.userId = userId;
		this.coinId = coinId;
		this.sum_of_assets = sum_of_assets;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public float getSum_of_assets() {
		return sum_of_assets;
	}

	public void setSum_of_assets(float sum_of_assets) {
		this.sum_of_assets = sum_of_assets;
	}

	public boolean insert() {
		String query = String.format("INSERT INTO Assets VALUES ('0', ?, ?, ?)");
		PreparedStatement ps = con.prepareStatement(query);
			
		try {
			ps.setString(1, userId);
			ps.setString(2, coinId);
			ps.setFloat(3, sum_of_assets);
			
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Assets get(String userId, String coinId) {
		String query = String.format("SELECT * FROM Assets WHERE userId=? and coinId=?");
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userId);
			ps.setString(2, coinId);
			ResultSet rs = ps.executeQuery();
			Assets Assets = null;
			if(rs.next()) {
				Assets = map(rs);
			}	
			return Assets;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean delete() {
		String query = String.format("DELETE FROM Assets WHERE assetId = ?");
		PreparedStatement ps = con.prepareStatement(query);
			
		try {
			ps.setInt(1, assetId);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private Assets map(ResultSet rs) {
		int assetId;
		String userId, coinId;
		float sum_of_assets;
		
		try {
			assetId = rs.getInt("assetId");
			userId = rs.getString("userId");
			coinId = rs.getString("coinId");
			sum_of_assets = rs.getFloat("sum_of_assets");

			return new Assets(assetId, userId, coinId, sum_of_assets);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Assets> getAll(){
		String query = "SELECT * FROM Assets";
		ResultSet rs = con.executeQuery(query);
		Vector<Assets> transactions = new Vector<>();
		try {
			while(rs.next()) {
				Assets Assets = map(rs);
				transactions.add(Assets);
			}
			return transactions;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void updateData() {
		String query = "update Assets set sum_of_assets = ? where userId = ? and coinId = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setFloat(1, sum_of_assets);
			ps.setString(2, userId);
			ps.setString(3, coinId);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
