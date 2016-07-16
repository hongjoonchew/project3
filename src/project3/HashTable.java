package project3;

import java.util.LinkedList;
/**
 * TODO Replace this comment with your own.
 * 
 * Stub code for an implementation of a DataCounter that uses a hash table as
 * its backing data structure. We included this stub so that it's very clear
 * that HashTable works only with Strings, whereas the DataCounter interface is
 * generic.  You need the String contents to write your hashcode code.
 */
public class HashTable implements DataCounter<String>  {
	private static final int DEFAULT_TABLE_SIZE = 101;
	
	private LinkedList<DataCount<String>>[] theLists;
	
	public HashTable(){
		this(DEFAULT_TABLE_SIZE);
	}
	
	public HashTable(int size){
		theLists = new LinkedList[size] ;
		for(int i =0; i < theLists.length; i++){
			theLists[i] = new LinkedList<DataCount<String>>();
		}
	}
	
	private int myhash(String s){
		int intLength = s.length() / 4;
		long sum = 0;
		for (int j = 0; j < intLength; j++) {
			char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
			long mult = 1;
			for (int k = 0; k < c.length; k++) {
				sum += c[k] * mult;
				mult *= 256;
			}
		}

		char c[] = s.substring(intLength * 4).toCharArray();
		long mult = 1;
		for (int k = 0; k < c.length; k++) {
			sum += c[k] * mult;
			mult *= 256;
		}

		return (int) (Math.abs(sum) % theLists.length);

	}
	
    /** {@inheritDoc} */
    public DataCount<String>[] getCounts() {
        for(int i=0; i < theLists.length;i++){
        	
        }
    }

    /** {@inheritDoc} */
    public int getSize() {
       int size = 0;
       for(int i = 0 ; i < theLists.length;i++){
    	   size += theLists[i].size();
       }
       return size;
    }

    /** {@inheritDoc} */
    
    //INCOMPLETE
    public void incCount(String data) {
       if (theLists[myhash(data)].contains(data) || theLists[myhash(data)] == null){
    	   theLists[myhash(data)].get(theLists[myhash(data)].indexOf(data)).incCount();
       }
       else
       {
    	   theLists[myhash(data)].add(new DataCount<String>(data,1));
       }
    }
    

    public void rehash() {
		LinkedList<DataCount<String>>[] list = new LinkedList[theLists.length * 2];
		for(LinkedList<DataCount<String>> lists: theLists){
			for(DataCount<String> count: lists){
				theLists[myhash(count.data)].add(new DataCount<String>(count.data,count.count));
			}
		}
		theLists = list;

	}
}
