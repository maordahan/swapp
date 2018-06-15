package item;


import serverItems.ItemCategory;

public class Item {


	public enum Status
	{
		USED ("USED"),
		NEW ("NEW"),
		UNKNOWN ("UNKNOWN");
		
		private final String name;
		
		private Status(String s) {
			name = s;
		}
		
		public String toString() {
			return name;
		}
	}
	
	
	private String itemId;
	private String name;
	private String desctiption;
	private Status status;
	private String userID;
	private String images;
	private ItemCategory category;
	
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesctiption() {
		return desctiption;
	}
	
	public void setDesctiption(String desctiption) {
		this.desctiption = desctiption;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getImage() {
		return images;
	}
	
	public void setImage(String images) {
		this.images = images;
	}
	
	public ItemCategory getCategory() {
		return category;
	}
	
	public void setCategory(ItemCategory category) {
		this.category = category;
	}
		
}

