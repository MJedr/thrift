package edu.pja.sri.lab07.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import edu.pja.sri.lab07.ProductCart;
import edu.pja.sri.lab07.ProductManager;
import edu.pja.sri.lab07.server.handlers.ProductCartHandler;
import edu.pja.sri.lab07.server.handlers.ProductManagerHandler;

// Generated code

public class ProductCartServer {

	  public static ProductCartHandler handlerCart;
	  public static ProductManagerHandler handlerManager;

	  public static ProductCart.Processor processorCart;
	  public static ProductManager.Processor processorManager;

	  public static void main(String [] args) {
		try {
		  handlerCart = new ProductCartHandler();
		  handlerManager = new ProductManagerHandler();

		  processorCart = new ProductCart.Processor(handlerCart);
		  processorManager = new ProductManager.Processor(handlerManager);

		  Runnable simpleManager = new Runnable() {
			public void run() {
			  simpleManager(processorManager);
			}
		  };

		  Runnable simpleCart = new Runnable() {
			public void run() {
				simpleCart(processorCart);
			}
		  };

		  new Thread(simpleManager).start();
		  new Thread(simpleCart).start();
		} catch (Exception x) {
		  x.printStackTrace();
		}
	  }

	  public static void simpleManager(ProductManager.Processor productManagerProcessor) {
		try {
		  TServerTransport serverTransportManager = new TServerSocket(9090);
		  TServer serverManager = new TSimpleServer(new Args(serverTransportManager).
				  processor(productManagerProcessor));
		  System.out.println("Starting the simple ProductManager server...");
		  serverManager.serve();
		} catch (Exception e) {
		  e.printStackTrace();
		}
	  }

	public static void simpleCart(ProductCart.Processor productCartProcessor) {
		try {
			TServerTransport serverTransportCart = new TServerSocket(9091);
			TServer serverCart = 
					new TSimpleServer(new Args(serverTransportCart).
							processor(productCartProcessor));
			System.out.println("Starting the simple ProductCart server...");
			serverCart.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}