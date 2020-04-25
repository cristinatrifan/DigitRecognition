package recognition;
import java.util.*;

public class Main {
    public static void main(String[] args) {
       Main obj = new Main();

       //create immutable lists with the weights values
       Main.createWeights();

        System.out.println("Input grid: ");

       //calculate output based on weights and cells values //
       obj.calcBestFit(obj.interpretCellvalues());
    }

    private static List<Integer> listWeights0;
    private static List<Integer> listWeights1;
    private static List<Integer> listWeights2;
    private static List<Integer> listWeights3;
    private static List<Integer> listWeights4;
    private static List<Integer> listWeights5;
    private static List<Integer> listWeights6;
    private static List<Integer> listWeights7;
    private static List<Integer> listWeights8;
    private static List<Integer> listWeights9;

    private static void createWeights() {
        //15 weights and a bias - immutable lists
        listWeights0 = List.of(
                1,1,1,1,-1,1,1,-1,1,1,-1,1,1,1,1,-1);
        listWeights1 = List.of(
                -1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,6);
        listWeights2 = List.of(
                1,1,1,-1,-1,1,1,1,1,1,-1,-1,1,1,1,1);
        listWeights3 = List.of(
                1,1,1,-1,-1,1,1,1,1,-1,-1,1,1,1,1,0);
        listWeights4 = List.of(
                1,-1,1,1,-1,1,1,1,1,-1,-1,1,-1,-1,1,2);
        listWeights5 = List.of(
                1,1,1,1,-1,-1,1,1,1,-1,-1,1,1,1,1,0);
        listWeights6 = List.of(
                1,1,1,1,-1,-1,1,1,1,1,-1,1,1,1,1,-1);
        listWeights7 = List.of(
                1,1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,3);
        listWeights8 = List.of(
                1,1,1,1,-1,1,1,1,1,1,-1,1,1,1,1,-2);
        listWeights9 = List.of(
                1,1,1,1,-1,1,1,1,1,-1,-1,1,1,1,1,-1);
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

    private int calcOutput(List<Integer> listWeighs,
                            ArrayList<Integer> cellValues) {

        int output = 0;
        int index = 0 ;

        while (index < 15) {
            output += listWeighs.get(index) * cellValues.get(index);
            index++;
        }
        output += listWeighs.get(15);
        return output;
    }

    private void calcBestFit(ArrayList<Integer> cellValues) {
           TreeMap<Integer, Integer> allOutputs
                   = new TreeMap<Integer, Integer>();

        allOutputs.put(this.calcOutput(listWeights0, cellValues), 0);
        allOutputs.put(this.calcOutput(listWeights1, cellValues), 1);
        allOutputs.put(this.calcOutput(listWeights2, cellValues), 2);
        allOutputs.put(this.calcOutput(listWeights3, cellValues), 3);
        allOutputs.put(this.calcOutput(listWeights4, cellValues), 4);
        allOutputs.put(this.calcOutput(listWeights5, cellValues), 5);
        allOutputs.put(this.calcOutput(listWeights6, cellValues), 6);
        allOutputs.put(this.calcOutput(listWeights7, cellValues), 7);
        allOutputs.put(this.calcOutput(listWeights8, cellValues), 8);
        allOutputs.put(this.calcOutput(listWeights9, cellValues), 9);

        System.out.println("This number is "
                + allOutputs.lastEntry().getValue());
    }
}
