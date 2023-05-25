import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.InputMismatchException;

public class Receiver extends Person implements userInterface {
    private ArrayList<Items> receivedItems = new ArrayList<Items>();
    private int amount = 0;

    Receiver() {
    }

    // Receiver select items
    void selectItems(Items i) throws FileNotFoundException{
        Scanner sc = new Scanner(System.in);
        ArrayList<Items> rewritedItems = new ArrayList<Items>();
        ArrayList<Items> allItems = new ArrayList<Items>();
        
        Scanner cfile = new Scanner(new File("Items_list.txt"));
        int count = 0;
        if (cfile.hasNextLine())
            cfile.nextLine();
        while (cfile.hasNextLine()) {
            cfile.nextLine();
            count++;
        }
        cfile.close();

        int[] num = new int[count];
        String[] name = new String[count];
        int[] quantity = new int[count];
        int[] cat = new int[count];// categorycat ;// category
        char add;
        Scanner file = new Scanner(new File("Items_list.txt"));
        file.nextLine();
        for (int j = 0; file.hasNext(); j++) {
            num[j] = file.nextInt();
            name[j] = file.next();
            String f = file.next();
            if (f.matches("[0-9]+")) {
            } else {
                name[j] += " " + f;
                f = file.next();
            }
            quantity[j] = Integer.parseInt(f);
            cat[j] = file.nextInt();
            allItems.add(new Items(name[j], quantity[j], cat[j]));
        }


        while (true) {
            try {
                // fill up reciver information first
                System.out.println("Please enter your information:");
                fillUpInfo();
                clrscr();
                char want='y';
                int receivedMenuChoice = 1, firstLoop = 1;

                do {
                    if (firstLoop != 1) {
                        System.out.println("--Received Item Menu--");
                        System.out.println("1. Add new receiving item");
                        System.out.println("2. Remove of modify receviing item");
                        while (true) {
                            try {
                                System.out.print("Enter 1 to 2: ");
                                receivedMenuChoice = sc.nextInt();
                                if (receivedMenuChoice < 1 || receivedMenuChoice > 2) {
                                    System.out.println("Please input the correct number");
                                    System.out.println();
                                    continue;
                                }
                                System.out.println();
                                break;
                            } catch (InputMismatchException e) {
                                inputErrorMessage(sc);
                            }
                        }
                    }
                    clrscr();

                    switch (receivedMenuChoice) {
                        case 1:
                            int choice;
                            System.out.println("Menu for searching items\n1. Search by category\n2. Search by name");
                            while (true) {
                                try {
                                    System.out.print("Enter 1 to 2: ");
                                    choice = sc.nextInt();
                                    if (choice < 1 || choice > 2) {
                                        System.out.println("Please input the correct number");
                                        System.out.println();
                                        continue;
                                    }
                                    System.out.println();
                                    break;
                                } catch (InputMismatchException e) {
                                    inputErrorMessage(sc);
                                }
                                
                            }
                            clrscr();
                            // To count the number of line in previous file

                            switch (choice) {
                                case 1:
                                    int index = 0;
                                    ArrayList<Items> storedItems = new ArrayList<Items>();
                                    Vector<Integer> fileNum = new Vector<Integer>();
                                    int category = i.searchItems("receiver");
                                    clrscr();
                                    System.out.printf("%-2s %-15s %-5s %-5s\n", "No", "Name", "Quantity", "Category");
                                    for(int j=0;j<cat.length;j++){
                                        if (cat[j] == category) {
                                        fileNum.addElement(Integer.valueOf(num[j] - 1));
                                        storedItems.add(new Items(name[j], quantity[j], cat[j]));
                                        System.out.printf("%-2d %-15s %-8d %-6s\n", ++index, name[j], quantity[j],
                                                cat[j]);
                                        }
                                    }
                                    
                                    if (index == 0) {
                                        System.out.println("NO ITEM AVAILABLE");
                                    } else {
                                        int no, addedQuantity;
                                        System.out.println("Do you want to add one of the items ?\nIf yes press 'y': ");
                                        add = sc.next().charAt(0);
                                        if (Character.toLowerCase(add) == 'y') {
                                            while (true) {
                                                try {
                                                    while(true){
                                                        System.out.print("Item's number to be added: ");
                                                        no = sc.nextInt();
                                                        if(no < 1 || no >storedItems.size()){
                                                            System.out.println("Invalid Item's number!Please input a valid numeber.");
                                                            continue;
                                                        }
                                                        break;
                                                    }
                                                    
                                                    while(true){
                                                        System.out.print("Quantity added to the list: ");
                                                        addedQuantity = sc.nextInt();
                                                        if(addedQuantity<=0){
                                                            System.out.println("Invalid Quantity number!Please input a valid numeber.");
                                                            continue;
                                                        }
                                                        if(storedItems.get(no - 1).getItemQuantity()
                                                        - addedQuantity >=0)break;
                                                        else{
                                                            System.out.println("Please input a valid quantiy number.");
                                                        }
                                                    }
                                                    
                                                    int leftQuantity = storedItems.get(no - 1).getItemQuantity()
                                                            - addedQuantity;
                                                    int actualNo = fileNum.elementAt(no - 1).intValue();
                                                    rewritedItems.add(new Items(allItems.get(actualNo)));
                                                    rewritedItems.get(rewritedItems.size()-1).setQuantity(leftQuantity);
                                                    storedItems.get(no - 1).setQuantity(addedQuantity);
                                                    receivedItems.add(storedItems.get(no - 1));
                                                    amount++;
                                                    break;
                                                } catch (InputMismatchException e) {
                                                    inputErrorMessage(sc);
                                                }

                                            }

                                        }
                                        clrscr();
                                    }

                                    file.close();
                                    break;

                                case 2:
                                    String searchItem;
                                    int choose = -1;

                                    sc.nextLine();
                                    System.out.print("Enter item name: ");
                                    searchItem = sc.nextLine();
                                    for(int j=0; j<name.length;j++){
                                        if (searchItem.equals(name[j])) {
                                            choose = j;
                                        }
                                    }
                                    file.close();
                                    if (choose != -1) {
                                        System.out.println("Item info: ");
                                        Items it = new Items(name[choose], quantity[choose], cat[choose]);
                                        it.displayInfoItem();
                                        System.out.println("Do you want to add this item ?\nIf yes press 'y'");
                                        add = sc.next().charAt(0);
                                        int added;
                                        if (Character.toLowerCase(add) == 'y') {
                                            while (true) {
                                                try {
                                                    while(true){
                                                        System.out.print("Quantity added to the list: ");
                                                        added = sc.nextInt();
                                                        if(added<=0){
                                                            System.out.println("Invalid Quantity number!Please input a valid numeber.");
                                                            continue;
                                                        }
                                                        if(it.getItemQuantity() - added >=0)break;
                                                        else{
                                                            System.out.println("Please input a valid quantiy number.");
                                                        }
                                                    }
                                                    int left = it.getItemQuantity() - added;
                                                    it.setQuantity(added);
                                                    rewritedItems.add(new Items(allItems.get(choose)));
                                                    rewritedItems.get(rewritedItems.size()-1).setQuantity(left);
                                                    receivedItems.add(it);
                                                    amount++;
                                                    break;
                                                } catch (InputMismatchException e) {
                                                    inputErrorMessage(sc);
                                                }
                                            }
                                        }
                                    } else {
                                        System.out.println("Item is not available!!");
                                        waitsrc();
                                    }
                                    
                                    break;
                                default:
                                    System.out.println("Please enter the correct number!");
                            }
                            clrscr();

                            confirmation();
                            // take item name & quantity
                            System.out.println(
                                    "Do you still want to search for new item or modify your recevied items? \nIf yes press 'y': ");
                            want = sc.next().charAt(0);
                            System.out.println();
                            firstLoop = 0;
                            clrscr();
                            break;

                        case 2:
                            System.out
                                    .println("Menu for modifying receiving items list\n1. Remove Item\n2. Modify Item");
                            while (true) {
                                try {
                                    System.out.print("Enter 1 to 2: ");
                                    choice = sc.nextInt();
                                    if (choice < 1 || choice > 2) {
                                        System.out.println("Please input the correct number");
                                        System.out.println();
                                        continue;
                                    }
                                    System.out.println();
                                    break;
                                } catch (InputMismatchException e) {
                                    inputErrorMessage(sc);
                                }
                            }
                            clrscr();
                            confirmation();
                            if(receivedItems.size() == 0){
                                waitsrc();
                                clrscr();
                                continue;
                            } 
                            if (choice == 1){
                                removeItem();
                                for(int j=0; j<rewritedItems.size();j++){
                                    if(j == receivedItems.size()){
                                        rewritedItems.remove(j);
                                        break;
                                    }
                                    if(j==0){
                                        if(rewritedItems.get(j+1).getItemName() == receivedItems.get(j).getItemName()){
                                            rewritedItems.remove(j);
                                            break;
                                        }
                                    }
                                    if(rewritedItems.get(j).getItemName() == receivedItems.get(j).getItemName())
                                        continue;
                                    else{
                                        rewritedItems.remove(j);
                                        break;
                                    }
                                }
                            }
                                

                            else if (choice == 2)
                                modifyItem(rewritedItems);

                            confirmation();
                            System.out.println(
                                    "Do you still want to search for new item or modify your recevied items? \nIf yes press 'y': ");
                            want = sc.next().charAt(0);
                            firstLoop = 0;
                            clrscr();
                            break;
                        default:
                            want = 'e';
                            System.out.println(
                                    "This message should not be come out. Please chcek the input of receivedMenuChoice.");
                            System.exit(-1);
                    }
                } while (Character.toLowerCase(want) == 'y');
                                
                reWriteFile(allItems, rewritedItems);

                break;
            } catch (InputMismatchException e) {
                inputErrorMessage(sc);
            }
        }
    }

    // Function to rewrite the file after reducing the quantity of the item
    public void reWriteFile(ArrayList<Items> items, ArrayList<Items> rewriteItems) throws FileNotFoundException {
        if(rewriteItems.size() ==0){
            return;
        }
        
        PrintWriter outputFile = new PrintWriter("Items_list.txt");
        int rewriteCount=0;
        outputFile.println("No.   Name             Quantity    Category");
        for (int i = 0; i < items.size(); i++) {
            outputFile.format("%-6d", (i+1));
            outputFile.format("%-17s", items.get(i).getItemName());
            if(items.get(i).getItemName().equals(rewriteItems.get(rewriteCount).getItemName())){
                outputFile.format("%-12d", rewriteItems.get(rewriteCount).getItemQuantity());
                rewriteCount++;
                if(rewriteCount == rewriteItems.size())
                    rewriteCount -=1; 
            }
            else{
                outputFile.format("%-12d", items.get(i).getItemQuantity());
            } 
            String[] categoryList = { "Money", "Food", "Clothing", "Book", "Computer and Accessories", "Toys",
                    "Others" };
            int cat = 0;
            for (int j = 0; j < categoryList.length; j++) {
                if (categoryList[j].equals(items.get(i).getItemCategory())) {
                    cat = j + 1;
                    break;
                }
            }
            outputFile.format("%-18s", cat);
            outputFile.println();
        }
        outputFile.close();
    }

    // Print the receipt for reciever
    public void viewReceipt(Items[] items) {
        System.out.println("------RECEIVER RECEIPT-------");
        System.out.println("---Welfare Management System---");
        System.out.printf("received by: %s ( %s )\n", getName(), getID());
        System.out.printf("Street Address: %s \n", getAddress());
        System.out.printf("Phone number: %s \n", getTeleNo());
        System.out.printf("Number of item received: %s \n", amount);
  
        if(amount != 0){
            for (int i = 0; i < receivedItems.size(); i++) {
            items[i] = receivedItems.get(i);
            }
            System.out.printf("%-2s %-15s %-5s %-5s\n", "No", "Name", "Quantity", "Category");
            int index = 0;
            for (Items item : items) {
                System.out.printf("%-2d %-15s %-8d %-6s\n", ++index, item.getItemName(), item.getItemQuantity(),
                        item.getItemCategory());
                if(index == receivedItems.size())break;
            }
        }
        else{
            System.out.println("No itme choose to be recieved.");
            System.out.println();
        }
    }

    // Receive items list confirmation for receiver
    public void confirmation() {
        System.out.println("--Receive Items List Confirmation--");
        if(receivedItems.size() !=0){
            int index = 0;
            System.out.printf("%-2s %-15s %-5s %-5s\n", "No", "Name", "Quantity", "Category");
        
            for (Items item : receivedItems) {
                System.out.printf("%-2d %-15s %-8d %-6s\n", ++index, item.getItemName(), item.getItemQuantity(),
                        item.getItemCategory());
            }
        }
        else{
            System.out.println("No itme choose to be received yet.");
        }
        System.out.println();
    }

    // Delete item during confirmation
    public void removeItem() {
        Scanner ri = new Scanner(System.in);
        while (true) {
            try {
                
                int newRemove = 0; // remove item number
                System.out.println("--Remove Receiving Item--");
                System.out.print("Enter item number to remove: ");
                newRemove = ri.nextInt();
                System.out.println();
                clrscr();

                receivedItems.remove(newRemove - 1);
                
                amount -= 1;
                break;
            } catch (InputMismatchException e) {
                inputErrorMessage(ri);
            }
        }

    }

    // Modify item during confimation
    public void modifyItem(ArrayList<Items> rewritedItems) throws FileNotFoundException {
        Scanner mi = new Scanner(System.in);
        while (true) {
            try {
                
                int inum = 0; // incorrect item numebr
                while(true){
                    System.out.print("Please choose the incorrect item number: ");
                    inum = mi.nextInt();
                    if(inum <= 0 || inum >receivedItems.size()){
                        System.out.println("Invalid Item's number. Please input a valid number.");
                        continue;
                    }
                    break;
                }
                clrscr();
                
                System.out.print("\n");
                inputItem(inum, mi, rewritedItems);
                break;
            } catch (InputMismatchException e) {
                inputErrorMessage(mi);
            }
        }

    }

    // Input new item during confirmation
    public void inputItem(int inum, Scanner iI, ArrayList<Items> rewritedItems) throws FileNotFoundException {
        int choice;
        ArrayList<Items> allItemsMethod = new ArrayList<Items>();
        Items i = new Items();
        Scanner methodFile = new Scanner(new File("Items_list.txt"));
        System.out.println("Menu for searching items\n1. Search by category\n2. Search by name");
        while (true) {
            try {
                System.out.print("Enter 1 to 2: ");
                choice = iI.nextInt();
                if (choice < 1 || choice > 2) {
                    System.out.println("Please input the correct number");
                    System.out.println();
                    continue;
                }
                System.out.println();
                break;
            } catch (InputMismatchException e) {
                inputErrorMessage(iI);
            }
            
        }
        clrscr();
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

        int[] num = new int[count];
        String[] name = new String[count];
        int[] quantity = new int[count];
        int[] cat = new int[count];// categorycat ;// category
        char add;

        methodFile.nextLine();
        for (int j = 0; methodFile.hasNext(); j++) {
            num[j] = methodFile.nextInt();
            name[j] = methodFile.next();
            String f = methodFile.next();
            if (f.matches("[0-9]+")) {
            } else {
                name[j] += " " + f;
                f = methodFile.next();
            }
            quantity[j] = Integer.parseInt(f);
            cat[j] = methodFile.nextInt();
            allItemsMethod.add(new Items(name[j], quantity[j], cat[j]));
        }

        switch (choice) {
            //add item
            case 1:
                int index = 0;
                ArrayList<Items> storedItems = new ArrayList<Items>();
                Vector<Integer> fileNum = new Vector<Integer>();
                int category = i.searchItems("receiver");
                clrscr();
                System.out.printf("%-2s %-15s %-5s %-5s\n", "No", "Name", "Quantity", "Category");
                for(int j=0;j<cat.length;j++){
                    if (cat[j] == category) {
                    fileNum.addElement(Integer.valueOf(num[j] - 1));
                    storedItems.add(new Items(name[j], quantity[j], cat[j]));
                    System.out.printf("%-2d %-15s %-8d %-6s\n", ++index, name[j], quantity[j],
                            cat[j]);
                    }
                }
                
                if (index == 0) {
                    System.out.println("NO ITEM AVAILABLE");
                } else {
                    int no, addedQuantity;
                    System.out.println("Do you want to add one of the items ?\nIf yes press 'y': ");
                    add = iI.next().charAt(0);
                    if (Character.toLowerCase(add) == 'y') {
                        while (true) {
                            try {
                                while(true){
                                    System.out.print("Item's number to be added: ");
                                    no = iI.nextInt();
                                    if(no < 1 || no >storedItems.size()){
                                        System.out.println("Invalid Item's number!Please input a valid numeber.");
                                        continue;
                                    }
                                    break;
                                }
                                
                                while(true){
                                    System.out.print("Quantity added to the list: ");
                                    addedQuantity = iI.nextInt();
                                    if(addedQuantity<=0){
                                        System.out.println("Invalid Quantity number!Please input a valid numeber.");
                                        continue;
                                    }
                                    if(storedItems.get(no - 1).getItemQuantity()
                                    - addedQuantity >=0)break;
                                    else{
                                        System.out.println("Please input a valid quantiy number.");
                                    }
                                }
                                
                                int leftQuantity = storedItems.get(no - 1).getItemQuantity()
                                        - addedQuantity;
                                int actualNo = fileNum.elementAt(no - 1).intValue();
                                rewritedItems.remove(inum-1);
                                rewritedItems.add(inum-1,new Items(allItemsMethod.get(actualNo)));
                                rewritedItems.get(inum-1).setQuantity(leftQuantity);
                                storedItems.get(no - 1).setQuantity(addedQuantity);
                                receivedItems.set(inum-1, (storedItems.get(no - 1)));
                                amount++;
                                break;
                            } catch (InputMismatchException e) {
                                inputErrorMessage(iI);
                            }

                        }

                    }
                }

                methodFile.close();
                break;
            //remove or modify item
            case 2:
                String searchItem;
                int choose = -1;

                iI.nextLine();
                System.out.print("Enter item name: ");
                searchItem = iI.nextLine();
                for(int j=0; j<name.length;j++){
                    if (searchItem.equals(name[j])) {
                        choose = j;
                    }
                }
                methodFile.close();
                if (choose != -1) {
                    System.out.println("Item info: ");
                    Items it = new Items(name[choose], quantity[choose], cat[choose]);
                    it.displayInfoItem();
                    System.out.println("Do you want to add this item ?\nIf yes press 'y'");
                    add = iI.next().charAt(0);
                    int added;
                    if (Character.toLowerCase(add) == 'y') {
                        while (true) {
                            try {
                                while(true){
                                    System.out.print("Quantity added to the list: ");
                                    added = iI.nextInt();
                                    if(added<=0){
                                        System.out.println("Invalid Quantity number!Please input a valid numeber.");
                                        continue;
                                    }
                                    if(it.getItemQuantity() - added >=0)break;
                                    else{
                                        System.out.println("Please input a valid quantiy number.");
                                    }
                                }
                                int left = it.getItemQuantity() - added;
                                it.setQuantity(added);
                                rewritedItems.remove(inum-1);
                                rewritedItems.add(inum-1,new Items(allItemsMethod.get(choose)));
                                rewritedItems.get(inum-1).setQuantity(left);
                                receivedItems.set(inum-1,it);
                                amount++;
                                break;
                            } catch (InputMismatchException e) {
                                inputErrorMessage(iI);
                            }
                        }

                    }
                } else {
                    System.out.println("Item is not available!!");
                    waitsrc();
                }

                break;
            default:
                System.out.println("Please enter the correct number!");
        }
        clrscr();
    }
    //display input error message in receiver class
    void inputErrorMessage(Scanner sc) {
        System.out.println("Input error!");
        System.out.println("Please enter the correct input again!!");
        sc.nextLine();
    }
}
