package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private static final Admin admin = Admin.checkPasswordAndGet("admin@123");

    public static void main(String[] args) {
        JFrame frame = new JFrame("byteMe");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // to keep the program alive
        frame.setSize(350, 500);
        frame.setLayout(new GridLayout(2, 1));

        JButton menuButton = new JButton("View Menu");
        JButton ordersButton = new JButton("View Pending Orders");

        // For menu
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPopup(frame, "Menu", Menu.me.getMenuDetails());
            }
        });
        // For pending orders
        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPopup(frame, "Pending Orders", admin.getPendingOrders());
            }
        });

        frame.add(menuButton);
        frame.add(ordersButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("\nReturning...");
                synchronized (frame) {
                    frame.notify();
                }
            }
        });

        synchronized (frame) {
            try {
                frame.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    private static void displayPopup(JFrame parent, String title, String content) {
        // for same size as the parent frame
        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(parent.getSize());
        dialog.setLocationRelativeTo(parent);

        Color color1 = new Color(255, 253, 208);
        Color color2 = new Color(245, 245, 220);

        JTextArea textArea = setUpTextArea(content, color1);
        JScrollPane scrollPane = setUpScrollPane(textArea, color1, color2);

        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private static JTextArea setUpTextArea(String content, Color color){
        // function for setting up text area (color, padding, text, etc)
        JTextArea txAr = new JTextArea(content);

        txAr.setEditable(false); //read-only
        txAr.setLineWrap(true);
        txAr.setWrapStyleWord(true);

        txAr.setBackground(color);
        txAr.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return txAr;
    }

    private static JScrollPane setUpScrollPane(JTextArea txAr, Color color1, Color color2){
        // function for making the popup scrollable
        JScrollPane scrP = new JScrollPane(txAr);

        scrP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrP.getViewport().setBackground(color1);
        scrP.setBorder(BorderFactory.createLineBorder(color2, 2));

        return scrP;
    }
}
