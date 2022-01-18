import com.example.simonspuzzel.BoardUtils;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * Board Unit test class to test all the boardUtils class methods
 *
 * CreatedBy - Mansoor
 */
public class BoardUtilsTest {

    /**
     * Checks for generated new grid has all 16 values that is 4X4
     */
    @Test
    public void testGenerateCellValue(){
        assert(BoardUtils.generateCellValue().size() == 16);
    }

    /**
     * Checks if the move is valid for valid data
     */
    @Test
    public void testIsValidMove(){
        int[][] ractGrid = new int[4][4];
        ractGrid[0][0] = 5;
        ractGrid[0][1] = 4;
        ractGrid[1][0] = 2;
        ractGrid[1][1] = 3;
        assert(BoardUtils.isValidMove(ractGrid,6,0,0));
    }

    /**
     * Checks for the element is present in not present in first row for the move to be done
     */
    @Test
    public void testIsPresentFirstRowFirstElement(){
        int[][] ractGrid = new int[4][4];
        ractGrid[0][0] = 5;
        ractGrid[0][1] = 4;
        ractGrid[1][0] = 2;
        ractGrid[1][1] = 3;
        assert(BoardUtils.isPresentFirstRow(ractGrid,7,0,1));
    }

    /**
     * Creates a 2D matrix and assign valid values.
     * Checks for the number is not present in first row second column
     */
    @Test
    public void testIsPresentFirstRowMiddleElements(){
        int[][] ractGrid = new int[4][4];
        ractGrid[0][0] = 5;
        ractGrid[0][1] = 4;
        ractGrid[0][2] = 5;
        ractGrid[1][0] = 2;
        ractGrid[1][1] = 3;
        ractGrid[1][2] = 1;
        assert(BoardUtils.isPresentFirstRow(ractGrid,6,0,1));
    }

    /**
     * Creates a 2D matrix and assign valid values.
     * Checks for the number is not present in first row last column
     */
    @Test
    public void testIsPresentFirstRowLastElement(){
        int[][] ractGrid = new int[4][4];
        ractGrid[0][3] = 5;
        ractGrid[0][2] = 4;
        ractGrid[1][2] = 2;
        ractGrid[1][3] = 3;
        assert(BoardUtils.isPresentFirstRow(ractGrid,6,0,3));
    }

    /**
     * Creates a 2D matrix and assign valid values.
     * Checks for the number is not present in middle row second column.
     */
    @Test
    public void testIsPresentSecondRowElement(){
        int[][] ractGrid = new int[4][4];
        ractGrid[0][1] = 3;
        ractGrid[0][2] = 7;
        ractGrid[0][3] = 5;
        ractGrid[1][1] = 4;
        ractGrid[1][2] = 6;
        ractGrid[1][3] = 4;
        ractGrid[2][1] = 5;
        ractGrid[2][2] = 7;
        ractGrid[2][3] = 3;
        assert(BoardUtils.isPresentMiddleRows(ractGrid,8,1,2));
    }

    /**
     * Creates a 2D matrix and assign valid values.
     * Checks for the number is not present in third row third column.
     */
    @Test
    public void testIsPresentThirdRowLastElemnet(){
        int[][] ractGrid = new int[4][4];
        ractGrid[0][2] = 6;
        ractGrid[0][3] = 4;
        ractGrid[1][2] = 7;
        ractGrid[1][3] = 3;
        ractGrid[2][2] = 6;
        ractGrid[2][3] = 4;
        assert(BoardUtils.isPresentMiddleRows(ractGrid,5,2,3));
    }

    /**
     * Creates a 2D matrix and assign valid values.
     * Checks for the number is not present in last or fourth row first column.
     */
    @Test
    public void testIsPresentLastRowFirstElement(){
        int[][] ractGrid = new int[4][4];
        ractGrid[2][0] = 5;
        ractGrid[2][1] = 4;
        ractGrid[3][0] = 2;
        ractGrid[3][1] = 3;
        assert(BoardUtils.isPresentLastRow(ractGrid,6,3,0));
    }

    /**
     * Creates a 2D matrix and assign valid values.
     * Adds remaining values in hashmap and checks for those numbers can still be moved
     */
    @Test
    public void testForIsPlayable(){
        int[][] ractGrid = new int[4][4];
        Rectangle[][] grid = new Rectangle[400][400];
        for(int i = 0; i < 400; i += 100){
            for(int j = 0; j < 400; j += 100){
                Rectangle r = new Rectangle(i, j, 100, 100);
                r.setFill(Color.GREEN);
                grid[i/100][j/100]  = r;
            }
        }
        ractGrid[0][1] = 3;
        ractGrid[0][2] = 7;
        ractGrid[0][3] = 5;
        ractGrid[1][1] = 4;
        ractGrid[1][2] = 6;
        ractGrid[1][3] = 4;
        ractGrid[2][1] = 5;
        ractGrid[2][2] = 7;
        ractGrid[2][3] = 3;
        HashMap<Integer,Integer> numbers= new HashMap<>();
        numbers.put(3,2);
        numbers.put(1,1);
        assert (BoardUtils.isPlayable(ractGrid,grid,numbers));
    }

    /**
     * Test to check set text method is working
     */
    @Test
    public void testForSetText(){
        Text text = BoardUtils.setText(new Text(0,0,"Game"), Color.WHITE,35);
        assert(text.getText().equals("Game"));
        assert(text.getFont().getSize() == 35);
        assert(text.getFill().equals(Color.WHITE));
    }
}
