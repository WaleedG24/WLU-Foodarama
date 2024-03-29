package cp213;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores a HashMap of MenuItem objects and the quantity of each MenuItem
 * ordered. Each MenuItem may appear only once in the HashMap.
 *
 * @author  Waleed Ghufran
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-11-20
 */
public class Order implements Printable {

    /**
     * The current tax rate on menu items.
     */
    public static final BigDecimal TAX_RATE = new BigDecimal(0.13);

    // Attributes

    HashMap<MenuItem, Integer> items = new HashMap<MenuItem, Integer>();
    /**
     * Increments the quantity of a particular MenuItem in an Order with a new
     * quantity. If the MenuItem is not in the order, it is added.
     *
     * @param item     The MenuItem to purchase - the HashMap key.
     * @param quantity The number of the MenuItem to purchase - the HashMap value.
     */
    public void add(final MenuItem item, final int quantity) {

	if (items.containsKey(item) && quantity > 0)
	    items.put(item, items.get(item)+quantity);
	else if (quantity > 0)
	    items.put(item, quantity);

    }

    /**
     * Calculates the total value of all MenuItems and their quantities in the
     * HashMap.
     *
     * @return the total price for the MenuItems ordered.
     */
    public BigDecimal getSubTotal() {
	BigDecimal subTotal = null;
	double total = 0;

	for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
	    MenuItem item = entry.getKey();
	    Integer value = entry.getValue();
	    total += item.getPrice().doubleValue() * value;
	}
	
	
	subTotal = new BigDecimal(Double.toString(total));
	subTotal = subTotal.setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();

	return subTotal;
    }

    /**
     * Calculates and returns the total taxes to apply to the subtotal of all
     * MenuItems in the order. Tax rate is TAX_RATE.
     *
     * @return total taxes on all MenuItems
     */
    public BigDecimal getTaxes() {
	double tax = 0;

	BigDecimal subTotal = this.getSubTotal();
	tax = subTotal.doubleValue() *  TAX_RATE.doubleValue();
	
	BigDecimal taxes = new BigDecimal(Double.toString(tax));
	taxes = taxes.setScale(2, RoundingMode.HALF_EVEN);
	return taxes;
    }

    /**
     * Calculates and returns the total price of all MenuItems order, including tax.
     *
     * @return total price
     */
    public BigDecimal getTotal() {

	BigDecimal total = new BigDecimal(Double.toString(this.getSubTotal().doubleValue()+this.getTaxes().doubleValue()));
	total = total.setScale(2, RoundingMode.HALF_EVEN);
	return total;
    }

    /*
     * Implements the Printable interface print method. Prints lines to a Graphics2D
     * object using the drawString method. Prints the current contents of the Order.
     */
    @Override
    public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex)
	    throws PrinterException {
	int result = PAGE_EXISTS;

	if (pageIndex == 0) {
	    final Graphics2D g2d = (Graphics2D) graphics;
	    g2d.setFont(new Font("MONOSPACED", Font.PLAIN, 12));
	    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	    // Now we perform our rendering
	    final String[] lines = this.toString().split("\n");
	    int y = 100;
	    final int inc = 12;

	    for (final String line : lines) {
		g2d.drawString(line, 100, y);
		y += inc;
	    }
	} else {
	    result = NO_SUCH_PAGE;
	}
	return result;
    }

    /**
     * Returns a String version of a receipt for all the MenuItems in the order.
     */
    @Override
    public String toString() {
	String order = "";

	for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
	    MenuItem item = entry.getKey();
	    Integer value = entry.getValue();
	    System.out.println(item.getPrice());
	    order += String.format("%-15s %d @ $%5.2f = $%5.2f \n",item.getName(), value, item.getPrice(), item.getPrice().doubleValue() * value);
	}
	
	order += String.format("\n%-28s $%5.2f \n%-28s $%5.2f \n%-28s $%5.2f", "Subtotal", this.getSubTotal(),"Taxes", this.getTaxes(), "Total", this.getTotal());
	return order;
    }

    /**
     * Replaces the quantity of a particular MenuItem in an Order with a new
     * quantity. If the MenuItem is not in the order, it is added. If quantity is 0
     * or negative, the MenuItem is removed from the Order.
     *
     * @param item     The MenuItem to update
     * @param quantity The quantity to apply to item
     */
    public void update(final MenuItem item, final int quantity) {
	
	
	if (quantity <= 0)
	    items.remove(item);
	else 
	    items.put(item, quantity);

    }
}