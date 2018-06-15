package clientItem;

import java.util.ArrayList;
import java.util.List;

import item.Item;
import serverItems.ItemCategory;

public class MyItem extends Item{

	//List of items that liked this item.
	private List<String> itemIdLikedme = new ArrayList<String>();
	
	public static List<String> itemIdLikedmeFromString(String dbString) {
    	String[] array = dbString.split(";");
    	List<String> l = new ArrayList<String>();
    	for (String s : array) {
    		l.add(s);
    	}
    	
    	return l;
	}

	public List<String> getItemIdLikedme() {
		return itemIdLikedme;
	}

	public void setItemIdLikedme(List<String> itemIdLikedme) {
		this.itemIdLikedme = itemIdLikedme;
	}

}
