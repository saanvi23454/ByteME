package org.example;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class Cart implements Serializable {
    private final HashMap<FoodItem, Integer> cartList = new HashMap<>();
    private Integer numOfItem;
    private Float totalPrice;
    private final Customer customer;

    public Cart(Customer c){
        numOfItem = 0;
        totalPrice = 0f;
        customer = c;

    }

    @Override
    public String toString() {
        return "CART\n" +
                "Cart List : " + cartList + "\n" +
                "Number of Items : " + numOfItem + "\n" +
                "Total Price : $" + totalPrice + "\n" +
                "Customer : " + customer;
    }

    public HashMap<FoodItem,Integer> getItemList(){
        return cartList;
    }

    public void addItem(String fid, int quantity) throws ItemOutOfStockException{
        //assume quantity is positive integer
        if (Menu.me.getItemList().containsKey(fid)){
            FoodItem f = Menu.me.getItemList().get(fid);
            if (f.getStock() < quantity){
                throw new ItemOutOfStockException("Sorry, only " + f.getStock() + " are left in stock!");
            }
            if (cartList.containsKey(f)){
                cartList.put(f, cartList.get(f)+quantity);
            }
            else {
                f.addCart(this);
                cartList.put(f, quantity);
            }
            numOfItem += quantity;
            totalPrice += (quantity * f.getPrice());
            f.takeStock(quantity);
        }
        else{
            throw new ItemNotFoundException("FoodItem with FID "+fid+" does not exist");
        }
    }

    public void discardItem(String fid){
        if (Menu.me.getItemList().containsKey(fid)){
            FoodItem f = Menu.me.getItemList().get(fid);
            if (cartList.containsKey(f)){
                int q = cartList.get(f);
                float p = f.getPrice();
                cartList.remove(f);
                totalPrice -= (q*p);
                numOfItem -= q;
                f.removeCart(this);
                f.setStock(f.getStock()+q);
                return;
            }
        }
        throw new ItemNotFoundException("FoodItem with FID "+fid+" not found in the cart");
    }

    public void discardItem(Menu m, String fid, int quantity){
        //quantity is positive integer
        if (m.getItemList().containsKey(fid)){
            FoodItem f = m.getItemList().get(fid);
            if (cartList.containsKey(f)){
                if (quantity >= cartList.get(f)){
                    quantity = cartList.get(f);
                    numOfItem -= quantity;
                    totalPrice -= quantity * f.getPrice();
                    cartList.remove(f);
                    f.removeCart(this);
                    f.setStock(f.getStock()+quantity);
                    return;
                }
                cartList.put(f, cartList.get(f)-quantity);
                numOfItem -= quantity;
                totalPrice -= quantity * f.getPrice();
                f.setStock(f.getStock()+quantity);
                return;
            }
        }
        throw new ItemNotFoundException("FoodItem with FID "+fid+" not found in the cart");
    }

    public void displayCart(){
        System.out.println(numOfItem + " items in the cart: ");
        int i = 0;
        for (Map.Entry<FoodItem, Integer> item : cartList.entrySet()){
            System.out.printf("%-20s %-10s %10s\n",++i + ". " + item.getKey().getName(),"$" + item.getKey().getPrice(),"x "+item.getValue());
        }
        System.out.println("Total price: $" + totalPrice);
    }

    public void changePrice(Float x){
        totalPrice += x;
    }

    public void sendNotification(String s){
        customer.getNotification(s);
    }

    public Float getTotalPrice(){
        return totalPrice;
    }

    public int getTotalItems(){
        return numOfItem;
    }

    public int getQuant(String fid){
        if (Menu.me.getItemList().containsKey(fid)) {
            FoodItem f = Menu.me.getItemList().get(fid);
            return f.getStock();
        }
        throw new ItemNotFoundException("Item with FID " + fid + "does not exist");
    }
}
