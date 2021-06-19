package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenti;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public void listFoodPorzioni(int porzioni, Map<Integer,Food> mappa) {
		String sql = "SELECT DISTINCT f.food_code, display_name "
				+ "from food AS f, `portion` AS p "
				+ "WHERE f.food_code = p.food_code "
				+ "GROUP BY f.food_code "
				+ "HAVING COUNT( distinct p.portion_id)> ?";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, porzioni);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f = new Food(res.getInt("f.food_code"),
							res.getString("display_name"));
					if(!mappa.containsKey(f.getFood_code())) {
						mappa.put(f.getFood_code(), f);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Adiacenti> getArchi(int porzioni, Map<Integer,Food> mappa){
		String sql = "SELECT p1.food_code, p2.food_code, (p1.grassi-p2.grassi) AS peso "
				+ "FROM (SELECT DISTINCT food_code,AVG(saturated_fats) AS grassi "
				+ "	from `portion` "
				+ "	GROUP BY food_code "
				+ "	HAVING COUNT( distinct portion_id)> ?) AS p1, "
				+ "	(SELECT DISTINCT food_code, AVG(saturated_fats) AS grassi "
				+ "	from `portion` "
				+ "	GROUP BY food_code "
				+ "	HAVING COUNT( distinct portion_id)> ?) AS p2 "
				+ "WHERE p1.grassi>p2.grassi";
		
		List<Adiacenti> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, porzioni);
			st.setInt(2, porzioni);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f1 = mappa.get(res.getInt("p1.food_code"));
					Food f2 = mappa.get(res.getInt("p2.food_code"));
					if(f1 != null && f2 != null) {
						result.add(new Adiacenti(f1,f2,res.getDouble("peso")));
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
}
