package milk.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortbyGain implements Comparator<List<Integer>> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(List<Integer> a, List<Integer> b) {
        return b.get(2) - a.get(2);
    }

}
