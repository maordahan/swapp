package serverApi;

import java.util.List;

import clientItem.MyItem;
import clientItem.NotMyItem;
import item.Item;
import user.SwappUser;

public interface Api {
	
	//******************Inspired From first manPage ******************
	
	public List<MyItem> getMytemList(String userId );
	
	public List<NotMyItem> getNotMytemList(String userId );
	
	//when the swapp user want to upload new item to the server
	public boolean addItem(Item item );
	
	public boolean updateItem(Item item );
	
	public boolean deleteItem(String itemId );
	// this is the result of Swapp user when he using the application and swipe btweens items, i.e. the Swapp user can like or dislike the not my items
	public void updateNotMyItems(updateNotMyItemsInput input  );
	//Sing up.
	public boolean addUpdateSwappUser(SwappUser user);
	
	
	//****************************************************************
	
	public class updateNotMyItemsInput {
		private String myItemId;
		private List<String> likedId;
		private List<String> dislikeId;
		
		public String getMyItemId() {
			return myItemId;
		}
		public void setMyItemId(String myItemId) {
			this.myItemId = myItemId;
		}
		public List<String> getLikedId() {
			return likedId;
		}
		public void setLikedId(List<String> likedId) {
			this.likedId = likedId;
		}
		public List<String> getDislikeId() {
			return dislikeId;
		}
		public void setDislikeId(List<String> dislikeId) {
			this.dislikeId = dislikeId;
		}
		
	}

}
