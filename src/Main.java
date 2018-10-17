import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    ArrayList<Point> data = new ArrayList<>();
    int classes = 21;
    int vocab = 61189;
    int[] documents = new int[classes];
    String[] vocabulary = new String[vocab];
    int totalDocs = 0;

    public static void main(String[] args){

        Main main = new Main();
        main.init();
    }

    public void init(){
        for (int i = 0; i < classes; i++) documents[i] = 0;
        readFile();
        for (int i = 0; i < classes; i++) totalDocs += documents[i];
        naiveBayes();
    }

    private void naiveBayes(){
        int[] py = mle();
        double[][] pxy = map();
    }

    private int[] mle(){
        int[] py = new int[classes];
        for (int i = 0; i < classes; i++) {
            py[i] = documents[i]/totalDocs;
        }
        return py;
    }

    private double[][] map(){
        double[][] pxy = new double[classes][vocab];
        int[][] wordCount = new int[classes][vocab];
        int[] totalWordsinClass = new int[classes];
        double beta = 1/vocab;
        double alpha = 1+beta;

        for (int i = 0; i < classes; i++) {
            for (Point p : data) {
                if (p.className == i) totalWordsinClass[i] += p.value;
            }
        }

        for (int i = 0; i < classes; i++) {
            for (Point p : data) {
                for (int j = 0; j < vocab; j++) {
                    if(p.className == i && p.y == j){
                        wordCount[i][j]+=p.value;
                    }
                }
            }
        }

        ArrayList<Point> dataCopy = removeDuplicates();
        for (int i = 0; i < classes; i++) {
            for (Point p: dataCopy){
                if(p.className == i){
                    System.out.print(p.x+", "+p.y+", "+p.value+", "+p.className+", "+wordCount[i][p.y]+": ");
                    pxy[i][p.y] = ((wordCount[i][p.y])+(alpha-1))/(totalWordsinClass[i] +((alpha-1)*vocab));
                    System.out.println(pxy[i][p.y]);
                }
            }
        }
        System.out.println();
        return pxy;
    }

    private ArrayList<Point> removeDuplicates() {
        ArrayList<Point> dataCopy = new ArrayList<>();
        boolean isAdd = true;

        dataCopy.add(data.get(0));

        for(Point p: data){
            for(Point p1: dataCopy){
                if(p1.y==p.y && p1.className==p.className){
                    isAdd = false;
                    break;
                }
                isAdd = true;
            }
            if(isAdd){
                dataCopy.add(p);
            }
        }

        return dataCopy;
    }

    private void readFile(){

        String word;
        try {

            InputStream in = getClass().getResourceAsStream("/test");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            InputStream in2 = getClass().getResourceAsStream("/vocabulary.txt");
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));

            BufferedWriter writer = new BufferedWriter(new FileWriter("src/out.csv", false));
            int i = 0;
            while ((word = reader.readLine()) != null) {
                String[] wordList = word.split(",");
                int className = Integer.parseInt(wordList[wordList.length-1]);
                documents[className] += 1;
                for (int j = 1; j < wordList.length-1; j++) {
                    if(!wordList[j].equals("0")){
                        int val = Integer.parseInt(wordList[j]);
                        data.add(new Point(i, j, val, className));
                    }
                }
                i++;
            }

            i = 0;
            while ((word = reader2.readLine()) != null) {
                vocabulary[i] = word;
                i++;
            }

            for(Point p : data){
                writeToFile(writer, p);
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error while reading file");
        }
    }

    private void writeToFile(BufferedWriter writer, Point p) throws IOException {
        String str = p.x+","+p.y+","+p.value+","+p.className+"\n";
        writer.append(' ');
        writer.append(str);
    }

}