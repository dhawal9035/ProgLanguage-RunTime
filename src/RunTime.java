import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Dhawal Soni on 4/29/2016.
 */
public class RunTime {
    public static HashMap<String,String> symbolTable = new HashMap<>();
    public static Stack<HashMap<String,String>> activationStack = new Stack<>();
    public static Stack<String> varStack = new Stack<>();
    public static Stack<Scanner> scannerStack = new Stack<>();
    public static String fileName = "ArithmeticOperations.djpclass";


    public static void main(String args[]) throws Exception{
        Scanner sc = new Scanner(new File(fileName));

        if (sc.nextLine().equalsIgnoreCase("Program Body begins")) {
            startExecution(sc);
        }
    }
    public static void startExecution(Scanner sc) throws Exception {
        int a,b;
        int cond=0;
        String functionName = "";
        while(!sc.nextLine().equals("Function Name: main"));
        while(sc.hasNext()) {
            String s = sc.nextLine();
            String[] opCode = s.split(" ");
            switch(opCode[0]){
                case "PUSH":
                    varStack.push(opCode[1]);
                    break;
                case "STORE":
                    if (symbolTable.containsKey(opCode[1]) && !symbolTable.get(opCode[1]).getClass().equals(varStack.peek().getClass())) {
                        throw new Exception("Input mismatch");
                    } else {
                    symbolTable.put(opCode[1], String.valueOf(varStack.pop()));
                    }
                    break;
                case "LOAD":
                    if (symbolTable.containsKey(opCode[1])) {
                        varStack.push(symbolTable.get(opCode[1]));
                    } else {
                        throw new VariableNotDeclaredException("The variable has not been declared");
                    }
                    break;
                case "DIV":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    varStack.push(String.valueOf(b / a));
                    break;
                case "MUL":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    varStack.push(String.valueOf(b * a));
                    break;
                case "ADD":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    varStack.push(String.valueOf(b + a));
                    break;
                case "SUB":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    varStack.push(String.valueOf(b - a));
                    break;
                case "Print":
                    System.out.print(varStack.pop());
                    break;
                case "PrintLN":
                    System.out.println(varStack.pop());
                    break;
                case "EQ":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    if (a == b)
                        cond = 1;
                    else
                        cond = 0;
                    break;
                case "GT":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    if (b > a)
                        cond = 1;
                    else
                        cond = 0;
                    break;
                case "LT":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    if (b < a)
                        cond = 1;
                    else
                        cond = 0;
                    break;
                case "LTE":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    if (b <= a)
                        cond = 1;
                    else
                        cond = 0;
                    break;
                case "GTE":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    if (b >= a)
                        cond = 1;
                    else
                        cond = 0;
                    break;
                case "NOTEQUALS":
                    a = Integer.parseInt(varStack.pop());
                    b = Integer.parseInt(varStack.pop());
                    if (a != b)
                        cond = 1;
                    else
                        cond = 0;
                    break;
                case "if" : varStack.push(String.valueOf(cond));
                    if(!varStack.peek().equals("1")){
                        while(!(sc.nextLine()).equals("GO TO EndIF"+opCode[2]));
                    }
                    break;
                case "else" :
                    if(varStack.peek().equals("1")){
                        while(!(sc.nextLine()).equals("EndIF"+opCode[1]));
                        varStack.pop();
                    }
                    break;
                case "GOTOEndIF":
                    break;
                case "EndIF" :
                    varStack.pop();
                    break;
                case "WhileStart" :
                    break;
                case "While":
                    if(cond!=1){
                        while((sc.nextLine()).equals("WhileEnd "+opCode[1]) == false);
                    }
                    break;
                case "WhileEnd" :
                    break;
                case "GOTOWhileStart":
                    sc.close();
                    sc = new Scanner(new File(fileName));
                    while((sc.nextLine()).equals("WhileStart "+opCode[1]) == false);
                    break;
                case "Call":
                    functionName = opCode[1];
                    activationStack.push(new HashMap<String,String>(symbolTable));
                    scannerStack.push(sc);
                    symbolTable = new HashMap<String,String>();
                    sc = new Scanner(new File(fileName));
                    try{
                        while(!sc.nextLine().equals("Function Name: "+functionName));
                    }
                    catch(Exception e){
                        throw new Exception("Function is not defined");
                    }
                    String command = sc.nextLine();
                    while(!command.equals("Function Body Starts")){
                        opCode = command.split(" ");
                        command = sc.nextLine();
                        switch(opCode[0]){
                            case "STORE":
                                if ((symbolTable.containsKey(opCode[1])) && (!symbolTable.get(opCode[1]).getClass().equals(varStack.peek().getClass()))){
                                    throw new Exception("Input mismatch");
                                }
                                else {
                                    symbolTable.put(opCode[1], String.valueOf(varStack.pop()));
                                }
                                break;
                            default:
                        }
                    }
                    break;
                case "RETURN" : ;
                case "FunctionBodyEnds" :
                    symbolTable = activationStack.pop();
                    sc = scannerStack.pop();
                case "FunctionBodyEnds:Main":
                    //symbolTable.clear();
                    break;
                case "Stack":

                    break;
                case "STACKPUSH":
                    break;
                case "STACKPOP":
                    break;
                case "STACKTOP":
                    break;
                case "STACKEMPTY":
                    break;
                case "ENDS":
                    System.exit(0);
            }
        }
    }
}
