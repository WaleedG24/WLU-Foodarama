package cp213;

import java.util.Scanner;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author  Waleed Ghufran
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-11-20
 */
public class Cashier {

    // Attributes
    private Menu menu = null;

    /**
     * Constructor.
     *
     * @param menu A Menu.
     */
    public Cashier(Menu menu) {
	this.menu = menu;
    }

    /**
     * Prints the menu.
     */
    private void printCommands() {
	System.out.println("\nMenu:");
	System.out.println(menu.toString());
	System.out.println("Press 0 when done.");
	System.out.println("Press any other key to see the menu again.\n");
    }

    /**
     * Asks for commands and quantities. Prints a receipt when all orders have been
     * placed.
     *
     * @return the completed Order.
     */
    public Order takeOrder() {
	System.out.println("Welcome to WLU Foodorama!");
	Order order = new Order();
	Scanner keyboard = new Scanner(System.in);
	int num = 0;
	int quantity = 0;
		
	boolean flag = true;
	//do 
	  this.printCommands();
	   
	while (flag) {
	    System.out.println("Command:");
	    
	    if (keyboard.hasNextInt()) {
		num = keyboard.nextInt();
		if (num >= 1 && num <= menu.size()) {
		    System.out.println("How many do you want?");
		    if (keyboard.hasNextInt()) {
			quantity = keyboard.nextInt();
			if (quantity > 0) {
			    order.add(menu.getItem(num -1), quantity);
			}
			else {
			    System.out.println("Not a valid Number?");
			    keyboard.next();
			}
		    }
		    else {
			System.out.println("Not a valid Number?");
			keyboard.next();
		    }
		}
		else if (num == 0) 
		    flag = false;
		else
		    this.printCommands();
		
	    }
	    else {
		System.out.println("Not a valid number!");
		keyboard.next();
	    }
	    
	    
	}
	keyboard.close();
	System.out.println(order.toString());

	return null;
    }
}