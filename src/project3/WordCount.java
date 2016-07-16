package project3;

import java.io.IOException;

/**
 * model the WordCount class
 *
 */
public class WordCount {
	public static void main(String[] args) {
		if (args.length != 4) {
			System.err
			.println("Usage: WordCount [-b | -h] [-frequency | -num_unique] <filename>\n");
			System.err.println("-b - Use an Unbalanced BST");
			System.err.println("-h - Use a Hashtable\n");
			System.err
			.println("-frequency - Print all the word/frequency pair, ordered by frequency, and then by the words in lexicographic order");
			System.err
			.println("-num_unique - Print the number of unique words in the document. ");
			System.exit(1);
		}

		boolean useBST = false; 
		boolean freq = false; 
		if (args[1].compareTo("-b") == 0)
			useBST = true;
		else if (args[1].compareTo("-h") == 0)
			useBST= false;
		else {
			System.err.println("\tSaw " + args[1]
					+ " instead of -b or -h as first argument");
			System.exit(1);
		}
		if (args[2].compareTo("-frequency") == 0)
			freq = true;
		else if (args[2].compareTo("-num_unique") == 0)
			freq = false;
		else {
			System.err.println("\tSaw " + args[2]
					+ " instead of -frequency or -num_unique as second argument");
			System.exit(1);
		}
		if(freq)
		{
			countWords(args[1], args[3]);
		}
		else
		{

		}
	}

	private static void countWords(String dataStructure, String file) {

		DataCounter<String> counter;
		if(dataStructure.equals("-b"))
		{
			System.out.println("Using BinarySearchTree Data Structure: ");
			counter = new BinarySearchTree<>();
		}
		else if(dataStructure.equals("-h"))
		{
			System.out.println("Using HashTable Data Structure: ");
			counter = new HashTable();
		}
		else
		{
			System.err.println("incorrect input: use -b or -h. automatically use BST");
			counter = new BinarySearchTree<>();
		}

		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			while (word != null) {
				counter.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + e);
			System.exit(1);
		}

		DataCount<String>[] counts = counter.getCounts();
		sortByDescendingCount(counts);
		for (DataCount<String> c : counts)
			System.out.println(c.count + " \t" + c.data);
	}

	/**
	 * TODO Replace this comment with your own.
	 * 
	 * Sort the count array in descending order of count. If two elements have
	 * the same count, they should be in alphabetical order (for Strings, that
	 * is. In general, use the compareTo method for the DataCount.data field).
	 * 
	 * This code uses insertion sort. You should modify it to use a different
	 * sorting algorithm. NOTE: the current code assumes the array starts in
	 * alphabetical order! You'll need to make your code deal with unsorted
	 * arrays.
	 * 
	 * The generic parameter syntax here is new, but it just defines E as a
	 * generic parameter for this method, and constrains E to be Comparable. You
	 * shouldn't have to change it.
	 * 
	 * @param counts
	 *            array to be sorted.
	 */
	private static <E extends Comparable<? super E>> void sortByDescendingCount(
			DataCount<E>[] counts) {
		for (int i = 1; i < counts.length; i++) {
			DataCount<E> x = counts[i];
			int j;
			for (j = i - 1; j >= 0; j--) {
				if (counts[j].count >= x.count) {
					break;
				}
				counts[j + 1] = counts[j];
			}
			counts[j + 1] = x;
		}

	}

}