package com.swapp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import clientItem.MyItem;
import clientItem.NotMyItem;
import item.Item;
import item.Item.Status;
import serverApi.Api.updateNotMyItemsInput;
import serverItems.ItemCategory;
import user.SwappUser;

public class RandomTest {
	
	public static Gson gson;
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting(); 
		//How the thread safe work, if we have a lot of request do we have starvation to some request. 
		gson = builder.create();
	}

	public static void main(String[] args) throws Exception {
		
		String jsonRes;
		
		/**************************ADD MAOR USER*****************************/
		SwappUser maor = new SwappUser();
		maor.setUserId("maor.dahan@gmail.com");
		maor.setName("Maor Dahan");
		maor.setImage("\\usr\\local\\tmp\\maor.png");
		maor.setLocation("Haifa");
		ItemCategory maorItemCategory = new ItemCategory();
		maorItemCategory.setMinEstimatePrice(0);
		maorItemCategory.setMaxEstimatePrice(100);
		maor.setSearchCategory(maorItemCategory);
		
		jsonRes = TomcatTest.test("addUpdateSwappUser", gson.toJson(maor));
		System.out.println(jsonRes);
		
		/**************************ADD KEREN USER*****************************/
		SwappUser keren = new SwappUser();
		keren.setUserId("keren.pastov@gmail.com");
		keren.setName("Keren Pastov");
		keren.setImage("\\usr\\local\\tmp\\keren.png");
		keren.setLocation("Byalic");
		ItemCategory kerenItemCategory = new ItemCategory();
		kerenItemCategory.setMinEstimatePrice(50);
		kerenItemCategory.setMaxEstimatePrice(75);
		keren.setSearchCategory(kerenItemCategory);
		
		jsonRes = TomcatTest.test("addUpdateSwappUser", gson.toJson(keren));
		System.out.println(jsonRes);
	
		
		/**************************ADD TV ITEM TO MAOR************************/
		ItemCategory tvItemCategory = new ItemCategory();
		tvItemCategory.setMinEstimatePrice(1050);
		tvItemCategory.setMaxEstimatePrice(1050);
		
		byte[] tvImageByte = readBytesFromFile("C:\\home\\swapp\\testImage.png");
		String tvImage = Base64.getEncoder().encodeToString(tvImageByte);
		
		Item tv = new Item();
		tv.setItemId("maor.dahan@gmail.com$TV");
		tv.setName("TV");
		tv.setDesctiption("My TV");
		tv.setImage(tvImage);
		tv.setCategory(tvItemCategory);
		tv.setStatus(Status.NEW);
		tv.setUserID("maor.dahan@gmail.com");		
		jsonRes = TomcatTest.test("addItem", gson.toJson(tv));
		System.out.println(jsonRes);
		
		/**************************ADD ZIPO ITEM TO MAOR************************/
		ItemCategory zipoItemCategory = new ItemCategory();
		zipoItemCategory.setMinEstimatePrice(100);
		zipoItemCategory.setMaxEstimatePrice(100);
		
		byte[] zipoImageByte = readBytesFromFile("C:\\home\\swapp\\testImage.png");
		String zipoImage = Base64.getEncoder().encodeToString(zipoImageByte);
		
		Item zipo = new Item();
		zipo.setItemId("maor.dahan@gmail.com$ZIPO");
		zipo.setName("ZIPO");
		zipo.setDesctiption("My ZIPO");
		zipo.setImage(zipoImage);
		zipo.setCategory(zipoItemCategory);
		zipo.setStatus(Status.USED);
		zipo.setUserID("maor.dahan@gmail.com");		
		jsonRes = TomcatTest.test("addItem", gson.toJson(zipo));
		System.out.println(jsonRes);
		
		/**************************ADD BARBY ITEM TO KEREN************************/
		ItemCategory barabyItemCategory = new ItemCategory();
		barabyItemCategory.setMinEstimatePrice(100);
		barabyItemCategory.setMaxEstimatePrice(100);
		
		byte[] barabyImageByte = readBytesFromFile("C:\\home\\swapp\\testImage.png");
		String barabyImage = Base64.getEncoder().encodeToString(barabyImageByte);
		
		Item baraby = new Item();
		baraby.setItemId("keren.pastov@gmail.com$BARBY");
		baraby.setName("BARBY");
		baraby.setDesctiption("My BARBY");
		baraby.setImage(barabyImage);
		baraby.setCategory(barabyItemCategory);
		baraby.setStatus(Status.USED);
		baraby.setUserID("keren.pastov@gmail.com");		
		jsonRes = TomcatTest.test("addItem", gson.toJson(baraby));
		System.out.println(jsonRes);
		
		/**************************Test update not my items************************/
			
		List<String> likedList = new ArrayList<String>();
		likedList.add("maor.dahan@gmail.com$ZIPO");
		likedList.add("maor.dahan@gmail.com$TV");
		
		updateNotMyItemsInput input  = new updateNotMyItemsInput();
		input.setMyItemId("keren.pastov@gmail.com$BARBY");
		input.setLikedId(likedList);
		input.setDislikeId(likedList);
		
		jsonRes = TomcatTest.test("updateNotMyItems", gson.toJson(input));
		System.out.println(jsonRes);
		
		/**************************Test delete item************************/
		jsonRes = TomcatTest.test("deleteItem",gson.toJson("maor.dahan@gmail.com$TV"));
		System.out.println(jsonRes);
		
		/**************************Add back the tv item************************/
		jsonRes = TomcatTest.test("addItem", gson.toJson(tv));
		System.out.println(jsonRes);
		
		/**************************Test keren Pikso URL********************/
		/*Put this is the browser  -->
		http://localhost:8080/images/maor.dahan@gmail.com$ZIPO.png*/
		
		/**************************Test getMytemList********************/
		
		jsonRes = TomcatTest.test("getMytemList",gson.toJson("maor.dahan@gmail.com"));
		System.out.println(jsonRes);
		
		Type listType = new TypeToken<ArrayList<MyItem>>(){}.getType();
		@SuppressWarnings("unused") //Check in  debug mode.
		List<MyItem> res_getMytemList = new Gson().fromJson(jsonRes, listType);
		
		/**************************Test getNotMytemList********************/
		
		jsonRes = TomcatTest.test("getNotMytemList",gson.toJson("maor.dahan@gmail.com"));
		System.out.println(jsonRes);
		
		listType = new TypeToken<ArrayList<NotMyItem>>(){}.getType();
		@SuppressWarnings("unused") //Check in  debug mode.
		List<NotMyItem> res = new Gson().fromJson(jsonRes, listType);
		
	}
	
	
    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }

}
