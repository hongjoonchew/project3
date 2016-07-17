package project3;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Model Correlator class
 *
 */
public class Correlator {
	public static void main(String[] args) {
		if (args.length != 4) {
			System.err
			.println("Usage: Correlator [-b | -h] <filename 1> <filename 2>\n");
			System.err.println("-b Use an unbalanced BST in the backend");
			System.err.println("-h Use a Hashtable in the backend");
			System.exit(1);
		}

		DataCount<String>[] firstFile = countWords(args[1], args[2]);
		DataCount<String>[] secondFile = countWords(args[1], args[3]);

		System.out.println("The metric differences: "
				+ getDifferenceMetric(firstFile, secondFile));
	}

	private static DataCount<String>[] countWords(String dataStructure,
			String file) {
		DataCounter<String> counter;
		if (dataStructure.equals("-b")) {
			counter = new BinarySearchTree<>();
		} else if (dataStructure.equals("-h")) {
			counter = new HashTable();
		} else {
			System.err
			.println("incorrect input: use -b or -h. automatically use BST");
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

		return counter.getCounts();
	}

	private static double getDifferenceMetric(DataCount<String>[] firstFile,
			DataCount<String>[] secondFile) {

		System.out.println("First file frequencies:");
		Map<String, Double> freq = findFrequency(firstFile);
		System.out.println();
		System.out.println("Second file frequencies:");
		Map<String, Double> freq2 = findFrequency(secondFile);

		double theSum = 0;
		double difference = 0;
		for (String theKey : freq.keySet()) {
			if (freq2.containsKey(theKey)) {
				difference = (freq.get(theKey) - freq2.get(theKey));
				theSum += difference * difference;
			}
		}
		return theSum;
	}

	private static Map<String, Double> findFrequency(
			DataCount<String>[] dataCount) {

		double totalWordsCount = 0;
		for (DataCount<String> dataCounts : dataCount) {
			totalWordsCount += dataCounts.count;
		}
		Map<String, Double> thefreq = new TreeMap<>();
		for (DataCount<String> dataCounts : dataCount) {
			double freq = dataCounts.count / totalWordsCount;
			if (freq > 0.0001 && freq < 0.01) {
				thefreq.put(dataCounts.data, freq);
				System.out.printf("%s = %f\n", dataCounts.data, freq);
			}
		}
		return thefreq;
	}