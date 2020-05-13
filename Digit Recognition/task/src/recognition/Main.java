package recognition;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
       Main obj = new Main();
       int i = 0;

        System.out.println("1. Learn the network");
        System.out.println("2. Guess a number");
        System.out.println("Your choice: ");

        Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
            }

        if (i == 1) {
            // create random initial weights/read already existing weights
            // calculate output neurons
            // calculate delta weight for each possible grid - use file with weights
            // save weights to file
            for (int j = 0; j < 1000; j++) {
                createCellsGrid();
                obj.createInitialWeights();
                //obj.writeDeltaWeights(obj.calculateDeltaWeights(obj.calculateOutputNeurons()));
                obj.writeDeltaWeights(obj.calcWeights2());

            }
        }

        else if (i == 2) {
            System.out.println("Input grid: ");

       //create/read immutable lists with the weights values from file
       obj.createWeights();

       //calculate output based on weights and cells values
       obj.calcBestFit(obj.interpretCellValues(scanner));
        }
    }

    private static Map<Integer, List<Integer>> mapCellsGrid;
    private Map<Integer, List<Double>> initialWeights;

    private void createInitialWeights() throws FileNotFoundException {

        initialWeights = this.readDeltaWeights();
        if (initialWeights.size() <= 1) {
             Random random = new Random();
            for (int i = 0; i < 10; i++) {
                List<Double> initialWeightsList = new ArrayList<>();
                for (int j = 0; j < 15; j++) {
                 initialWeightsList.add(random.nextGaussian());
                //    initialWeightsList.add(0.0);
                }
                initialWeights.put(i, initialWeightsList);
            }
        }
    }

    //calculate the output neurons based on random initial weights:
    private Map<Integer, Double> calculateOutputNeurons() {
        Map<Integer, Double> outputNeurons = new HashMap<Integer, Double>();

        //calculate based on formula On = S(isCelActiven * initialWeight)
        for (int i = 0; i < 10; i++) {
            Double outputNeuron = 0.0;
            for (int j = 0; j < 15; j++) {
                outputNeuron = outputNeuron + (mapCellsGrid.get(i).get(j) * initialWeights.get(i).get(j));
            }
            //transform outputNeuron with sigmoid function
            outputNeuron = 1 / (1 + Math.pow(Math.E, (outputNeuron * -1)));
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
                        deltaanon = deltaanon + (0.5 * mapCellsGrid.get(k).get(j)
                            * (mapCellsGrid.get(i).get(j) - outputNeurons.get(i)));
                    }
                    deltaanon = initialWeights.get(i).get(j) + deltaanon / 10;
                    listdeltaWeights.add(deltaanon);
                }
                mapDeltaWeights.put(i, listdeltaWeights);
        }

        return mapDeltaWeights;
    }

    private Map<Integer, List<Double>> calcWeights2() {
        Map<Integer, List<Double>> mapDeltaWeights = new HashMap<>();
        Map<Integer, List<Double>> mapWeights = new HashMap<>();
        Map<Integer, Double> outputNeurons = new HashMap<Integer, Double>();

        //calculate based on formula On = S(isCelActiven * initialWeight)
        for (int i = 0; i < 10; i++) {
            Double outputNeuron = 0.0;
            for (int j = 0; j < 15; j++) {
                outputNeuron = outputNeuron + (mapCellsGrid.get(i).get(j)
                        * initialWeights.get(i).get(j));
            }
            //transform outputNeuron with sigmoid function
            outputNeuron = 1 / (1 + Math.pow(Math.E, (outputNeuron * -1)));
            //add neuron value to neuron list
           // outputNeurons.put(i, outputNeuron);
        }
        // for each output neuron

        for (int x = 0;x < 10; x++) {
            List<Double> listWeights = new ArrayList<>();
            for (int i = 0; i < 10; i++) {

                Double outputNeuron = 0.0;
                List<Double> listdeltaWeights = new ArrayList<>();
                //for each ideal value of gridcells of the neuron
                for (int y = 0; y < 15; y++) {
                    outputNeuron += mapCellsGrid.get(i).get(y)
                            * initialWeights.get(x).get(y);
                }
                outputNeuron = 1 / (1 + Math.pow(Math.E, (outputNeuron * -1)));

                for (int a = 0; a < 15; a++) {
                    Double deltaanon = 0.00;
                    int ideal = x == i ? 1 : 0;
                    deltaanon = 0.5 * mapCellsGrid.get(i).get(a) * (ideal - outputNeuron);
                    listdeltaWeights.add(deltaanon);
                }
                mapDeltaWeights.put(i, listdeltaWeights);
            }

                    for (int r = 0; r < 15; r++) {
                        Double mean = 0.0;
                        for (int d = 0; d < 10; d++) {
                            mean +=mapDeltaWeights.get(d).get(r);
                        }
                        listWeights.add(mean / 10);
                    }
                        mapWeights.put(x, listWeights);
            }

        return mapWeights;
    }

    private void writeDeltaWeights(Map<Integer, List<Double>> mapDeltaWeights) {

        try (FileWriter writer = new FileWriter("C:/Users/3058/OneDrive - Q_PERIOR AG/Desktop/Inspiri/orga/deltaWeights.txt", false)) {

            for (var item : mapDeltaWeights.entrySet()) {
                 writer.write(item.getKey().toString());
                 writer.write("  ");
                 item.getValue().forEach(x -> {
                     try {
                         writer.write(" " + x.toString() + " ");
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 });
                 writer.write("  ");
            }

          System.out.println(mapDeltaWeights.toString());
        }
        catch (Exception e) {
            System.out.println("File writing error occurred");
        }
    }

    private Map<Integer, List<Double>> readDeltaWeights() throws FileNotFoundException {
        //deltaWeightsFile
        Map<Integer, List<Double>> deltaWeightsTempFile = new HashMap<Integer, List<Double>>();
        File file = new File("C:/Users/3058/OneDrive - Q_PERIOR AG/Desktop/Inspiri/orga/deltaWeights.txt");
        Scanner scanner = new Scanner(file);

        for (int i = 0; i < 10; i++) {
            int neuron = 0;
            List<Double> list = new ArrayList<>();

            if (scanner.hasNextInt()) {
                neuron = scanner.nextInt();
            }

            for (int j = 0; j < 15; j++) {
                if (scanner.hasNextDouble()) {
                    list.add(scanner.nextDouble());
                }
            }
            deltaWeightsTempFile.put(neuron, list);
        }

        scanner.close();
        return deltaWeightsTempFile;
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

    private void createWeights() throws FileNotFoundException {
        //10neurons x 15 weights - read from file

        initialWeights = this.readDeltaWeights();

    }

    private ArrayList<Integer> interpretCellValues(Scanner scanner) {
        String str = "";
        ArrayList<Integer> cellValues = new ArrayList<>();

            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                for (int i = 0; i < str.length(); i++) {
                    if (String.valueOf(str.charAt(i)).equals("X")) {
                        cellValues.add(1);
                    } else {
                        cellValues.add(0);
                    }
                }
            }

            scanner.close();
        return cellValues;
    }

    private Double calcOutput(List<Double> listWeighs,
                            ArrayList<Integer> cellValues) {

        Double output = 0.0;
        int index = 0 ;

        while (index < 15) {
            output = output + (listWeighs.get(index) * cellValues.get(index));
            index++;
        }

       // double outint = output * -1;
       // double pow = Math.pow(Math.E, outint);
       // output = 1 / (1 + Math.pow(Math.E, (output * -1)));

        return output;
    }

    private void calcBestFit(ArrayList<Integer> cellValues) {
           TreeMap<Double, Integer> allOutputs
                   = new TreeMap<Double, Integer>();
        double[] arr = new double[15];

        for (int i = 0; i < 10; i++) {
                   allOutputs.put(this.calcOutput(initialWeights.get(i), cellValues), i);
                   arr[i] = this.calcOutput(initialWeights.get(i), cellValues);
        }

        Arrays.sort(arr);

        System.out.println("This number is "
                + allOutputs.get(allOutputs.lastKey()));
    }
}