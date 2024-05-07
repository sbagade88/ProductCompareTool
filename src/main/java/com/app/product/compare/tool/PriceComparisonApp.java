
package com.app.product.compare.tool;

import java.util.Arrays;
import java.util.List;

import com.app.product.compare.tool.data.ProductDetail;
import com.app.product.compare.tool.fetch.ProductFetcher;

public class PriceComparisonApp {

	public static void main(String[] args) {
		// the list of merchant URLs - using Array.asList method to convert the array to
		// List.
		List<String> urls = Arrays.asList("http://appedia.fake/api/v1/itemdata", "http://micromazon.lol/",
				"http://googdit.nop/");

		// upc of product
		String upc = "12345";

		// Fetch product details from each API
		List<ProductDetail> itemDetails = ProductFetcher.fetchProductDetails(urls, upc);

		// URL that has the lowest price and has the item in stock at any location
		String lowestPriceUrl = PriceComparator.findLowestPriceUrl(itemDetails);

		// Print the merchant url
		System.out
				.println("URL that has the lowest price and has the item in stock at any location: " + lowestPriceUrl);
	}
}
