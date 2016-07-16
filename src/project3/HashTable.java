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
		int listsLength = 0;
		DataCount<String>[] dataCounts;
		int i = 0;
		for(LinkedList<DataCount<String>> lists: theLists) {
			if(!lists.isEmpty()){
			listsLength += lists.size();
			}
		}
		dataCounts = new DataCount[listsLength];
		for(LinkedList<DataCount<String>> lists: theLists){
			for(DataCount<String> node: lists){
				
				dataCounts[i++] = new DataCount<String>(node.data,node.count);
			}
		}
		return dataCounts;
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
    
    public void incCount(String data) {
    	LinkedList<DataCount<String>> hash = theLists[myhash(data)];
    	DataCount<String> node = new DataCount<String>(data,1);
       if (hash.isEmpty()){
    	   hash.add(node);
       }
       else
       {
    	   boolean found = false;
    	   for(DataCount<String> stringData: hash){
    		   if(stringData.data.equalsIgnoreCase(data)){
    			   stringData.incCount();
    			   found = true;
    			   break;
    		   }
    	   }
    	   if(!found) hash.add(node);
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
    
    public static void main(String[] args) {
        HashTable hash = new HashTable(10);

        String[] data = {"bob", "hello there", "bob", "bob", "hello there", "javadoc", "data structures", "hello there",
                         "javadoc", "dumbo", "computer science", "project3", "computer science", "project3", "phyllislau", "jeremy", "phyllislau", "dumbo"};

        DataCount<String> d = new DataCount<>("hello there", 3);
        DataCount<String> d1 = new DataCount<>("bob", 3);
        DataCount<String> d2 = new DataCount<>("jeremy", 1);
        DataCount<String> d3 = new DataCount<>("computer science", 2);
        DataCount<String> d4 = new DataCount<>("project3", 2);
        DataCount<String> d5 = new DataCount<>("javadoc", 2);
        DataCount<String> d6 = new DataCount<>("data structures", 1);
        DataCount<String> d7 = new DataCount<>("dumbo", 2);
        DataCount<String> d8 = new DataCount<>("phyllislau", 2);


        DataCount[] expected = {d, d1, d2, d3, d4, d5, d6, d7, d8};

        boolean error = false;

        for(String s : data) {
            hash.incCount(s);
        }

        DataCount<String>[] dataCounts = hash.getCounts();
        if(dataCounts.length != expected.length) {
            error = true;
        } else {
            int k = 0;
            for(DataCount<String> c : dataCounts) {
                if(!c.data.equals(expected[k].data) || c.count != expected[k].count) {
                    error = true;
                    break;
                }
                k++;
            }
        }

        if(error) {
            System.out.println("Test failed");
        } else {
            System.out.println("Test passed");
        }

    }
}
