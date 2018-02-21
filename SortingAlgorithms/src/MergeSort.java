public class MergeSort implements SortingStratergy {
    
    //When called, runs on separate thread
    
    @Override
    public void Sort (int[] myArray){
        SortAux(myArray,0,myArray.length-1);
    }
    
    public void SortAux(int[] myArray, int first, int last){
        int mid=(first+last)/2;
        if (first<last){
            SortAux(myArray,first,mid);
            SortAux(myArray,mid+1,last);
        }
        Merge(myArray,first,mid,last);
    }
  
    public void Merge(int[] myArray, int first, int mid, int last){
        int[] tempArray=myArray;
        int startHalfOne=first;
        int endHalfOne=mid;
        int startHalfTwo=mid+1;
        int endHalfTwo=last;
        int index=0;
        
        while ((startHalfOne<=endHalfOne)&&(startHalfTwo<=endHalfTwo)){
            if (myArray[startHalfOne]>=myArray[startHalfTwo]){
                tempArray[index]=myArray[startHalfOne];
                startHalfOne++;
            } 
            else {
                tempArray[index]=myArray[startHalfTwo];
                startHalfTwo++;
            }
            index++;
        }
        if (startHalfOne>endHalfOne){//first empty and second not empty
            while (!(startHalfTwo>endHalfTwo)){
                tempArray[index]=myArray[startHalfTwo];
                startHalfTwo++;
                index++;
            }
        }
        
        else if (startHalfTwo>endHalfTwo){//second empty and first not empty
            while (!(startHalfOne>endHalfOne)){
                tempArray[index]=myArray[startHalfOne];
                startHalfOne++;
                index++;
            }
        }
        for (int i=0; i<tempArray.length;i++){
            myArray[i]=tempArray[i];
        }
    }
}
