package user;

import java.util.List;

import clientItem.MyItem;
import clientItem.NotMyItem;
import item.Item;
import serverItems.ItemCategory;
import serverItems.ServerItem;

public class SwappUser extends User {
	
	private String location;
	private int maxSerchLocation;
	private List<ServerItem> itemList;
	private ItemCategory searchCategory;
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getMaxSerchLocation() {
		return maxSerchLocation;
	}
	
	public void setMaxSerchLocation(int maxSerchLocation) {
		this.maxSerchLocation = maxSerchLocation;
	}
	
	public List<ServerItem> getItemList() {
		return itemList;
	}
	
	public void setItemList(List<ServerItem> itemList) {
		this.itemList = itemList;
	}
	
	public ItemCategory getSearchCategory() {
		return searchCategory;
	}
	
	public void setSearchCategory(ItemCategory searchCategory) {
		this.searchCategory = searchCategory;
	}

}
