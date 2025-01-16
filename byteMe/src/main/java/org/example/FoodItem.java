package org.example;

import java.io.Serializable;
import java.util.*;

public class FoodItem implements Serializable, Comparable<FoodItem>{
    transient static int FID = 0;

    private final Integer itemID;
    private final String itemName;
    private String category;
    private Float price;
    private Integer stock;
    private Boolean currentlyExists = true;

    private final Map<String, Order> orderMap = new HashMap<>();
    private final Set<Cart> cartList = new HashSet<>();
    private final List<Review> reviews = new ArrayList<>();

    public FoodItem(String n, String c, Float p, Integer s){
        itemName = n;
        category = c;
        price = p;
        stock = s;
        itemID = (++FID);
    }

    @Override
    public String toString() {
        return  "ItemID : " + getID() + "\n" +
                "\tItemName \t: " + itemName + "\n"+
                "\tCategory \t: " + category + "\n" +
                "\tPrice \t: $ " + price + "\n" +
                "\tStock \t: " + stock ;
    }

    public int compareTo(FoodItem f) {
        if (this.price < f.price){
            return -1;
        }
        else if (this.price > f.price){
            return 1;
        }
        else{
            return (this.getIDint() - f.getIDint());
        }
    }

    //getters and setters
    public String getName(){
        return itemName;
    }
    public String getCategory(){
        return category;
    }
    public Float getPrice(){
        return price;
    }
    public Integer getStock(){
        return stock;
    }
    public String getID(){
        return "F"+Integer.toString(itemID);
    }

    public int getIDint(){
        return itemID;
    }

//    private void setName(String n){
//        itemName = n;
//    }
    public void setPrice(Float p){
        // change for every cart
        for (Cart c : cartList){
            Float x = (p - price) * c.getItemList().get(this);
            c.changePrice(x);
            c.sendNotification("Price of item with FID "+getID()+" in your cart has changed.");
        }
        price = p;
    }
    public void setStock(Integer s){
        stock = s;
    }
    public void setCategory(String c){
        category = c;
    }

    //check availability
    public Boolean isAvailable(int i){
        return ((stock-i) >= 0);
    }
    public Boolean isAvailable(){
        return (stock > 0);
    }

    public Boolean placeOrder(int i){
        if (isAvailable(i)){
            stock -= i;
            return true;
        }
        return false;
    }


    public void addOrder(Order o){
        orderMap.put(o.getOrderID(), o);
    }

    public void selfDestroy(){
        for (Order o : orderMap.values()){
            o.denyOrder();
        }
        currentlyExists = false;
    }

    //adding cart;
    public void addCart(Cart c){
        cartList.add(c);
    }

    public void removeCart(Cart c){
        cartList.remove(c);
    }

    public void takeStock(int i){
        stock -= i;
    }

    public Boolean exists(){
        return currentlyExists;
    }

    public void addReview(Review r){
        reviews.add(r);
    }

    public void viewReview(){
        int i = 0;
        System.out.println("Reviews :");
        for (Review r : reviews){
            System.out.println();
            System.out.println(++i + ". " + r);
        }
        if (i == 0){
            System.out.println("No reviews exist yet for this item..");
        }
    }
}
