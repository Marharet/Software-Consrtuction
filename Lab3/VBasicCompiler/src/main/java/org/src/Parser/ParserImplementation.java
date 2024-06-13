package org.src.Parser;

import org.src.Errors.*;

/**
 * This class implements a basic parser and interpreter for a simple interpretation if Visual Basic.
 * It processes arithmetic expressions and assignment operations.
 */
public class ParserImplementation {
    // Main interpreter variables
    private char[] prog;    // Array holding the program
    private int progIdx;    // Current index in the program
    private String token;   // Holds the current token
    private Lexsem tokType; // Holds the type of the current token
    private InsidesInterp kwToken; // Internal representation of a keyword
    private double vars[];  // Array of variables
    public final String EOP = "\0"; // End of program marker

    ErrorHandling eh;

    // Codes for binary operators like <=.
    final char LE = 1;
    final char GE = 2;
    final char NE = 3;

    /**
     * Constructor for ParserImplementation.
     *
     * @param prog_ The program to be compiled.
     */
    public ParserImplementation(char[] prog_) {
        prog = prog_;
        vars = new double[26];
        progIdx = 0;
        eh = new ErrorHandling();
    }

    // Comparison operators
    char rops[] = {
            GE, NE, LE, '<', '>', '=', 0
    };

    // String of relation operators
    String relops = new String(rops);

    // Keyword table with their internal representation
    Keyword kwTable[] = {
            new Keyword("print", InsidesInterp.PRINT),
            new Keyword("input", InsidesInterp.INPUT),
            new Keyword("if", InsidesInterp.IF),
            new Keyword("or", InsidesInterp.OR),
            new Keyword("and", InsidesInterp.AND),
            new Keyword("then", InsidesInterp.THEN),
            new Keyword("goto", InsidesInterp.GOTO),
            new Keyword("for", InsidesInterp.FOR),
            new Keyword("next", InsidesInterp.NEXT),
            new Keyword("to", InsidesInterp.TO),
            new Keyword("gosub", InsidesInterp.GOSUB),
            new Keyword("return", InsidesInterp.RETURN),
            new Keyword("end", InsidesInterp.END)
    };

    /**
     * Entry point for evaluating the program.
     *
     * @return The result of evaluating the expression.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    public double evaluate() throws ErrorHandling.InterpreterException {
        double result = 0.0;

        getToken();

        if (token.equals(EOP)) {
            eh.handleErr(ErrorKind.NOEXP); // No expression found
        }

        // Start analyzing the expression
        result = evalExp1();

        putBack();

        return result;
    }

    /**
     * Process assignment operator.
     *
     * @return The result of the evaluation.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    private double evalExp1() throws ErrorHandling.InterpreterException {
        double l_temp, r_temp, result;
        char op;

        result = evalExp2();

        if (token.equals(EOP)) { // End of program reached
            return result;       // Exit the method
        }

        op = token.charAt(0);

        if (isRelop(op)) {
            l_temp = result;
            getToken();
            r_temp = evalExp1();
            switch (op) { // Perform comparison operations
                case '<':
                    result = (l_temp < r_temp) ? 1.0 : 0.0;
                    break;
                case LE:
                    result = (l_temp <= r_temp) ? 1.0 : 0.0;
                    break;
                case '>':
                    result = (l_temp > r_temp) ? 1.0 : 0.0;
                    break;
                case GE:
                    result = (l_temp >= r_temp) ? 1.0 : 0.0;
                    break;
                case '=':
                    result = (l_temp == r_temp) ? 1.0 : 0.0;
                    break;
                case NE:
                    result = (l_temp != r_temp) ? 1.0 : 0.0;
                    break;
            }
        }

        return result;
    }

    /**
     * Addition and subtraction.
     *
     * @return The result of the evaluation.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    private double evalExp2() throws ErrorHandling.InterpreterException {
        char op;
        double result;
        double partialResult;

        result = evalExp3();

        while ((op = token.charAt(0)) == '+' || op == '-') {
            getToken();
            partialResult = evalExp3();
            result = switch (op) {
                case '-' -> result - partialResult;
                case '+' -> result + partialResult;
                default -> result;
            };
        }

        return result;
    }

    /**
     * Multiplication and division.
     *
     * @return The result of the evaluation.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    private double evalExp3() throws ErrorHandling.InterpreterException {
        char op;
        double result;
        double partialResult;

        result = evalExp4();

        while ((op = token.charAt(0)) == '*' || op == '/' || op == '%') {
            getToken();
            partialResult = evalExp4();

            result = switch (op) {
                case '*' -> result * partialResult;
                case '/' -> {
                    if (partialResult == 0.0) {
                        eh.handleErr(ErrorKind.DIVBYZERO);
                    }
                    yield result / partialResult;
                }
                case '%' -> {
                    if (partialResult == 0.0) {
                        eh.handleErr(ErrorKind.DIVBYZERO);
                    }
                    yield result % partialResult;
                }
                default -> result;
            };
        }
        return result;
    }

    /**
     * Exponentiation.
     *
     * @return The result of the evaluation.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    private double evalExp4() throws ErrorHandling.InterpreterException {
        double result;
        double partialResult;
        double ex;
        int t;

        result = evalExp5();

        if (token.equals("^")) {
            getToken();
            partialResult = evalExp4();

            ex = result;

            if (partialResult == 0.0) {
                result = 1.0;
            } else {
                for (t = (int) partialResult - 1; t > 0; t--) {
                    result = result * ex;
                }
            }
        }

        return result;
    }

    /**
     * Unary plus and minus.
     *
     * @return The result of the evaluation.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    private double evalExp5() throws ErrorHandling.InterpreterException {
        double result;
        String op;

        op = "";

        if ((tokType == Lexsem.DELIMITER) && token.equals("+") || token.equals("-")) {
            op = token;
            getToken();
        }

        result = evalExp6();

        if (op.equals("-")) {
            result = -result;
        }

        return result;
    }

    /**
     * Handle parentheses.
     *
     * @return The result of the evaluation.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    private double evalExp6() throws ErrorHandling.InterpreterException {
        double result;

        if (token.equals("(")) {
            getToken();
            result = evalExp2();

            if (!token.equals(")")) {
                eh.handleErr(ErrorKind.UNBALPARENS);
            }

            getToken();
        } else {
            result = atom();
        }

        return result;
    }

    /**
     * Get the value of a number or variable.
     *
     * @return The result of the evaluation.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    private double atom() throws ErrorHandling.InterpreterException {
        double result = 0.0;

        switch (tokType) {
            case NUMBER:
                try {
                    result = Double.parseDouble(token);
                } catch (NumberFormatException exc) {
                    eh.handleErr(ErrorKind.SYNTAX);
                }
                getToken();
                break;
            case VARIABLE:
                result = findVar(token);
                getToken();
                break;
            default:
                eh.handleErr(ErrorKind.SYNTAX);
                break;
        }

        return result;
    }

    /**
     * Return the value of a variable.
     *
     * @param vname The name of the variable.
     * @return The value of the variable.
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    private double findVar(String vname) throws ErrorHandling.InterpreterException {
        if (!Character.isLetter(vname.charAt(0))) {
            eh.handleErr(ErrorKind.SYNTAX);
            return 0.0;
        }

        return vars[Character.toUpperCase(vname.charAt(0)) - 'A'];
    }

    /**
     * Put the current token back into the input stream.
     */
    private void putBack() {
        if (EOP.equals(token)) {
            return;
        }

        for (int i = 0; i < token.length(); i++) {
            progIdx--;
        }
    }

    /**
     * Get the next token.
     *
     * @throws ErrorHandling.InterpreterException If an error occurs during interpretation.
     */
    public void getToken() throws ErrorHandling.InterpreterException {
        char ch;

        tokType = Lexsem.NONE;
        token = "";
        kwToken = InsidesInterp.UNKNCOM;

        if (progIdx == prog.length) { // End of program?
            token = EOP;
            return;
        }

        // Skip spaces
        while (progIdx < prog.length && isSpaceOrTab(prog[progIdx])) {
            progIdx++;
        }

        // End of program
        if (progIdx == prog.length) {
            token = EOP;
            tokType = Lexsem.DELIMITER;
            return;
        }

        if (prog[progIdx] == '\r') { // Handle '\r' character
            progIdx += 2;
            kwToken = InsidesInterp.EOL;
            token = "\r\n";
            return;
        }

        // Handle relation operations
        ch = prog[progIdx];

        if (ch == '<' || ch == '>') {
            if (progIdx + 1 == prog.length) {
                eh.handleErr(ErrorKind.SYNTAX);
            }

            switch (ch) {
                case '<':
                    if (prog[progIdx + 1] == '>') {
                        progIdx += 2;
                        token = String.valueOf(NE);
                    } else if (prog[progIdx + 1] == '=') {
                        progIdx += 2;
                        token = String.valueOf(LE);
                    } else {
                        progIdx++;
                        token = "<";
                    }
                    break;
                case '>':
                    if (prog[progIdx + 1] == '=') {
                        progIdx += 2;
                        token = String.valueOf(GE);
                    } else {
                        progIdx++;
                        token = ">";
                    }
                    break;
            }
            tokType = Lexsem.DELIMITER;
            return;
        }

        if (isDelim(prog[progIdx])) { // Operator
            token += prog[progIdx];
            progIdx++;
            tokType = Lexsem.DELIMITER;
        } else if (Character.isLetter(prog[progIdx])) { // Keyword
            while (!isDelim(prog[progIdx])) {
                token += prog[progIdx];
                progIdx++;
                if (progIdx >= prog.length) {
                    break;
                }
            }
            kwToken = lookUp(token);
            if (kwToken == InsidesInterp.UNKNCOM) {
                tokType = Lexsem.VARIABLE;
            } else {
                tokType = Lexsem.COMMAND;
            }
        } else if (Character.isDigit(prog[progIdx])) { // Number
            while (!isDelim(prog[progIdx])) {
                token += prog[progIdx];
                progIdx++;
                if (progIdx >= prog.length) {
                    break;
                }
            }
            tokType = Lexsem.NUMBER;
        } else if (prog[progIdx] == '"') { // Quoted string
            progIdx++;
            ch = prog[progIdx];
            while (ch != '"' && ch != '\r') {
                token += ch;
                progIdx++;
                ch = prog[progIdx];
            }
            if (ch == '\r') {
                eh.handleErr(ErrorKind.MISSINGQUOTE);
            }
            progIdx++;
            tokType = Lexsem.QUOTEDSTR;
        } else { // Unknown character
            token = EOP;
            return;
        }
    }

    /**
     * Checks if the character is a delimiter.
     *
     * @param c The character to check.
     * @return True if the character is a delimiter, otherwise false.
     */
    private boolean isDelim(char c) {
        return " \r,;<>+-/*%^=()".indexOf(c) != -1;
    }

    /**
     * Checks if the character is a space or a tab.
     *
     * @param c The character to check.
     * @return True if the character is a space or tab, otherwise false.
     */
    boolean isSpaceOrTab(char c) {
        return c == ' ' || c == '\t';
    }

    /**
     * Checks if the character is a comparison operator.
     *
     * @param c The character to check.
     * @return True if the character is a comparison operator, otherwise false.
     */
    boolean isRelop(char c) {
        return relops.indexOf(c) != -1;
    }

    /**
     * Find the internal representation of a keyword.
     *
     * @param s The keyword to look up.
     * @return The internal representation of the keyword.
     */
    private InsidesInterp lookUp(String s) {
        int i;

        // Convert to lowercase
        s = s.toLowerCase();

        // Check if the element is in the table
        for (i = 0; i < kwTable.length; i++) {
            if (kwTable[i].keyword.equals(s)) {
                return kwTable[i].keywordTok;
            }
        }

        return InsidesInterp.UNKNCOM; // Unknown word
    }

    /**
     * Find the start of the next line.
     */
    public void findEOL() {
        while (progIdx < prog.length && prog[progIdx] != '\n') {
            ++progIdx;
        }
        if (progIdx < prog.length) {
            progIdx++;
        }
    }

    public Lexsem getTokType() {
        return tokType;
    }

    public InsidesInterp getKwToken() {
        return kwToken;
    }

    public String getTokenString() {
        return token;
    }

    public int getProgIdx() {
        return progIdx;
    }

    public void setProgIdx(int idx) {
        progIdx = idx;
    }

    public void progIdxMinus() {
        progIdx--;
    }

    public void resetProgInx() {
        progIdx = 0;
    }

    public void setVarsElement(int index, double value) {
        vars[index] = value;
    }

    public double getVarsElement(int index) {
        return vars[index];
    }

    public void progIdxPlus() {
        progIdx++;
    }
}
