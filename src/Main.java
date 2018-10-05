import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    ArrayList<Integer> nums = new ArrayList<>();
    TreeMap<TreeMap<Integer, Integer>, Integer> numbers = new TreeMap<>();

    public static void main(String[] args){

        Main main = new Main();
        main.readFile();

        for(int n: main.nums){
            if(n == 1000) System.out.println("\n");
            else if(n != 0) System.out.print(n+"\t");
            else System.out.print("."+"\t");
        }
        System.out.println("\n\n");

        for(Map.Entry<TreeMap<Integer, Integer>, Integer> entry: main.numbers.entrySet()){
            for(Map.Entry<Integer, Integer> key: entry.getKey().entrySet()){
                System.out.println(key.getKey()+", "+key.getValue()+"=> "+entry.getValue());
            }
        }
    }

    private void readFile(){

        String word;
        try {

            InputStream in = getClass().getResourceAsStream("/test");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            int i = 0;
            int j = 0;
            while ((word = reader.readLine()) != null) {
                j = 0;
                for(char c : word.toCharArray()){
                    int num = Integer.parseInt(c+"");
                    if(num != 0){
                        TreeMap<Integer, Integer> temp = new TreeMap<>();
                        temp.put(i, j);
                        numbers.put(temp, num);
                        nums.add(num);
                    }
                    j++;
                }
                nums.add(1000);
                i++;
            }

        } catch (IOException e) {
            System.out.println("Error while reading file");
        }
    }

}
