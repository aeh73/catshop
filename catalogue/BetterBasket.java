package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

/**
 * Write a description of class BetterBasket here.
 * 
 * @author  Your Name 
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable, Comparator<Product>
{
  private static final long serialVersionUID = 1L;
 
  @Override
  public boolean add(Product p1) {
	  /*Improved code from 2019 lecture slides*/
	  //Search existing products for matching record
	  for (Product p2: this) {
		  if(p1.getProductNum().equals(p2.getProductNum())) {
			  //Found - update quantity and return
			  p2.setQuantity(p2.getQuantity()+p1.getQuantity());
			  return true;
		  }
	  }
	  //not found - add new product, using the superclass method
	  super.add(p1);
	  //To sort thelist we turn BetterBasket into a comparator object
	  //and giv it a compare method toproducts
	  Collections.sort(this, this);
	  return true;
  }
  public int compare(Product p1, Product p2) {
	  return p1.getProductNum().compareTo(p2.getProductNum());
  }
  
  /*Original code
   * @Override
	public boolean add(Product pr) {
        //  loops through the size ofthe arraylist 
		for (int i = 0; i < this.size(); i++) {
			// checks if what we adding is already in basket
			if (this.get(i).getProductNum().equals(pr.getProductNum())) {
				// if so quantity++ and return 
				this.get(i).setQuantity(this.get(i).getQuantity() + pr.getQuantity());
				return true;
		}
	}
	// Sorting  
	pnSort(pr);
	return true;
		
	}
	
	public void pnSort (Product prod) {
		super.add(prod);
		Collections.sort(this, (p1, p2) -> p1.getProductNum().compareTo(p2.getProductNum())); 
	}*/ 
  
	public boolean delete(Product pr) {
		
		 for(int i=0;i<this.size();i++) {
			  if(this.get(i).getProductNum().equals(pr.getProductNum())) {
				  this.get(i).setQuantity(this.get(i).getQuantity()-pr.getQuantity());
				  if(this.get(i).getQuantity() < 1) {
					  this.remove(i);
					  return true;
				  }
			  }
		  }
		return false;
	  }
}
