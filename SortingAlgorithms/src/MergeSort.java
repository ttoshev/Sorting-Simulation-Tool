import javafx.application.Platform;

public class MergeSort implements SortingStratergy {

    private int[] arr;
    private int first;
    private int last;
    private SortingViewController drawController;

    @Override
    public void setSortingViewController(SortingViewController controller) {
        this.drawController = controller;
    }

    @Override
    public void Sort(int[] myArray) {//initialize parameters to be used by SortAux and call it
        arr = myArray;
        first = 0;//index of first element
        last = myArray.length - 1;//index of last element
        SortAux(arr, first, last);
    }

    public void SortAux(int[] myArray, int first, int last) {
       
        Thread sortThread= new Thread() {
            @Override
            public void run() {

                int currentSize;
                int left_start;
                int end = myArray.length;

                for (currentSize = 1; currentSize <= end - 1; currentSize = 2 * currentSize) {
                    
                    for (left_start = 0; left_start < end - 1; left_start += 2 * currentSize) {
                        
                        int mid = left_start + currentSize - 1;
                        int right_end = min(left_start + 2 * currentSize - 1, end - 1);
                        Merge(arr, left_start, mid, right_end);
                    }
                }
                
            }//end Run
            
        };//end sortThread
        
        sortThread.start();
    }

    int min(int x, int y) {//return the minimum of 2 integers
        return (x < y) ? x : y;
    }

    public void Merge(int[] array, int low, int middle, int high) { //runs inside sortThread

        int[] helper = new int[array.length];
	for (int i = low; i <= high; i++) {
		helper[i] = array[i];
	}
	
	int helperLeft = low;
	int helperRight = middle+1;
	int current = low;
	
	while (helperLeft <= middle && helperRight <=high) {
		if(helper[helperLeft] <= helper[helperRight]){
			array[current] = helper[helperLeft];
			helperLeft++;
			
		}else{
			array[current] = helper[helperRight];
			helperRight++;
		}
		current ++;		
	}
	
	int remaining = middle - helperLeft;
	for (int i = 0; i <= remaining; i++) {
		array[current+i] = helper[helperLeft+ i];
	}
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drawController.drawArray();
            }
        });//end Runnable
        
        try {Thread.sleep(100);}
        catch (InterruptedException ex) {};
        
    }//end Merge
}
