
import java.util.*;
import java.io.*;

public class Main {

    static HashMap<String, Product> products = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    static int id = 101;

    // Product Class
    static class Product {
        String name, category;
        String productId;
        double price;
        int stock;

        Product(String productId, String name, String category,
                double price, int stock) {
            this.productId = productId;
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }

        void display() {
            System.out.println(productId + " | " + name + " | "
                    + category + " | " + price + " | Stock: " + stock);
        }
    }

    // Add Product
    static void addProduct() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        double price;
        while (true) {
            try {
                System.out.print("Enter Price: ");
                price = Double.parseDouble(sc.nextLine());

                if (price > 0 && Double.isFinite(price)) break;
                System.out.println("Price must be greater than 0.");
            } catch (Exception e) {
                System.out.println("Invalid price.");
            }
        }

        int stock;
        while (true) {
            try {
                System.out.print("Enter Stock: ");
                stock = Integer.parseInt(sc.nextLine());

                if (stock >= 0) break;
                System.out.println("Stock cannot be negative.");
            } catch (Exception e) {
                System.out.println("Invalid stock.");
            }
        }

        String productId = "P" + id++;
        products.put(productId,
                new Product(productId, name, category, price, stock));

        System.out.println("Product added. ID: " + productId);
    }

    // View Products
    static void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (Product p : products.values()) {
            p.display();
        }
    }

    // Search Product
    static void searchProduct() {
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Category");
        System.out.print("Enter choice: ");
        String choice = sc.nextLine();

        if (choice.equals("1")) {
            System.out.print("Enter Product ID: ");
            String searchId = sc.nextLine().trim().toUpperCase();

            Product p = products.get(searchId);

            if (p != null) p.display();
            else System.out.println("Product not found.");

        } else if (choice.equals("2")) {
            System.out.print("Enter Category: ");
            String category = sc.nextLine();

            boolean found = false;

            for (Product p : products.values()) {
                if (p.category.equalsIgnoreCase(category)) {
                    p.display();
                    found = true;
                }
            }

            if (!found) System.out.println("Category not found.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Sort Products
    static void sortProducts() {
        ArrayList<Product> list =
                new ArrayList<>(products.values());

        list.sort(Comparator.comparingDouble(p -> p.price));

        for (Product p : list) {
            p.display();
        }
    }

    // Update Stock
    static void updateStock() {
        System.out.print("Enter Product ID: ");
        String productId = sc.nextLine().trim().toUpperCase();

        Product p = products.get(productId);

        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        int stock;

        while (true) {
            try {
                System.out.print("Enter New Stock: ");
                stock = Integer.parseInt(sc.nextLine());

                if (stock >= 0) break;
                System.out.println("Stock cannot be negative.");
            } catch (Exception e) {
                System.out.println("Invalid stock.");
            }
        }

        p.stock = stock;
        System.out.println("Stock updated.");

        if (stock == 0) {
            System.out.println(productId + " is Out of Stock.");
        }
    }

    // Save Data
    static void saveData() {
        try {
            FileWriter fw = new FileWriter("products.txt");

            for (Product p : products.values()) {
                fw.write(p.productId + "|" + p.name + "|"
                        + p.category + "|" + p.price + "|"
                        + p.stock + "\n");
            }

            fw.close();
            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            System.out.println("File saving error.");
        }
    }

    // Load Data
    static void loadData() {
        try {
            File file = new File("products.txt");

            if (!file.exists()) return;

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split("\\|", -1);

                if (data.length != 5) continue;

                String productId = data[0];

                Product p = new Product(
                        productId,
                        data[1],
                        data[2],
                        Double.parseDouble(data[3]),
                        Integer.parseInt(data[4])
                );

                products.put(productId, p);

                int number = Integer.parseInt(productId.substring(1));
                if (number >= id) id = number + 1;
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("File loading error.");
        }
    }

    // Main Method
    public static void main(String[] args) {

        loadData();

        while (true) {
            System.out.println("\n===== E-COMMERCE PRODUCT MANAGEMENT =====");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product");
            System.out.println("4. Sort by Price");
            System.out.println("5. Update Stock");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    addProduct();
                    break;

                case "2":
                    viewProducts();
                    break;

                case "3":
                    searchProduct();
                    break;

                case "4":
                    sortProducts();
                    break;

                case "5":
                    updateStock();
                    break;

                case "6":
                    saveData();
                    System.out.println("Thank You!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
}
