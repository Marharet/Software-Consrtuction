package org.src;

import org.src.Errors.ErrorHandling;
import org.src.SBasic.SBasicImplementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter path to the file: ");
        String file = reader.readLine();
        System.out.println("File path entered: " + file);

        if (file == null || file.isEmpty()) {
            System.err.println("File path cannot be null or empty.");
            return;
        }

        try {
            SBasicImplementation inter = new SBasicImplementation(file);
            System.out.println("SBasicImplementation instance created successfully.");
            inter.run();
            System.out.println("SBasicImplementation run method executed successfully.");
        } catch (ErrorHandling.InterpreterException e) {
            System.err.println("InterpreterException occurred: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("An unexpected exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
