package edu.pja.sri.lab07.multiclient;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import edu.pja.sri.lab07.Product;
import edu.pja.sri.lab07.ProductCart;
import edu.pja.sri.lab07.ProductManager;
import edu.pja.sri.lab07.ProductNotAvaiable;
import edu.pja.sri.lab07.db.SampleDB;

public class MulitplexedClient {
	private SampleDB dbHandler = SampleDB.getInstance();
	
	  public static void main(String [] args) {
		  BasicConfigurator.configure();
			try {
			  TTransport transportManager;
			  TTransport transportCart;
			  	
			  transportManager = new TSocket("localhost", 9090);
		      transportCart = new TSocket("localhost", 9091);
		       
		      transportManager.open();
		      transportCart.open();
		       
			  TProtocol protocolManager = new  TBinaryProtocol(transportManager);
			  ProductManager.Client productManagerclient = new ProductManager.Client(protocolManager);
			  
			  TProtocol protocolCart = new  TBinaryProtocol(transportCart);
			  ProductCart.Client cartClient = new ProductCart.Client(protocolCart);

			  perform(cartClient, productManagerclient);

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
			  
		  }
		}
