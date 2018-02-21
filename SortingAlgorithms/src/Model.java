public class Model {
    private int arraySize;
    private int[] myArray;

    public Model(int sz) {
        this.arraySize=sz;
        this.myArray=new int[sz];
        int[] temp=new int[sz];
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
    
    public void reset(int sz){
        this.myArray=new int[sz];
    }
    
    public void setArray(int[] arr){
        this.myArray=arr;
    }
    
}
