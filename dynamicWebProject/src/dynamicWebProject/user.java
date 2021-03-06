package dynamicWebProject;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import dynamicWebProject.dbconnector;

public class user {

	public static boolean addUser(String username, String first, String last, String password){
		//Creates a User, returns a account id
		String l_in[] = {"user_name,first_name,last_name, password"};
		String l_in2[] = {username,first, last, password};
		
		try {
			dbconnector.insertStatement("user",l_in,l_in2);
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean userExists(String username){
			try {
				CachedRowSet c = dbconnector.selectStatement("*","user"," user_name = '" + username + "' ","");
				
				if (c.next()){
					return true;
				}
				else {
					return false;
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	public static CachedRowSet login(String username, String password){
		try {
			CachedRowSet c = dbconnector.selectStatement("*","user"," user_name = '" + username + "' ","");
			
			return c;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
}
	
	public static boolean addQuest(String account_number,String quest_id){
		String l_in[] = {"account_number","quest_id"};
		String l_in2[] = {account_number,quest_id};
		
		try {
			dbconnector.insertStatement("user_quests",l_in,l_in2);
				
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	//get all available quests	
	public static CachedRowSet getAvailableQuests(String account_number){
		try {
			CachedRowSet c = dbconnector.selectStatement("*","quests"," quests.quest_id not in (select quest_id from wom.user_quests where account_number = " + account_number + ")","quest_reqlvl");
			
			return c;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public static CachedRowSet getQuestLog(String account_number){
		try {
			CachedRowSet c = dbconnector.selectStatement("*","user_quests join wom.quests"," quests.quest_id = user_quests.quest_id and completion = '0' and account_number = '" + account_number + "' ","");
			
			return c;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static CachedRowSet getCompletedQuests(String account_number){
		try {
			CachedRowSet c = dbconnector.selectStatement("*","user_quests join wom.quests"," quests.quest_id = user_quests.quest_id and completion = '1' and account_number = '" + account_number + "' ","");
			
			return c;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean completeQuest(String account_number, String questid){
		
		String l_in[] = {"completion"};
		String l_in2[] = {"1"};
		
		try {
			dbconnector.updateStatement("user_quests",l_in, l_in2, "user_quests.account_number = " + "'" + account_number + "' AND user_quests.quest_id = " + questid);
				
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static CachedRowSet getUserData(String account_number){
		try {
			CachedRowSet c = dbconnector.selectStatement("*","user_data"," account_number = '" + account_number + "' ","");
			
			return c;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static CachedRowSet getAllUsers(){
		try {
			CachedRowSet c = dbconnector.selectStatement("*","user","","account_number");
			
			return c;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean initUserData(String account_number){
		//Creates a User, returns a account id
				String l_in[] = {"account_number,char_level,quests_completed, image_path"};
				String l_in2[] = {account_number,"1", "0", ""};
				
				try {
					dbconnector.insertStatement("user_data",l_in,l_in2);
					return true;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return false;
				}
	}
	
	public static String getAccountNumber(String user_name){
		try {
			CachedRowSet c = dbconnector.selectStatement("*","user"," user_name = '" + user_name + "' ","");
			c.next();
			
			return c.getString(1);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//--------------------------------------------------- TO DO
	
	//increments quests completed for account number
	public static boolean incrementQuestsCompleted(String account_number){
		String l_in[] = {"quests_completed"};
		String l_in2[] = {"quests_completed + 1"};
		
		try {
			dbconnector.updateStatement("user_data",l_in,l_in2, "account_number =" + account_number);
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//increments character level based on incAmount
	public static boolean updateCharacterLevel(String account_number){
		String l_in[] = {"char_level"};
		String l_in2[] = {"char_level + 2"};
		
		try {
			dbconnector.updateStatement("user_data", l_in, l_in2, "account_number = " + account_number);
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	//updates image path for account
	public static boolean updateImagePath(String account_number, String path){
		String l_in[] = {"image_path"};
		String l_in2[] = {path};
		
		try {
			dbconnector.updateStatement("user_data", l_in, l_in2, "account_number = " + account_number);
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	//------------------------------------------------------
}
