package vending;
public class NoChangeAvailableException extends RuntimeException {
    private String message;
   
    public NoChangeAvailableException(String string) {
        this.message = string;
    }
   
    @Override
    public String getMessage(){
        return message;
    }
   
}



