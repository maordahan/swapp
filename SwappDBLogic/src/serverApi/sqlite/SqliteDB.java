package serverApi.sqlite;

import java.util.List;

import clientItem.MyItem;
import clientItem.NotMyItem;
import item.Item;
import serverApi.Api;
import user.SwappUser;

public class SqliteDB implements Api {
	
	
	private static SqliteDBDao sqliteDao;
	static {
		try {
			sqliteDao = new SqliteDBDao();
			sqliteDao.createDbTablesIfNotExist();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private SqliteDB() {}
	public static SqliteDB instance = new SqliteDB();
	

	@Override
	public List<MyItem> getMytemList(String userId) {
		return sqliteDao.getAllUserItems(userId);
	}

	@Override
	public List<NotMyItem> getNotMytemList(String userId) {
		return sqliteDao.getNotMytemList( userId);
	}

	@Override
	public boolean addItem(Item item) {
		return sqliteDao.addItem(item);
	}

	@Override
	//TODO Maybe need to delete also from the like/dislike list/ maybe in the background 
	public boolean deleteItem(String itemId) {
		return sqliteDao.deleteItem(itemId);
	}

	@Override
	public void updateNotMyItems(updateNotMyItemsInput input) {
		for (String id : input.getLikedId()) {
			sqliteDao.updateItemSwappdByItemID(input.getMyItemId(), id, true); // id was liked by myItemId
		}

		String userId = sqliteDao.getUserItem(input.getMyItemId());
		List<String> allUserItems = sqliteDao.getAllUserItemsID(userId);

		for (String userItem : allUserItems) {
			for (String id : input.getDislikeId()) {
				sqliteDao.updateItemSwappdByItemID(userItem, id, false); // id was disliked by userItem
			}
		}	
		//TODO
		//Update the Match table abut the match, only the 2 item id's, client will send the rest of the information,
		//If client send info that not in the table is a BUG.
		//If the server put info and don't get it from the client in some time it also a bug. 
	}

	@Override
	public boolean addUpdateSwappUser(SwappUser user) {
		return sqliteDao.insertUpdateSwappUser(user);
		
	}

	@Override
	public boolean updateItem(Item item) {
		return false;
	}

}
