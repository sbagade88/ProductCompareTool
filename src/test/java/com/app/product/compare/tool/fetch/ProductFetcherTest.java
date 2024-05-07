package com.app.product.compare.tool.fetch;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.testng.annotations.Test;
import com.app.product.compare.tool.data.ProductDetail;

public class ProductFetcherTest {
	@Test
	public void testFetchProductDetails() throws Exception {

		List<String> merchantUrls = List.of("http://appedia.fake/api/v1/itemdata");
		String upc = "12345";

		// Mock required URL and HTTP connection
		URL mockUrl = mock(URL.class);
		HttpURLConnection mockConn = mock(HttpURLConnection.class);

		//when(mockUrl.openConnection()).thenReturn(mockConn);
		when(mockConn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
		// Mock the input
		String mockResponse = "{\"price\": \"$4.99\", \"stock\": 7}";
		InputStream mockInputStream = new ByteArrayInputStream(mockResponse.getBytes());
		when(mockConn.getInputStream()).thenReturn(mockInputStream);

		// Function call
		List<ProductDetail> productDetails = ProductFetcher.fetchProductDetails(merchantUrls, upc);
		// assert the results
		assertFalse(productDetails.isEmpty());
		ProductDetail detail = productDetails.get(0);
		assertEquals("http://merchant1.com/api/item", detail.getUrl());
		assertEquals(4.99, detail.getPrice());
		assertTrue(detail.isInStock());
	}
}