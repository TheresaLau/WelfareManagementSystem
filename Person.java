import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Person {
    private String id;
    private String name;
    private String address;
    private String ic;
    private String teleNo;

    Person() {
        id = "";
        name = "";
        address = "";
        ic = "";
        teleNo = "";
    }

    Person(String id, String n, String a, String ic, String teleNo) {
        this.id = id;
        name = n;
        address = a;
        this.ic = ic;
        this.teleNo = teleNo;
    }

    // Filling up Information
    public void fillUpInfo() throws InputMismatchException {
        Scanner input = new Scanner(System.in);
        System.out.print("ID       : ");
        id = input.nextLine();
        System.out.print("Name     : ");
        name = input.nextLine();
        System.out.print("Address  : ");
        address = input.nextLine();
        System.out.print("IC       : ");
        ic = input.nextLine();
        System.out.print("Phone No.: ");
        teleNo = input.nextLine();
        System.out.print("\n\n");

    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getIC() {
        return ic;
    }

    public String getTeleNo() {
        return teleNo;
    }

    public static void clrscr() {
        // Clears Screen in java
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void waitsrc() {
        // System wait in java
        System.out.println("Press Enter key to continue...");
            try
            {
                System.in.read();
                System.in.read();
            }  
            catch(Exception e)
            {} 
      }
}