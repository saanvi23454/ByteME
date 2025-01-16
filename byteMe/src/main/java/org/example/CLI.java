package org.example;

import java.io.IOException;
import java.util.Scanner;

public class CLI {
    private final transient static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // assume choices will be correct

        int choice;
        while (true) {
            System.out.println("Welcome to \"byteMe\" Meals Ordering Platform :)");
            System.out.println("""
                    Enter
                    0 for Back
                    1 for SignUp
                    2 for Login""");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) {
                break;
            }
            switch (choice) {
                case 1:
                    System.out.println("SIGNUP");
                    Customer me1 = signup();
                    startExperience(me1);
                    break;
                case 2:
                    try {
                        System.out.println("LOGIN");
                        System.out.println("""
                                Enter
                                1 as Customer
                                2 as Admin""");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        switch (choice) {
                            case 1:
                                Customer me2 = loginAsCustomer();
                                startExperience(me2);
                                break;
                            case 2:
                                Admin me3 = loginAsAdmin();
                                startExperience(me3);
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }
                    } catch (InvalidLoginException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            System.out.println("Back to home..");
        }
    }

    public static Customer loginAsCustomer() {
        String username, password;
        System.out.println("Username : ");
        username = scanner.nextLine();
        System.out.println("Password : ");
        password = scanner.nextLine();
        return Customer.checkPasswordAndGet(username, password);
    }

    public static Admin loginAsAdmin() {
        String password;
        System.out.println("Password : ");
        password = scanner.nextLine();
        return Admin.checkPasswordAndGet(password);
    }

    public static Customer signup() {
        String name, username, password;
        System.out.println("Name : ");
        name = scanner.nextLine();
        System.out.println("Username : ");
        username = scanner.nextLine();
        System.out.println("Password : ");
        password = scanner.nextLine();
        return new Customer(name, username, password);
    }

    public static void startExperience(Customer cus) {
        int choice;
        while (true) {
            try {
                System.out.println("""
                        Enter
                        0 to Logout
                        1 to Upgrade to VIP
                        2 to Browse Menu
                        3 to View Cart
                        4 to Track Order
                        5 to Review Items
                        6 to Check Notifications""");
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 0) {
                    break;
                }
                switch (choice) {
                    case 1:
                        System.out.println("Enter 'YES' to proceed to payment : ");
                        String ans = scanner.nextLine();
                        if (ans.equalsIgnoreCase("yes")) {
                            cus.becomeVIP(true);
                            System.out.println("Hooray, you have upgraded to VIP..");
                        } else {
                            System.out.println("Payment failed..");
                        }
                        break;
                    case 2:
                        while (true) {
                            System.out.println("""
                                    Enter
                                    0 to Return
                                    1 to View All Items
                                    2 to Search Item
                                    3 to Filter by Category
                                    4 to Sort by Price""");
                            choice = scanner.nextInt();
                            scanner.nextLine();
                            if (choice == 0) {
                                break;
                            }
                            switch (choice) {
                                case 1:
                                    cus.viewAllItems();
                                    break;
                                case 2:
                                    cus.searchByKeyWord();
                                    break;
                                case 3:
                                    cus.filterByCategory();
                                    break;
                                case 4:
                                    cus.sortByPrice();
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                        }
                        break;
                    case 3:
                        while (true) {
                            try {
                                System.out.println("""
                                        Enter
                                        0 to Return
                                        1 to Add To Cart
                                        2 to Modify Quantity
                                        3 to Remove Item
                                        4 to View Total
                                        5 to CheckOut""");
                                choice = scanner.nextInt();
                                scanner.nextLine();
                                if (choice == 0) {
                                    break;
                                }
                                switch (choice) {
                                    case 1:
                                        cus.addToCart();
                                        break;
                                    case 2:
                                        cus.modifyQuantity();
                                        break;
                                    case 3:
                                        cus.removeItem();
                                        break;
                                    case 4:
                                        cus.viewTotal();
                                        break;
                                    case 5:
                                        cus.checkoutProcess();
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            } catch (ItemNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 4:
                        while (true) {
                            try {
                                System.out.println("""
                                        Enter
                                        0 to Return
                                        1 to View Order History
                                        2 to Replicate Order
                                        3 to Cancel Order
                                        4 to View Order Status""");
                                choice = scanner.nextInt();
                                scanner.nextLine();
                                if (choice == 0) {
                                    break;
                                }
                                switch (choice) {
                                    case 1:
                                        cus.viewOrderHistory();
                                        break;
                                    case 2:
                                        cus.reOrder();
                                        break;
                                    case 3:
                                        cus.cancelOrder();
                                        break;
                                    case 4:
                                        cus.viewOrderStatus();
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            } catch (OrderNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 5:
                        while (true) {
                            try {
                                System.out.println("""
                                        Enter
                                        0 to Return
                                        1 to Provide Review
                                        2 to View Reviews""");
                                choice = scanner.nextInt();
                                scanner.nextLine();
                                if (choice == 0) {
                                    break;
                                }
                                switch (choice) {
                                    case 1:
                                        cus.provideReview();
                                        break;
                                    case 2:
                                        cus.viewReview();
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            } catch (ItemNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 6:
                        cus.readNotification();
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (MenuNotSelectedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Logging out..");
    }

    public static void startExperience(Admin adm) {
        int choice;
        while (true) {
            System.out.println("""
                    Enter
                    0 to Logout
                    1 to Manage Menu
                    2 to Manage Orders
                    3 to Generate Report""");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) {
                break;
            }
            switch (choice) {
                case 1:
                    while (true) {
                        try {
                            System.out.println("""
                                    Enter
                                    0 to Return
                                    1 to View Menu
                                    2 to Add Item
                                    3 to Update Item
                                    4 to Remove Item""");
                            choice = scanner.nextInt();
                            scanner.nextLine();
                            if (choice == 0) {
                                break;
                            }
                            switch (choice) {
                                case 1:
                                    adm.viewMenu();
                                    break;
                                case 2:
                                    adm.addFoodItem();
                                    break;
                                case 3:
                                    adm.updateFoodItem();
                                    break;
                                case 4:
                                    adm.removeItem();
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        try {
                            System.out.println("""
                                    Enter
                                    0 to Return
                                    1 to View Pending Orders
                                    2 to Update Order Status
                                    3 to Cancel and Refund
                                    4 to Handle Requests""");
                            choice = scanner.nextInt();
                            scanner.nextLine();
                            if (choice == 0) {
                                break;
                            }
                            switch (choice) {
                                case 1:
                                    adm.viewPendingOrders();
                                    break;
                                case 2:
                                    adm.updateOrderStatus();
                                    break;
                                case 3:
                                    adm.cancelAndRefund();
                                    break;
                                case 4:
                                    adm.handleRequest();
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                        } catch (OrderNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 3:
                    adm.generateTodayReport();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        System.out.println("Logging out..");
    }
}