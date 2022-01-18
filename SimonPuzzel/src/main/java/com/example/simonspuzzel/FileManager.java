package com.example.simonspuzzel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * File Manager class to store players details in files
 * Store Players last state and load back when required
 *
 * Created By - Mansoor
 */
public class FileManager {

    /**
     * Writes to the highest score file and stores players game records like name and score
     *
     * @param name - player name
     * @param score - player score
     * @throws IOException
     */
    public static void storePlayerScores(final String name, final int score) throws IOException {
        Files.write(Paths.get("HighScore.txt"), ("\n"+ name + ","+score).getBytes(), StandardOpenOption.APPEND);
    }

    /**
     * Gets players at most four previous scores.
     * Retrieves all scores from file and stores in list. Sorts the list and sends scores
     *
     * @param playerName - player name whose score is to be fetched
     * @return - returns players at most 4 top scores
     */
    public static String getPreviousHighScores(final String playerName){
        File file = new File("HighScore.txt");
        Scanner line = null;
        try {
            line = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> topScores = new ArrayList<>();
        String oldScores = "";
        while(line.hasNextLine()) {
            String[] data = line.nextLine().split(",");
            if(data[0].equalsIgnoreCase(playerName)) {
                topScores.add(Integer.parseInt(data[1]));
            }
        }
        Collections.sort(topScores,Collections.reverseOrder());
        int i = 0;
        for(int score : topScores){
            if(i < 4) {
                oldScores = oldScores + score + " ";
                i++;
            }
        }
        return oldScores;
    }

    /**
     * Writes serializable boards state and coins details in a file of the name of player
     * Gets object output stream and stores as object
     *
     * @param record - grid values
     * @param coinsList - coins details
     * @param fileName - file name
     * @throws IOException
     */
    public static void updatePlayersDetails(int[][] record,ArrayList<Coins> coinsList, String fileName) throws IOException {
        FileOutputStream f = new FileOutputStream(new File(fileName));
        ObjectOutputStream o = new ObjectOutputStream(f);
        ArrayList<String> coinsInfo = new ArrayList<>();
        for(Coins coins : coinsList){
            coinsInfo.add(coins.getX()+","+coins.getY()+","+coins.getText().getText());
        }
        o.writeObject(record);
        o.writeObject(coinsInfo);

        o.close();
        f.close();
    }

    /**
     * Reads 2D objects of saved boards state. Deserializes and send back
     *
     * @param fileName - file name
     * @return - 2D array
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static int[][] readRectangles(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File(fileName));
        ObjectInputStream oi = new ObjectInputStream(fi);
        int[][] rectRecords = (int[][]) oi.readObject();
        oi.close();
        fi.close();
        return rectRecords;
    }

    /**
     * Reads coins object which stores all the coins details. Deserializes the object and sends back
     *
     * @param fileName - file name
     * @return - Arraylist of all coins data
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList<String> readCoins(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File(fileName));
        ObjectInputStream oi = new ObjectInputStream(fi);
        oi.readObject();
        ArrayList<String> coinsRecords = (ArrayList<String>) oi.readObject();
        oi.close();
        fi.close();
        return coinsRecords;
    }
}