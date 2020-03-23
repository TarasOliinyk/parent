package com.lits.console.component;

import com.lits.api.dto.ProfileDTO;
import com.lits.api.service.ProfileService;

import java.util.Scanner;

public class Menu {
    private ProfileService profileService;

    public Menu(ProfileService profileService) {
        this.profileService = profileService;
    }

    public void listCommands() {
        System.out.println("1. Find all");
        System.out.println("2. Find by id");
        System.out.println("3. Create");
        System.out.println("4. Update");
        System.out.println("5. Delete");
    }

    public void run(int command) {
        Scanner scanner = null;
        System.out.println("\n================== Command processing ===================");

        switch (command) {
            case 1:
                profileService.findAll().forEach(e -> {
                    System.out.println("ID = " + e.getId() + ", Name = " + e.getName() + ", LastName = " + e.getLastName()
                    + ", Age = " + e.getAge());
                });
                break;
            case 2:
                System.out.print("Please enter profile id: ");
                int id = Integer.parseInt(new Scanner(System.in).next());

                try {
                    ProfileDTO profileDTO = profileService.findById(id);
                    System.out.println("ID = " + profileDTO.getId() + ", Name = " + profileDTO.getName() + ", LastName = " +
                            profileDTO.getLastName() + ", Age = " + profileDTO.getAge());
                } catch (RuntimeException e) {

                    if (e.getMessage().contains("has not been found")) {
                        System.out.println("There is no profile with such id");
                    } else {
                        throw e;
                    }
                }
                break;
            case 3:
                scanner = new Scanner(System.in);
                System.out.print("Please enter first name: ");
                String name = scanner.next();
                System.out.print("Please enter last name: ");
                String lastName = scanner.next();
                System.out.print("Please enter age: ");
                int age = Integer.parseInt(scanner.next());
                ProfileDTO profile = new ProfileDTO(name, lastName, age);
                profile = profileService.createProfile(profile);
                int newProfileId = profile.getId();

                if (newProfileId != 0) {
                    System.out.println("Profile has been successfully created. Profile id : " + newProfileId);
                } else {
                    System.out.println("Profile has not been created");
                }
                break;
            case 4:
                ProfileDTO profileToUpdate;
                scanner = new Scanner(System.in);
                System.out.print("Please enter id of the profile intended to be updated: ");
                int profileId = Integer.parseInt(scanner.next());

                try {
                    profileToUpdate = profileService.findById(profileId);
                } catch (RuntimeException e) {

                    if (e.getMessage().contains("has not been found")) {
                        System.out.println("There is no profile with id " + profileId);
                        break;
                    } else {
                        throw e;
                    }
                }
                System.out.print("Please enter new first name: ");
                String newName = scanner.next();
                System.out.print("Please enter new last name: ");
                String newLastName = scanner.next();
                System.out.print("Please enter new age: ");
                int newAge = Integer.parseInt(scanner.next());
                profileToUpdate.setName(newName);
                profileToUpdate.setLastName(newLastName);
                profileToUpdate.setAge(newAge);
                profileService.updateProfile(profileToUpdate);
                System.out.println("Profile has been successfully updated");
                break;
            case 5:
                scanner = new Scanner(System.in);
                System.out.print("Please enter id of the profile intended to be deleted: ");
                int idOfProfileToDelete = Integer.parseInt(scanner.next());

                try {
                    profileService.findById(idOfProfileToDelete);
                } catch (RuntimeException e) {

                    if (e.getMessage().contains("has not been found")) {
                        System.out.println("There is no profile with id " + idOfProfileToDelete);
                        break;
                    } else {
                        throw e;
                    }
                }
                boolean result = profileService.deleteProfile(idOfProfileToDelete);

                if (result) {
                    System.out.println("Profile has been successfully deleted");
                } else {
                    System.out.println("Profile has been not been deleted");
                }
                break;
            default:
                System.out.println("Wrong command.\n> Available commands: ");
                listCommands();
        }
        System.out.println("=========================================================");

        if (scanner != null) {
            scanner.close();
        }
    }
}
