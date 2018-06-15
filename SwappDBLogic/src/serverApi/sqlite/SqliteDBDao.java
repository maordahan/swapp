package serverApi.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import clientItem.MyItem;
import clientItem.NotMyItem;
import item.Item;
import item.Item.Status;
import serverItems.ItemCategory;
import user.ItemOwner;
import user.SwappUser;

public class SqliteDBDao {
	
	private Connection swapConnection = null;
	private static String STORAGE = "/home/swapp/storage";
	
	public SqliteDBDao() throws Exception   {
        Class.forName("org.sqlite.JDBC");
        swapConnection = DriverManager.getConnection("jdbc:sqlite:"+ STORAGE + "/swapp.db");
        config();
        System.out.println("Opened database successfully");
	}
	
	
	//TODO ID and USERID need to be the PRIMARY key
	private static String ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS ITEMS_TABLE " +
                        "(ID  TEXT PRIMARY   KEY    NOT NULL, " +
                        " NAME              TEXT   NOT NULL,  " + 
                        " DESCRIPTION       TEXT   NOT NULL,  " + 
                        " STATUS            TEXT   NOT NULL,  " +
                        " USERID            TEXT   NOT NULL,  " +
                        " IMAGE             TEXT   NOT NULL,  " +
                        " CATEGORY          TEXT   NOT NULL,  " +
                        " ITEMS_LIKED_ME    TEXT,  "            +
                        " ITEMS_DISLIKED_ME TEXT,  "            +
                        " FOREIGN KEY(USERID) REFERENCES USERS_TABLE(ID))";
	
	private static String USERS_TABLE ="CREATE TABLE IF NOT EXISTS USERS_TABLE " +
						 "(ID  TEXT PRIMARY  KEY    NOT NULL, " +
            			 " NAME              TEXT   NOT NULL,  " +
            			 " IMAGE             TEXT   NOT NULL,  " +
            			 " LOCATION          TEXT   NOT NULL,  " +
            			 " SEARCH_CATEGORY   TEXT   NOT NULL)";
	
	private static String MATCH_TABLE ="CREATE TABLE IF NOT EXISTS MATCH_TABLE " +
			 "(ITEM_ID1          TEXT   NOT NULL, " +
			 " ITEM_ID2          TEXT   NOT NULL,  " +
			 " DATE              TEXT   NOT NULL,  " +
			 " PRIMARY KEY (ITEM_ID1, ITEM_ID2))";
	
	private static String isUserExist     = "SELECT ID FROM USERS_TABLE WHERE ID = ?";
	private static String insertSwappUser = "INSERT INTO USERS_TABLE(ID,NAME,IMAGE,LOCATION,SEARCH_CATEGORY) VALUES(?,?,?,?,?)";
	private static String updateSwappUser = "UPDATE USERS_TABLE SET NAME = ?, IMAGE = ? , LOCATION = ?, SEARCH_CATEGORY = ? WHERE ID = ?";
			
	
	private static String addItem = "INSERT INTO ITEMS_TABLE(ID,NAME,DESCRIPTION,STATUS,USERID," + 
                                                        "IMAGE,CATEGORY,ITEMS_LIKED_ME,ITEMS_DISLIKED_ME) " + 
                                                        "VALUES(?,?,?,?,?,?,?,?,?)";
	
	private static String deleteItem = "DELETE FROM ITEMS_TABLE WHERE ID = ?";  
	private static String deleteFromMatch = "DELETE FROM MATCH_TABLE WHERE ITEM_ID1 = ? or  ITEM_ID2 =? ";  
	
	private static String getAllUserItemsID = "SELECT ID from ITEMS_TABLE where USERID = ?" ;
		
	//Maybe in the Future for performance need to use the power of the in... ( if it save something) 
	private static String updateItemLikedMe    = "Update ITEMS_TABLE set ITEMS_LIKED_ME = ITEMS_LIKED_ME || ';' || ? where ID in ( ? )";
	private static String updateItemDisLikedMe = "Update ITEMS_TABLE set ITEMS_DISLIKED_ME = ITEMS_DISLIKED_ME || ';' || ? where ID in ( ? )";
	
	private static String getUserByItem = "SELECT USERID from ITEMS_TABLE where ID = ? ";
	
	private static String getAllUserItems = "SELECT ID, NAME, DESCRIPTION, STATUS, IMAGE,"
			+ "CATEGORY, ITEMS_LIKED_ME from ITEMS_TABLE where USERID = ?";
	
	private static String getNotMytemList = "select" +                       
			" ITEMS_TABLE.ID as I_ID,     ITEMS_TABLE.NAME as I_NAME   ,ITEMS_TABLE.DESCRIPTION," +
			" ITEMS_TABLE.STATUS ,ITEMS_TABLE.USERID ,ITEMS_TABLE.IMAGE as I_IMAGE ,ITEMS_TABLE.CATEGORY," +
			" USERS_TABLE.ID     ,USERS_TABLE.NAME   ,USERS_TABLE.IMAGE ,USERS_TABLE.LOCATION" + 
			
			" from ITEMS_TABLE, USERS_TABLE where" + 
			
			" ITEMS_TABLE.USERID != ? AND" + 
			" ITEMS_TABLE.USERID = USERS_TABLE.ID AND" +
			" ITEMS_TABLE.ITEMS_LIKED_ME NOT LIKE ';%?$%' AND" +
			" ITEMS_TABLE.ITEMS_DISLIKED_ME NOT LIKE ';%?$%'";
	
	
	
	
	private void config() throws SQLException {
		try (Statement stat = swapConnection.createStatement();) {
			stat.execute("PRAGMA foreign_keys = ON;");
		}
	}
	
	public void createDbTablesIfNotExist() throws SQLException {
		try (Statement stat = swapConnection.createStatement();) {
			stat.executeUpdate(SqliteDBDao.ITEMS_TABLE);
			stat.executeUpdate(SqliteDBDao.USERS_TABLE);
			stat.executeUpdate(SqliteDBDao.MATCH_TABLE);
		}	
	}
	
	public boolean insertUpdateSwappUser(SwappUser user) {
		
		boolean isExist =  isUserExist(user.getUserId());
		if ( ! isExist) {
		return insertSwappUser(user);
		}
		return updateSwappUser(user);
	}

	private boolean updateSwappUser(SwappUser user) {
		try (PreparedStatement pstmt = swapConnection.prepareStatement(updateSwappUser);) {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getImage());
			pstmt.setString(3, user.getLocation());
			pstmt.setString(4, user.getSearchCategory().toDBString());
			pstmt.setString(5, user.getUserId());
			
			pstmt.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	private boolean insertSwappUser(SwappUser user) {
		try (PreparedStatement pstmt = swapConnection.prepareStatement(insertSwappUser);) {
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getImage());
			pstmt.setString(4, user.getLocation());
			pstmt.setString(5, user.getSearchCategory().toDBString());
			
			pstmt.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	private boolean isUserExist(String id) {
		try (PreparedStatement pstmtE = swapConnection.prepareStatement(isUserExist);) {
			pstmtE.setString(1, id);
			try (ResultSet rs = pstmtE.executeQuery();) {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public boolean addItem(Item item){
		try (PreparedStatement pstmt = swapConnection.prepareStatement(addItem);) {
			pstmt.setString(1, item.getItemId());
			pstmt.setString(2, item.getName());
			pstmt.setString(3, item.getDesctiption());
			pstmt.setString(4, item.getStatus().toString());
			pstmt.setString(5, item.getUserID());
			pstmt.setString(6, item.getImage());
			pstmt.setString(7, item.getCategory().toDBString());
			pstmt.setString(8, "");
			pstmt.setString(9, "");
			
			pstmt.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;
		
	}
	
	
	public void updateItemSwappdByItemID(String itemId, String swappByItemId, boolean preference) {
		String statement = preference ? updateItemLikedMe : updateItemDisLikedMe;
		try (PreparedStatement pstmt = swapConnection.prepareStatement(statement);) {
			pstmt.setString(1, itemId);
			pstmt.setString(2, swappByItemId);
			pstmt.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public String getUserItem(String itemId) {
		try (PreparedStatement stmt = swapConnection.prepareStatement(getUserByItem);) {
			stmt.setString(1, itemId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					return rs.getString("USERID");
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	/*
	 * TODO NEED TO DO IN ONE TRANSACTION, if it delete from Item table if and
	 * only if deleted from the match table
	 */
	public boolean deleteItem(String itemId) {
		try (PreparedStatement pstmt1 = swapConnection.prepareStatement(deleteItem);
				PreparedStatement pstmt2 = swapConnection.prepareStatement(deleteFromMatch);) {
			pstmt1.setString(1, itemId);
			pstmt2.setString(1, itemId);
			pstmt1.executeUpdate();
			pstmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<String> getAllUserItemsID(String UserId) {
		List<String> res = new LinkedList<>();
		try (PreparedStatement stmt = swapConnection.prepareStatement(getAllUserItemsID);) {
			stmt.setString(1, UserId);
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					res.add(rs.getString("ID"));
				}
			}
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			return null;

		}
	}
	

	
	public List<MyItem> getAllUserItems(String userId) {
		List<MyItem> res = new LinkedList<>();
		try (PreparedStatement stmt = swapConnection.prepareStatement(getAllUserItems);) {
			stmt.setString(1, userId);
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					MyItem item = new MyItem();
					item.setUserID(userId);
					item.setItemId(rs.getString("ID"));
					item.setName(rs.getString("NAME"));
					item.setDesctiption(rs.getString("DESCRIPTION"));
					item.setStatus(Status.valueOf(rs.getString("STATUS")));
					item.setImage(rs.getString("IMAGE"));
					item.setCategory(ItemCategory.fromString(rs.getString("CATEGORY")));
					item.setItemIdLikedme(MyItem.itemIdLikedmeFromString(rs.getString("ITEMS_LIKED_ME")));
					res.add(item);
				}
			}
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			return null;

		}
	}
	
	
	List<NotMyItem> getNotMytemList(String userId ) {
		List<NotMyItem> res = new LinkedList<>();
		try (PreparedStatement stmt = swapConnection.prepareStatement(getNotMytemList);) {
			stmt.setString(1, userId);
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					NotMyItem item = new NotMyItem();
					item.setUserID(rs.getString("USERID"));
					item.setItemId(rs.getString("I_ID"));
					item.setName(rs.getString("I_NAME"));
					item.setDesctiption(rs.getString("DESCRIPTION"));
					item.setStatus(Status.valueOf(rs.getString("STATUS")));
					item.setImage(rs.getString("I_IMAGE"));
					item.setCategory(ItemCategory.fromString(rs.getString("CATEGORY")));
								
					ItemOwner owner = new ItemOwner();
					item.setLocation(rs.getString("LOCATION"));
					owner.setName(rs.getString("NAME"));
					owner.setImage(rs.getString("IMAGE"));
					owner.setUserId(rs.getString("ID"));
					item.setOwner(owner);
					res.add(item);
				}
			}
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			return null;

		}
	}
}
	
	

