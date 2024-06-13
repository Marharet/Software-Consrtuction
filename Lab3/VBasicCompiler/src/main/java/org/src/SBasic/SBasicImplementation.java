package org.src.SBasic;

import org.src.Errors.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for starting point of interpreter
 */
public class SBasicImplementation {
    final int PROG_SIZE = 10000; // max file size
    private char[] prog;    // code programme
    private final ErrorHandling eh;
    private Interpreter interpreter;

    /**
     * Constructor of SBasicImplementation
     * @param progName - path to file
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    public SBasicImplementation(String progName) throws ErrorHandling.InterpreterException {
        char tempbuf[] = new char[PROG_SIZE];
        int size;
        eh = new ErrorHandling();

        // Loading of program
        size = loadProgram(tempbuf, progName);

        if (size != -1) {
            prog = new char[size];
            System.arraycopy(tempbuf, 0, prog, 0, size);
        }
        interpreter = new Interpreter(prog);
    }

    /**
     * Loading the programme
     * @param p - programme
     * @param fname - file name
     * @return size
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    private int loadProgram(char[] p, String fname) throws ErrorHandling.InterpreterException   {
        int size = 0;   // розмір програми
        try {
            FileReader fr = new FileReader(fname);
            BufferedReader br = new BufferedReader(fr);
            size = br.read(p, 0, PROG_SIZE);

            String program = new String(p, 0, size); // перетворення масиву чарів в рядок з урахуванням реального розміру

            program = program.replace("\r\n", "\n").replace("\r", "\n");
            program = program.replace("\n", "\r\n");

            char [] normProg = program.toCharArray();
            System.arraycopy(normProg, 0, p, 0, normProg.length); // оновлення масиву з нормалізованим текстом
            size = normProg.length;
            fr.close();
        } catch (FileNotFoundException exc) {
            eh.handleErr(ErrorKind.FILENOTFOUND);
        } catch (IOException exc) {
            eh.handleErr(ErrorKind.FILEIOERROR);
        }

        /**
         * Deletion of EOF if it is encountered
         */
        if (p[size - 1] == (char) 26) {
            size--;
        }

        return size;
    }

    /**
     * Loading programme
     * @throws ErrorHandling.InterpreterException - if error in interpreter is encountered
     */
    public void run() throws ErrorHandling.InterpreterException {
        // initialisation of parameters
        interpreter.scanLabels();       // looking for labels
        interpreter.sbInterp();         // starting programme
    }
}
