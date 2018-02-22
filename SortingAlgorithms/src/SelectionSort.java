import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;

public class SelectionSort implements SortingStratergy {
    int[] arr;
    //When called, runs on separate thread
    
    @Override
    public void Sort (int[] myArray)throws InterruptedException{
        arr=myArray;
        sortTask _sort = new sortTask();
        Thread th = new Thread(_sort);
        th.start();
        
    }
    class sortTask implements Runnable{
        @Override
        public void run(){
            for (int index=0;index<arr.length;index++){
                int secondSmallestIndex=getSmallestIndex(arr,index,arr.length-1);
                try
                {
                    Thread.sleep(5);
                }catch(InterruptedException ex){}
                swap(arr,index,secondSmallestIndex);
            }
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
