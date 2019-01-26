package vending;


public enum Coin {
    PENNY(1), NICKEL(5), DIME(10), QUARTER(25),DOLLAR(100),TWODOLLARS(200);
   
    private int cash;
   
    private Coin(int cash){
        this.cash = cash;
    }
   
    public int getcash(){
        return cash;
    }
}


