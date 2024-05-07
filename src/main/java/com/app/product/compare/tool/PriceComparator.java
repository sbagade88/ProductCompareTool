package com.app.product.compare.tool;

import java.util.List;

import com.app.product.compare.tool.data.ProductDetail;

public class PriceComparator {
	public static String findLowestPriceUrl(List<ProductDetail> productDetails) {
		// initializing the variable
		String lowestPriceUrl = null;
		double lowestPrice = Double.MAX_VALUE;

		for (ProductDetail product : productDetails) {
			// logic - first check if an item is in stock and its price should be lowerPrice
			if (product.isInStock() && product.getPrice() < lowestPrice) {
				lowestPrice = product.getPrice();
				lowestPriceUrl = product.getUrl();
			}

		}
		return lowestPriceUrl;

	}

}
