package edu.pja.sri.lab07.client;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import edu.pja.sri.lab07.OrderItem;
import edu.pja.sri.lab07.Product;
import edu.pja.sri.lab07.ProductCart;
import edu.pja.sri.lab07.ProductManager;
import edu.pja.sri.lab07.ProductNotAvaiable;
import edu.pja.sri.lab07.db.SampleDB;

public class ProductCartClient {
	
	private SampleDB dbHandler = SampleDB.getInstance();
	
	  public static void main(String [] args) {
			try {
			  TTransport transportManager;
			  TTransport transportCart;

			  transportManager = new TSocket("localhost", 9090);
			  transportManager.open();

			  transportCart = new TSocket("localhost", 9091);
			  transportCart.open();

			  TProtocol protocolManager = new  TBinaryProtocol(transportManager);
			  TProtocol protocolCart = new  TBinaryProtocol(transportCart);

			  ProductManager.Client productManagerclient = new ProductManager.Client(protocolManager);
			  ProductCart.Client cartClient = new ProductCart.Client(protocolCart);

			  perform(cartClient,productManagerclient);

			  transportManager.close();
			  transportCart.close();
			} catch (TException x) {
			  x.printStackTrace();
			}
		  }

	  private static void perform(ProductCart.Client cartClient,ProductManager.Client productManagerClient) throws TException {
			  List<Product> allProducts = productManagerClient.getAllProducts();
			  System.out.println("Avaliable products:");
			  System.out.println(allProducts.toString());
			  cartClient.addItem(allProducts.get(0), 1);
			  System.out.println("The content of your basket is:");
			  System.out.println(cartClient.showProductList().toString());
			  cartClient.placeOrder();
			  System.out.println("Your order is now confirmed");
			  
			  // throws exception
			  cartClient.addItem(allProducts.get(1), 10);
			  System.out.println("The content of your basket is:");
			  System.out.println(cartClient.showProductList().toString());
			  try {
				  cartClient.placeOrder();
				  System.out.println("Your order is now confirmed");
			  }
			  catch (ProductNotAvaiable e) {
				  System.out.println("Sorry, but transaction was not finalized because of "
				  					+ "the lack of the product in the stock");
			  }
			  
		  }
		}
