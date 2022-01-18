import com.example.simonspuzzel.Coins;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;

/**
 * Coins test class to validate if coins objects and methods are working fine
 *
 * CreatedBy - Mansoor
 */
public class CoinsTest {
    /**
     * Test to create coins object though constructor and validate if working
     */
    @Test
    public void testCoinsConstructor(){
        Coins coins = new Coins(10,20,15,new Circle(),new Text("Game"));
        assert (coins.getText().getText().equals("Game"));
        assert (coins.getX() == 10);
        assert (coins.getY() == 20);
    }

    /**
     * Tests to check draw method changes the circle radius.
     */
    @Test
    public void testDrawMethodForCircle(){
        Coins coins = new Coins(10,20,15,new Circle(),new Text("Game"));
        coins.draw(null);
        assert (coins.getCircle().getRadius() == 15.0);
    }

    /**
     * Test to check if text dimensions get changed while doing a draw
     */
    @Test
    public void testDrawMethodForText(){
        Coins coins = new Coins(10,15,10,new Circle(),new Text("Game"));
        coins.draw(null);
        assert (coins.getText().getTranslateX() == 0.0);
        assert (coins.getText().getTranslateY() == 25.0);
    }
}