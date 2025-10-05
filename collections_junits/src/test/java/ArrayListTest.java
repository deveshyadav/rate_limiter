import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArrayListTest {

    // Test adding elements to a list and retrieving them by index
    @Test
    private void testCrudInList() {
        //Function

        // TODO: Remove element 20 by value
        List<Integer> list1 = new ArrayList<>(Arrays.asList(10, 20, 30));
        list1.remove(Integer.valueOf(20));
        assertEquals(2, list1.size());
        Assertions.assertFalse(list1.contains(20));
        assertEquals(Arrays.asList(10, 30), list1);

        // TODO: Replace "b" with "z" at index 1
        List<String> list2 = new ArrayList<>(Arrays.asList("a", "b", "c"));
        list2.set(list2.indexOf("b"), "z");
        assertTrue(list2.contains("z"));
        Assertions.assertFalse(list2.contains("b"));
        assertEquals(Arrays.asList("a", "z", "c"), list2);

        // TODO: Reverse the list in-place
        List<Integer> list3 = new ArrayList<>(Arrays.asList(1, 2, 3));
        Collections.reverse(list3);
        assertEquals(3, list3.size());
        assertEquals(Arrays.asList(3, 2, 1), list3);
    }

    // Test natural alphabetical sorting of strings
    @Test
    void testSortNaturalOrder() {
        List<String> list = new ArrayList<>(Arrays.asList("banana", "apple", "cherry"));
        // TODO: Sort the list in natural order
        Collections.sort(list);
        assertEquals(Arrays.asList("apple", "banana", "cherry"), list);
        Collections.sort(list,(x,y)-> x.compareTo(y));
        assertEquals(Arrays.asList("apple", "banana", "cherry"), list);
    }

    // Test custom sorting strings by their length
    @Test
    void testCustomSortByLength() {
        List<String> list = new ArrayList<>(Arrays.asList("apple", "kiwi", "banana"));
        // TODO: Sort the list by string length

        Collections.sort(list,(x,y)-> Integer.compare(x.length(),y.length()) );
        assertEquals(Arrays.asList("kiwi", "apple", "banana"), list);

        Collections.sort(list, (x,y)-> x.length()-y.length());
        assertEquals(Arrays.asList("kiwi", "apple", "banana"), list);


    }

    // Test filtering even numbers using Streams API
    @Test
    void testStreamFilterAndCollect() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.sort((x,y)->Integer.compare(x,y));
        List<Integer> evens = list.stream().filter(x->x%2==0).toList(); // TODO: Filter only even numbers

        Assertions.assertNotNull(evens);
        assertEquals(2, evens.size());
        assertEquals(Arrays.asList(2, 4), evens);
    }

    // Test mapping strings to their lengths using Streams API
    @Test
    void testMapToLength() {
        List<String> list = Arrays.asList("a", "abcd", "xyz");
        List<Integer> lengths = list.stream().map(x->x.length()).toList();
        // TODO: Map strings to their lengths

        assertEquals(3, lengths.size());
        assertEquals(Arrays.asList(1, 4, 3), lengths);
    }

    // Test extracting a sublist from the original list
    @Test
    void testSublist() {
        List<Integer> list = Arrays.asList(10, 20, 30, 40, 50);
        List<Integer> sub = list.subList(1,4); // TODO: Extract sublist from index 1 to 4 (exclusive)

        Assertions.assertNotNull(sub);
        assertEquals(3, sub.size());
        assertEquals(Arrays.asList(20, 30, 40), sub);
    }

    // Test accessing elements from a nested list
    @Test
    void testNestedListAccess() {
        List<List<Integer>> matrix = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4)
        );
        // TODO: No change needed, just understand nested list access

        assertEquals(3, matrix.get(1).get(0));
    }

    // Test removing duplicate elements using Stream.distinct()
    @Test
    void testDistinctElements() {
        List<String> list = Arrays.asList("a", "b", "a", "c", "b");
        List<String> unique = list.stream().distinct().toList(); // TODO: Use stream distinct to remove duplicates
        String str = "ss";
        //System.out.println(Pattern.);
        assertEquals(3, unique.size());
        assertEquals(Arrays.asList("a", "b", "c"), unique);
    }

    // Test checking if list is empty and adding a null element
    @Test
    void testEmptyAndNullHandling() {
        List<String> list = new ArrayList<>();
        list.add(null);

        assertTrue(list.contains(null));
        Assertions.assertNull(list.get(0));
    }

    // Test immutability of List.of()
    @Test
    void testImmutableList() {
        List<Integer> immutable = List.of(1, 2, 3);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutable.add(4));
    }

    // Sort strings by length, and if equal then by name
    @Test
    void testSortByLengthThenAlphabetically() {
        List<String> list = new ArrayList<>(Arrays.asList("kiwi", "apple", "pear", "banana", "fig"));

        // TODO: Sort first by length, then alphabetically
        list.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));

        assertEquals(Arrays.asList("fig", "kiwi", "pear", "apple", "banana"), list);
    }

    static class Person {
        String name;
        int age;
        Person(String name, int age) {
            this.name = name; this.age = age;
        }
    }

    @Test
    void testGroupByAgeBucket() {
        List<Person> people = Arrays.asList(
                new Person("Alice", 22),
                new Person("Bob", 35),
                new Person("Charlie", 28),
                new Person("David", 41),
                new Person("Eve", 23)
        );

        // TODO: Group by age group (20s, 30s, 40s)
        Map<String, List<String>> grouped = people.stream()
                .collect(Collectors.groupingBy(
                        p -> {
                            if (p.age < 30) return "20s";
                            else if (p.age < 40) return "30s";
                            else return "40s";
                        },
                        Collectors.mapping(p -> p.name, Collectors.toList())
                ));



        assertEquals(Arrays.asList("Alice", "Charlie", "Eve"), grouped.get("20s"));
        assertEquals(List.of("Bob"), grouped.get("30s"));
        assertEquals(List.of("David"), grouped.get("40s"));

        String str = "ss";

    }

    @Test
    void testPartitionByEvenOdd() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);

        // TODO: Partition into even and odd
        Map<Boolean, List<Integer>> partitioned = nums.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));

        assertEquals(Arrays.asList(2, 4, 6), partitioned.get(true));
        assertEquals(Arrays.asList(1, 3, 5), partitioned.get(false));
    }

    @Test
    void testMaxByLength() {
        List<String> list = Arrays.asList("car", "truck", "airplane", "van");

        // TODO: Find max by length
        String max = list.stream().max(Comparator.comparingInt(String::length)).orElseThrow();

        assertEquals("airplane", max);
    }

    @Test
    void testFlattenNestedLists() {
        List<List<String>> nested = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("c", "d"),
                Arrays.asList("e")
        );

        // TODO: Flatten to a single list
        List<String> flat = nested.stream().flatMap(List::stream).collect(Collectors.toList());

        assertEquals(Arrays.asList("a", "b", "c", "d", "e"), flat);
    }

    @Test
    void testSortedUnmodifiableList() {
        List<String> list = Arrays.asList("orange", "banana", "apple");

        // TODO: Sort and return unmodifiable list
        List<String> sorted = list.stream()
                .sorted()
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

        assertEquals(Arrays.asList("apple", "banana", "orange"), sorted);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> sorted.add("grape"));
    }

    @Test
    void testIntSummaryStatistics() {
        List<Integer> nums = Arrays.asList(10, 20, 30, 40, 50);

        // TODO: Compute stats
        IntSummaryStatistics stats = nums.stream().mapToInt(i -> i).summaryStatistics();

        assertEquals(5, stats.getCount());
        assertEquals(150, stats.getSum());
        assertEquals(10, stats.getMin());
        assertEquals(50, stats.getMax());
        assertEquals(30.0, stats.getAverage());
    }

    @Test
    void testSecondHighestSalary() {
        List<Integer> salaries = Arrays.asList(3000, 6000, 2000, 6000, 1000);

        // TODO: Find second highest salary (distinct)
        Integer secondHighest = salaries.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElseThrow();

        assertEquals(3000, secondHighest);
    }

    @Test
    void testNthLargestElement() {
        List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9, 7, 7);
        int n = 3;

        // TODO: Find 3rd largest distinct number
        Integer nth = numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(n - 1)
                .findFirst()
                .orElse(-1);

        assertEquals(7, nth);
    }

    static class Employee {
        String name, dept;
        int salary;
        Employee(String name, String dept, int salary) {
            this.name = name; this.dept = dept; this.salary = salary;
        }
    }

    @Test
    void testTopEmployeePerDepartment() {
        List<Employee> emps = Arrays.asList(
                new Employee("Alice", "HR", 5000),
                new Employee("Bob", "HR", 6000),
                new Employee("Charlie", "IT", 8000),
                new Employee("David", "IT", 7500),
                new Employee("Eva", "Finance", 7000)
        );

        // TODO: Find top salaried employee name per department
        Map<String, String> topEmpByDept = emps.stream()
                .collect(Collectors.groupingBy(
                        e -> e.dept,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(e -> e.salary)),
                                opt -> opt.map(e -> e.name).orElse("")
                        )
                ));

        assertEquals("Bob", topEmpByDept.get("HR"));
        assertEquals("Charlie", topEmpByDept.get("IT"));
        assertEquals("Eva", topEmpByDept.get("Finance"));
    }

    @Test
    void testTopTwoSalariesPerDept() {
        List<Employee> emps = Arrays.asList(
                new Employee("A", "Eng", 1000),
                new Employee("B", "Eng", 2000),
                new Employee("C", "Eng", 3000),
                new Employee("D", "Sales", 1200),
                new Employee("E", "Sales", 800)
        );


      // TODO: Top 2 salaries per department
        Map<String, List<Integer>> topTwo = emps.stream()
                .collect(Collectors.groupingBy(
                        e -> e.dept,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .map(e -> e.salary)
                                        .sorted(Comparator.reverseOrder())
                                        .limit(2)
                                        .collect(Collectors.toList())
                        )
                ));

        assertEquals(Arrays.asList(3000, 2000), topTwo.get("Eng"));
        assertEquals(Arrays.asList(1200, 800), topTwo.get("Sales"));
    }

    @Test
    void testSalaryBucketCount() {
        List<Employee> emps = Arrays.asList(
                new Employee("X", "IT", 5000),
                new Employee("Y", "IT", 5000),
                new Employee("Z", "HR", 6000),
                new Employee("P", "HR", 7000),
                new Employee("Q", "HR", 6000)
        );

        // TODO: Count how many employees earn each salary
        Map<Integer, Long> salaryCounts = emps.stream()
                .collect(Collectors.groupingBy(
                        e -> e.salary,
                        Collectors.counting()
                ));

        assertEquals(2L, salaryCounts.get(5000));
        assertEquals(2L, salaryCounts.get(6000));
        assertEquals(1L, salaryCounts.get(7000));
    }

    @Test
    void testEmployeesAboveAverageSalary() {
        List<Employee> emps = Arrays.asList(
                new Employee("A", "X", 4000),
                new Employee("B", "Y", 6000),
                new Employee("C", "Z", 8000),
                new Employee("D", "X", 2000)
        );

        double avgSalary = emps.stream().mapToInt(e -> e.salary).average().orElse(0);

        // TODO: Find employees earning above average
        List<String> aboveAvg = emps.stream()
                .filter(e -> e.salary > avgSalary)
                .map(e -> e.name)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList("B", "C"), aboveAvg);
    }


    /**
     * Showing parallel stream
     */
    @Test
    void shouldDemonstrateParallelStreamPerformance() {
        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000).boxed().toList();

        long start = System.currentTimeMillis();
        long count = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .count();
        long end = System.currentTimeMillis();

        assertTrue(count > 0);
        System.out.println("Parallel stream count: " + count + ", time(ms): " + (end - start));
    }


    /**
     * Summarizing integer collection
     */
    @Test
    void shouldSummarizeIntegerList() {
        List<Integer> numbers = List.of(10, 20, 30, 40, 50);

        IntSummaryStatistics stats = numbers.stream()
                .collect(Collectors.summarizingInt(Integer::intValue));

        assertEquals(5, stats.getCount());
        assertEquals(150, stats.getSum());
        assertEquals(30.0, stats.getAverage());
        assertEquals(50, stats.getMax());
        assertEquals(10, stats.getMin());
        ExecutorService ee = Executors.newFixedThreadPool(1);

    }


    /**
     * Giving characters frequency map
     */
    @Test
    void shouldCountCharacterFrequencyInString() {
        String input = "banana";

        Map<Character, Long> freqMap = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        assertEquals(3L, freqMap.get('a'));
        assertEquals(2L, freqMap.get('n'));
        assertEquals(1L, freqMap.get('b'));
    }

    /**
     * Sorted by Frequency Then Lexicographically
     */
    @Test
    void shouldReturnCharacterFrequencySorted() {
        String input = "banana";

        Map<Character, Long> freqMap = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Map.Entry<Character, Long>> sorted = freqMap.entrySet().stream()
                .sorted(Comparator
                        .comparing(Map.Entry<Character, Long>::getValue, Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .toList();

        assertEquals(List.of(
                Map.entry('a', 3L),
                Map.entry('n', 2L),
                Map.entry('b', 1L)
        ), sorted);
    }

    @Test
    void testingListOfList(){
        List<List<Integer>> input = Arrays.asList(
                List.of(1, 3),  // Employee 1, Manager 3
                List.of(2, 3),  // Employee 2, Manager 3
                List.of(3, 6),  // Employee 3, Manager 6
                List.of(4, 5),  // Employee 4, Manager 5
                List.of(5, 6),  // Employee 5, Manager 6
                List.of(0, 1),  // Employee 0, Manager 1
                List.of(1, 8)   // Employee 1, Manager 8 (Note: Employee 1 has two managers in this example)
        );

        // Group by manager and collect a list of employees for each manager
        Map<Integer, Long> employeesCountPerManager = input.stream()
                .collect(Collectors.groupingBy(
                        list -> list.get(1), // Classifier function: Group by manager ID (second element)
                        Collectors.counting()
                                 // Downstream collector: Collect employee IDs into a list

                ));

        // Group by manager and collect a list of employees for each manager
        Map<Integer, List<Integer>> employeesListPerManager = input.stream()
                .collect(Collectors.groupingBy(
                        list -> list.get(1), // Classifier function: Group by manager ID (second element)
                        Collectors.mapping(
                                list -> list.get(0), // Mapper function: Extract employee ID (first element)
                                Collectors.toList()  // Downstream collector: Collect employee IDs into a list
                        )
                ));

        employeesCountPerManager.forEach((x,y)-> System.out.println("Manager:"+x+" have employees number: "+y));

        // Print the result
        employeesListPerManager.forEach((managerId, employeeList) ->
                System.out.println("Manager ID: " + managerId + ", Employees: " + employeeList)
        );
    }

    public static void main(String[] args) {
       List<String> xx = Arrays.stream("my name is devesh yadav".split(" ")).collect(Collectors.toList());
       Collections.sort(xx);
        System.out.println(xx);

        //TreeMap<>
    }

    @Test
    public void testConvertingListPojoToMap(){
        Partner p1 = new Partner(1,"p1");
        Partner p2 = new Partner(2,"p2");
        Partner p3 = new Partner(3,"p3");
        List<Partner> partners = Arrays.asList(p1,p2,p3);

        Map<Integer,Partner> map = partners.stream().collect(Collectors.toMap(p->p.id, p->p));
        assertEquals("p1",map.get(1).name);

    }

    class Partner{
        int id;
        String name;
        Partner(int id, String name){
            this.id=id;
            this.name=name;
        }

        @Override
        public String toString() {
            return "Partner{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }



}