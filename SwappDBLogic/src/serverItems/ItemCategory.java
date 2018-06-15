package serverItems;

public class ItemCategory {
	
	//In case it set from Item, Min and Max will be equal, no range there.
    private double minEstimatePrice;
    private double maxEstimatePrice;
    
	public double getMinEstimatePrice() {
		return minEstimatePrice;
	}
	
	public void setMinEstimatePrice(double minEstimatePrice) {
		this.minEstimatePrice = minEstimatePrice;
	}
	
	public double getMaxEstimatePrice() {
		return maxEstimatePrice;
	}
	public void setMaxEstimatePrice(double maxEstimatePrice) {
		this.maxEstimatePrice = maxEstimatePrice;
	}
    
    public static ItemCategory fromString(String dbString) {
    	String[] array = dbString.split(";");
    	ItemCategory c = new ItemCategory();
    	c.setMinEstimatePrice(Double.parseDouble(array[0]));
    	c.setMaxEstimatePrice(Double.parseDouble(array[1]));
    	
    	return c;
    }
    
    public String toDBString() {
    	return minEstimatePrice + ";" + maxEstimatePrice;
    }
	
}
