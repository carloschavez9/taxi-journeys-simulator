package comparators;

import java.util.Comparator;
import model.Journey;

/**
 * Comparator for journeys for ordering by cost
 */
public class JourneyCostComparator implements Comparator<Journey> {

	/**
	 * Compares two journeys by cost
	 * @param journey1 Journey for comparison
	 * @param journey2 Journey to compare to
	 */
	public int compare(Journey journey1, Journey journey2) {
		return journey1.getCost().compareTo(journey2.getCost());
	}
}
