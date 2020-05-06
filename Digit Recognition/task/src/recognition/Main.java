package recognition;
import java.io.CharArrayWriter;
import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
       Main obj = new Main();
       int i = 0;

        System.out.println("1. Learn the network");
        System.out.println("2. Guess a number");
        System.out.println("Your choice: ");

        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
            }
        }
        catch (Exception e) {
            System.out.println("Please enter 1 or 2");
        }

        if (i == 1) {
            //create random initial weights
            //calculate output neurons
            //calculate delta weight for each possible grid - use file with weights
            //save weights to file

            createCellsGrid();
            obj.createInitialRandomWeights();
            obj.writeDeltaWeights(obj.calculateDeltaWeights(obj.calculateOutputNeurons()));

        }
        else if (i == 2) {

       //create immutable lists with the weights values
       Main.createWeights();// will change with weights from file

        System.out.println("Input grid: ");

       //calculate output based on weights and cells values
       obj.calcBestFit(obj.interpretCellvalues());//will use sigmoid instead of bias
        }

    }

 //   public static List<Integer> getListWeights() { return listWeights; }
 //   public static void setListWeights(List<Integer> listWeights) {Main.listWeights = listWeights;}

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

    private static Map<Integer, List<Integer>> mapCellsGrid;

    private List<Double> initialRandomWeights;

    private void createInitialRandomWeights() {
        Random random = new Random();
        initialRandomWeights = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            initialRandomWeights.add(random.nextDouble());
        }
    }

    //calculate the output neurons based on random initial weights:
    private Map<Integer, Double> calculateOutputNeurons() {
        Map<Integer, Double> outputNeurons = new HashMap<Integer, Double>();

        //calculate based on formula On = S(isCelActiven * initialWeight)
        for (int i = 0; i < 10; i++) {
            Double outputNeuron = 0.0;
            for (int j = 0; j < 15; j++) {
                outputNeuron += mapCellsGrid.get(i).get(j) * initialRandomWeights.get(j);
            }
            //transform outputNeuron with sigmoid function
            outputNeuron = 1 / (1 + Math.pow(Math.E, -outputNeuron));
            //add neuron value to neuron list
            outputNeurons.put(i, outputNeuron);
        }

        return outputNeurons;
    }

    //calculate delta weight for each possible grid - use file with weights
    private Map<Integer, List<Double>> calculateDeltaWeights (Map<Integer, Double> outputNeurons) {
        Map<Integer, List<Double>> mapDeltaWeights = new HashMap<>();

        // for each output neuron
        for (int i = 0; i < 10; i++) {
            List<Double> listdeltaWeights = new ArrayList<>();
            //for each ideal value of gridcells of the neuron
                for (int j = 0; j < 15; j++) {
                    Double deltaanon = 0.00;
                    //for each activation value of the number we are testing
                    for (int k = 0; k < 10; k++) {
                        deltaanon =+ 0.5 * mapCellsGrid.get(k).get(j)
                            * (mapCellsGrid.get(i).get(j) - outputNeurons.get(i));
                }
                    deltaanon = deltaanon / 10;
                    listdeltaWeights.add(deltaanon);
            }
                mapDeltaWeights.put(i, listdeltaWeights);
        }

        return mapDeltaWeights;
    }

    private void writeDeltaWeights(Map<Integer, List<Double>> mapDeltaWeights) {
        //C:\Users\3058\OneDrive - Q_PERIOR AG\Desktop\Inspiri\orga\deltaWeights.docx

        try (FileWriter writer = new FileWriter("C:/Users/3058/OneDrive - Q_PERIOR AG/Desktop/Inspiri/orga/deltaWeights.txt", false)) {

            for (var item : mapDeltaWeights.entrySet()) {
                 writer.write(item.getKey().toString());
                 writer.write(item.getValue().toString());
            }

          System.out.println(mapDeltaWeights.toString());
        }
        catch (Exception e) {
            System.out.println("File writing error occurred");
        }
    }

    private static void createWeights() {
        //15 weights and a bias
        listWeights0 = List.of(
                1,1,1,1,-1,1,1,-1,1,1,-1,1,1,1,1,-1,0);
        listWeights1 = List.of(
                -1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,6,1);
        listWeights2 = List.of(
                1,1,1,-1,-1,1,1,1,1,1,-1,-1,1,1,1,1,2);
        listWeights3 = List.of(
                1,1,1,-1,-1,1,1,1,1,-1,-1,1,1,1,1,0,3);
        listWeights4 = List.of(
                1,-1,1,1,-1,1,1,1,1,-1,-1,1,-1,-1,1,2,4);
        listWeights5 = List.of(
                1,1,1,1,-1,-1,1,1,1,-1,-1,1,1,1,1,0,5);
        listWeights6 = List.of(
                1,1,1,1,-1,-1,1,1,1,1,-1,1,1,1,1,-1,6);
        listWeights7 = List.of(
                1,1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,3,7);
        listWeights8 = List.of(
                1,1,1,1,-1,1,1,1,1,1,-1,1,1,1,1,-2,8);
        listWeights9 = List.of(
                1,1,1,1,-1,1,1,1,1,-1,-1,1,1,1,1,-1,9);
    }

    private static void createCellsGrid() {
        //10 lists x 15 activations of the ideal grid for each number 0-9

        mapCellsGrid = Map.of(
        0, List.of(
                1,1,1,1,0,1,1,0,1,1,0,1,1,1,1),
        1, List.of(
                0,1,0,0,1,0,0,1,0,0,1,0,0,1,0),
        2, List.of(
                1,1,1,0,0,1,1,1,1,1,0,0,1,1,1),
        3, List.of(
                1,1,1,0,0,1,1,1,1,0,0,1,1,1,1),
        4, List.of(
                1,0,1,1,0,1,1,1,1,0,0,1,0,0,1),
        5, List.of(
                1,1,1,1,0,0,1,1,1,0,0,1,1,1,1),
        6, List.of(
                1,1,1,1,0,0,1,1,1,1,0,1,1,1,1),
        7, List.of(
                1,1,1,0,0,1,0,0,1,0,0,1,0,0,1),
        8, List.of(
                1,1,1,1,0,1,1,1,1,1,0,1,1,1,1),
        9, List.of(
                1,1,1,1,0,1,1,1,1,0,0,1,1,1,1)
        );
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
