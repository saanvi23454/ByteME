package org.example;

import java.io.Serializable;
import java.util.*;

public class Customer implements Comparable<Customer>, Serializable {
    transient static int CID = 0;
    transient static final Map<String, Customer> customerList = new HashMap<>();

    private final String name;
    private final String username;
    private final String password;
    private Boolean vip;
    private final Integer cusID;
    private final Stack<String> notifications = new Stack<>();
    private int unread;
    private final ArrayList<String> readNotifs = new ArrayList<>();
    private Cart cart;
    private final Map<String, Order> orderHistory = new HashMap<>();

    public Customer(String n, String u, String p){
        name = n;
        username = u;
        password = p;
        vip = false;
        cusID = (++CID);
        unread = 0;
        cart = new Cart(this);
        customerList.put(username, this);
    }

    public static Map<String, Customer> getCustomerList() {
        return customerList;
    }

    @Override
    public String toString() {
        return  "CusID : F" + cusID + "\n" +
                "\tName : " + name + "\n" +
                "\tUsername : " + username + "\n" +
                "\tPassword : ******\n" +
                "\tVIP : " + vip + "\n" ;
    }

    @Override
    public int compareTo(Customer c) {
        if (this.isVIP() && (!c.isVIP())){
            return -1;
        }
        else if ((!this.isVIP()) && c.isVIP()){
            return 1;
        }
        else{
            return (this.cusID - c.cusID);
        }
    }

    public static Customer checkPasswordAndGet(String username, String pwd){
        if (customerList.containsKey(username)) {
            Customer user = customerList.get(username);
            if (user.checkMyPassword(pwd)) {
                return user;
            }
            throw new InvalidLoginException("Password invalid!");
        }
        throw new InvalidLoginException("Username invalid!");
    }

    public Boolean checkMyPassword(String pwd){
        return (pwd.equals(password));
    }

    //getters and setters
    public String getName(){
        return name;
    }

    public String getUserName(){
        return username;
    }
    public String getCusID(){
        return "C"+Integer.toString(cusID);
    }

    public void addOrder(Order o){
        Menu.me.addOrder(o);
    }

    public void getNotification(String s){
        unread++;
        notifications.push(s);
    }

    public void readNotification(){
        System.out.println("You have " + unread + " unread notifications : ");
        while (unread > 0){
            String n = notifications.pop();
            readNotifs.add(n);
            System.out.println((unread--) + ". " + n );
        }
    }

    private void setVIP(){
        vip = true;
    }
    private void clearVIP(){
        vip = false;
    }

    public Boolean isVIP(){
        return vip;
    }

    //BECOME VIP
    public void becomeVIP(Boolean paid){
        if (paid){
            if (isVIP()) {
                System.out.println("(already a VIP)");
            }
            setVIP();
        }
    }


    // BROWSE MENU
    public void viewAllItems(){
        if (Menu.me!=null){
            Menu.me.viewItems();
        }
        else{
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
    }

    public void searchByKeyWord(){
        Scanner scanner = new Scanner(System.in);

        if (Menu.me!=null){
            int i = 0;
            System.out.println("Enter keyword : ");
            String k = scanner.nextLine();
            for (FoodItem f : Menu.me.getItemList().values()){
                if ((f.getName().toLowerCase()).contains(k.toLowerCase())){
                    System.out.println(++i + ". " + f);
                }
            }
            if (i == 0){
                System.out.println("No item found..");
            }
        }
        else{
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
    }

    public void filterByCategory(){
        Scanner scanner = new Scanner(System.in);

        if (Menu.me!=null){
            System.out.println("Categories : " + String.join(", ",Menu.me.getCategoryList()));
            System.out.println("Enter categories you want to see (for example : A, B, C) : ");
            String c = scanner.nextLine();
            Set<String> cat = new HashSet<>();
            Collections.addAll(cat, c.split(", "));

            int i = 0;
            for (FoodItem f : Menu.me.getItemList().values()){
                if (cat.contains(f.getCategory())){
                    System.out.println(++i + ". " + f);
                }
            }
            if (i == 0){
                System.out.println("No item found..");
            }
        }
        else{
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
    }

    public void sortByPrice(){
        Scanner scanner = new Scanner(System.in);

        if (Menu.me == null){
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
        TreeSet<FoodItem> foodList = new TreeSet<>(Menu.me.getItemList().values());

        System.out.println("Enter A for ascending order Or D for descending order of price : ");
        String c = scanner.nextLine();
        //"D" is descending, anything else is ascending
        if (c.equals("D")){
            foodList = (TreeSet<FoodItem>)foodList.descendingSet();
        }

        int i = 0;
        System.out.println("MENU");
        for (FoodItem f : foodList){
            System.out.println();
            System.out.println(++i + ". " + f);
            if (f.getStock() != 0 ){
                System.out.println("Available");
            }
            else{
                System.out.println("Out of Stock");
            }
        }
        if (i == 0){
            System.out.println("No item in menu..");
        }
    }

    // CART OPERATIONS
    public void addToCart(){
        Scanner scanner = new Scanner(System.in);

        if (Menu.me == null){
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
        System.out.println("Enter Item's FID : ");
        String fid = scanner.nextLine();
        System.out.println("Enter Quantity : ");
        Integer q = scanner.nextInt();
        scanner.nextLine();
        try{
            cart.addItem(fid,q);
        }
        catch (ItemOutOfStockException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Item added to cart..");
    }

    public void modifyQuantity(){
        Scanner scanner = new Scanner(System.in);

        if (Menu.me == null){
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
        System.out.println("Enter Item's FID : ");
        String fid = scanner.nextLine();

        if (!Menu.me.getItemList().containsKey(fid)){
            throw new ItemNotFoundException("FoodItem with FID "+fid+" not found in the menu");
        }
        FoodItem f = Menu.me.getItemList().get(fid);
        if (!cart.getItemList().containsKey(f)){
            throw new ItemNotFoundException("FoodItem with FID "+fid+" not found in the cart");
        }

        System.out.println("Enter quantity to modify ( with + or - ) : ");
        int q = scanner.nextInt();
        scanner.nextLine();
        if (q <= 0){
            cart.discardItem(Menu.me, fid, (-1)*q);
        }
        else{
            cart.addItem(fid, q);
        }
        System.out.println("Modified item quantity in cart..");
    }

    public void removeItem(){
        Scanner scanner = new Scanner(System.in);

        if (Menu.me == null){
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
        System.out.println("Enter Item's FID : ");
        String fid = scanner.nextLine();

        if (!Menu.me.getItemList().containsKey(fid)){
            throw new ItemNotFoundException("FoodItem with FID "+fid+" not found in the menu");
        }
        FoodItem f = Menu.me.getItemList().get(fid);

        if (!cart.getItemList().containsKey(f)){
            throw new ItemNotFoundException("FoodItem with FID "+fid+" not found in the cart");
        }
        cart.discardItem(fid);
        System.out.println("Removed item from cart..");
    }

    public void viewTotal(){
        cart.displayCart();
    }

    public void checkoutProcess(){
        Scanner scanner = new Scanner(System.in);

        if (cart.getTotalItems() == 0){
            System.out.println("Cart empty..");
            return;
        }
        System.out.println("Enter delivery address : ");
        String address = scanner.nextLine();
        System.out.println("Enter payment detail ( enter COD or UPI ): ");
        String payment = scanner.nextLine();

        Order o = new Order(this, cart, address, payment);
        orderHistory.put(o.getOrderID(),o);
        cart = new Cart(this);

        System.out.println("Enter (if) any special request for us : ");
        o.setRequest(scanner.nextLine());
        System.out.println("Order placed..");
    }

    // ORDER TRACKING
    public void viewOrderHistory(){
        int i = 0;
        for (Order o : orderHistory.values()){
            System.out.println(++i + ". ");
            o.displayOrder();
        }
        if (i == 0){
            System.out.println("No orders yet..");
        }
    }

    public void reOrder(){
        Scanner scanner = new Scanner(System.in);

        // adds 1 quantity of item in previous order (given in stock)
        System.out.println("Enter Order OID : ");
        String oid = scanner.nextLine();
        if (!orderHistory.containsKey(oid)){
            throw new OrderNotFoundException("Order with OID " + oid + " could not be found in your history!");
        }
        Order o = orderHistory.get(oid);
        cart = new Cart(this);
        for (FoodItem f : o.getOrderList().keySet()){
            if (f.exists() && f.isAvailable()){
                cart.addItem(f.getID(), 1);
            }
        }
        System.out.println("Cart restored from previous order");
    }

    public void cancelOrder(){
          Admin.me.cancelAndRefund();

//        System.out.println("Enter Order OID : ");
//        String oid = scanner.nextLine();
//        if (!orderHistory.containsKey(oid)){
//            throw new OrderNotFoundException("Order with OID " + oid + " could not be found in your history!");
//        }
//        Order o = orderHistory.get(oid);
//        o.cancelOrder();
//        System.out.println("Order with OID " + oid + " cancelled..");
    }

    public void viewOrderStatus(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Order OID : ");
        String oid = scanner.nextLine();
        if (!orderHistory.containsKey(oid)){
            throw new OrderNotFoundException("Order with OID " + oid + " could not be found in your history!");
        }
        Order o = orderHistory.get(oid);
        System.out.println("Order Status : " + o.getStatus());
    }

    // ITEM REVIEWS
    public void provideReview(){
        Scanner scanner = new Scanner(System.in);

        // will only provide for whatever items customer has purchased
        if (Menu.me == null){
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
        System.out.println("Enter Item's FID : ");
        String fid = scanner.nextLine();
        if (!Menu.me.getItemList().containsKey(fid)){
            throw new ItemNotFoundException("FoodItem with FID "+fid+" not found in the menu");
        }
        System.out.println("Enter rating : ");
        Integer i = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter review : ");
        String s = scanner.nextLine();
        Review r = new Review(this, i, s);
        Menu.me.getItem(fid).addReview(r);
        System.out.println("Your review has been added..");
    }

    public void viewReview(){
        Scanner scanner = new Scanner(System.in);

        if (Menu.me == null){
            throw new MenuNotSelectedException("Careful! Menu not selected..");
        }
        System.out.println("Enter Item's FID : ");
        String fid = scanner.nextLine();
        if (!Menu.me.getItemList().containsKey(fid)){
            throw new ItemNotFoundException("FoodItem with FID "+fid+" not found in the menu");
        }
        Menu.me.getItem(fid).viewReview();
    }
}
