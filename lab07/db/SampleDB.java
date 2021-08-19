package edu.pja.sri.lab07.db;


import edu.pja.sri.lab07.OrderItem;
import edu.pja.sri.lab07.Product;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SampleDB {
    private static SampleDB instance;
    private List<Product> productsList;
    private List<OrderItem> productsAvailable;


    private SampleDB() {}


    public static synchronized SampleDB getInstance() {
        if (instance==null) {
            instance = new SampleDB();
        }
        return instance;
    }


    public List<Product> showProductList() {
        Product product1 = new Product(1, "Burrata", 99, 7.99);
        Product product2 = new Product(2,"Raclette", 2, 20.99);
        Product product3 = new Product(3, "Brillat-Savarin", 9, 19.99);
        Product product4 = new Product(4, "Frais de chevre", 3, 5.99);
		List<Product> mockedProductList = Arrays.asList(product1, product2, product3, product4);
        return mockedProductList;
    }

}