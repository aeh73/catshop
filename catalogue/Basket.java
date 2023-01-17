package catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

/**
 * A collection of products from the CatShop.
 *  used to record the products that are to be/
 *   wished to be purchased.
 * @author  Mike Smith University of Brighton
 * @version 2.2
 *
 */
public class Basket extends ArrayList<Product> implements Serializable
{
  private static final long serialVersionUID = 1;
  private int    theOrderNum = 0;          // Order number
  
  /**
   * Constructor for a basket which is
   *  used to represent a customer order/ wish list
   */
  public Basket()
  {
    theOrderNum  = 0;
  }
  
  /**
   * Set the customers unique order number
   * Valid order Numbers 1 .. N
   * @param anOrderNum A unique order number
   */
  public void setOrderNum( int anOrderNum )
  {
    theOrderNum = anOrderNum;
  }

  /**
   * Returns the customers unique order number
   * @return the customers order number
   */
  public int getOrderNum()
  {
    return theOrderNum;
  }
  
  /**
   * Add a product to the Basket.
   * Product is appended to the end of the existing products
   * in the basket.
   * @param pr A product to be added to the basket
   * @return true if successfully adds the product
   */
  // Will be in the Java doc for Basket
  @Override
  public boolean add( Product pr )
  {                              
    return super.add( pr );     // Call add in ArrayList
  }
  
  public boolean delete( Product pr )
  {                              
    return super.remove( pr );     // Call remove in ArrayList
  }
  public Product remove()
  {
    int item = super.size() - 1;
    Product prod = super.remove(item);     // Call remove in ArrayList
    return prod;
  }

  /**
   * Returns a description of the products in the basket suitable for printing.
   * @return a string description of the basket products
   */
  public String getDetails()
  {
    Locale uk = Locale.UK;
    StringBuilder sb = new StringBuilder(256);
    Formatter     fr = new Formatter(sb, uk);
    String csign = (Currency.getInstance( uk )).getSymbol();
    double total = 0.00;
    if ( theOrderNum != 0 )
      fr.format( "Order number: %03d\n", theOrderNum );
      
    if ( this.size() > 0 )
    {
      for ( Product pr: this )
      {
        int number = pr.getQuantity();
        fr.format("%-7s",       pr.getProductNum() );
        fr.format("%-14.14s ",  pr.getDescription() );
        fr.format("(%3d) ",     number );
        fr.format("%s%7.2f",    csign, pr.getPrice() * number );
        fr.format("\n");
        total += pr.getPrice() * number;
      }
      fr.format("----------------------------\n");
      fr.format("Total                       ");
      fr.format("%s%7.2f\n",    csign, getTotal());
      fr.format("Total with \npromotional code            ");
      fr.format("%s%7.2f\n",    csign, getPromo());
      fr.close();
    }
    return sb.toString();
  }
  
  /*Method that loops through theBasket arraylist and multiplies each price by their quantity and keeps a running total*/
  public double getTotal()
  {
      double total = 0.0;
	  for ( Product pr: this )
      {
        total += pr.getPrice() * pr.getQuantity();
      }    
	  return total;
  }
  
  /*Method to generate discount, as default  its 10% off with a promotional code*/
  public double getPromo()
  {
	  return (getTotal() / 100)*90 ;
  }
  
  
  
  
  
public boolean checkProduct(Product pr) {
	for(int i=0;i<this.size();i++) {
		if(this.get(i).getProductNum().equals(pr.getProductNum())) {
			return true;
		}
	}
	return false;
}



}
