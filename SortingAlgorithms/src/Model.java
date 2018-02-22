import java.util.Random;

public class Model {

    private int arraySize;
    private int[] myArray;

    public Model(int sz) {
        this.arraySize = sz;
        this.myArray = new int[sz];
        //int[] temp = new int[sz];
    }

    public int getSize() {
        return arraySize;
    }

    public void setSize(int sz) {
        this.arraySize = sz;
    }
    
    public boolean isSorted(){
        for (int i=0;i<arraySize-1;i++){
            if (myArray[i]>myArray[i+1]){
                return false;
            }
        }
        return true;
    }
    
    public int[] getUnsortedList() {
        return myArray;
    }

    public void reset(int sz) {
        this.myArray = new int[sz];
        this.arraySize = sz;
        fillArray(sz);
    }

    public void setArray(int[] arr) {
        this.myArray = arr;
    }

    private void fillArray(int sz) {
        for (int i = 0; i < sz; i++) {
            this.myArray[i] = i+1;
        }
        
        Random rnd = new Random();
        for (int i = sz - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = this.myArray[index];
            this.myArray[index] = this.myArray[i];
            this.myArray[i] = a;
        }    
    //printArrayForTesting();
    }
    
    public void printArrayForTesting() {
        System.out.println();
        System.out.println("NEW ARRAY OF SIZE " + this.arraySize + ":");
        for (int i = 0; i < this.arraySize; i++) {
            System.out.println("Array[" + i + "] = " + this.myArray[i]);
        }
        System.out.println();
    }
}


