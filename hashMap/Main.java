package hashMap;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		double colAvg = 0;
		hashMap hash1 = new hashMap(.70);

		System.out.println("Creates 100 hashtables for each alpha and finds the average collisions for each.");
		for (int i = 0; i < 100; i++) {
			hash1 = new hashMap(.70);
			colAvg += hash1.average_collision();
		}
		System.out.println("Average Collisions with alpha of " + 70 + ": " + (colAvg / 100));
		colAvg = 0;
		for (int i = 0; i < 100; i++) {
			hash1 = new hashMap(.75);
			colAvg += hash1.average_collision();
		}
		System.out.println("Average Collisions with alpha of " + 75 + ": " + (colAvg / 100));
		colAvg = 0;
		for (int i = 0; i < 100; i++) {
			hash1 = new hashMap(.80);
			colAvg += hash1.average_collision();
		}
		System.out.println("Average Collisions with alpha of " + 80 + ": " + (colAvg / 100));
		colAvg = 0;
		for (int i = 0; i < 100; i++) {
			hash1 = new hashMap(.85);
			colAvg += hash1.average_collision();
		}
		System.out.println("Average Collisions with alpha of " + 85 + ": " + (colAvg / 100));

		System.out.println("\nCreating 15 new hash maps and testing if search function works.");
		for (int i = 0; i < 15; i++) {
			hash1 = new hashMap(.70);
			System.out.println("Zip 99929 has a population of " + hash1.search("99929"));
		}

		hash1 = new hashMap(.70);
		System.out.println("\nAttempting to search ZIP 99931 (should not exist): " + hash1.search("99931"));
	}
}