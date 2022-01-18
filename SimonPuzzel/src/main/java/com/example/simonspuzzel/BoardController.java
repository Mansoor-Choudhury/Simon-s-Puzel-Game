package com.example.simonspuzzel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controller class to initialize the UI elements like creating the grid, coins, buttons, texts, labels and their coordinates
 *
 * CreatedBy: Mansoor
 */
public class BoardController {

    /**
     * The board pane object where all the rectangles,coins,texts and labels are added are added
     */
    @FXML
    public Pane pane;

    /**
     * Close button object to close the application
     */
    @FXML
    public Button closeButton;

    /**
     * Start button object to start or restart the game
     */
    @FXML
    public Button startButton;

    /**
     * Welcome message label to display the players name
     */
    @FXML
    public Label welcomeMsg;

    /**
     * Textfield object which stores the user name
     */
    @FXML
    public TextField userName;

    /**
     * Previous data button object to load previous state of game
     */
    @FXML
    public Button previousData;

    private static int dimension = 400;
    public static HashMap<Integer,Integer> remainingNumbers;
    static int[][] gridNumbers = new int[4][4];
    private static int finalScore;
    static int blocks = 4;
    private static int rectangleSize = dimension / blocks;
    private boolean isStarted = false;
    private boolean isPrevious = false;
    private static double circleX;
    private static double circleY;
    private static String playerName = "Player";

    private ArrayList<Coins> coinsList;
    static Rectangle[][] grid;

    /**
     * It gets triggered when begin or start button is clicked
     * Initializes the coordinates of buttons
     * sets the current state as if its load a previous game or new game
     */
    @FXML
    protected void onStartButtonClick() {
        if(pane.getChildren().size() > 0) {
            pane.getChildren().remove(0, pane.getChildren().size());
        }
        remainingNumbers = new HashMap<>();
        finalScore = 0;
        isStarted = true;
        startButton.setText("RESTART");
        previousData.setTranslateX(285);
        previousData.setTranslateY(-137);
        startButton.setTranslateX(480);
        startButton.setTranslateY(0);
        closeButton.setTranslateX(670);
        closeButton.setTranslateY(-70);
        if(!userName.getText().trim().isEmpty()) {
            playerName = userName.getText();
        }
        welcomeMsg.setText("Welcome " + playerName + "!");
        welcomeMsg.setTranslateX(-100);
        userName.relocate(-900,-900);
        isPrevious = false;
        previousData.setDisable(false);
        initializeBoard();
    }

    /**
     * It initializes the flags to load the game in previous state
     */
    @FXML
    protected void onPreviousButtonClick() {
        isPrevious = true;
        isStarted = false;
        initializeBoard();
    }

    /**
     * Closes the stage when clicked
     *
     * @param event - event
     */
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * It initializes the board. Calls the create rectange and coins method to initialize the board.
     * for previous state games, it initializes the board to old state where the game ended.
     */
    @FXML
    public void initializeBoard() {
        if (isStarted) {
            ArrayList<Integer> numbers = BoardUtils.generateCellValue();
            createGridRectangle(pane,numbers);
            createCoins(pane,numbers);
        }else if(isPrevious){
            try {
                int[][] data = FileManager.readRectangles(playerName+".txt");
                loadPreviousRectangles(data);
                ArrayList<String> previousCoins = FileManager.readCoins(playerName+".txt");
                loadPreviousCoins(previousCoins);
                displayGameResults();
            } catch (IOException | ClassNotFoundException e) {
                pane.getChildren().add(BoardUtils.setTextLabel("No Previous Records:"+ System.lineSeparator() +" Restart Game to play again",Color.RED,30,450,150));
            }
        }
    }

    /**
     * gets called when coin is pressed. Also initializes the initial coordinates of the circle
     *
     * @param event - mouse event
     * @param coins - coins object which is being moved
     */
    public void coinPressed(MouseEvent event, Coins coins) {
        circleX = coins.getX();
        circleY = coins.getY();
    }

    /**
     * It is called which coin is dragged. Sets the new x and y coordinates for coins as it gets moved
     * It draws the coin to board only if coin is moveable
     *
     * @param event - mouse event
     * @param coins - coin getting moved
     */
    public void coinDragged(MouseEvent event, Coins coins) {
        if(!coins.isPressed() && !isPrevious) {
            coins.setColor(Color.RED);
            coins.setX(coins.getX() + event.getX());
            coins.setY(coins.getY() + event.getY());
            coins.draw(event);
        }
    }

    /**
     * It checks if the coin is releasable and is a valid move.
     * Checks for if the game is still playable , if not displays results
     * changes current object of circle or rectangle after it is placed in the board
     * Reads data from file and displays to screen and also stores data in file for new game
     * Sets back and send the coin back if the move was unsuccessful
     *
     * @param event - Mouse event
     * @param coins - coin getting released
     */
    public void coinReleased(MouseEvent event, Coins coins) {
        int xCoordinate = (int)coins.getX() / rectangleSize;
        int yCoordinate = (int)coins.getY() / rectangleSize;
        int num = Integer.parseInt(coins.getText().getText());
        if((xCoordinate <= 3 && yCoordinate <= 3 && !grid[xCoordinate][yCoordinate].getFill().equals(Color.NAVY) && BoardUtils.isValidMove(gridNumbers,num ,yCoordinate, xCoordinate))) {
            coins.setX(rectangleSize / 2 + rectangleSize * xCoordinate);
            coins.setY(rectangleSize / 2 + rectangleSize * yCoordinate);
            coins.draw(event);
            coins.setPressed(Boolean.TRUE);
            coins.getCircle().setFill(Color.GREEN);
            gridNumbers[yCoordinate][xCoordinate] = Integer.parseInt(coins.getText().getText());
            grid[xCoordinate][yCoordinate].setFill(Color.NAVY);
            finalScore++;
            if (remainingNumbers.containsKey(num)) {
                remainingNumbers.put(num, remainingNumbers.get(num) - 1);
            }
            if(!BoardUtils.isPlayable(gridNumbers,grid,remainingNumbers)) {
                displayGameResults();
                try {
                    FileManager.storePlayerScores(playerName,finalScore);
                    FileManager.updatePlayersDetails(gridNumbers,coinsList,playerName+".txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            coins.setX(circleX);
            coins.setY(circleY);
            coins.draw(event);
            if(!coins.isPressed()) {
                coins.getCircle().setFill(Color.BLUEVIOLET);
            }
        }
    }

    /**
     * Creates the board of a particular dimension and creates each rectangle as a board piece
     * Groups the same numbers to be put in the board and whose coins are to be created.
     * Adds the numbers to the boards for respective rectangle or board piece
     *
     * @param pane - board pane
     * @param numbers - numbers to be put up on grid
     */
    private static void createGridRectangle(Pane pane, ArrayList<Integer> numbers){
        grid = new Rectangle[blocks][blocks];
        int count = 0;
        for (int i = 0; i < dimension; i += rectangleSize) {
            for (int j = 0; j < dimension; j += rectangleSize) {
                Rectangle r = new Rectangle(i, j, rectangleSize, rectangleSize);
                grid[i / rectangleSize][j / rectangleSize] = r;
                r.setFill(Color.BLACK);
                r.setStroke(Color.WHITE);
                int id = count + i / 100;
                int num = numbers.get(id);
                if (remainingNumbers.containsKey(num))
                    remainingNumbers.put(num, remainingNumbers.get(num) + 1);
                else
                    remainingNumbers.put(num, 1);
                int centerX = i + dimension / 10;
                int centerY = j + dimension / 10 + rectangleSize / 4;
                gridNumbers[j / 100][i / 100] = numbers.get(id);
                count++;
                Text text = BoardUtils.setText(new Text(centerX,centerY,String.valueOf(num)),Color.WHITE,35);
                pane.getChildren().addAll(r, text);
            }
            count--;
        }
    }

    /**
     * Creates coins with Circle class and assigns the respective number to it
     * Gives coordinates, color and dimension to the coins
     * Fetches appropriate mouse event when coin is dragged,released or pressed
     *
     * @param pane - grid pane
     * @param numbers - numbers list to be out in coins
     */
    public void createCoins(Pane pane,ArrayList<Integer> numbers){
        coinsList = new ArrayList<Coins>();
        List keys = new ArrayList(remainingNumbers.keySet());
        pane.getChildren().add(BoardUtils.setTextLabel("C.O.I.N.S",Color.BLUE, 50,850,0));
        for (int i = 0; i < numbers.size(); i++) {
            Circle circle = new Circle();
            circle.setFill(Color.BLUEVIOLET);
            circle.setStroke(Color.BLUEVIOLET);
            double radius = rectangleSize / 3.0;
            int x = 0;
            int y = 0;
            if(keys.get(0).equals(numbers.get(i))) {
                x = rectangleSize / 2 + rectangleSize + 800;
                y = rectangleSize / blocks + rectangleSize + 100;
            }else if(keys.get(1).equals(numbers.get(i))) {
                x = rectangleSize / 2 + rectangleSize + 700;
                y = rectangleSize / blocks + rectangleSize + 100;
            }else if(keys.get(2).equals(numbers.get(i))) {
                x = rectangleSize / 2 + rectangleSize + 900;
                y = rectangleSize / blocks + rectangleSize + 100;
            }else if(keys.get(3).equals(numbers.get(i))) {
                x = rectangleSize / 2 + rectangleSize + 800;
                y = rectangleSize / blocks + rectangleSize;
            }else if(keys.get(4).equals(numbers.get(i))) {
                x = rectangleSize / 2 + rectangleSize + 800;
                y = rectangleSize / blocks + rectangleSize + 200;
            }
            Text text = BoardUtils.setText(new Text(String.valueOf(numbers.get(i))),Color.BLACK,29);
            Coins coin = new Coins(x, y, radius, circle, text);
            coinsList.add(coin);
            circle.setOnMousePressed(event -> coinPressed(event, coin));
            circle.setOnMouseDragged(event -> coinDragged(event, coin));
            circle.setOnMouseReleased(event -> coinReleased(event, coin));
            pane.getChildren().addAll(circle, text);
            coin.draw(null);
        }
    }

    /**
     * Loads previous state boards values.
     * Iterates over the panes children list and replaces current state numbers with old state's
     *
     * @param ractGrid - grid values in each board piece
     */
    private void loadPreviousRectangles(int[][] ractGrid){
        ArrayList<Integer> ract = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4 ; j++){
                ract.add(ractGrid[j][i]);
            }
        }
        int n = 0;
        for(Node node: pane.getChildren()){
            if(node instanceof Text){
               if(((Text)node).getFont().getSize() == 35){
                   ((Text)node).setText(String.valueOf(ract.get(n)));
                   n++;
               }
            }
        }
    }

    /**
     * Loads the coins previous data. Assigns coin's coordinates and numbers to current coins and takes back to previous state
     * Calculates old score of finished state.
     * Brings back old rectangles state and replaces with current loaded ones
     *
     * @param coinsData - coins previous data
     */
    private void loadPreviousCoins(ArrayList<String> coinsData){
        int i = 0;
        for(Node node: pane.getChildren()) {
            if (node instanceof Text) {
                if (((Text) node).getFont().getSize() == 29) {
                    String[] coin = coinsData.get(i).split(",");
                    ((Text) node).setTranslateX(Double.parseDouble(coin[0]) - 10);
                    ((Text) node).setTranslateY(Double.parseDouble(coin[1]) + 10);
                    ((Text) node).setText(String.valueOf(coin[2]));
                    i++;
                }
            }
        }
        i = 0;
        finalScore = 0;
        for(Node node: pane.getChildren()) {
            if(node instanceof Circle){
                String[] coin = coinsData.get(i).split(",");
                ((Circle)node).setTranslateX(Double.parseDouble(coin[0]));
                ((Circle)node).setTranslateY((Double.parseDouble(coin[1])));
                if(node.getTranslateX() < 400 && node.getTranslateY() < 400){
                    int gridx = (int)node.getTranslateX() / rectangleSize;
                    int gridy = (int)node.getTranslateY() / rectangleSize;
                    ((Circle) node).setFill(Color.GREEN);
                    grid[gridx][gridy].setFill(Color.NAVY);
                    finalScore++;
                }
                i++;
            }
        }
    }

    /**
     * Displays games result, final score and current games sate.
     * Displays old scores if previous records are avilable or displays nothing
     */
    private void displayGameResults(){
        pane.getChildren().add(BoardUtils.setTextLabel("Game Over",Color.RED,30,150,450));
        pane.getChildren().add(BoardUtils.setTextLabel("Score : " + finalScore,Color.BROWN,30,550,200));
        String oldScores = FileManager.getPreviousHighScores(playerName);
        if(!oldScores.trim().isEmpty()) {
            pane.getChildren().add(BoardUtils.setTextLabel("Previous  Scores : " + oldScores, Color.BROWN, 30, 450, 150));
        }
    }
}
