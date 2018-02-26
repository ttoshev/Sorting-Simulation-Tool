
import javafx.application.Platform;

public class SelectionSort implements SortingStratergy {

    private SortingViewController drawController;

    @Override
    public void setSortingViewController(SortingViewController controller) {

        this.drawController = controller;

    }

    @Override
    public void Sort(int[] myArray) {

        Thread thread2 = new Thread() {

            @Override
            public void run() {

                for (int index = 0; index < myArray.length; index++) {
                    int secondSmallestIndex = getSmallestIndex(myArray, index, myArray.length - 1);
                    swap(myArray, index, secondSmallestIndex);
                }
            }
        };
        thread2.start();

    }

    public int getSmallestIndex(int[] myArray, int first, int last) {
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

    public void swap(int[] myArray, int i, int j) {

        int temp = myArray[i];
        myArray[i] = myArray[j];
        myArray[j] = temp;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drawController.drawArray();

            }

        });

        try {
            System.out.println("About to sleep");
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            System.out.println("exception thrown");
        };
    }

}
