package catalogue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class BetterBasketTest {
	
	@Test
	public void testMergeAddProduct() {
		BetterBasket b = new BetterBasket();
		Product p1 = new Product("0001", "Toaster", 10.00, 1);
		Product p2 = new Product("0001", "Toaster", 10.00, 1);
		Product p3 = new Product("0002", "Blender", 20.00, 1);
		Product p4 = new Product("0002", "Blender", 20.00, 2);
		
		//Test that p1 and p2 get merged
		b.add(p1);
		b.add(p2);
		assertEquals(1, b.size(), "Size Error");
		assertEquals(2, b.get(0).getQuantity(), "Quantity Error");
		
		//Test that p3 does not get merged
		b.add(p3);
		assertEquals(2, b.size(), "Size Error");
		
		//Test that p4 merges two into p3
		b.add(p4);
		assertEquals(2, b.size(), "Quantity Error");
		
	}
	
	@Test
	public void testSortAddProduct() {
		BetterBasket b = new BetterBasket();
		Product p1 = new Product("0001", "Toaster", 10.00, 1);
		Product p2 = new Product("0001", "Microwave", 50.00, 1);
		Product p3 = new Product("0002", "Gaming PC", 1000.00, 1);
		
		//Test that p1 and p2 get merged
		b.add(p3);
		b.add(p1);
		assertEquals("0001", b.get(0).getProductNum(), "Missorted");
		assertEquals("0003", b.get(1).getProductNum(), "Missorted");
		
		//Test that p2 gets inserted
		b.add(p2);
		assertEquals("0001", b.get(0).getProductNum(), "Error");
		assertEquals("0002", b.get(1).getProductNum(), "Error");
		assertEquals("0003", b.get(2).getProductNum(), "Error");
	}
	
	
}
