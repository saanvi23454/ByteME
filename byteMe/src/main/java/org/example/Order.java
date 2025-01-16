package org.example;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Order implements Serializable, Comparable<Order>{
    transient static final List<String> statusMap = new ArrayList<>(Arrays.asList("Cancelled","Pending", "Preparing", "Packing", "Out for Delivery", "Delivered", "Completed", "Denied"));
    transient static Integer OID = 0;

    private final Integer orderID;
    private final HashMap<FoodItem, Integer> orderList = new HashMap<>();
    private Float orderValue;
    private Integer status;
    private final Customer customer;
    private final LocalDateTime timestamp;
    private String request = "None";
    private final String address;
    private final String payment;


    public Order(Customer cus, Cart c, String a, String p){
        customer = cus;
        orderValue = 0f;
        for (Map.Entry<FoodItem, Integer> item : c.getItemList().entrySet()){
            FoodItem f = item.getKey();
            Integer q = item.getValue();
            orderList.put(f,q);
        }
        orderValue = c.getTotalPrice();
        status = 1;
        orderID = ++OID;
        timestamp = LocalDateTime.now();
        address = a;
        payment = p;
        customer.addOrder(this);
    }


    @Override
    public String toString() {
        int i = 0;
        String orderItems = "";
        for (Map.Entry<FoodItem, Integer> item : orderList.entrySet()){
            if ( i == 0){
                orderItems = orderItems.concat(item.getKey().getName() + " x " + item.getValue() + "\n");
            }
            else{
                orderItems = orderItems.concat("\t\t  " + item.getKey().getName() + " x " + item.getValue() + "\n");
            }
            i++;
        }
        return  "OrderID : O" + orderID + "\n" +
                "\tOrderList \t: " + orderItems +
                "\tOrderValue \t: $ " + orderValue + "\n" +
                "\tStatus \t: " + getStatus() + "\n" +
                "\tCustomer \t: " + customer.getCusID();
    }

    @Override
    public int compareTo(Order o) {
        if (customer.isVIP() && (!o.customer.isVIP())){
            return -1;
        }
        else if ((!customer.isVIP()) && o.customer.isVIP()){
            return 1;
        }
        else {
            return (this.orderID - o.getOID());
        }
    }

    public String getStatus(){
        return statusMap.get(status);
    }

    public String getOrderID(){
        return "O"+Integer.toString(orderID);
    }

    public void nextStatus(){
        if ((status < 6) && (status > 0)){
            status++;
        }
    }

    public void markAsCompleted(){
        status = 6;
    }

    public void prevStatus(){
        if ((status > 1) && (status < 7)){
            status--;
        }
    }

    public void cancelOrder(){
        if (status == 1 || status == 2 ) {
            for (Map.Entry<FoodItem, Integer> item : getOrderList().entrySet()){
                FoodItem f = item.getKey();
                f.setStock(f.getStock() + item.getValue());
            }
            sendNotification(customer, "Your order with orderID " + getOrderID() + " has been Cancelled. Refund has been initiated. Please contact customer care for information.");
            status = 0;
        }
        else{
            throw new OrderNotFoundException("Cannot cancel now..order is already in "  +getStatus() + " stage");
        }
    }

    private static void sendNotification(Customer c, String n){
        c.getNotification(n);
    }

    public void denyOrder(){
        sendNotification(customer, "Your order with orderID "+orderID+" has been Denied. Please contact customer care for information.");
        status = 7;
    }

    public Float getOrderValue(){
        return orderValue;
    }

    public void displayOrder(){
        System.out.println("Order ID "+ getOrderID());
        System.out.println("STATUS : "+getStatus());
        System.out.println("Order summary : ");
        int i = 0;
        for (Map.Entry<FoodItem, Integer> item : orderList.entrySet()){
            System.out.printf("%-20s %-10s %10s\n",++i + ". " + item.getKey().getName(),"$" + item.getKey().getPrice(),"x "+item.getValue());
        }
        System.out.println("Total price: $" + orderValue);
        System.out.println("Delivery Address : " + address);
        System.out.println("Payment Mode : " + payment);

    }

    public Integer getOID(){
        return orderID;
    }
    public LocalDateTime getTimeStamp(){
        return timestamp;
    }
    public HashMap<FoodItem, Integer> getOrderList() {
        return orderList;
    }
    public void setRequest(String s){
        request = s;
    }
    public String getRequest(){
        return request;
    }

}
