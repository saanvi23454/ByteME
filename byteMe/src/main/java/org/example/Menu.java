package org.example;
import java.io.Serializable;
import java.util.*;

public class Menu implements Serializable {
    private final Map<String, FoodItem> itemList = new HashMap<>();
    private final Map<String, FoodItem> deletedItems = new HashMap<>();
    private Integer numOfItems;
    private final Set<String> category = new HashSet<>();

    transient static Menu me = new Menu();

    public Menu(){
        numOfItems = 0;
    }

    @Override
    public String toString() {
        return  "MENU" + "\n" +
                "\tItemList : " + itemList +
                "\tDeletedItems : " + deletedItems +
                "\tNumber of Items : " + numOfItems;
    }

    public void addItem(FoodItem f){
        numOfItems++;
        itemList.put(f.getID(),f);
        category.add(f.getCategory());
    }

    public void modifyItem(String fid, Float p){
        if (itemList.containsKey(fid)) {
            itemList.get(fid).setPrice(p);
            return;
        }
        throw new ItemNotFoundException("FoodItem with FID "+fid+" does not exist");
    }
    public void modifyItem(String fid, Integer s){
        if (itemList.containsKey(fid)) {
            itemList.get(fid).setStock(s);
            return;
        }
        throw new ItemNotFoundException("FoodItem with FID "+fid+" does not exist");
    }
    public void modifyItem(String fid, String c){
        if (itemList.containsKey(fid)) {
            itemList.get(fid).setCategory(c);
            return;
        }
        throw new ItemNotFoundException("FoodItem with FID "+fid+" does not exist");
    }

    public void removeItem(String fid){
        if (itemList.containsKey(fid)) {
            FoodItem f = itemList.get(fid);
            f.selfDestroy();
            deletedItems.put(f.getCategory(), f);
            itemList.remove(fid);
            numOfItems--;
            return;
        }
        throw new ItemNotFoundException("FoodItem with FID "+fid+" does not exist");
    }

    public FoodItem getItem(String fid){
        if (itemList.containsKey(fid)){
            return itemList.get(fid);
        }
        throw new ItemNotFoundException("FoodItem with FID "+fid+" does not exist");
    }

    //protection of menu list ?
    public Map<String, FoodItem> getItemList(){
        return itemList;
    }

    public void viewItems(){
        int i = 0;
        System.out.println("MENU");
        for (FoodItem f : itemList.values()){
            System.out.println();
            System.out.println(++i + ". " + f);
            if (f.getStock() != 0 ){
                System.out.println("Available");
            }
            else{
                System.out.println("Out of Stock");
            }
        }
        if ( i == 0){
            System.out.println("No item in menu..");
        }
    }

    public Set<String> getCategoryList(){
        return category;
    }

    public void addOrder(Order o){
        Admin.me.addOrder(o);
    }

    public String getMenuDetails() {
        int i = 0;
        String returnString = "MENU";
        for (FoodItem f : itemList.values()){
            returnString = returnString.concat("\n\n" + ++i + ". " + f);
            if (f.getStock() != 0 ){
                returnString = returnString.concat("\n\tAvailable");
            }
            else{
                returnString = returnString.concat("\n\tOut of Stock");
            }
        }
        if ( i == 0){
            returnString = returnString.concat("\nNo item in menu...");
        }
        return returnString;
    }
}
