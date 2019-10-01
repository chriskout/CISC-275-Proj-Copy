import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

//Utility class for reading and writing to high score files
public class Scoring {
    static final int NUM_HIGH_SCORES = 5;
    static  final String FISH_FILE = "Fish";
    static  final String MATCHING_FILE = "Matching";



    public static void sortScores(String file){
        ArrayList<Integer> scores = getScores(file);
        Collections.sort(scores, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        String output = "";
        for (int i = 0; i<NUM_HIGH_SCORES; i ++){
            output += scores.get(i) + "\n";
        }
        try{
            FileOutputStream out = new FileOutputStream("src/output/" + file + "Scores.txt");
            out.write(output.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

     }


    //Outputting score assumes input is matching game or fish game
    public static void storeScore(int val, String file){
        try{
            FileOutputStream out = new FileOutputStream("src/output/" + file + "Scores.txt", true);
            String score = val + "\n";
            out.write(score.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Inputting score sassumes input is matching game or fish game
    public  static ArrayList<Integer> getScores(String file){
        ArrayList<Integer> output = new ArrayList<>();
        try{
            FileInputStream in = new FileInputStream("src/output/" + file + "Scores.txt");
            Scanner scnr = new Scanner(in);
            while(scnr.hasNextInt()) {
                    output.add(scnr.nextInt());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
    public static String scoreString (String file){
        ArrayList<Integer> scores = Scoring.getScores(Scoring.FISH_FILE);
        String scoreString = "";

        int index = 1;
        for (Integer curScore: scores){
            scoreString += index + ": " + curScore + "\n";
            index ++;
        }
        return scoreString;

    }
}
