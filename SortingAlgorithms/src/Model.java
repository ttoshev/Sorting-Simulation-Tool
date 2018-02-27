import java.util.Random;

public class Model {

    private int arraySize;
    private int[] myArray;

    public Model(int sz) { //initialize with given size
        this.arraySize = sz;
        this.myArray = new int[sz];
    }

    public int getSize() {
        return arraySize;
    }

    public void setSize(int sz) {
        this.arraySize = sz;
    }
    
    public int[] getUnsortedList() {
        return myArray;
    }

    public void reset(int sz) {
        this.myArray = new int[sz];
        this.arraySize = sz;
        
        for (int i = 0; i < arraySize; i++) {//fill the array with ordered integers
            this.myArray[i] = i+1;
        }
        
        Random rnd = new Random();
        for (int i = arraySize - 1; i > 0; i--){//randomize integers
            int index = rnd.nextInt(i + 1);
            
            // Simple swap
            int a = this.myArray[index];
            this.myArray[index] = this.myArray[i];
            this.myArray[i] = a;
        }
    }
}


