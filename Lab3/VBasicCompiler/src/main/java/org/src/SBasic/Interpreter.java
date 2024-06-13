package org.src.SBasic;

import org.src.Errors.ErrorHandling;
import org.src.Errors.ErrorKind;
import org.src.Parser.InsidesInterp;
import org.src.Parser.Lexsem;
import org.src.Parser.ParserImplementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.Stack;
import java.util.EmptyStackException;

/**
 * Class for Interpreter
 */
public class Interpreter {
    private ParserImplementation parser;
    private final ErrorHandling eh;
    // map of labels
    private TreeMap<String, Integer> labelTable;
    // stack for loop for
    private Stack<ForInfo> fStack;

    // stack for GOSUB
    private Stack<Integer> gStack;

    /**
     * Constructor for Interpreter
     * @param prog_ - data
     */
    public Interpreter(char[] prog_)
    {
        fStack = new Stack<>();
        labelTable = new TreeMap<>();
        gStack = new Stack<>();
        parser = new ParserImplementation(prog_);
        eh = new ErrorHandling();
    }

    /**
     * Returns actual element back to input stream
     */
    private void putBack() {
        if (parser.EOP.equals(parser.getTokenString())) {
            return;
        }

        for (int i = 0; i < parser.getTokenString().length(); i++) {
            parser.progIdxMinus();
        }
    }

    /**
     * Finds all labels
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    public void scanLabels() throws ErrorHandling.InterpreterException {
        Object result;

        // checking if the first element is label
        parser.getToken();

        if (parser.getTokType() == Lexsem.NUMBER) {
            labelTable.put(parser.getTokenString(), parser.getProgIdx());
        }

        parser.findEOL();

        do {
            parser.getToken();
            if (parser.getTokType() == Lexsem.NUMBER) {         // should be number of string
                result = labelTable.put(parser.getTokenString(), parser.getProgIdx());
                if (result != null) {
                    eh.handleErr(ErrorKind.DUPLABEL);
                }
            }
            // finding next string if it isn`t last
            if (parser.getKwToken() != InsidesInterp.EOL) {
                parser.findEOL();
            }
        } while (!parser.getTokenString().equals(parser.EOP));
        parser.resetProgInx();                    // dropping the index into the start of project
    }

    /**
     * Processing and assigning
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void assignment() throws ErrorHandling.InterpreterException {
        int var;
        double value;
        char vname;

        parser.getToken();                       // getting the name of value

        vname = parser.getTokenString().charAt(0);          // and using just first symbol

        if (!Character.isLetter(vname)) {    // if it isn`t symbol - then error
            eh.handleErr(ErrorKind.NOTVAR);
            return;
        }

        // changing name of value into its index in array
        var = (int) Character.toUpperCase(vname) - 'A';

        parser.getToken();                         // reading symbol of assigment "="

        if (!parser.getTokenString().equals("=")) {
            eh.handleErr(ErrorKind.EQUALEXPECTED);
            return;
        }

        value = parser.evaluate();                 // evaluating expression

        parser.setVarsElement(var, value);                 // and assign to value
    }

    /**
     * Entrance point
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    public void sbInterp() throws ErrorHandling.InterpreterException {
        // the main loop of the interpreter
        do {
            parser.getToken();       // we get a new element
            if (parser.getTokType() == Lexsem.VARIABLE) {
                putBack();      // if it is a variable, then we return it back
                assignment();   // and process assignments
            } else // if it is a keyword, then we define which one
            {
                switch (parser.getKwToken()){
                    case InsidesInterp.PRINT://parser.PRINT:
                        print();
                        break;
                    case InsidesInterp.GOTO://parser.GOTO:
                        execGoto();
                        break;
                    case InsidesInterp.IF://parser.IF:
                        execIf();
                        break;
                    case InsidesInterp.AND:
                        execAnd();
                        break;
                    case InsidesInterp.OR:
                        execOr();
                        break;
                    case InsidesInterp.FOR://parser.FOR:
                        execFor();
                        break;
                    case InsidesInterp.NEXT://parser.NEXT:
                        next();
                        break;
                    case InsidesInterp.INPUT://parser.INPUT:
                        input();
                        break;
                    case InsidesInterp.GOSUB://parser.GOSUB:
                        gosub();
                        break;
                    case InsidesInterp.RETURN://parser.RETURN:
                        greturn();
                        break;
                    case InsidesInterp.END://parser.END:
                        return;
                    default:
                        break;
                }
            }
        } while (!parser.getTokenString().equals(parser.EOP));
    }

    /**
     * Execute the PRINT statement
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void print() throws ErrorHandling.InterpreterException {
        double result;
        int len = 0, spaces;
        String lastDelim = "";

        do {
            parser.getToken();                       // the next element
            if (parser.getKwToken() == InsidesInterp.EOL || parser.getTokenString().equals(parser.EOP)) {
                break;
            }

            if (parser.getTokType() == Lexsem.QUOTEDSTR) {     // if it is a tape, then we display it on the screen
                System.out.print(parser.getTokenString());
                len += parser.getTokenString().length();
                parser.getToken();                     // and we get the next element
            } else {                            // if the expression
                putBack();
                result = parser.evaluate();            // then we calculate the expression
                System.out.print(result);       // and derive the result
                parser.getToken();                     // we get the next element

                // We calculate the lifetime of the derived result
                len += Double.toString(result).length();
            }

            lastDelim = parser.getTokenString();

            // If there is a comma, then we move to the next tabulation
            if (lastDelim.equals(",")) {
                spaces = 8 - (len % 8);        // we calculate the necessary
                // кількість пробілів
                len += spaces;               // and add to the current position
                while (spaces != 0) {
                    System.out.print(" ");
                    spaces--;
                }
            } else if (parser.getTokenString().equals(";")) {
                System.out.print(" ");
                len++;
            } else if (parser.getKwToken() != InsidesInterp.EOL && !parser.getTokenString().equals(parser.EOP)) {
                eh.handleErr(ErrorKind.SYNTAX);
            }
        } while (lastDelim.equals(";") || lastDelim.equals(","));
        if (parser.getKwToken() == InsidesInterp.EOL || parser.getTokenString().equals(parser.EOP)) {
            if (!lastDelim.equals(";") && !lastDelim.equals(",")) {
                System.out.println();
            }
        } else {
            eh.handleErr(ErrorKind.SYNTAX);
        }
    }

    /**
     * Execute INPUT
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void input() throws ErrorHandling.InterpreterException {
        int var;
        double value = 0.0;
        String str;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        parser.getToken();                         // Is there a text of the invitation to enter?

        if (parser.getTokType() == Lexsem.QUOTEDSTR) {
            System.out.print(parser.getTokenString());        // we bring it out
            parser.getToken();
            if (!parser.getTokenString().equals(",")) {       // separating comma
                eh.handleErr(ErrorKind.SYNTAX);
            }

            parser.getToken();
        } else {
            System.out.print("? ");         // the default invitation is '?'
        }

        // the variable in which the result will be written
        var = Character.toUpperCase(parser.getTokenString().charAt(0)) - 'A';

        try {
            str = br.readLine();            // we read the value
            value = Double.parseDouble(str);  // and turn it into a double
        } catch (IOException exc) {
            eh.handleErr(ErrorKind.INPUTIOERROR);
        } catch (NumberFormatException exc) {
            System.out.println("Invalid input.");   // redo error
        }

        parser.setVarsElement(var, value);                   // save the entered value
    }

    /**
     * Execute GOTO
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void execGoto() throws ErrorHandling.InterpreterException {
        parser.getToken();                                     // get a label

        Integer loc = labelTable.get(parser.getTokenString());  // find its position

        if (loc == null) // we check whether such a label
        {
            eh.handleErr(ErrorKind.UNDEFLABEL);                        // is in the program
        } else // we make the transition to the label
        {
            parser.setProgIdx(loc);
        }
    }

    /**
     * Execute IF
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void execIf() throws ErrorHandling.InterpreterException {
        double result = parser.evaluate();          // calculate the condition expression
        boolean condition = result != 0.0;

        // Processing logical operators AND and OR
        while (true) {
            parser.getToken(); // we get the next token
            if (parser.getKwToken() == InsidesInterp.THEN) {
                break;
            } else if (parser.getKwToken() == InsidesInterp.AND) {
                result = parser.evaluate();
                condition = condition && (result != 0.0);
            } else if (parser.getKwToken() == InsidesInterp.OR) {
                result = parser.evaluate();
                condition = condition || (result != 0.0);
            } else {
                eh.handleErr(ErrorKind.SYNTAX);
                return;
            }
        }

        if (!condition) {// execute the THEN part
            parser.findEOL(); // find the beginning of the next tape
        }
    }

    /**
     * Execute OR
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void execOr() throws ErrorHandling.InterpreterException {
        double left = parser.evaluate();
        parser.getToken();
        double right = parser.evaluate();
        if (left != 0.0 || right != 0.0) {
            parser.setVarsElement((int) left, 1.0);
        } else {
            parser.setVarsElement(0, 0.0);
        }
    }

    /**
     * Execute AND
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void execAnd() throws ErrorHandling.InterpreterException {
        double left = parser.evaluate();
        parser.getToken();
        double right = parser.evaluate();
        if (left != 0.0 && right != 0.0) {
            parser.setVarsElement((int) left, 1.0);
        } else {
            parser.setVarsElement((int) left, 0.0);
        }
    }

    /**
     * Ewecute loop FOR
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void execFor() throws ErrorHandling.InterpreterException {
        ForInfo stckvar = new ForInfo();
        double value;
        char vname;

        parser.getToken();                 // get a counter variable

        vname = parser.getTokenString().charAt(0);

        if (!Character.isLetter(vname)) {
            eh.handleErr(ErrorKind.NOTVAR);
            return;
        }

        // Save the index in the counter variable
        stckvar.var = Character.toUpperCase(vname) - 'A';

        parser.getToken();                 // read the assignment symbol

        if (parser.getTokenString().charAt(0) != '=') {
            eh.handleErr(ErrorKind.EQUALEXPECTED);
            return;
        }

        value = parser.evaluate();         // calculate the initialization value

        parser.setVarsElement(stckvar.var, value); // and put it in a variable

        parser.getToken();                 // read keyword TO

        if (parser.getKwToken() != InsidesInterp.TO) {
            eh.handleErr(ErrorKind.TOEXPECTED);
        }

        stckvar.target = parser.evaluate(); // get a control value

        // we check whether the cycle should be executed at least once
        if (value >= parser.getVarsElement(stckvar.var)) {
            stckvar.loc = parser.getProgIdx();      // we save the index of the beginning of the cycle
            fStack.push(stckvar);       // into stack
        } else {    // if the condition in the loop is not fulfilled - we search
            // its ending
            while (parser.getKwToken() != InsidesInterp.NEXT) {
                parser.getToken();
            }
        }
    }

    /**
     * Execute NEXT
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void next() throws ErrorHandling.InterpreterException {
        ForInfo stckvar;
        try {
            // We get information about the cycle
            stckvar = fStack.pop();
            parser.setVarsElement(stckvar.var, parser.getVarsElement(stckvar.var)+1);// we increase the counter

            // We check the cycle execution condition
            if (parser.getVarsElement(stckvar.var) > stckvar.target) {
                return;
            }
            fStack.push(stckvar);       // we return the data back to the stack
            parser.setProgIdx(stckvar.loc);      // and make the transition to the beginning of the cycle
        } catch (EmptyStackException exc) {
            eh.handleErr(ErrorKind.NEXTWITHOUTFOR);
        }

    }

    /**
     * Execute GOSUB
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void gosub() throws ErrorHandling.InterpreterException {
        parser.getToken();

        // Find a label to call
        Integer loc = labelTable.get(parser.getTokenString());

        if (loc == null) {
            eh.handleErr(ErrorKind.UNDEFLABEL);          // label not found
        } else {
            // We save the place of trust
            gStack.push(parser.getProgIdx());

            // we make the transition to the subroutine
            parser.setProgIdx(loc);
        }
    }

    /**
     * Execute RETURN
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private void greturn() throws ErrorHandling.InterpreterException {
        Integer t;
        try {
            t = gStack.pop();     // restore return index
            parser.setProgIdx(t);         // go to index
        } catch (EmptyStackException exc) {
            eh.handleErr(ErrorKind.RETURNWITHOUTGOSUB);
        }
    }
}
