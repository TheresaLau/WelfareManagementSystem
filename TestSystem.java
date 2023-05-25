import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

public class TestSystem {
  public static void main(String[] args)  {
    System.out.println("===================================================");
    System.out.println("\tWELCOME TO THE WELFARE MANAGEMENT SYSTEM");
    System.out.println("===================================================");
    System.out.println("This system is made by Afflatus Squad: ");
    System.out.println("\t1. Theresa Lau Xin Yi A20EC0167");
    System.out.println("\t2. Teoh Wei Jian A20EC0229");
    System.out.println("\t3. Soh Zen Ren A20EC0152");
    System.out.println("\t4. Wong Hui Shi A20EC0169");
    System.out.println("===================================================\n");

    Scanner input = new Scanner(System.in);
    int cMMenu=0;
    Donor d=new Donor();
    do {
      try{
        System.out.print("\n");
        mainMenu();
        cMMenu = input.nextInt();
        clrscr();
        switch (cMMenu) {
          case 1:
            // Admin login
            displaySystem();
            Admin ad = new Admin("Afflatus", "Squad");
            String username, password;
            do {
              System.out.println("\n==========Admin Login==========");
              System.out.print("Username: ");
              username = input.next();
              System.out.print("Password: ");
              password = input.next();
              if ((ad.checkLogin(username, password)) == false)
                System.out.println("The Username or Password is incorrect. Please try again.");
            } while ((ad.checkLogin(username, password)) == false);
            System.out.println("\nCongrats! Login Successful.\n");
            waitsrc();
            clrscr();
            // Admin Menu
            int admenu;
            do {
              admenu = ad.adminMenu();
              switch (admenu) {
                case 1:
                  ad.getDonation(d);
                  waitsrc();
                  clrscr();
                  break;
                case 2:
                  ad.getDonation(d);
                  ad.addDonation();
                  waitsrc();
                  clrscr();
                  break;
                case 3:
                  ad.getDonation(d);
                  ad.withdrawDonation();
                  waitsrc();
                  clrscr();
                  break;
                case 4:
                  ad.addItem(d);
                  waitsrc();
                  clrscr();
                  break;
                case 5:
                  ad.viewItem();
                  waitsrc();
                  clrscr();
                  break;
                case 6:
                  break;
                default:
                  System.out.println("Invalid choice, please reinput.");
                  waitsrc();
                  clrscr();
                  break;
              }
            } while (admenu != 6);
            break;
          case 2:
            // Donor
            clrscr();
            displaySystem();
            System.out.println("===============Donor==============");
            //give maximum number of items to 10
            Scanner sc = new Scanner(System.in);
            int amount;
            // enter the donation details 
            System.out.println("-------Enter Donation Details------");
            while (true) {
                try {
                    System.out.print("Enter item amount: ");
                    amount = sc.nextInt();
                    if (amount <1){
                      System.out.println("Invalid amount number!Please input a valid number.\n");
                      continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    inputErrorMessage(sc);
                }
            }
            Items[] items = new Items[amount];
            d.donate(items);
            clrscr();
            d.viewReceipt(items);
            waitsrc();
            clrscr();
            System.out.print("\n");
            break;
          case 3:
            // Receiver
            displaySystem();
            System.out.println("===============Receiver==============");
            Receiver r = new Receiver();
            Items i = new Items();
            r.selectItems(i);
            waitsrc();
            clrscr();
            Items[] item = new Items[100];
            r.viewReceipt(item);
            waitsrc();
            clrscr();
            break;
          case 4:
            // Exit
            displaySystem();
            System.out.println("\nThank you for using our system! :)");
            System.out.println("Any enquiry please contact: ");
            System.out.println("email: afflatussquad@gmail.com\n");
            waitsrc();
            clrscr();
            break;
          default:
            System.out.println("Invalid choice, please choose again!\n");
            waitsrc();
            clrscr();
            break;
        }
      }
      catch(InputMismatchException e){
        inputErrorMessage(input);
      }
      catch(FileNotFoundException e){
        System.out.println("Fatal Error!! System cannot find the txt files named as 'Items_list.txt' or 'DonationMoney.txt'");
        System.out.println("Please manually created the txt file named as shown in the above message.");
        System.exit(-1);
      }
    } while (cMMenu != 4);
  }
// display the input error
public static void inputErrorMessage(Scanner sc) {
  System.out.println("Input error!");
  System.out.println("Please enter the correct input again!!");
  System.out.println();
  sc.nextLine();
}

  public static void displaySystem() {
    System.out.println("===================================================");
    System.out.println("\tAFFLATUS SQUAD WELFARE MANAGEMENT SYSTEM");
    System.out.println("===================================================");
  }

  public static void mainMenu() {
    System.out.println("Please choose your role:");
    System.out.println("1. Admin");
    System.out.println("2. Donor");
    System.out.println("3. Receiver");
    System.out.println("4. Exit");
    System.out.print("Enter 1 to 4: ");
  }

  public static void clrscr() {
    // Clears Screen in java
    try {
      if (System.getProperty("os.name").contains("Windows"))
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      else
        Runtime.getRuntime().exec("clear");
    } catch (IOException | InterruptedException ex) {
    }
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