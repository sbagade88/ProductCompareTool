
package com.app.product.compare.tool.data;

public class ProductDetail {

	private String url;

	private double price;

	private boolean inStock;

	// constructor - to create an object

	public ProductDetail(String url, double price, boolean inStock) {

		this.url = url;

		this.price = price;

		this.inStock = inStock;

	}

	public String getUrl() {

		return url;

	}

	public double getPrice() {

		return price;

	}

	public boolean isInStock() {

		return inStock;

	}

}
