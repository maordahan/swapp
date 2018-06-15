package clientItem;

import item.Item;
import user.ItemOwner;

public class NotMyItem extends Item {

	private String location;
	private ItemOwner owner;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ItemOwner getOwner() {
		return owner;
	}

	public void setOwner(ItemOwner owner) {
		this.owner = owner;
	}

}
