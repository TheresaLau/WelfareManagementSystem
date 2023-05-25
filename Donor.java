import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.text.SimpleDateFormat;

public class Donor extends Person implements userInterface {
    private ArrayList<Items> donatedItems = new ArrayList<Items>();
    private int amount = 0;
    private double donationMoney;
    private String date;
    // Constructor
    Donor() {}

    // View Donation Receipt
    public void viewReceipt(Items[] items) {
        if(items.length != donatedItems.size())
        items = new Items[donatedItems.size()];
        System.out.println("------DONATION RECEIPT-------");
        System.out.println("---Welfare Management System---");
        System.out.printf("Donated by: %s ( %s )\n", getName(), getID());
        System.out.printf("Street Address: %s \n", getAddress());
        System.out.printf("Phone number: %s \n", getTeleNo());
        System.out.printf("Number of item donated: %s \n", items.length);
        int index = 0;
        System.out.println();
        if(items.length == 0){
            System.out.println("No item has been donated.");
            return;
        }
        System.out.printf("%-2s %-15s %-5s %-5s\n", "No", "Name", "Quantity", "Category");
        for (Items item : donatedItems) {
            if (item.getItemName().equals("Money"))
                System.out.printf("%-2d %-15s %-8.2f %-6s\n", ++index, item.getItemName(), item.getItemMoney(),
                        item.getItemCategory());
            else
                System.out.printf("%-2d %-15s %-8d %-6s\n", ++index, item.getItemName(), item.getItemQuantity(),
                        item.getItemCategory());
        }
        System.out.println(" \nThank you for making donation with our system !!! ");
        clearItems();
    }
    public void clearItems() {
        donatedItems.clear();
    }
    // Donate item or money
    public void donate(Items[] items) throws FileNotFoundException {
            String name;
            int quantity, category;
            // fill up donor info first!
            System.out.println("Please enter your information:");
            fillUpInfo();
            clrscr();
            Scanner sc = new Scanner(System.in);
            System.out.println();
            // To count the number of line in previous file
            Scanner cfile = new Scanner(new File("Items_list.txt"));
            int count = 0;
            if (cfile.hasNextLine())
                cfile.nextLine();
            while (cfile.hasNextLine()) {
                cfile.nextLine();
                count++;
            }
            cfile.close();

            // create the input file object
            Scanner file = new Scanner(new File("Items_list.txt"));
            // create all the stored array object for storing back the old data
            int[] oldNum = new int[count];
            String[] oldName = new String[count];
            int[] oldQuantity = new int[count];
            int[] oldCategory = new int[count];
            int num = 0;
            if (count >= 1) {
                file.nextLine();
                for (int j = 0; file.hasNext(); j++) {
                    oldNum[j] = file.nextInt();
                    oldName[j] = file.next();
                    String f = file.next();
                    if (f.matches("[0-9]+")) {
                    } else {
                        oldName[j] += " " + f;
                        f = file.next();
                    }
                    oldQuantity[j] = Integer.parseInt(f);
                    oldCategory[j] = file.nextInt();
                }
            }
            file.close();

            // Create outputFile variable for writing the data into the output file
            PrintWriter outputFile = new PrintWriter("Items_list.txt");
            outputFile.println("No.   Name             Quantity    Category");
            for (int i = 0; i < items.length; i++) {
                System.out.println("Item " + (i + 1));
                Items it = new Items();
                category = it.searchItems("donor");
                while (true) {
                    try {
                        if (category == 1) {
                            System.out.println("Category: Money");
                            System.out.print("Enter the amount= RM");
                            double money = sc.nextDouble();
                            Money m = new Money(money);
                            Items item = new Items(m);
                            donatedItems.add(item);
                        } else {
                            System.out.print("Enter item name: ");
                            name = sc.nextLine();
                            System.out.println();
                            System.out.print("Enter item quantity: ");
                            quantity = sc.nextInt();
                            sc.nextLine();
                            Items item = new Items(name, quantity, category);
                            donatedItems.add(item);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        inputErrorMessage(sc);
                    }
                }
                System.out.print("\n");
                clrscr();
            }
            itemCheck();
            // to check the new items with the existing items and update the new quantity of
            // the existing items
            for (int i = 0; i < donatedItems.size(); i++) {
                for (int k = 0; k < oldNum.length; k++) {
                    if ((donatedItems.get(i).getItemName().equals(oldName[k]))) {
                        oldQuantity[k] = (donatedItems.get(i).getItemQuantity() + oldQuantity[k]);
                    }
                }
            }

            // to restore back the old information and update the quantity of the existing items
            for (int i = 0; i < oldNum.length; i++) {
                outputFile.format("%-6d", ++num);
                outputFile.format("%-17s", oldName[i]);
                outputFile.format("%-12d", oldQuantity[i]);
                outputFile.format("%-18s", oldCategory[i]);
                outputFile.println();
            }

            // to output the new items in the file
            for (int i = 0; i < donatedItems.size(); i++) {
                boolean check = false;
                for (int k = 0; k < oldNum.length; k++) {
                    if ((donatedItems.get(i).getItemName().equals(oldName[k]))) {
                        check = true;
                    }
                }
                if (donatedItems.get(i).getItemName().equals("Money")) {
                    readMoney();
                    donationMoney += donatedItems.get(i).getItemMoney();
                    writeMoney(donationMoney);
                    continue;
                }
                if (check == false) {
                    outputFile.format("%-6d", ++num);
                    outputFile.format("%-17s", donatedItems.get(i).getItemName());
                    outputFile.format("%-12d", donatedItems.get(i).getItemQuantity());
                    outputFile.format("%-18s", donatedItems.get(i).getItemCategoryNum());
                    outputFile.println();
                }
            }
            outputFile.close();
            this.amount = donatedItems.size();
    }

    // display the input error
    public void inputErrorMessage(Scanner sc) {
        System.out.println("Input error!");
        System.out.println("Please enter the correct input again!!");
        System.out.println();
        sc.nextLine();
    }

    // Checking item after choose to donate
    public void itemCheck() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Items> rewritedItems = new ArrayList<Items>();
        String ans = "";
        int modifyNum = 0; // modify menu function number
        while (!ans.equals("N")) {
            while (true) {
                confirmation();
                System.out.print("Would you like to modify your donated item list?(Y/N): ");
                ans = sc.next().toUpperCase();
                clrscr();
                if (ans.equals("Y")) {
                    System.out.println();
                    System.out.println("--Modify Menu--");
                    System.out.println("1. Adding Item");
                    System.out.println("2. Remove Item");
                    System.out.println("3. Modify Item");
                    
                    while ((modifyNum < 1) || (modifyNum > 3)) {
                        try{
                            System.out.print("Please choose your function by typing the number(1/2/3): ");
                            modifyNum = sc.nextInt();
                            if ((modifyNum < 1) || (modifyNum > 3)) {
                                inputErrorMessage(sc);
                                System.out.println("The number should not less than 1 or greater than 3!");
                            }
                            System.out.println();
                        }
                        catch(InputMismatchException e){
                            inputErrorMessage(sc);
                        }     
                    }
                    clrscr();
                    if (modifyNum == 1) {
                        modifyNum = 0;
                        int newAdd = 0; // new adding item amount
                        System.out.println("--Adding Item--");
                        while(true){
                            try{
                                System.out.print("Enter new adding item amount: ");
                                newAdd = sc.nextInt();
                                break;
                            }
                            catch(InputMismatchException e){
                                inputErrorMessage(sc);
                            }
                        }
                        clrscr();
                        System.out.println();
                        int oldAmount = amount;
                        amount += newAdd;
                        for (int i = oldAmount; i < amount; i++)
                            inputItem(i + 1, sc, rewritedItems);
                    } else if (modifyNum == 2) {
                        modifyNum = 0;
                        removeItem();
                    } else {
                        modifyNum = 0;
                        modifyItem(rewritedItems);
                    }

                    break;
                } else if (ans.equals("N")) {
                    System.out.print("\n\n");
                    clrscr();
                    break;
                } else {
                    inputErrorMessage(sc);
                }
            }

        }
    }

    // Donation List Confirmation
    public void confirmation() {
        System.out.println("--Donated Item List--");
        int index = 0;
        System.out.printf("%-2s %-15s %-5s %-5s\n", "No", "Name", "Quantity", "Category");
        for (Items item : donatedItems) {
            if (item.getItemName().equals("Money"))
                System.out.printf("%-2d %-15s %-8.2f %-6s\n", ++index, item.getItemName(), item.getItemMoney(),
                        item.getItemCategory());
            else
                System.out.printf("%-2d %-15s %-8d %-6s\n", ++index, item.getItemName(), item.getItemQuantity(),
                        item.getItemCategory());
        }
        System.out.println();
    }

    // Remove the item during confirmation
    public void removeItem() {
        Scanner ri = new Scanner(System.in);
        int newRemove = 0; // remove item number
        confirmation();
        if(donatedItems.size() == 0) {
            System.out.println("No item donated yet.\n");
            waitsrc();
            clrscr();
            return;
        }
        while (true) {
            try {
                System.out.println("--Remove Donate Item--");
                System.out.print("Enter item number to remove: ");
                newRemove = ri.nextInt();
                System.out.println();
                if(newRemove <1 || newRemove > donatedItems.size()){
                    System.out.println("Invalid Item's number!Please input a valid number.\n");
                    continue;
                }
                clrscr();
                break;
            } catch (InputMismatchException e) {
                inputErrorMessage(ri);
            }
        }donatedItems.remove(newRemove-1);
    }

    // Modify the donated item during confirmation
    public void modifyItem(ArrayList<Items> rewritedItems) throws FileNotFoundException {
        Scanner mi = new Scanner(System.in);
        int inum = 0; // incorrect item numebr
        confirmation();
        if(donatedItems.size() == 0){
            System.out.println("No item donated yet.\n");
            waitsrc();
            clrscr();
            return;
        }
        while (true) {
            try {
                System.out.println("--Modify Donate Item--");
                System.out.print("Please choose the incorrect item number: ");
                inum = mi.nextInt();
                System.out.print("\n");
                if(inum<1 || inum > donatedItems.size()){
                    System.out.println("Invalid Item's number!Please input a valid number.\n");
                    continue;
                }
                clrscr();
                donatedItems.remove(inum-1);
                inputItem(inum, mi, rewritedItems);
                break;
            } catch (InputMismatchException e) {
                inputErrorMessage(mi);
            }
        }
    }

    // Input new item during confirmation
    public void inputItem(int itemNum, Scanner sc, ArrayList<Items> item) throws FileNotFoundException {
        String name;
        int quantity, category;

        System.out.println("Item " + (itemNum));
        Items it = new Items();
        category = it.searchItems("donor");
        while (true) {
            try {
                if (category == 1) {
                    System.out.println("Category: Money");
                    System.out.print("Enter the amount= RM");
                    double money = sc.nextDouble();
                    Money m = new Money(money);
                    Items i = new Items(m);
                    donatedItems.add(i);
                } else {
                    System.out.print("Enter item name: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    System.out.println();
                    System.out.print("Enter item quantity: ");
                    quantity = sc.nextInt();
                    System.out.println();
                    Items i = new Items(name, quantity, category);
                    donatedItems.add(i);
                }
                break;
            } catch (InputMismatchException e) {
                inputErrorMessage(sc);
            }
        }
        System.out.print("\n");
        clrscr();
    }
  
    //return total amount of donated money to admin
    public double totalDonation() {
        return donationMoney;
    }

    // fixed format for date and time
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    // Update the date and time to now
    public String updateDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        date = sdf.format(cal.getTime());
        return sdf.format(cal.getTime());
    }
    //return date
    public String getDate() {
        return date;
    }

    // read the DonationMoney.txt
    public void readMoney() throws FileNotFoundException {
        Scanner inputFile = new Scanner(new File("DonationMoney.txt"));
        date = inputFile.nextLine();
        donationMoney = inputFile.nextDouble();
        inputFile.close();
    }

    // update the lastest data to DonationMonet.txt
    public void writeMoney(double donationMoney) throws FileNotFoundException {
        PrintWriter outputFile = new PrintWriter(new File("DonationMoney.txt"));
        outputFile.println(updateDate());
        outputFile.print(donationMoney);
        outputFile.close();
    }
}