/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashtable;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Abdelkader
 */
public class HashTableController implements Initializable {

    @FXML
    private MenuBar mainMenu;

    @FXML
    TextArea console;

    @FXML
    TextField _trials;

    @FXML
    TextField _arraySize;

    @FXML
    TextField _seed;

    @FXML
    TextField _insertCount;

    @FXML
    TextField _load;

    @FXML
    TextField _startingSize;

    int arraySize;
    int trials;
    int seed;
    Integer data[];
    String stringData[];
    DictionaryInterface<Integer, Integer> table;
    TreeMap<Integer, Integer> reference;
    int insertCount;
    double load;
    int startingSize;

    HashedDictionaryOpenAddressingLinearInstrumented<String, String> linearTable;
    HashedDictionaryOpenAddressingDoubleInstrumented<String, String> doubleTable;
    HashedDictionaryOpenAddressingPerfectInstrumented<String, String> perfectTable;

    public void TestingLinearHashing() {
        this.RunHashTest(new HashedDictionaryOpenAddressingLinearInstrumented<>(10));
    }

    public void TestingDoubleHashing() {
        this.RunHashTest(new HashedDictionaryOpenAddressingDoubleInstrumented<>(10));
    }

    public void TestingPerfectHashing() {
        this.RunHashTest(new HashedDictionaryOpenAddressingPerfectInstrumented<>(10));
    }

    public void HashPerformance() {
        int insertionLinearProbes = 0, insertionDoubleProbes = 0, insertionPerfectProbes = 0;
        int successLinearProbes = 0, successDoubleProbes = 0, successPerfectProbes = 0;
        int failureLinearProbes = 0, failureDoubleProbes = 0, failurePerfectProbes = 0;
        arraySize = Integer.parseInt(this._arraySize.getText());
        trials = Integer.parseInt(this._trials.getText());
        seed = Integer.parseInt(this._seed.getText());
        insertCount = Integer.parseInt(this._insertCount.getText());
        load = Double.parseDouble(this._load.getText());
        startingSize = Integer.parseInt(this._startingSize.getText());

        this.console.clear();

        for (int i = 0; i < trials; i++) {
            stringData = generateRandomData(insertCount);

            linearTable = new HashedDictionaryOpenAddressingLinearInstrumented<String, String>(startingSize);
            doubleTable = new HashedDictionaryOpenAddressingDoubleInstrumented<String, String>(startingSize);
            perfectTable = new HashedDictionaryOpenAddressingPerfectInstrumented<String, String>(startingSize);

            linearTable.setMaxLoadFactor(load);
            doubleTable.setMaxLoadFactor(load);
            perfectTable.setMaxLoadFactor(load);

            HashedDictionaryOpenAddressingLinearInstrumented.resetTotalProbes();
            HashedDictionaryOpenAddressingDoubleInstrumented.resetTotalProbes();
            HashedDictionaryOpenAddressingPerfectInstrumented.resetTotalProbes();

            this.console.appendText("The data is: \n" + getString(stringData) + "\n\n");
            insertAllData(linearTable, stringData);
            insertAllData(doubleTable, stringData);
            insertAllData(perfectTable, stringData);

            insertionLinearProbes += HashedDictionaryOpenAddressingLinearInstrumented.getTotalProbes();
            insertionDoubleProbes += HashedDictionaryOpenAddressingDoubleInstrumented.getTotalProbes();
            insertionPerfectProbes += HashedDictionaryOpenAddressingPerfectInstrumented.getTotalProbes();
            
            //System.out.println(HashedDictionaryOpenAddressingLinearInstrumented.getTotalProbes());
            
            // ADD CODE HERE TO DO SUCCESSFULL AND FAILURE SEARCHES
        }

        this.console.appendText("Linear hashing\n");
        this.console.appendText("    Total probes for inserting data values: " + insertionLinearProbes + "\n\n");
        this.console.appendText("       Average probes made: " + insertionLinearProbes / (float) (trials * insertCount) + "\n\n");
        // ADD CODE HERE TO REPORT THE RESULTS FOR THE SUCCESS AND FAILURE SEARCHES

        this.console.appendText("Double hashing\n");
        this.console.appendText("    Total probes for inserting data values: " + insertionDoubleProbes + "\n\n");
        this.console.appendText("       Average probes made: " + insertionDoubleProbes / (float) (trials * insertCount) + "\n\n");
        // ADD CODE HERE TO REPORT THE RESULTS FOR THE SUCCESS AND FAILURE SEARCHES

        this.console.appendText("Perfect hashing\n");
        this.console.appendText("    Total probes for inserting data values: " + insertionPerfectProbes + "\n\n");
        this.console.appendText("       Average probes made: " + insertionPerfectProbes / (float) (trials * insertCount) + "\n\n");

        // ADD CODE HERE TO REPORT THE RESULTS FOR THE SUCCESS AND FAILURE SEARCHES
    }

    private void RunHashTest(DictionaryInterface table) {
        arraySize = Integer.parseInt(this._arraySize.getText());
        trials = Integer.parseInt(this._trials.getText());
        seed = Integer.parseInt(this._seed.getText());
        this.console.clear();

        for (int i = 0; i < trials; i++) {
            data = generateRandomArray(arraySize, seed);
            insertFirstHalfIntoDictionary(table, data);

            reference = new TreeMap<>();
            insertFirstHalfIntoReference(reference, data);

            this.console.appendText("The initial data is: \n" + reference + "\n\n");

            // search for all possible values in the data set
            // and a few guaranteed not to be in the set
            boolean failed = false;
            for (int k = -2; k < arraySize + 2; k++) {

                Integer lookFor = new Integer(k);
                //System.out.println("Looking for: " + lookFor);

                Object found = table.getValue(lookFor);
                //System.out.println("The returned value is: " + found);

                Object referenceResult = reference.get(lookFor);

                if (referenceResult == null) {
                    if (found != null) {
                        failed = true;
                        this.console.appendText("***** fails - item: " + k
                                + " should not have been found" + "\n\n");

                    }
                } else {
                    if (!referenceResult.equals(found)) {
                        failed = true;
                        this.console.appendText("***** fails - item: " + k
                                + " should have been found; got " + found + " instead" + "\n\n");

                    }
                }

            }

            if (!failed) {
                this.console.appendText(">>>> passes searching after initial insertion\n\n");
            }

            // Remove the first quarter of the data values (more or less)
            removeQuarterFromDictionary(table, data);
            removeQuarterFromReference(reference, data);

            this.console.appendText("The data after removal is: \n" + reference + "\n\n");

            // Repeat the searches
            failed = false;
            for (int k = -2; k < arraySize + 2; k++) {

                data = generateRandomArray(arraySize, seed);
                Integer lookFor = new Integer(k);
                //System.out.println("Looking for: " + lookFor);

                Object found = table.getValue(lookFor);
                //System.out.println("The returned value is: " + found);

                Object referenceResult = reference.get(lookFor);

                if (referenceResult == null) {
                    if (found != null) {
                        failed = true;
                        this.console.appendText("***** fails - item: " + k
                                + " should not have been found" + "\n\n");
                    }
                } else {
                    if (!referenceResult.equals(found)) {
                        failed = true;
                        this.console.appendText("***** fails - item: " + k
                                + " should have been found; got " + found + " instead\n\n");

                    }
                }

            }

            if (!failed) {
                this.console.appendText(">>>> passes after removing data\n\n");
            }

            // Add in the last half of the data
            insertSecondHalfIntoDictionary(table, data);
            insertSecondHalfIntoReference(reference, data);

            this.console.appendText("The data after addition inserts is: \n" + reference + "\n\n");

            // Repeat the searches
            failed = false;
            for (int k = -2; k < arraySize + 2; k++) {

                data = generateRandomArray(arraySize, seed);
                Integer lookFor = new Integer(k);
                //System.out.println("Looking for: " + lookFor);

                Object found = table.getValue(lookFor);
                //System.out.println("The returned value is: " + found);

                Object referenceResult = reference.get(lookFor);

                if (referenceResult == null) {
                    if (found != null) {
                        failed = true;
                        this.console.appendText("***** fails - item: " + k
                                + " should not have been found\n\n");
                    }
                } else {
                    if (!referenceResult.equals(found)) {
                        failed = true;
                        this.console.appendText("***** fails - item: " + k
                                + " should have been found; got " + found + " instead\n\n");

                    }
                }

            }

            if (!failed) {
                this.console.appendText(">>>> passes after inserting more data\n\n");
            }

            seed++;
        } //end for
    }

    private String[] generateRandomData(int size) {
        String result[] = new String[size];
        HashedDictionaryOpenAddressingLinear<String, String> checkTable = new HashedDictionaryOpenAddressingLinear<String, String>();

        String firstSyl[] = {"ther", "fal", "sol", "cal", "com", "don", "gan", "tel", "fren", "ras", "tar", "men", "tri", "cap", "har"};
        String secondSyl[] = {"mo", "ta", "ra", "te", "bo", "bi", "du", "ca", "dan", "sen", "di", "no", "fe", "mi", "so"};
        String thirdSyl[] = {"tion", "ral", "tal", "ly", "nance", "tor", "ing", "ger", "ten", "ful", "son", "dar", "der", "den", "ton"};

        // ADD CODE TO GENERATE THE RANDOM WORDS
        //>>>>>>>>>>> ADDED CODE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        Random rand = new Random();
        
        for (int i = 0; i < size; i++){
            String word = firstSyl[rand.nextInt(firstSyl.length)] + secondSyl[rand.nextInt(secondSyl.length)] + thirdSyl[rand.nextInt(thirdSyl.length)];    
            result[i] = word;
        }
               
        return result;
    }

    /**
     * Insert all of the values in the array into the hash table
     *
     * @param dict the dictionary to insert all the words into
     */
    private void insertAllData(DictionaryInterface<String, String> dict, String[] data) {
        for (int i = 0; i < data.length; i++) {
            dict.add(data[i], data[i]);
        }
    }

    private Integer[] generateRandomArray(int size, int seed) {

        Integer result[] = new Integer[size];
        Random generator = new Random(seed);

        for (int i = 0; i < size; i++) {
            int value = generator.nextInt(size);
            result[i] = new Integer(value);
        }

        return result;
    }

    /**
     * Insert the first half of the data values from an array of objects into a
     * dictionary.
     *
     * @param aDictionary The dictionary that the values will be inserted into.
     * @param keys An array of objects that will be the keys for insertion into
     * the dictionary. The value will just be the key itself.
     */
    private void insertFirstHalfIntoDictionary(DictionaryInterface<Integer, Integer> aDictionary, Integer[] keys) {

        for (int i = 0; i < keys.length / 2; i++) {
            aDictionary.add(keys[i], keys[i]);
        }
    }

    /**
     * Insert the first half of the data values from an array of objects into a
     * tree map.
     *
     * @param referenceMap A tree map that the values will be inserted into.
     * @param keys An array of integers that will be the keys for insertion into
     * the map. The value will just be the key itself.
     */
    private void insertFirstHalfIntoReference(TreeMap<Integer, Integer> referenceMap, Integer[] keys) {

        for (int i = 0; i < keys.length / 2; i++) {
            referenceMap.put(keys[i], keys[i]);
        }
    }

    /**
     * Insert the second half of the data values from an array of objects into a
     * dictionary.
     *
     * @param aDictionary The dictionary that the values will be inserted into.
     * @param keys An array of integers that will be the keys for insertion into
     * the dictionary. The value will just be the key itself.
     */
    private void insertSecondHalfIntoDictionary(DictionaryInterface<Integer, Integer> aDictionary, Integer[] keys) {

        for (int i = keys.length / 2 + 1; i < keys.length; i++) {
            aDictionary.add(keys[i], keys[i]);
        }
    }

    /**
     * Insert the second half of the data values from an array of objects into a
     * tree map.
     *
     * @param referenceMap A tree map that the values will be inserted into.
     * @param keys An array of Integers that will be the keys for insertion into
     * the map. The value will just be the key itself.
     */
    private void insertSecondHalfIntoReference(TreeMap<Integer, Integer> referenceMap, Integer[] keys) {

        for (int i = keys.length / 2 + 1; i < keys.length; i++) {
            referenceMap.put(keys[i], keys[i]);
        }
    }

    /**
     * Remove the first quarter of the data values from the dictionary.
     *
     * @param aDictionary The dictionary that the values will be deleted from.
     * @param keys Ab array of integers that will be the keys for deletion from
     * the dictionary.
     */
    private void removeQuarterFromDictionary(DictionaryInterface<Integer, Integer> aDictionary, Integer[] keys) {

        for (int i = 0; i < keys.length / 4; i++) {
            aDictionary.remove(keys[i]);
        }
    }

    /**
     * Remove the first quarter of the data values from the tree map.
     *
     * @param referenceMap A tree map that the values will be deleted from.
     * @param keys An array of objects that will be the keys for deletion the
     * map.
     */
    private void removeQuarterFromReference(TreeMap<Integer, Integer> referenceMap, Integer[] keys) {

        for (int i = 0; i < keys.length / 4; i++) {
            referenceMap.remove(keys[i]);
        }
    }

// ADD A METHOD HERE TO INSERT THE FIRST HALF OF THE DATA VALUES IN AN ARRAY
// INTO A DICTIONARY
//>>>>>>>>>>> ADDED CODE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// ADD A METHOD HERE TO SEARCH FOR ITEMS FROM THE FIRST HALF OF THE ARRAY
// (SUCCESS SEARCHES)
//>>>>>>>>>>> ADDED CODE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// ADD A METHOD HERE TO SEARCH FOR ITEMS FROM THE SECOND HALF OF THE ARRAY
// (FAILURE SEARCHES)
//>>>>>>>>>>> ADDED CODE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * A displayable representation of an array of Objects where toString is
     * applied on each object in the array.
     *
     * @param data The array to display,
     * @return A printable string.
     */
    private String getString(String[] data) {
        String result = new String("[ ");

        for (int i = 0; i < data.length; i++) {
            result = result + data[i].toString() + " ";
        }

        result = result + "]";

        return result;
    }

    private Double getDoublePercent() {
        Scanner input;
        Double result = .5;        //default value is .5
        try {
            input = new Scanner(System.in);
            this.console.appendText("   It should be a floating point value greater than 0 and less than 1.\n");

            result = input.nextDouble();

        } catch (NumberFormatException e) {
            this.console.appendText("Could not convert input to a double value\n");
            this.console.appendText(e.getMessage() + "\n");
            this.console.appendText("Will use 0.5 as the default value\n");
        } catch (Exception e) {
            this.console.appendText("There was an error with System.in\n");
            this.console.appendText(e.getMessage() + "\n");
            this.console.appendText("Will use 0.5 as the default value\n");
        }

        return result;

    }

    @FXML
    public void exit() {

        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
