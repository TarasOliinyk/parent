package com.lits.console;

import com.lits.model.config.DbConnection;

/**
 * Hello world!
 */
public class App {

    public static void main( String[] args ) {
        System.out.println("Application starting...");
        ConsoleApp.launch(new DbConnection());
    }
}

