import org.junit.jupiter.api.*;
import java.util.*;
import java.util.stream.Collectors;

public class LinkedListAndOthersTest {

    // Covers add, remove, peek, poll, push, pop for LinkedList and Stack
    @Test
    void testLinkedListAndStackBasicOps() {
        LinkedList<String> ll = new LinkedList<>();
        // TODO: Add "a", "b", "c", remove head, peek/poll elements
        ll.add("a");ll.add("b");ll.add("c");
        Assertions.assertEquals("c", ll.removeLast());
        Assertions.assertEquals("a",ll.peek());
        Assertions.assertEquals("a",ll.poll());
        Assertions.assertEquals(1, ll.size());

        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.pop();stack.pop();
        // TODO: Push 10, 20, 30; pop twice; peek top

        // Stack assertions
        Assertions.assertEquals(1, stack.size());
        Assertions.assertEquals(10, stack.peek());
    }

    // Use LinkedList as Queue and Deque for BFS-style operations
    @Test
    void testLinkedListAsQueueAndDeque() {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(10);
        queue.poll();

        // TODO: offer(), poll(), peekFirst(), addLast(), removeFirst()

        // Assert behavior as queue
        Assertions.assertEquals(2, queue.size());
        Assertions.assertEquals(30, queue.peekLast());
    }

    // Test Set types (HashSet, LinkedHashSet, TreeSet) and common usecases
    @Test
    void testSetVariants() {
        Set<String> hashSet = new HashSet<>();
        Set<String> linkedSet = new LinkedHashSet<>();
        Set<String> treeSet = new TreeSet<>();

        // TODO: Add "z", "a", "m", "a" to all sets

        // HashSet - unordered
        Assertions.assertEquals(3, hashSet.size());
        // LinkedHashSet - insertion order
        Assertions.assertEquals(Arrays.asList("z", "a", "m"), new ArrayList<>(linkedSet));
        // TreeSet - sorted order
        Assertions.assertEquals(Arrays.asList("a", "m", "z"), new ArrayList<>(treeSet));
    }

    // Test intersection and union of sets
    @Test
    void testSetUnionIntersection() {
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5, 6));

        // TODO: Find intersection and union
        Set<Integer> intersection = set1.stream().filter(set2::contains).collect(Collectors.toSet());  // should be [3, 4]
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        // should be [1,2,3,4,5,6]

        Assertions.assertEquals(Set.of(3, 4), intersection);
        Assertions.assertEquals(Set.of(1, 2, 3, 4, 5, 6), union);
    }

    // TreeSet with custom comparator (reverse order)
    @Test
    void testTreeSetCustomComparator() {
        Set<String> reversedSet = new TreeSet<>(Comparator.reverseOrder());
        // TODO: Add "beta", "alpha", "gamma"

        Assertions.assertEquals(Arrays.asList("gamma", "beta", "alpha"), new ArrayList<>(reversedSet));
    }
}