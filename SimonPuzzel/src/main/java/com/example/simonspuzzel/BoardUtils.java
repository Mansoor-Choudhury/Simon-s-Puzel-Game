package com.example.simonspuzzel;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *BoardUtils class to handle boards opperation
 *
 * CreatedBy: Mansoor
 */
public class BoardUtils {

    /**
     * Generates boards values which are of 2,3,3,4,4 order.
     * Takes random numbers and generates the boards values
     * checks for the generated numbers are valid and can be put in the board.
     * Place's numbers in each board if immediate neighbour is not same.
     *
     * @return - list of numbers to be put in board
     */
    public static ArrayList<Integer> generateCellValue(){
        int min = 5,max = 10;
        ArrayList<Integer> numbersList = new ArrayList<>();
        ArrayList<Integer> finalList = new ArrayList<>();
        int num = min + (int)(Math.random() * ((max - min) + 1));
        for(int maxNum = 5; maxNum > 0 ; maxNum--){
            for(int i = 1; i < min ; i++){
                numbersList.add(num);
                finalList.add(0);
            }
            num--;
            if (maxNum % 2 == 0) {
                min--;
            }
        }
        for(int i = 0; i < numbersList.size(); i++){
            int temp = numbersList.get(i);
            if(finalList.get(0) == 0){
                finalList.set(0,temp);
            }else if(i == finalList.size() - 1){
                int val = finalList.get(4);
                finalList.set(i,val);
                finalList.set(4,temp);
            }else {
                for(int j = 1; j < finalList.size(); j++) {
                    if (j < 4 && finalList.get(j - 1) != temp && finalList.get(j) == 0) {
                        finalList.set(j,temp);
                        break;
                    } else if (j >= 4 && finalList.get(j) == 0) {
                        if (j % 4 == 0 && finalList.get(j - 3) != temp && finalList.get(j - 4) != temp && finalList.get(j) != temp) {
                            finalList.set(j,temp);
                            break;
                        } else if ((j % 4 == 1 || j % 4 == 2) && finalList.get(j - 1) != temp && finalList.get(j - 3) != temp && finalList.get(j - 4) != temp && finalList.get(j - 5) != temp && finalList.get(j) != temp) {
                            finalList.set(j,temp);
                            break;
                        } else if (j % 4 == 3 && finalList.get(j - 1) != temp && finalList.get(j - 4) != temp && finalList.get(j - 5) != temp && finalList.get(j) != temp) {
                            finalList.set(j,temp);
                            break;
                        }
                    }
                }
            }
        }
        return finalList;
    }

    /**
     * Checks if the move is valid. Checks it in first row, middle rows and last row
     *
     * @param gridValues - grid containing all the numbers in 4*4 order
     * @param cellValue - value which is to checked
     * @param i - row value
     * @param j - column value
     * @return - true or false if move is valid or invalid
     */
    public static boolean isValidMove(int[][] gridValues,int cellValue, int i, int j){
        if(i < 1 && isPresentFirstRow(gridValues,cellValue,i,j)){
            return true;
        }else if(i < 3 && i > 0 && isPresentMiddleRows(gridValues,cellValue,i,j)){
            return true;
        }else if(i == 3 && isPresentLastRow(gridValues,cellValue,i,j)){
            return true;
        }
        return false;
    }

    /**
     * Checks for the number is not available in first row and its immediate neighbours.
     *
     * @param gridValues - grid containing all the numbers in 4*4 order
     * @param cellValue - value which is to checked
     * @param i - row value
     * @param j - column value
     * @return - true or false if element is present or not in first row
     */
    public static boolean isPresentFirstRow(int[][] gridValues, int cellValue, int i, int j){
        if(j == 0 && gridValues[i][j] != cellValue && gridValues[i][j+1] != cellValue && gridValues[i+1][j] != cellValue && gridValues[i+1][j+1] != cellValue) {
            return true;
        }else  if(j == 3 && gridValues[i][j] != cellValue && gridValues[i][j-1] != cellValue && gridValues[i+1][j] != cellValue && gridValues[i+1][j-1] != cellValue) {
            return true;
        }else  if((j == 2 || j == 1) && gridValues[i][j] != cellValue && gridValues[i][j-1] != cellValue && gridValues[i][j+1] != cellValue && gridValues[i+1][j] != cellValue && gridValues[i+1][j-1] != cellValue && gridValues[i+1][j+1] != cellValue) {
            return true;
        }
        return false;
    }

    /**
     * hecks for the number in middle rows is not same as its immediate neighbours.
     *
     * @param gridValues - grid containing all the numbers in 4*4 order
     * @param cellValue - value which is to checked
     * @param i - row value
     * @param j - column value
     * @return - true or false if element is present or not in middle rows
     */
    public static boolean isPresentMiddleRows(int[][] gridValues,int cellValue, int i, int j){
        if(j == 0 && gridValues[i][j] != cellValue && gridValues[i][j+1] != cellValue && gridValues[i+1][j] != cellValue && gridValues[i+1][j+1] != cellValue && gridValues[i-1][j+1] != cellValue && gridValues[i-1][j] != cellValue) {
            return true;
        }else  if(j == 3 && gridValues[i][j] != cellValue && gridValues[i][j-1] != cellValue && gridValues[i+1][j] != cellValue && gridValues[i+1][j-1] != cellValue && gridValues[i-1][j-1] != cellValue && gridValues[i-1][j] != cellValue) {
            return true;
        }else  if((j == 2 || j == 1) && gridValues[i][j] != cellValue && gridValues[i][j-1] != cellValue && gridValues[i][j+1] != cellValue && gridValues[i+1][j] != cellValue && gridValues[i+1][j-1] != cellValue && gridValues[i+1][j+1] != cellValue && gridValues[i-1][j] != cellValue && gridValues[i-1][j-1] != cellValue && gridValues[i-1][j-1] != cellValue) {
            return true;
        }
        return false;
    }

    /**
     * Checks for the number in last row is not same as its immediate neighbours.
     *
     * @param gridValues - grid containing all the numbers in 4*4 order
     * @param cellValue - value which is to checked
     * @param i - row value
     * @param j - column value
     * @return - true or false if element is present or not in the last row
     */
    public static boolean isPresentLastRow(int[][] gridValues,int cellValue, int i, int j){
        if(j == 0 && gridValues[i][j] != cellValue && gridValues[i][j+1] != cellValue && gridValues[i-1][j] != cellValue && gridValues[i-1][j+1] != cellValue) {
            return true;
        }else  if(j == 3 && gridValues[i][j] != cellValue && gridValues[i][j-1] != cellValue && gridValues[i-1][j] != cellValue && gridValues[i-1][j-1] != cellValue) {
            return true;
        }else  if((j == 2 || j == 1) && gridValues[i][j] != cellValue && gridValues[i][j-1] != cellValue && gridValues[i][j+1] != cellValue && gridValues[i-1][j] != cellValue && gridValues[i-1][j-1] != cellValue && gridValues[i-1][j+1] != cellValue) {
            return true;
        }
        return false;
    }

    /**
     * Checks if game is playable or not.
     * For all the remaining numbers in the coins checks if the coin can still be placed.
     * Breaks if even one valid move is found.
     * Calls methods to validate if the remaining coins can be moved or not
     *
     * @param gridValues - grid containing all the numbers in 4*4 order
     * @param grid - rectangle grid having the rectangles details
     * @param remainingNumbers - remaining numbers of coins
     * @return - true or false if move is valid or invalid
     */
    public static boolean isPlayable(int[][] gridValues, Rectangle[][] grid, HashMap<Integer, Integer> remainingNumbers){
        boolean flag = false;
        for(Map.Entry<Integer,Integer> entry : remainingNumbers.entrySet()) {
            if (entry.getValue() != 0) {
                for (int i = 0; i < BoardController.blocks; i++) {
                    for (int j = 0; j < BoardController.blocks; j++) {
                        if (!grid[j][i].getFill().equals(Color.NAVY)) {
                            if (i < 1 && isPresentFirstRow(gridValues,entry.getKey(), i, j)) {
                                flag = true;
                            } else if (i < 3 && i > 0 && isPresentMiddleRows(gridValues,entry.getKey(), i, j)) {
                                flag = true;
                            } else if (i == 3 && isPresentLastRow(gridValues,entry.getKey(), i, j)) {
                                flag = true;
                            }
                        }
                        if (flag)
                            break;
                    }
                    if (flag)
                        break;
                }
                if (flag)
                    break;
            }
        }
        return flag;
    }

    /**
     * Sets label details
     *
     * @param textLabel - text for the label
     * @param color - font color
     * @param fontSize - font size
     * @param x - x coordiante of lebel
     * @param y - y coordinate of lebel
     * @return - returns the label object created
     */
    public static Label setTextLabel(final String textLabel, Color color, int fontSize, int x, int y){
        Label label = new Label();
        label.setText(textLabel);
        label.setFont(new Font(fontSize));
        label.setTextFill(color);
        label.setTranslateX(x);
        label.setTranslateY(y);
        return label;
    }

    /**
     * Sets text attributes to a text object
     *
     * @param text - text object
     * @param color - color of text
     * @param size - font size
     * @return - text object after alteration
     */
    public static Text setText(Text text, Color color, int size){
        text.setFont(new Font(size));
        text.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        text.setFill(color);
        return text;
    }
}
