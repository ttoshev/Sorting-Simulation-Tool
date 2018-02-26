
import javafx.application.Platform;

public class MergeSort implements SortingStratergy {

    int[] arr;
    int first;
    int last;
    private SortingViewController drawController;

    @Override
    public void setSortingViewController(SortingViewController controller) {

        this.drawController = controller;
    }
    //When called, runs on separate thread

    @Override
    public void Sort(int[] myArray) {

        arr = myArray;
        first = 0;
        last = myArray.length - 1;
        SortAux(arr, first, last);

    }

    public void SortAux(int[] myArray, int first, int last) {

        Thread thread2 = new Thread() {

            @Override
            public void run() {

                int mid = (first + last) / 2;
                if (first < last) {
                    SortAux(myArray, first, mid);
                    SortAux(myArray, mid + 1, last);
                }
                Merge(myArray, first, mid, last);

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
        };
        thread2.start();

    }

    public void Merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        /* Merge the temp arrays */
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }

    }
}
