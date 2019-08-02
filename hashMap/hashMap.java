package hashMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class hashMap {
	int m; // m size of the table
	int p; // prime m < p < 2m
	int a; // 1 <= a <= p
	int b; // 1 <= b <= p and a ≠ b
	private static final int n = 33093;
	hashNode[] hashtable;

	// Constructor.
	// Pick a universal hash function here and called readCSV()
	public hashMap(double alpha) throws FileNotFoundException {
		m = (int) (Math.ceil((n / alpha))) + 1;
		m = Math.abs(m);
		// hashNode[] table = new hashNode[m];
		hashtable = new hashNode[m];
		p = nextPrime(ThreadLocalRandom.current().nextInt(1, (m - 1)) + m);
		a = ThreadLocalRandom.current().nextInt(1, (p - 1));
		b = ThreadLocalRandom.current().nextInt(1, (p - 1));
		b = randomB(b, a, p);
		readCsv();
		// System.out.println("m= " + m + " p= " + p + " a= " + a + " b= " + b);
	}// Complete this hashMap method.

	// return an index of the table
	public int universalHash(String key) {
		int k = key.hashCode();
		// System.out.println("Hashcode of " + key + ": " + k);
		k = ((a * k + b) % p);
		if (k < 0)
			k += Math.abs((a * k + b));
		int ktemp = k;
		k = k % m;
		if (k < 0)
			k += Math.abs(ktemp);
		k = k % m;
		// System.out.println("Key: " + key + " Universal hash: " + k);
		return k;
	}// Complete universalHash method. Note return value is dummy.

	// insert would call universalHash
	public void insert(String key, String data) {
		hashNode tmp = new hashNode(key, data);
		int hash = (universalHash(key));
		if (nodeFinder(hashtable[hash], key) != null)
			return;
		else if (hashtable[hash] == null) {
			hashtable[hash] = tmp;
		} else if (hashtable[hash] != null) {
			tmp.next = hashtable[hash];
			hashtable[hash] = tmp;
		}
	}// Complete this insert method

	// First read the csv file and then insert data into the table.
	// This method should be called with the contructor.
	public void readCsv() throws FileNotFoundException {
		String fileName = "Dataset.csv";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// skips first line
			line = bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				// use comma as separator
				String[] temp = line.split(",");
				insert(temp[0], temp[1]);
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}// complete the search method.

	// generate random number between [x,y] inclusive
	public int rand(int x, int y) {
		return ((int) Math.random() * y + x);
	}

	// return the average length of the linkedlists.
	public double average_length() {
		double nonNullEntries = 0;
		double totalLengths = 0;
		for (int i = 0; i < m; i++) {
			if (hashtable[i] != null) {
				nonNullEntries++;
				totalLengths += count(hashtable[i]);
			}
		}
		// System.out.println(totalLengths + " / " + nonNullEntries + " = " +
		// (totalLengths / nonNullEntries));
		return totalLengths / nonNullEntries;
	}// Complete this method

	// return the average number of collisions.
	public double average_collision() {
		return average_length() - 1;
	}

	public int count(hashNode head) {
		int LLLength = 0;
		hashNode tmp = head;
		while (tmp != null) {
			LLLength++;
			tmp = tmp.next;
		}
		return LLLength;
	}

	/* Function to generate next prime number >= n */
	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;
		for (; !isPrime(n); n += 2)
			;
		return n;
	}

	/* Function to check if given number is prime */
	private static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;
		if (n == 1 || n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;
		return true;
	}

	public int randomB(int b, int a, int p) {
		while (b == a)
			b = ThreadLocalRandom.current().nextInt(1, (p - 1));
		return b;
	}

	public String search(String key) {
		int hash = (universalHash(key));
		// System.out.println(hash);
		hashNode tmp = hashtable[hash];
		if (tmp != null && tmp.key.equals(key)) {
			return tmp.data;
		} else {
			while (tmp != null && tmp.key != key) {
				tmp = tmp.next;
			}
			if (tmp == null)
				return "Key doesn't exist!";
			else
				return tmp.data;
		}
	}

	public static hashNode nodeFinder(hashNode head, String key) {
		hashNode tmp = head;
		while (tmp != null) {
			if (tmp.key.equals(key)) {
				return tmp;
			}
			tmp = tmp.next;
		}
		return tmp;
	}
}
