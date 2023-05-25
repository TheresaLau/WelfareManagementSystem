import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Admin {
    private String userID = "";
    private String password = "";
    private Donor donation;
    private double totalDonation = 0;
    Scanner input = new Scanner(System.in);

    // Constructor Admin
    Admin(String u, String p) {
        userID = u;
        password = p;
    }

    // check valid login for admin
    public boolean checkLogin(String user, String pass) {
        if ((user.equals(userID)) && (pass.equals(password)))
            return true;
        else
            return false;
    }

    // read and print the DonationMoney.txt
    public void getDonation(Donor d) throws FileNotFoundException {
        donation = d;
        donation.readMoney();
        totalDonation = donation.totalDonation();
        System.out.printf("Total Amount of Donation = RM %.2f\n", totalDonation);
        System.out.println("Last updates: " + donation.getDate() + "\n");
    }

    // Deposit donation money by admin
    public void addDonation() throws FileNotFoundException {
        System.out.print("Enter the amount to deposit: RM");
        double add = input.nextDouble();
        totalDonation += add;
        donation.writeMoney(totalDonation);
    }

    // Withraw donation money by admin
    public void withdrawDonation() throws FileNotFoundException {
        System.out.print("Enter the amount to withdraw: RM");
        double withdraw = input.nextDouble();
        totalDonation -= withdraw;
        donation.writeMoney(totalDonation);
    }

    // Donate or Adding item
    public void addItem(Donor d) throws FileNotFoundException {
        donation = d;
        Scanner sc = new Scanner(System.in);
        int amount;
      // enter the donation details 
      System.out.println("-------Enter Donation Details------");
      while (true) {
          try {
              System.out.print("Enter item amount: ");
              amount = sc.nextInt();
              break;
          } catch (InputMismatchException e) {
              inputErrorMessage(sc);
          }
      }
      Items[] items = new Items[amount];
      d.donate(items);
      d.clearItems();
    }
// display the input error
public static void inputErrorMessage(Scanner sc) {
    System.out.println("Input error!");
    System.out.println("Please enter the correct input again!!");
    System.out.println();
    sc.nextLine();
  }
  
    // Read and print the Items_list.txt
    public void viewItem() throws FileNotFoundException {
        Scanner inputFile = new Scanner(new File("Items_list.txt"));
        while (inputFile.hasNextLine()) {
            System.out.println(inputFile.nextLine());
        }
        inputFile.close();
    }

    // User Menu for admin
    public int adminMenu() {
        int choice = 0;
        while (true) {
            try {
                System.out.println("\n-----------Admin Menu-----------");
                System.out.println("1. Check Total Amount of Donated Money");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Add Donation Item");
                System.out.println("5. View List of Items");
                System.out.println("6. Exit");
                System.out.print("\nEnter your choice: ");
                choice = input.nextInt();
                TestSystem.clrscr();
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Wrong data type.");
                System.out.println();
            } catch (Exception ex) {
                System.out.println("Exception.");
                System.out.println();
                input.nextLine();
            } 

        }

        return choice;
    }
}
