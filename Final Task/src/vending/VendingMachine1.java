package vending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class VendingMachine1 implements VendingMachine {   
    private Inventory<Coin> cashInventory = new Inventory<Coin>();
    private Inventory<Item> itemInventory = new Inventory<Item>();  
    private long totalSales;
    private Item currentItem;
    private long currentBalance; 
   
    public VendingMachine1(){
        initialize();
    }
   
    private void initialize(){       
             
        for(Coin c : Coin.values()){
            cashInventory.put(c, 10);
        }
       
        for(Item i : Item.values()){
            itemInventory.put(i, 10);
        }
       
    }
   
   @Override
    public long selectItemAndGetPrice(Item item) {
        if(itemInventory.hasItem(item)){
            currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold Out, Please buy another item");
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getcash();
        cashInventory.add(coin);
    }

    @Override
    public Basket<Item, List<Coin>> collectItemAndChange() {
        Item item = collectItem();
        totalSales = totalSales + currentItem.getPrice();
       
        List<Coin> change = collectChange();
       
        return new Basket<Item, List<Coin>>(item, change);
    }
       
    private Item collectItem() throws NoChangeAvailableException,
    NotSufficientPaidException{
        if(isFullPaid()){
            if(hasEnoughChange()){
                itemInventory.deduct(currentItem);
                return currentItem;
            }           
            throw new NoChangeAvailableException("No Change Available");
           
        }
        long balanceBalance = currentItem.getPrice() - currentBalance;
        throw new NotSufficientPaidException("Price not full paid, balance : ", 
                                          balanceBalance);
    }
    
    private List<Coin> collectChange() { 
    	long changeAmount = currentBalance - currentItem.getPrice();
    	List<Coin> change = getChange(changeAmount); 
    	updateCashInventory(change);
    	currentBalance = 0; 
    	currentItem = null;
    	return change;
    	}
   
    @Override public List<Coin> refund(){ 
    	List<Coin> refund = getChange(currentBalance);
    	updateCashInventory(refund); 
    	currentBalance = 0; 
    	currentItem = null; 
    	return refund;
    	
    	}

    private boolean isFullPaid() {
    	if(currentBalance >= currentItem.getPrice()){
    		return true;
    		}
    	return false; 
    	
    }

      
    private List <Coin>getChange(long amount) throws 
    NoChangeAvailableException {
    	List <Coin>changes = Collections.emptyList();
    	int CHANGE_PENNY = 0; 
    	int CHANGE_NICKEL = 0; 
    	int CHANGE_DIME = 0; 
    	int CHANGE_QUARTER = 0; 
    	int CHANGE_DOLLAR = 0; 
    	if(amount > 0)
    	{
    	changes = new ArrayList<Coin>();
    	long balance = amount;
    	while (balance > 0) {
    	if (balance >= Coin.DOLLAR.getcash()
    	&& cashInventory.hasItem(Coin.DOLLAR) && CHANGE_DOLLAR < cashInventory.getQuantity(Coin.DOLLAR)) {
    	changes.add(Coin.DOLLAR);
    	balance = balance - Coin.DOLLAR.getcash();
    	CHANGE_DOLLAR++;
    	continue;
    	}else if (balance >= Coin.QUARTER.getcash()
    	&& cashInventory.hasItem(Coin.QUARTER) && CHANGE_QUARTER < cashInventory.getQuantity(Coin.QUARTER) ) {
    	changes.add(Coin.QUARTER);
    	balance = balance - Coin.QUARTER.getcash();
    	CHANGE_QUARTER++;
    	continue;
    	} else if (balance >= Coin.DIME.getcash()
    	&& cashInventory.hasItem(Coin.DIME) && CHANGE_DIME < cashInventory.getQuantity(Coin.DIME)) {
    	changes.add(Coin.DIME);
    	balance = balance - Coin.DIME.getcash();
    	CHANGE_DIME++;
    	continue;
    	} else if (balance >= Coin.NICKEL.getcash()
    	&& cashInventory.hasItem(Coin.NICKEL) && CHANGE_NICKEL < cashInventory.getQuantity(Coin.NICKEL)) {
    	changes.add(Coin.NICKEL);
    	balance = balance - Coin.NICKEL.getcash();
    	CHANGE_NICKEL++;
    	continue;
    	} else if (balance >= Coin.PENNY.getcash()
    	&& cashInventory.hasItem(Coin.PENNY) && CHANGE_PENNY < cashInventory.getQuantity(Coin.PENNY)) {
    	changes.add(Coin.PENNY);
    	balance = balance - Coin.PENNY.getcash();
    	CHANGE_PENNY++;
    	continue;
    	} else {
    	throw new NoChangeAvailableException(
    	"Not Sufficient Change, Please try another product");
    	}
    	}

    	}
    	return changes;
    	}

    
   
    @Override
    public void reset(){
        cashInventory.clear();
        itemInventory.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;
    } 
       
    public void printStats(){
        System.out.println("Total Sales : " + totalSales);
        System.out.println("Current Item Inventory : " + itemInventory);
        System.out.println("Current Cash Inventory : " + cashInventory);
    }   
   
  
    private boolean hasEnoughChange(){
        return hasEnoughChangeForAmount(currentBalance - currentItem.getPrice());
    }
   
    private boolean hasEnoughChangeForAmount(long amount){
        boolean hasChange = true;
        try{
            getChange(amount);
        }catch(NoChangeAvailableException nsce){
            return hasChange = false;
        }
       
        return hasChange;
    }

    private void updateCashInventory(List <Coin>change) {
        for(Coin c : change){
            cashInventory.deduct(c);
        }
    }
   
    public long getTotalSales(){
        return totalSales;
    }

}

