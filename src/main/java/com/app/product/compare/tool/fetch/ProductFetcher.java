package com.app.product.compare.tool.fetch;

import com.app.product.compare.tool.data.ProductDetail;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductFetcher {

	public static List<ProductDetail> fetchProductDetails(List<String> urls, String upc) {

		// initialize an empty list
		List<ProductDetail> productDetails = new ArrayList<>();

		for (String url : urls) {
			try {
				// invoke the API request
				URL apiUrl = new URL(url + "?upc=" + upc);
				HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
				conn.setRequestMethod("GET");

				// Check if we get a okay (200) response from the api
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					// now read the response
					final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					// close the reader to free up
					reader.close();

					// Parse the JSON response
					JSONObject jsonResponse = new JSONObject(response.toString());

					if (url.contains("appedia.fake")) {
						// Parse appedia.fake response - first api
						parseAppediaFakeResponse(jsonResponse, url, productDetails);
					} else if (url.contains("micromazon.lol")) {
						// parse micromazon.lol response - second api
						parseMicromazonLolResponse(jsonResponse, url, productDetails);
					} else if (url.contains("googdit.nop")) {
						// parse googdit.nop response - thrid api
						parseGoogditNopResponse(jsonResponse, url, productDetails);
					}
				} else {
					System.err.println("Failed to read response from " + url + " for UPC " + upc + ". Status code: "
							+ conn.getResponseCode());
				}
			} catch (Exception e) {
				// catch exception
				System.err.println("Exception when reading response from " + url + ": " + e.getMessage());
			}
		}

		return productDetails;
	}

	// parse appedia.fake response - first api
	private static void parseAppediaFakeResponse(JSONObject jsonResponse, String url,
			List<ProductDetail> productDetails) {
		// Extract price and stock
		String priceStr = jsonResponse.getString("price");
		double price = Double.parseDouble(priceStr.replace("$", ""));
		int stock = jsonResponse.getInt("stock");

		// check the in-stock status
		boolean inStock = stock > 0;

		// Add product detail to the list
		productDetails.add(new ProductDetail(url, price, inStock));
	}

	// parse micromazon.lol response - second api
	private static void parseMicromazonLolResponse(JSONObject jsonResponse, String url,
			List<ProductDetail> productDetails) {
		// read price and availability as per the requirement
		double price = jsonResponse.getDouble("price");
		boolean available = jsonResponse.getBoolean("available");

		// Add product detail to the list
		productDetails.add(new ProductDetail(url, price, available));
	}

	// parse googdit.nop response
	private static void parseGoogditNopResponse(JSONObject jsonResponse, String url,
			List<ProductDetail> productDetails) {
		// Extract price and availability
		long p = jsonResponse.getLong("p");
		double price = p / 1_000_000.0;

		JSONArray availabilityArray = jsonResponse.getJSONArray("a");
		boolean inStock = false;

		// read availability array for inStock status
		for (int i = 0; i < availabilityArray.length(); i++) {
			JSONObject availabilityObj = availabilityArray.getJSONObject(i);
			int quantity = availabilityObj.getInt("q");
			if (quantity > 0) {
				inStock = true;
				break;
			}
		}

		// Add product detail to the list
		productDetails.add(new ProductDetail(url, price, inStock));
	}

}
