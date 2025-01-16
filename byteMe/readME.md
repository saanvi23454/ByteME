# Byte Me! - A Canteen Management System

Byte Me! is a comprehensive food ordering and management system designed to simplify and enhance operations for a college canteen. It enables customers to browse menus, place orders, and track them, while providing admins with tools for managing orders, menus, and generating reports. The project integrates a command-line interface (CLI), a graphical user interface (GUI), and robust file handling for data persistence, along with extensive testing to ensure reliability.

---

## Features

### **Customer Features**
1. **Menu Browsing**
   - View the complete menu with prices and availability.
   - Search items by name or keyword.
   - Filter items by category (e.g., snacks, beverages, meals).
   - Sort items by price (ascending/descending).

2. **Cart Operations**
   - Add items to the cart with specified quantities.
   - Modify quantities or remove items before checkout.
   - View the total price of items in the cart.
   - Complete the order with payment and delivery details.

3. **Order Tracking**
   - Track order status (e.g., preparing, out for delivery, denied).
   - Cancel orders before processing begins.
   - View order history and reorder previous items.

4. **Item Reviews**
   - Provide reviews for purchased items with a rating between 0 and 5.
   - View reviews left by other customers.

5. **VIP Membership**
   - Upgrade to VIP for prioritized order processing.

---

### **Admin Features**
1. **Menu Management**
   - Add, update, or remove menu items.
   - Automatically update the status of pending orders if an item is removed.

2. **Order Management**
   - View and process pending orders in the order of priority (VIP first, then FIFO).
   - Update order statuses (e.g., completed, preparing).
   - Handle refunds and special requests (e.g., "extra spicy").

3. **Reports**
   - Generate daily sales reports with total sales, popular items, and order summaries.

4. **Data Persistence**
   - Save and load customer data, menu details, and order histories across sessions using I/O streams.

---

### **Technologies Used**
1. **CLI**
   - Fully functional command-line interface for all operations.
   - Real-time updates reflected in the GUI.

2. **GUI**
   - Built with Java Swing for intuitive navigation.
   - Separate screens for menu browsing and viewing pending orders.
   - GUI serves as a display-only interface, synchronized with CLI updates.

3. **File Handling**
   - User and order data stored using serialization for persistence across sessions.
   - Temporary cart storage during active sessions.

4. **JUnit Testing**
   - Test scenarios include:
     - Preventing orders for out-of-stock items.
     - Handling invalid login attempts.
     - Validating cart operations (e.g., adding/removing items, recalculating totals).

---

## How to Run

1. **Setup the Project**
   - Download the zip folder and extract all files.
   - Open the project in IntelliJ and replace the `src` directory with the extracted files.

2. **Run the Application**
   - Compile and run the `Main` class.
   - Choose between CLI and GUI interfaces at runtime.

3. **File Management**
   - Ensure read/write permissions for `.ser` files used for data persistence.

---

## Enhancements and Iterations
1. **GUI Integration**
   - Added a user-friendly interface for browsing menus and viewing orders.
   - Interactive elements include buttons for navigation and tables for data display.

2. **Advanced Order Management**
   - Incorporated VIP prioritization for orders.
   - Special request handling and refund processing.

3. **Data Synchronization**
   - Seamless data exchange between CLI and GUI through I/O streams.
   - Persistent storage of user data and order histories.

4. **Testing and Validation**
   - Extensive JUnit testing ensures the system's robustness and reliability.

---

## Future Scope
1. Enable GUI for order management and menu updates.
2. Add support for multiple canteen branches with centralized data.
3. Implement analytics for predicting popular items and optimizing stock.

--- 

Byte Me! simplifies food ordering while maintaining operational efficiency, providing a scalable solution for modern canteen management.
