package edu.pja.sri.lab07.server.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pja.sri.lab07.OrderItem;
import edu.pja.sri.lab07.Product;
import edu.pja.sri.lab07.ProductManager;
import edu.pja.sri.lab07.db.SampleDB;

import org.apache.thrift.TException;


public class ProductManagerHandler implements ProductManager.Iface {
	@Override
	public List<Product> getAllProducts() throws TException {
		System.out.println("Products available are: ");
		return SampleDB.getInstance().showProductList();
	}

}