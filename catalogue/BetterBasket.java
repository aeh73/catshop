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
	  //loop through products to see if there is a match
	  for (Product p2: this) {
		  // checks if what we adding is already in basket
		  if(p1.getProductNum().equals(p2.getProductNum())) {
			  //if found update the quantity and return true
			  p2.setQuantity(p2.getQuantity()+p1.getQuantity());
			  return true;
		  }
	  }
	  //else it was not found using the superclass method we add a new product
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
		 //  loops through the size ofthe arraylist 
		 for(int i=0;i<this.size();i++) {
			  // checks if what we adding is already in basket
			  if(this.get(i).getProductNum().equals(pr.getProductNum())) {
				  //if found update the quantity and return true
				  this.get(i).setQuantity(this.get(i).getQuantity()-pr.getQuantity());
				  //if it was the last item then remove it from the basket
				  if(this.get(i).getQuantity() < 1) {
					  this.remove(i);
					  return true;
				  }
			  }
		  }
		return false;
	  }
}
