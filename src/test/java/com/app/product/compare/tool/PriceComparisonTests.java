
package com.app.product.compare.tool;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.app.product.compare.tool.data.ProductDetail;

public class PriceComparisonTests {

    // Test findLowestPriceUrl function
    @Test
    public void testFindLowestPriceUrl() {
        // Setup
        List<ProductDetail> itemDetails = new ArrayList<>();
        itemDetails.add(new ProductDetail("http://appedia.fake/api/v1/itemdata?upc=12345", 5.67, true));
        itemDetails.add(new ProductDetail("http://micromazon.lol/12345/productinfo", 4.56, true));
        itemDetails.add(new ProductDetail("http://googdit.nop/12345", 2.34, false));
        
        // Call the function
        String lowestPriceUrl = PriceComparator.findLowestPriceUrl(itemDetails);
        
        // Verify the function returns the correct URL
        assertEquals("http://micromazon.lol/12345/productinfo", lowestPriceUrl);
    }

    
}

