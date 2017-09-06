package comparators;

import java.util.Comparator;

import model.Window;

/**
 * Comparator for journeys for ordering by cost
 */
public class WindowIDComparator implements Comparator<Window> {

	/**
	 * Compares two windows by id
	 * @param window1 Window for comparison
	 * @param window2 Window to compare to
	 */
	public int compare(Window window1, Window window2) {
		return Integer.compare(window1.getWindowNumber(), window2.getWindowNumber());
	}
}
