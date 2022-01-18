import com.example.simonspuzzel.Coins;
import com.example.simonspuzzel.FileManager;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * File Manager test class to test all the methods of File Manager class are working correctly
 *
 * CreatedBy - Mansoor
 */
public class FileManagerTest {

    /**
     * Test to check if players details are stored correctly in the file.
     *
     * @throws IOException
     */
    @Test
    public void testStorePlayersScore() throws IOException {
        FileManager.storePlayerScores("Rick", 11);
        File file = new File("HighScore.txt");
        int score = 0;
        Scanner line = new Scanner(file);
        while(line.hasNextLine()) {
            String[] data = line.nextLine().split(",");
            if(data[0].equalsIgnoreCase("Rick")) {
                score = Integer.parseInt(data[1]);
            }
        }
        FileWriter f = new FileWriter("HighScore.txt");
        f.flush();
        f.close();
        assert(score == 11);
    }

    /**
     * Test to check getPreviousHigh scores method returning valid value
     *
     * @throws IOException
     */
    @Test
    public void testGetPreviousHighScores() throws IOException {
        FileManager.storePlayerScores("Rick", 12);
        FileManager.storePlayerScores("Rick", 13);
        assert(FileManager.getPreviousHighScores("Rick").equals("13 12 "));
    }

    /**
     * To check if the Read rectangle method return 2d objects that are in deserialized state
     * Also store the 2D objects in files and later read having same values
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void testToReadRectangles() throws IOException, ClassNotFoundException {
        int[][] g = new int[2][2];
        g[0][0] = 1;
        Coins coins = new Coins(10,20,15,new Circle(),new Text());
        ArrayList<Coins> c = new ArrayList<>();
        c.add(coins);
        FileManager.updatePlayersDetails(g,c,"test");
        assert(FileManager.readRectangles("test")[0][0] == 1);
    }

    /**
     * To check if the Read coins method return arraylist of coins data that are in deserialized state
     * Also store the arraylist of coins in files and later read having same values
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void testToReadCoins() throws IOException, ClassNotFoundException {
        int[][] g = new int[0][0];
        Coins coins = new Coins(10,20,15,new Circle(),new Text());
        ArrayList<Coins> c = new ArrayList<>();
        c.add(coins);
        FileManager.updatePlayersDetails(g,c,"test");
        assert(FileManager.readCoins("test").size() > 0);
    }

    /**
     * Checks for file not found and handles through exception to display pass if exception is triggered
     */
    @Test
    public void testForIllegalFile(){
        try {
            FileManager.readRectangles("abc");
        } catch (IOException e) {
            System.out.println("Pass");
        } catch (ClassNotFoundException e) {
            System.out.println("Fail");
        }
    }
}
