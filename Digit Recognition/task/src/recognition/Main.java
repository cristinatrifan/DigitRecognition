package recognition;
import java.util.*;

public class Main {
    public static void main(String[] args) {
       Main obj = new Main();

       //create an immutable map with the weights values
       Main.createWeights();

        System.out.println("Input grid: ");
       //get the state of cells an map them in an Array with values 0 or 1
       //calculate output based on weights and cells values
       obj.calcOutput(getListWeights(), obj.interpretCellvalues());

    }

    public static List<Integer> getListWeights() {
        return listWeights;
    }

    public static void setListWeights(List<Integer> listWeights) {
        Main.listWeights = listWeights;
    }

    private static List<Integer> listWeights;

    private static void createWeights() {
        List<Integer> listWeights = List.of(
                //9 weights and a bias
                2, 1, 2, 4, -4, 4, 2, -1, 2, -5
        );
      setListWeights(listWeights);
    }

    private ArrayList<Integer> interpretCellvalues() {
        Scanner scanner = new Scanner(System.in);
        String str = "";
        ArrayList<Integer> cellValues = new ArrayList<>();

        while (scanner.hasNextLine()){
            str = scanner.nextLine();
            for (int i = 0; i < str.length(); i++) {
               if (String.valueOf(str.charAt(i)).equals("X")) {
                   cellValues.add(1);
               } else {
                   cellValues.add(0);
               }
            }
        }
        return cellValues;
    }

    private void calcOutput(List<Integer> listWeighs,
                            ArrayList<Integer> cellValues) {

        int output = 0;
        int index = 0 ;

        while (index < 9) {
            output += listWeighs.get(index) * cellValues.get(index);
            index++;
        }
        output += listWeighs.get(9);

        int out = (output > 0)
                ? 0
                : 1;

        System.out.println("This number is " + out);
    }
}
