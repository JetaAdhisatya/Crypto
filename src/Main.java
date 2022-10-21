import java.util.Scanner;
import java.util.Vector;

import model.Assets;
import model.Cryptocurrency;
import model.Transaction;
import model.User;

public class Main {
	Scanner scan = new Scanner(System.in);

	public Main() {
		int menu = -1;
		System.out.println("welcome");
		
		do {
			System.out.println("1. show all user transcation");
			System.out.println("2. show sum of transaction");
			System.out.println("3. process the transaction");
			System.out.println("4. show wealth");
			System.out.println("5. exit");
			try {
				System.out.print(">> ");
				menu = scan.nextInt();
			} catch (Exception e) {
				// TODO: handle exception
			}
			scan.nextLine();
			
			switch (menu) {
			case 1:
				userTransaction();
				break;
			case 2:
				sumOfTransaction();
				break;
			case 3:
				processTheTransaction();
				break;
			case 4:
				showWealth();
				break;

			default:
				break;
			}
			
			
		}while(menu != 5);
	
	}

	private void showWealth() {
		Vector<User> user = new User().getAll();
		Vector<Assets> ass = new Assets().getAll();
		
		System.out.println("Name | Assets in Rp");
		for(User temp: user) {
			double sum = 0;
			for(Assets x: ass) {
				Cryptocurrency coin = new Cryptocurrency().get(x.getCoinId());
				if(x.getUserId().equals(temp.getUserId())) {
					sum += x.getSum_of_assets() * coin.getRp_exchange_rate();
				}
			}
			System.out.println(temp.getUser_name()+" | "+sum);
		}
		
	}

	private void processTheTransaction() {
		Vector<Transaction> trans = new Transaction().getAll();
		Vector<Assets> ass = new Assets().getAll();
		
		for(Transaction temp: trans) {
			User user = new User().get(temp.getUserID());
			Cryptocurrency coin = new Cryptocurrency().get(temp.getCoinId());
			
			if(temp.getTransaction().equals("buy")) {
				float num_coin = 0;
				if(user.getRupiah_balance() > coin.getRp_exchange_rate() * temp.getNum_of_coins()) {
					num_coin = temp.getNum_of_coins();
					System.out.println("buy transaction is succesfull");
				}else {
					System.out.println("buy transaction unsuccesfull");
					continue;
				}
				int flag = 0;
				for(Assets x: ass) {
					if(x.getCoinId().equals(temp.getCoinId()) && x.getUserId().equals(temp.getUserID())) {
						flag = 1;
						break;
					}
				}
				user.setRupiah_balance(user.getRupiah_balance() - coin.getRp_exchange_rate() * temp.getNum_of_coins());
				user.updateData();
				if(flag == 1) {
					Assets up = new Assets().get(temp.getUserID(), temp.getCoinId());
					new Assets(0, temp.getUserID(), temp.getCoinId(), up.getSum_of_assets()+num_coin).updateData();
				}else {
					new Assets(0, temp.getUserID(), temp.getCoinId(), num_coin).insert();
				}
			}else {
				Assets yo = new Assets().get(temp.getUserID(), temp.getCoinId());
				if(yo == null || yo.getSum_of_assets() < temp.getNum_of_coins()) {
					System.out.println("sell transaction unsuccessful");
				}else {
					user.setRupiah_balance(user.getRupiah_balance() + coin.getRp_exchange_rate() * temp.getNum_of_coins());
					user.updateData();
					yo.setSum_of_assets(yo.getSum_of_assets()-temp.getNum_of_coins());
					yo.updateData();
					System.out.println("sell transaction succesful");
				}
			}
			
		}

		
		
	}

	private void sumOfTransaction() {
		Vector<Cryptocurrency> coin = new Cryptocurrency().getAll();
		System.out.println("Name | Rp Exchange Rate | Asset Sold | Asset Bought | Rp Sold | Rp Bought");
		Vector<Transaction> trans = new Transaction().getAll();
		
		
		for(Cryptocurrency temp: coin) {
			float buy = 0, sell = 0;
			for(int i=0; i<trans.size(); i++) {
				User user = new User().get(trans.get(i).getUserID());
				if(temp.getCoinId().equals(trans.get(i).getCoinId())) {
					if(trans.get(i).getTransaction().equals("buy")) {
						if(user.getRupiah_balance() > trans.get(i).getNum_of_coins() * temp.getRp_exchange_rate()) {
							buy += trans.get(i).getNum_of_coins();
						}
					}else {
						float userBuy = 0;
						for(int j=0; j<i; j++) {
							if(trans.get(j).getTransaction().equals("buy") && user.getRupiah_balance() > trans.get(j).getNum_of_coins() * temp.getRp_exchange_rate()) {
								userBuy += trans.get(j).getNum_of_coins();
							}
						}
						if(userBuy > trans.get(i).getNum_of_coins()) {
							sell += trans.get(i).getNum_of_coins();
						}
						
					}
				}
				
				
			}
			
			System.out.println(temp.getCoinId()+" | "+temp.getRp_exchange_rate()+" | "+sell+" | "+buy+" | "+sell *temp.getRp_exchange_rate()+" | "+buy* temp.getRp_exchange_rate());
			
			
		}
		
	}

	private void userTransaction() {
		Vector<Transaction> trans = new Transaction().getAll();
		System.out.println("User name | Transaction | Coin Name | Number of Coins");
		for(Transaction temp: trans) {
			User user = new User().get(temp.getUserID());
			Cryptocurrency coin = new Cryptocurrency().get(temp.getCoinId());
			System.out.println(user.getUser_name()+" | "+temp.getTransaction()+" | "+coin.getCoin_name()+" | "+temp.getNum_of_coins());
		}
		
	}

	public static void main(String[] args) {
		new Main();

	}

}
