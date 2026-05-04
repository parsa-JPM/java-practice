package com.example.interview_practice.Collection;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class TreeMapUsages {

    /*
       Range queries are most important usecase I guess.
       but in interview we mostly say if we want sorted element in Map we use tree Map/Set :)
       Note: for insertion ordered Map/Set we use LinkedHashSet/Map
     */

    @Test
    public void rangeQueriesMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(5, "five");
        map.put(10, "ten");
        map.put(20, "twenty");

        assertEquals(Map.of(5, "five", 10, "ten"), map.subMap(5, 15));    // keys between 5 and 15 → {5=five, 10=ten}
        assertEquals(Map.of(1, "one", 5, "five"), map.headMap(10));      // keys less than 10    → {1=one, 5=five}
        assertEquals(Map.of(10, "ten", 20, "twenty"), map.tailMap(10));  // keys >= 10           → {10=ten, 20=twenty}
    }

    @Test
    public void rangeQueriesSet() {
        TreeSet<Integer> set = new TreeSet<>(List.of(1, 5, 10, 20, 30));

        assertEquals(1, set.first());        // 1  — smallest
        assertEquals(30, set.last());         // 30 — largest
        assertEquals(5, set.floor(7));        // 5  — largest element ≤ 7
        assertEquals(10, set.ceiling(7));     // 10 — smallest element ≥ 7
        assertEquals(5, set.lower(10));       // 5  — largest element strictly < 10
        assertEquals(20, set.higher(10));     // 20 — smallest element strictly > 10

        assertEquals(new TreeSet<>(List.of(5, 10)), set.subSet(5, 20));             // elements from 5 (inclusive) to 20 (exclusive) → [5, 10]
        assertEquals(new TreeSet<>(List.of(5, 10, 20)), set.subSet(5, true, 20, true));  // both inclusive → [5, 10, 20]
        assertEquals(new TreeSet<>(List.of(1, 5)), set.headSet(10));                // elements < 10  → [1, 5]
        assertEquals(new TreeSet<>(List.of(10, 20, 30)), set.tailSet(10));          // elements >= 10 → [10, 20, 30]
    }

}
