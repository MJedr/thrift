package edu.pja.sri.lab07.server.handlers;

import edu.pja.sri.lab07.OrderItem;
import edu.pja.sri.lab07.Product;
import edu.pja.sri.lab07.ProductCart;
import edu.pja.sri.lab07.ProductNotAvaiable;
import edu.pja.sri.lab07.db.SampleDB;

import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

public class ProductCartHandler implements ProductCart.Iface {
    private List<OrderItem> myCart = new ArrayList<>();

	@Override
	public void placeOrder() throws ProductNotAvaiable, TException {
		List<Product>productList = SampleDB.getInstance().showProductList();
	
		for (OrderItem item: myCart) {
			for (Product product: productList) {
				if (item.getProduct().getName().equals(product.getName())) {
					if(item.getAmount() > product.getAmountInStock()) {
						throw new ProductNotAvaiable("Sorry, the order can't be realized.");
					}
				}
			}
		}
		System.out.println("Transaction finished! Your order will be delivered to you soon");
		myCart.clear();
	}


	@Override
	public void addItem(Product product, int amount) throws TException {
		OrderItem item = new OrderItem(product, amount);
		myCart.add(item);
		System.out.println("product " + product.name + " added to cart");
	}


	@Override
	public void removeProduct(Product product) throws TException {
		for(OrderItem item: myCart) {
			if (item.product == product) {
				myCart.remove(item);
				System.out.println("product " + product.name + " removed from cart");
			}
		}
		
	}

	@Override
	public void changeSelectedProductAmount(Product product, int newAmount) throws TException {
		for(OrderItem item: myCart) {
			if (item.product == product) {
				item.setAmount(newAmount);
				System.out.println("order updated");
			}
		}
		
	}

	@Override
	public List<OrderItem> showProductList() throws TException {
		return myCart;
	}
}