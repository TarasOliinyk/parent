package com.lits.console;

import com.lits.api.service.ProfileService;
import com.lits.console.component.Menu;
import com.lits.model.config.DbConnection;
import com.lits.model.dao.ProfileDao;
import com.lits.model.service.ProfileServiceImpl;

import java.util.Scanner;

public class ConsoleApp {

    public static void launch(DbConnection db) {
        ProfileDao profileDao = new ProfileDao(db.getConnection());
        ProfileService profileService = new ProfileServiceImpl(profileDao);
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu(profileService);
        System.out.println("> COMMANDS:");
        menu.listCommands();
        System.out.println("-----------------------------");
        System.out.print("Please enter your command: ");
        String command = sc.next();
        sc.close();
        System.out.println("-----------------------------");
        menu.run(Integer.parseInt(command));
    }
}
