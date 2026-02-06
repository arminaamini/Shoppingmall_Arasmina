package model;

public class Customer extends User {
    protected  String userId;
    private long balance;
    
    public Customer(){super();}

    public Customer(String userId, String username, String hashedPassword , long balance){
        super( username, hashedPassword, Role.CUSTOMER);
        this.userId = userId;

        this.balance = balance;
    }

    
    public String getUserId(){
        return userId;
    }
    public long getBalance(){return balance;}
    
    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setBalance(long balance){
        if(balance < 0) {
            throw new IllegalArgumentException("negetive balance");
        }
        this.balance = balance;
    }
    public void deposite(long amount){
        if(amount < 0) {
            throw new IllegalArgumentException("negetive amount");
        }
         this.balance += amount;
    }
    public void withdraw(long amount){
        if(amount < 0) {
            throw new IllegalArgumentException("negetive amount");
        }
        if(amount > balance) {
            throw new IllegalArgumentException("not enough balance");
        }
         this.balance -= amount;
    }
}
