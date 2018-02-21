import javafx.application.Platform;

public class SelectionSort implements SortingStratergy {
    
    //When called, runs on separate thread
    
    @Override
    public void Sort (int[] myArray){
        for (int index=0;index<myArray.length;index++){
            int secondSmallestIndex=getSmallestIndex(myArray,index,myArray.length-1);
            swap(myArray,index,secondSmallestIndex);
        }
    }

    
    public static int getSmallestIndex(int[] myArray, int first, int last){
        int min = myArray[first];
        int minIndex = first;
        for (int index=first+1;index<=last;index++){
            if (myArray[index]<min){
                min = myArray[index];
                minIndex=index;
            }
        }
        return minIndex;
    }   
    
    public static void swap (int[] myArray, int i, int j){
        int temp=myArray[i];
        myArray[i]=myArray[j];
        myArray[j]=temp;
    }
    
}
