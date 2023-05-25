import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Items {
    private String itemName;
    private int itemQuantity;
    private int itemCategory;
    private Money money;
    private String itemCtgr;

    // Default constructor
    Items() {
        itemName = "";
        itemQuantity = 0;
        itemCategory = 0;
    }

    // Overloading constructor for normal items
    Items(String itemName, int itemQuantity, int itemCategory) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemCategory = itemCategory;
    }

    //Overloading constructor for copy items
    Items(Items i) {
        this.itemName = i.getItemName();
        this.itemQuantity = i.getItemQuantity();
        this.itemCategory = i.itemCategory;
    }

    // Overloading constructor for donated item type money
    Items(Money m) {
        itemName = "Money";
        itemCategory = 1;
        itemQuantity = 0;
        money = m;
    }

    public double getItemMoney() {
        return money.getMoney();
    }

    // Update the itemQuantity
    public void setQuantity(int quantity) {
        itemQuantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public String getItemCategory() {
        switch (itemCategory) {
            case 1:
                itemCtgr = "Money";
                break;
            case 2:
                itemCtgr = "Food";
                break;
            case 3:
                itemCtgr = "Clothing";
                break;
            case 4:
                itemCtgr = "Book";
                break;
            case 5:
                itemCtgr = "Computer and Accessories";
                break;
            case 6:
                itemCtgr = "Toys";
                break;
            default:
                itemCtgr = "Others";
                break;
        }
        return itemCtgr;
    }

    public int getItemCategoryNum() {
        return itemCategory;
    }

    public void displayInfoItem() {
        System.out.println("Name: " + itemName);
        System.out.println("Quantity: " + itemQuantity);
        System.out.println("Category: " + getItemCategory());
    }

    // Search item from Items_list.txt and return category number based on the user type
    public int searchItems(String person) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            try {
                
                ArrayList<String> categoryList = new ArrayList<String>();
                String[] mylist = { "Money", "Food", "Clothing", "Book", "Computer and Accessories", "Toys", "Others" };
                for (String item : mylist)
                    categoryList.add(item);
                if (person.equals("receiver"))
                    categoryList.remove("Money");
                int list = 0;
                System.out.println("--Item Category--");
                for (String item : categoryList)
                    System.out.println(++list + ". " + item);
                int category;
                while (true) {
                    System.out.print("Enter the category: ");
                    category = sc.nextInt();
                    sc.nextLine();
                    if (category < 1 || category > mylist.length) {
                        System.out.println("Please input the correct number");
                        System.out.println();
                        continue;
                    }
                    break;
                }
                System.out.println();
                if(person=="donor")
                return category;
                else return category+1;//due to the money is already excluded from the list.
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input! Please check with the required data carefully");
                System.out.println();
                sc.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Incorrect input! Please check with the required data carefully");
                System.out.println();
                sc.nextLine();
            }
        }

    }

}