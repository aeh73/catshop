package clients.cashier;

import catalogue.Basket;
import catalogue.Product;
import clients.CaesarCipher;
import debug.DEBUG;
import middle.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Observable;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

/**
 * Implements the Model of the cashier client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class CashierModel extends Observable
{
  private enum State { process, checked }

  private State       theState   = State.process;   // Current state
  private Product     theProduct = null;            // Current product
  private Basket      theBasket  = null;            // Bought items

  private String      pn = "";                      // Product being processed

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;

  /**
   * Construct the model of the Cashier
   * @param mf The factory to create the connection objects
   */

  public CashierModel(MiddleFactory mf)
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // Database access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      DEBUG.error("CashierModel.constructor\n%s", e.getMessage() );
    }
    theState   = State.process;                  // Current state
  }
  
  /**
   * Get the Basket of products
   * @return basket
   */
  public Basket getBasket()
  {
    return theBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   */
  public void doCheck(String productNum )
  {
    String theAction = "";
    theState  = State.process;                  // State process
    pn  = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theStock.exists( pn ) )              // Stock Exists?
      {                                         // T
        Product pr = theStock.getDetails(pn);   //  Get details
        if ( pr.getQuantity() >= amount )       //  In stock?
        {                                       //  T
          theAction =                           //   Display 
            String.format( "%s : %7.2f (%2d) ", //
              pr.getDescription(),              //    description
              pr.getPrice(),                    //    price
              pr.getQuantity() );               //    quantity     
          theProduct = pr;                      //   Remember prod.
          theProduct.setQuantity( amount );     //    & quantity
          theState = State.checked;             //   OK await BUY 
        } else {                                //  F
          theAction =                           //   Not in Stock
            pr.getDescription() +" not in stock";
        }
      } else {                                  // F Stock exists
        theAction =                             //  Unknown
          "Unknown product number " + pn;       //  product no.
      }
    } catch( StockException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doCheck", e.getMessage() );
      theAction = e.getMessage();
    }
    setChanged(); notifyObservers(theAction);
  }

  /**
   * Buy the product
   */
  public void doBuy()
  {
    String theAction = "";
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theState != State.checked )          // Not checked
      {                                         //  with customer
        theAction = "Check if OK with customer first";
      } else {
        boolean stockBought =                   // Buy
          theStock.buyStock(                    //  however
            theProduct.getProductNum(),         //  may fail              
            theProduct.getQuantity() );         //
        if ( stockBought )                      // Stock bought
        {                                       // T
          makeBasketIfReq();                    //  new Basket ?
          theBasket.add( theProduct );          //  Add to bought
          theAction = "Purchased " +            //    details
                  theProduct.getDescription();  //
        } else {                                // F
          theAction = "!!! Not in stock";       //  Now no stock
        }
      }
    } catch( StockException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doBuy", e.getMessage() );
      theAction = e.getMessage();
    }
    theState = State.process;                   // All Done
    setChanged(); notifyObservers(theAction);
  }
  
  /**
   * Customer pays for the contents of the basket
   */
  public void doBought()
  {
try {
	DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");  //Will formate the date the way you like
	LocalDateTime now = LocalDateTime.now();										//Retreives the date from local computer
	//Create a new file object with a custom name which will have their order number followed by the date
	//and the .txt file extension
	File file = new File(theBasket.getOrderNum()+"["+customFormatter.format(now)+"]"+".txt");
	file.createNewFile();			
	//Once file is created we need to write the details, first we create a filewriter object which takes the file as a parameter
	//seems strange to use 3 different writer classes but it will not run without them
	FileWriter fw = new FileWriter(file, true);
	BufferedWriter bw = new BufferedWriter(fw);// the buffered writer object then takes the filewriter object as a parameter
	PrintWriter pw = new PrintWriter(fw);	   // and then the printwriter object takes the filewriter object as a parameter too
	pw.println(theBasket.getDetails());		   //finally we print the basket details using the getDetails method and close
	pw.close();
	
	
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
    String theAction = "";
    int    amount  = 1;                       //  & quantity
    try
    {
      if ( theBasket != null &&
           theBasket.size() >= 1 )            // items > 1
      {                                       // T
        theOrder.newOrder( theBasket );       //  Process order
        theBasket = null;                     //  reset
      }                                       //
      theAction = "Next customer";            // New Customer
      theState = State.process;               // All Done
      theBasket = null;
    } catch( OrderException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doCancel", e.getMessage() );
      theAction = e.getMessage();
    }
    theBasket = null;
    
    setChanged(); notifyObservers(theAction); // Notify
	String promo= JOptionPane.showInputDialog ("Check customer has promo code?");
	if(promo.equals("promo")) {
		JOptionPane.showMessageDialog(null, "Congratulations!! Customer is entitled to 10% off!!");
	}else {
		JOptionPane.showMessageDialog(null, "Sorry!! Customer is NOT entitled to 10% off!!");
	}
  }
  /*doRemove -  a method to remove a particular item from the basket and to re-add that item to stock
   * it is essentially the reverse of the doBuy and doBought methods*/  
  public void doRemove() {
	  String theAction = "";
	  try
	    {
	      if ( theBasket.isEmpty() )          // check basket
	      {                                         //  
	        theAction = "Your basket is empty!!..";
	      }else if ( theState != State.checked )       // check 
		      {                                         //  with customer
		        theAction = "Check if OK with customer first";
		      } else {
	        Product prod =  theBasket.remove();                 // call remove on the product in basket
	        theStock.addStock( prod.getProductNum(), 1); // re-add product to stock
	        theAction = "Removed " +            //    print action
	                prod.getDescription();  //
	      }
	    } catch( StockException e )
	    {
	      DEBUG.error( "%s\n%s",
	              "CashierModel.doBuy", e.getMessage() );
	      theAction = e.getMessage();
	    }
	    theState = State.process;                   // All Done
	    setChanged(); notifyObservers(theAction);
  }                                      
 
  /**
   * ask for update of view callled at start of day
   * or after system reset
   */
  public void askForUpdate()
  {
    setChanged(); notifyObservers("Welcome");
  }
  
  /**
   * make a Basket when required
   */
  private void makeBasketIfReq()
  {
    if ( theBasket == null )
    {
      try
      {
        int uon   = theOrder.uniqueNumber();     // Unique order num.
        theBasket = makeBasket();                //  basket list
        theBasket.setOrderNum( uon );            // Add an order number
      } catch ( OrderException e )
      {
        DEBUG.error( "Comms failure\n" +
                     "CashierModel.makeBasket()\n%s", e.getMessage() );
      }
    }
  }

  /**
   * return an instance of a new Basket
   * @return an instance of a new Basket
   */
  protected Basket makeBasket()
  {
    return new Basket();
  }
}
  
