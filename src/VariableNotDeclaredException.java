/**
 * Created by Dhawal Soni on 4/23/2016.
 */
public class VariableNotDeclaredException extends RuntimeException {
    String message;
    public VariableNotDeclaredException(){
        super();
    }

    public VariableNotDeclaredException(String message){
        this.message = message;
    }

    public VariableNotDeclaredException(Throwable cause){
        super(cause);
        this.message = cause.getMessage();
    }

    public VariableNotDeclaredException(String message, Throwable cause){
        super(message,cause);
    }
}
