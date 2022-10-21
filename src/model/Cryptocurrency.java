package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.Connect;

public class Cryptocurrency {
	private String  coinId, coin_name;
	private double rp_exchange_rate;
	
	private Connect con = Connect.getConnection();
	
	public Cryptocurrency() {}

	public Cryptocurrency(String coinId, String coin_name, double rp_exchange_rate) {
		super();
		this.coinId = coinId;
		this.coin_name = coin_name;
		this.rp_exchange_rate = rp_exchange_rate;
	}

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public String getCoin_name() {
		return coin_name;
	}

	public void setCoin_name(String coin_name) {
		this.coin_name = coin_name;
	}

	public double getRp_exchange_rate() {
		return rp_exchange_rate;
	}

	public void setRp_exchange_rate(double rp_exchange_rate) {
		this.rp_exchange_rate = rp_exchange_rate;
	}

	public Cryptocurrency get(String id) {
		String query = String.format("SELECT * FROM Cryptocurrency WHERE coinId=?");
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			Cryptocurrency Cryptocurrency = null;
			if(rs.next()) {
				Cryptocurrency = map(rs);
			}	
			return Cryptocurrency;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Cryptocurrency map(ResultSet rs) {
		String  coinId, coin_name;
		double rp_exchange_rate;
		
		try {
			coinId = rs.getString("coinId");
			coin_name = rs.getString("coin_name");
			rp_exchange_rate = rs.getDouble("rp_exchange_rate");

			return new Cryptocurrency(coinId, coin_name, rp_exchange_rate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Cryptocurrency> getAll(){
		String query = "SELECT * FROM Cryptocurrency";
		ResultSet rs = con.executeQuery(query);
		Vector<Cryptocurrency> crypto = new Vector<>();
		try {
			while(rs.next()) {
				Cryptocurrency Cryptocurrency = map(rs);
				crypto.add(Cryptocurrency);
			}
			return crypto;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
