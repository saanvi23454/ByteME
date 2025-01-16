package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Admin implements Serializable{

    private transient static final String password = "admin@123";
    transient static Admin me = new Admin(password);

    private TreeSet<Order> orderQueue = new TreeSet<>();
    private Map<String, Order> orderMap = new HashMap<>();
    private List<Order> completedOrder = new ArrayList<>();
    private List<Order> cancelledOrder = new ArrayList<>();

    private int CID = 0, FID = 0, OID = 0 ;

    public Admin(String pwd){
        if (! pwd.equals(password) ){
            throw new InvalidLoginException("Bad login attempt!");
        }
    }

    @Override
    public String toString() {
        return "ADMIN\n" + Menu.me;
    }

    public Boolean checkPassword(String pwd){
        return (pwd.equals(password));
    }

    public static Admin checkPasswordAndGet(String pwd){
        if (pwd.equals(password)){
            return me;
        }
        throw new InvalidLoginException("Password invalid!");
    }

    public void saveStaticVariables(){
        CID = Customer.CID;
        FID = FoodItem.FID;
        OID = Order.OID;
    }
    public void restoreStaticVariables(){
        Order.OID = OID;
        FoodItem.FID = FID;
        Customer.CID = CID;
    }

    // MENU MANAGEMENT

    public void viewMenu(){
        int i = 0;
        for (FoodItem f : Menu.me.getItemList().values()){
            System.out.println(++i + ". " + f);
        }
        if (i == 0){
            System.out.println("No items in the menu..");
        }
    }

    public void addFoodItem(){
        Scanner scanner = new Scanner(System.in);

        String name, category;
        Float price;
        Integer stock;

        System.out.println("Enter Item Details");
        System.out.println("Name : ");
        name = scanner.nextLine();
        System.out.println("Category : ");
        category = scanner.nextLine();
        System.out.println("Price : $");
        price = scanner.nextFloat();
        scanner.nextLine();
        System.out.println("Quantity in Stock : ");
        stock = scanner.nextInt();
        scanner.nextLine();

        Menu.me.addItem(new FoodItem(name,category,price,stock));
        System.out.println("New item added to menu..");
    }

    public void updateFoodItem(){
        Scanner scanner = new Scanner(System.in);

        String fid;
        System.out.println("Enter Item's FID : ");
        fid = scanner.nextLine();
        Float p;
        System.out.println("Enter New Price ( -1 if no change ): $ ");
        p = scanner.nextFloat();
        scanner.nextLine();
        if (p != -1){
            Menu.me.modifyItem(fid, p);
            System.out.println("Price updated..");
        }
        else{
            System.out.println("No changes made..");
        }
        Integer s;
        System.out.println("Enter New Stock Quantity  ( -1 if no change ): ");
        s = scanner.nextInt();
        scanner.nextLine();
        if (s != -1){
            Menu.me.modifyItem(fid, s);
            System.out.println("Stock quantity updated..");
        }
        else{
            System.out.println("No changes made..");
        }
    }

    public void removeItem(){
        Scanner scanner = new Scanner(System.in);

        String fid;
        System.out.println("Enter Item's FID : ");
        fid = scanner.nextLine();
        Menu.me.removeItem(fid);
        System.out.println("Item removed from menu..");
    }

    // ORDER MANAGEMENT

    public void addOrder(Order o){
        orderQueue.add(o);
        orderMap.put(o.getOrderID(), o);
    }

    //assumption -> admin will handle order in the way the priority queue is showing;
    public void viewPendingOrders(){
        int i = 0;
        for (Order o : orderQueue){
            System.out.println();
            System.out.println(++i + ". ");
            o.displayOrder();
        }
        if (i == 0){
            System.out.println("No order pending..");
        }
    }

    public void updateOrderStatus(){
        Scanner scanner = new Scanner(System.in);

        String oid;
        System.out.println("Enter Order's OID : ");
        oid = scanner.nextLine();
        System.out.println("Enter C to Mark as Completed or N to Next Status : ");
        String ans = scanner.nextLine();

        if (orderMap.containsKey(oid)) {

            Order o = orderMap.get(oid);
            if (ans.equalsIgnoreCase("c")){
                o.markAsCompleted();
            }
            else {
                o.nextStatus();
            }
            if (o.getStatus().equals("Completed")) {
                completedOrder.add(o);
                orderQueue.remove(o);
                orderMap.remove(oid);
            }
            System.out.println("Order status updated to " + o.getStatus());
        }
        else{
            throw new OrderNotFoundException("Order with OID "+oid+" could not be found..");
        }
    }

    public void cancelAndRefund(){
        Scanner scanner = new Scanner(System.in);

        String oid;
        System.out.println("Enter Order's OID : ");
        oid = scanner.nextLine();
        if (orderMap.containsKey(oid)) {
            Order o = orderMap.get(oid);
            o.cancelOrder();
            cancelledOrder.add(o);
            orderQueue.remove(o);
            orderMap.remove(oid);
            System.out.println("Order cancelled..");
        }
        else{
            throw new OrderNotFoundException("Order with OID "+oid+" could not be found..");
        }
    }

    public void handleRequest(){
        Scanner scanner = new Scanner(System.in);

        String oid;
        System.out.println("Enter Order's OID : ");
        oid = scanner.nextLine();
        if (orderMap.containsKey(oid)) {
            System.out.println(orderMap.get(oid).getRequest());
        }
        else{
            throw new OrderNotFoundException("Order with OID "+oid+" could not be found..");
        }
    }

    // REPORT GENERATION

    public void generateTodayReport(){
        int totalOrders = 0;
        Set<FoodItem> mostPopular = new HashSet<>();
        int mostPopularSale = 0;
        Float totalSales = 0f;
        Map<FoodItem, Integer> foodMap = new HashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = completedOrder.size() - 1; i >= 0 ; i--){
            Order o = completedOrder.get(i);
            totalSales += o.getOrderValue();

            if (!(((o.getTimeStamp()).toLocalDate()).equals(today))){
                break;
            }
            totalOrders ++;
            for (Map.Entry<FoodItem, Integer> item : o.getOrderList().entrySet()){
                FoodItem f = item.getKey();
                int q = item.getValue();
                if (foodMap.containsKey(f)){
                    q += foodMap.get(f);
                }
                foodMap.put(f, q);

                if (q > mostPopularSale){
                    mostPopular.clear();
                    mostPopular.add(f);
                    mostPopularSale = q;
                }
                else if (q == mostPopularSale){
                    mostPopular.add(f);
                }
            }
        }

        String result = "None";
        if(mostPopular != null) {
            result = mostPopular.stream().map(FoodItem::getName).collect(Collectors.joining(", "));
        }

        System.out.println("*".repeat(40));
        System.out.println("DAILY SALES REPORT FOR " + today);
        System.out.println("Total Sales : $" + totalSales);
        System.out.println("Total Orders : " + totalOrders);
        System.out.println("Most Popular Items : " + result);
        for (Map.Entry<FoodItem, Integer> entry : foodMap.entrySet()) {
            String key = entry.getKey().getName();
            int count = entry.getValue();
            // Print the item name and then the bar
            System.out.printf("%-20s | %s (%d)\n", key, "*".repeat(count), count);
        }
        System.out.println("*".repeat(40));
    }

    public Menu getMenu(){
        return Menu.me;
    }

//    public Object getPendingOrders() {
//        return orderQueue;
//    }

    public String getPendingOrders() {
        int i = 0;
        String returnString = "PENDING ORDERS";
        for (Order o : orderQueue){
            returnString = returnString.concat("\n\n"+ ++i + ". " + o);
        }
        if ( i == 0){
            returnString = returnString.concat("\n\nNo orders pending...");
        }
        return returnString;
    }
}


