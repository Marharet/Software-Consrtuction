package org.src.Errors;

public class ErrorHandling {
    /**
     * Method for handling errors
     * @param errorKind - error type
     * @throws InterpreterException - when error is found
     */
    public void handleErr(ErrorKind errorKind) throws InterpreterException {
        String[] err = {
                "Syntax Error",
                "Unbalanced Parentheses",
                "No Expression Present",
                "Division by Zero",
                "Equal sign expected",
                "Not a variable",
                "Label table full",
                "Duplicate label",
                "Undefined label",
                "THEN expected",
                "TO expected",
                "NEXT without FOR",
                "RETURN without GOSUB",
                "Closing quotes needed",
                "File not found",
                "I/O error while loading file",
                "I/O error on INPUT statement"
        };

        throw new InterpreterException(err[errorKind.ordinal()]);
    }

    /**
     * Custom exception for interpreter project
     */
    public class InterpreterException extends Exception {
        String errStr;          // error description

        /**
         * Constructor for InterpreterException
         * @param str - error`s text
         */
        public InterpreterException(String str) {
            errStr = str;
        }

        /**
         * Method that returns text of error
         * @return error`s text
         */
        public String toString() {
            return errStr;
        }
    }
}