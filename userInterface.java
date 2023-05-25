import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public interface userInterface{
    void viewReceipt(Items[] items);
    void confirmation();
    void removeItem();
    void modifyItem(ArrayList<Items> v) throws FileNotFoundException;
    void inputItem(int i, Scanner s, ArrayList<Items> v) throws FileNotFoundException;
}
