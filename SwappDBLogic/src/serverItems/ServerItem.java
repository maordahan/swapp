package serverItems;
import java.util.ArrayList;
import java.util.List;

import item.Item;

public class ServerItem extends Item {
	
	//List of items that liked this item.
	List<String> itemIdLikedme = new ArrayList<String>();
	//List of items that this current item did not like.
	List<String> itemIdIdislike = new ArrayList<String>();

	String getItemLocation() {
		return "TO_IMPLEMENT_LOCATION";
	}	
}
