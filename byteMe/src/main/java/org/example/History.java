package org.example;

import java.io.*;
import java.util.Map;

public class History {

    public static void saveCustomerState() {
        for (Customer customer : Customer.customerList.values()) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CustomerData/customer_" + customer.getCusID() + ".ser"))) {
                out.writeObject(customer);
            } catch (IOException e) {
                System.out.println("Error saving customer data for " + customer.getCusID() + ": " + e.getMessage());
            }
        }
    }

    public static void loadCustomerState() {
        File dir = new File("CustomerData");
        File[] files = dir.listFiles((d, name) -> name.startsWith("customer_") && name.endsWith(".ser"));

        if (files != null) {
            for (File file : files) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                    Customer customer = (Customer) in.readObject();
                    Customer.customerList.put(customer.getUserName(), customer);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error loading customer data from " + file.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    public static void saveAdminState() {
            Admin.me.saveStaticVariables();
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("AdminData/admin.ser"))) {
                out.writeObject(Admin.me);
            } catch (IOException e) {
                System.out.println("Error saving admin data : " + e.getMessage());
            }
    }

    public static void loadAdminState() {
        File dir = new File("AdminData");
        File file = new File(dir, "admin.ser");

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Admin.me = (Admin) in.readObject();
            Admin.me.restoreStaticVariables();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading admin data : " + e.getMessage());
        }
    }

    public static void saveMenuState() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("MenuData/menu.ser"))) {
            out.writeObject(Menu.me);
        } catch (IOException e) {
            System.out.println("Error saving menu data : " + e.getMessage());
        }
    }

    public static void loadMenuState() {
        File dir = new File("MenuData");
        File file = new File(dir, "menu.ser");


        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Menu.me = (Menu) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading admin data : " + e.getMessage());
        }
    }
}
