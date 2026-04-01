package com.github.azcx1.AppUtils;

import com.github.azcx1.banksystem.client.ContactDetails;
import com.github.azcx1.banksystem.client.CorporateCustomer;
import com.github.azcx1.banksystem.client.IndividualClient;
import com.github.azcx1.banksystem.common.model.client.*;

import java.util.Scanner;

public class ClientService {
    private final Scanner scanner;
    public ClientService(Scanner scanner){
        this.scanner = scanner;
    }

    public IndividualClient createIndividualCustomer(){
        System.out.println("Enter pesel number:");
        Pesel pesel = new Pesel(scanner.nextLine());

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        ContactDetails contactDetails = collectContactDetails();

        return
                new IndividualClient(
                        pesel,
                        firstName,
                        lastName,
                        contactDetails
                );
    }
    public CorporateCustomer createCorporateCustomer(){
        System.out.println("Enter company name:");
        String compamnyName = scanner.nextLine();
        System.out.println("Enter company nip:");
        Nip nip = new Nip( scanner.nextLine() );
        System.out.println("Enter company regon:");
        Regon regon = new Regon( scanner.nextLine() );

        ContactDetails contactDetails = collectContactDetails();

        return
                new CorporateCustomer(
                        nip,
                        regon,
                        compamnyName,
                        contactDetails
                );
    }

    private Address collectAddressDetails(){
        System.out.println("Enter your City:");
        String city = scanner.nextLine();
        System.out.println("Enter your street:");
        String street = scanner.nextLine();
        System.out.println("Enter your postal code(xx-xxx):");
        String postalCode = scanner.nextLine();

        System.out.println("you live in:");
        System.out.println("1. flat");
        System.out.println("2. house");
        int choice = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter building number:");
        String buildingNumber = scanner.nextLine();
        String flatNumber;
        if ( choice == 1){
            System.out.println("Enter flat number:");
            flatNumber = scanner.nextLine();
            return new Address(city, street, postalCode, buildingNumber, flatNumber);
        }else {
            return new Address(city, street, postalCode, buildingNumber);
        }
    }

    private ContactDetails collectContactDetails(){

        Address address = collectAddressDetails();

        System.out.println("Enter email address:");
        EmailAddress email = new EmailAddress(scanner.nextLine());
        System.out.println("Enter phone number:");
        PhoneNumber phoneNumber = new PhoneNumber(scanner.nextLine());

        return  new ContactDetails(address, email, phoneNumber);
    }
}
