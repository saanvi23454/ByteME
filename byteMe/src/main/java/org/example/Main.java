package org.example;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        restoreHistory();
        //prepopulate();

        int choice;
        Scanner scanner  = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to \"byteMe\" Meals Ordering Platform :)");
            System.out.println("""
                    Enter
                    0 for Exit
                    1 for CLI
                    2 for GUI""");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) {
                break;
            }
            switch (choice) {
                case 1:
                    CLI.main(null);
                    break;
                case 2:
                    GUI.main(null);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        saveHistory();
        System.out.println("\n\nExiting the application...");
    }

    public static void restoreHistory(){
        //System.out.println("Restoring customer history...");
        History.loadCustomerState();
        System.out.println("Customer history restored.");

        //System.out.println("Restoring admin history...");
        History.loadAdminState();
        System.out.println("Admin history restored.");

        //System.out.println("Restoring menu history...");
        History.loadMenuState();
        System.out.println("Menu history restored.");

        System.out.println("\n");
    }

    public static void saveHistory(){
        //System.out.println("Saving customer history...");
        History.saveCustomerState();
        System.out.println("Customer history saved.");

        //System.out.println("Saving admin history...");
        History.saveAdminState();
        System.out.println("Admin history saved.");

        //System.out.println("Saving menu history...");
        History.saveMenuState();
        System.out.println("Menu history saved.");
    }

    public static void prepopulate(){
        Customer cus = new Customer("Saanvi", "snvisng", "123");
        Menu.me.addItem(new FoodItem("McFries","McD",15f, 10));
        Menu.me.addItem(new FoodItem("McBurger", "McD", 10f, 5));
        Menu.me.addItem(new FoodItem("Veg Club Sandwich", "Bread", 12f, 3));
        Menu.me.addItem(new FoodItem("Oreo Shake", "Drink", 15f, 10));
        Menu.me.addItem(new FoodItem("Garlic Naan", "Bread", 5f, 2));
    }

    /*
    public static void clean(){
        Customer.customerList.clear();
        Menu.me = new Menu();
        Admin.me = new Admin("admin@123");
        Admin.me.restoreStaticVariables();
        Admin.me.saveStaticVariables();
    }
     */
}
