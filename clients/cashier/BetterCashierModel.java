package clients.cashier;

import catalogue.Basket;
import catalogue.BetterBasket;
import middle.MiddleFactory;
/*BetterCashierModel extends CashierModel and overrides the makeBasket
 * Method to return BetterBasket instead*/
public class BetterCashierModel extends CashierModel {
	
	public BetterCashierModel(MiddleFactory mf) {
		super(mf);
	}
	
	@Override
	protected Basket makeBasket() {
		return new BetterBasket();
	}
}
