import javafx.application.Platform;//for using RunLater

public class SelectionSort implements SortingStratergy {

    private SortingViewController drawController;

    @Override
    public void setSortingViewController(SortingViewController controller) {
        this.drawController = controller;
    }

    @Override
    public void Sort(int[] myArray) {
        Thread sortThread = new Thread() {
            @Override
            public void run() {//Selection Sort algorithm
                for (int index = 0; index < myArray.length; index++) {
                    int secondSmallestIndex = getSmallestIndex(myArray, index, myArray.length - 1);
                    swap(myArray, index, secondSmallestIndex);
                }
            }
            
        };//end sortThread
        
        sortThread.start();
    
    }//end Sort

    public int getSmallestIndex(int[] myArray, int first, int last) {//return index of smallest integer
        int min = myArray[first];
        int minIndex = first;
        for (int index = first + 1; index <= last; index++) {
            if (myArray[index] < min) {
                min = myArray[index];
                minIndex = index;
            }
        }
        return minIndex;
    }

    public void swap(int[] myArray, int i, int j) {//run swap inside sortThread
            
        //simple swap
        int temp = myArray[i];
        myArray[i] = myArray[j];
        myArray[j] = temp;
        
        //GUI update
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drawController.drawArray();
            }
        });//end runLater
        
        //sleep
        try {Thread.sleep(100);}
        catch (InterruptedException ex) {};
    }//End Swap

}
