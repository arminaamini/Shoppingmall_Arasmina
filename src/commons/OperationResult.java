package commons;

public class OperationResult<T> {
                       /////////////////OK\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private final boolean success;
    private final String message;
    private final T data;

    private  OperationResult(boolean success, String message,  T data){
        this.success = success;
        this.message = message;
        this.data = data;
    }

public static <T> OperationResult<T> ok(String message,T data){
    return new OperationResult<>(true, message, data);
}

public static <T> OperationResult<T> ok(T data){
    return new OperationResult<>(true, null, data);
}

public static <T> OperationResult<T> fail(String message){
    return new OperationResult<>(false, message, null);
}

public boolean getSuccess(){return  success;}
public String getMessage(){return  message;}
public T getData(){return data;}

}
