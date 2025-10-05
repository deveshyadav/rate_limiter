import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapTest {

    @Test
    void testCommonMapUsecases() {
        // Case 1: Basic put, get, containsKey
        Map<String, Integer> map = new HashMap<>();
        // TODO: put("apple", 100), put("banana", 200), put("orange", 300)

        assertEquals(3, map.size());
        assertTrue(map.containsKey("banana"));
        assertEquals(200, map.get("banana"));
        NavigableMap<Integer,Integer> mm = new TreeMap<>();

        // Case 2: Updating values conditionally using computeIfPresent
        // TODO: Increase value of "apple" by 50 if present

        assertEquals(150, map.get("apple"));

        // Case 3: Using computeIfAbsent
        // TODO: Insert "grape" with default 400 if not present

        assertEquals(400, map.get("grape"));

        // Case 4: Iteration and filtering
        // TODO: Create filtered map where value > 200

        Map<String, Integer> filtered = map.entrySet().stream()
            .filter(e -> e.getValue() > 200)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Assertions.assertFalse(filtered.containsKey("apple"));
        assertTrue(filtered.containsKey("grape"));
    }

    @Test
    void testGroupByAndCounting() {
        List<String> words = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");

        // TODO: Group and count frequency of each word using Map<String, Long>

        Map<String, Long> frequency = words.stream()
            .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        assertEquals(3, frequency.get("apple"));
        assertEquals(2, frequency.get("banana"));
        assertEquals(1, frequency.get("orange"));
    }

    @Test
    void testSortMapByValueDescending() {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("John", 80);
        scores.put("Alice", 95);
        scores.put("Bob", 75);

        // TODO: Sort by value descending and store LinkedHashMap

        Map<String, Integer> sorted = scores.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));

        assertEquals(List.of("Alice", "John", "Bob"), new ArrayList<>(sorted.keySet()));
    }

    @Test
    void testNestedMapStructure() {
        Map<String, Map<String, Integer>> deptSalaries = new HashMap<>();

        // TODO: Initialize nested map: HR -> {"Alice": 5000, "Bob": 6000}, IT -> {"Charlie": 8000}

        Map<String, Integer> hr = new HashMap<>();
        hr.put("Alice", 5000);
        hr.put("Bob", 6000);

        Map<String, Integer> it = new HashMap<>();
        it.put("Charlie", 8000);

        deptSalaries.put("HR", hr);
        deptSalaries.put("IT", it);

        // Assertions
        assertEquals(2, deptSalaries.get("HR").size());
        assertEquals(6000, deptSalaries.get("HR").get("Bob"));
        assertEquals(8000, deptSalaries.get("IT").get("Charlie"));
    }

    @Test
    void testMergeMapsAndConflictResolution() {
        Map<String, Integer> map1 = new HashMap<>(Map.of("A", 10, "B", 20));
        Map<String, Integer> map2 = new HashMap<>(Map.of("B", 30, "C", 40));

        // TODO: Merge both maps, if conflict, sum the values

        Map<String, Integer> merged = new HashMap<>(map1);
        map2.forEach((k, v) -> merged.merge(k, v, Integer::sum));

        assertEquals(3, merged.size());
        assertEquals(10, merged.get("A"));
        assertEquals(50, merged.get("B")); // 20 + 30
        assertEquals(40, merged.get("C"));
    }


    @Test
    void testGroupEmployeesByDepartmentCount() {
        List<Employee> employees = List.of(
                new Employee("Alice", "HR"),
                new Employee("Bob", "Tech"),
                new Employee("Charlie", "HR")
        );

        // TODO: Group employees by department and count number per department

        Map<String, Long> result = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        assertEquals(2, result.get("HR"));
        assertEquals(1, result.get("Tech"));
    }

    @Test
    void testMaxSalaryPerDepartment() {
        List<Employee> employees = List.of(
                new Employee("Alice", "HR", 50000),
                new Employee("Bob", "HR", 70000),
                new Employee("Charlie", "Tech", 80000)
        );

        // TODO: Find max salary employee per department

        Map<String, Optional<Employee>> result = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparing(Employee::getSalary))));

        assertEquals("Bob", result.get("HR").get().getName());
        assertEquals("Charlie", result.get("Tech").get().getName());
    }

    @Test
    void testPartitionStudentsByPassFail() {
        List<Student> students = List.of(
                new Student("A", 35),
                new Student("B", 50),
                new Student("C", 80)
        );

        // TODO: Partition students into passed and failed (marks >= 40)

        Map<Boolean, List<Student>> result = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getMarks() >= 40));

        assertEquals(2, result.get(true).size());
        assertEquals(1, result.get(false).size());
    }

    @Test
    void testFlattenOrderItems() {
        List<Order> orders = List.of(
                new Order(List.of(new Item("Pen"), new Item("Pencil"))),
                new Order(List.of(new Item("Notebook")))
        );

        // TODO: Flatten list of items across all orders

        List<Item> allItems = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toList());

        assertEquals(3, allItems.size());
    }

    @Test
    public void testCustomCollector(){
        List<Product> products = new ArrayList<>(
                Arrays.asList(
                        new Product("one",10.0),
                        new Product("one",20.0),
                        new Product("two",1.0),
                        new Product("two",20.0)
                ));

        Map<String,Double> result =
                products.stream().collect(Collectors.groupingBy(Product::getName,Collectors.summingDouble(Product::getPrice)));

        result.entrySet().forEach((k)-> System.out.println(k.getKey()+" -> " +k.getValue()));

        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        Supplier<Map<String,Double>> sup = HashMap::new;

        BiConsumer<Map<String,Double>,Product> accumulator =  (x, y)-> x.merge(y.getName(),y.getPrice(),Double::sum);

        BinaryOperator<Map<String,Double>> combiner = (map1, map2) -> {
            map2.forEach((key, value) -> map1.merge(key, value, Double::sum));
            return map1;
        };

        Function<Map<String,Double>,Map<String,Double>> finisher = (map) -> map.entrySet().stream().filter(x-> x.getValue()>20.0)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));

        Map<String,Double> finRes = products.stream().collect(Collector.of(sup,accumulator,combiner,finisher));

        finRes.entrySet().forEach(System.out::println);
    }

    public class Employee {
        private String name;
        private String department;
        private double salary;

        public Employee(String name, String department) {
            this.name = name;
            this.department = department;
        }

        public Employee(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public double getSalary() {
            return salary;
        }
    }

    public class Student {
        private String name;
        private int marks;

        public Student(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        public String getName() {
            return name;
        }

        public int getMarks() {
            return marks;
        }
    }

    public class Order {
        private List<Item> items;

        public Order(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    public class Item {
        private String name;

        public Item(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public class Product {
        private String name;
        private double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    /**
     * Problem:- Fetch orders data from json, have a lookup to get orders from given start-end time ranges.
     *
     * If you use a List:
     * You’d have to scan the entire list every time to find orders in a time range.
     * That’s O(n) per query, even if the list is sorted.
     *
     * Use TreeMap
     * It keeps keys (orderTime) sorted automatically.
     * You can directly use subMap(start, end) — it gives you the range in O(log n + k) (k = results).
     * No full scan needed; it uses tree navigation to jump to start time and collect efficiently.
     *
     * @throws Exception
     */
    @Test
    void testOrdersWithinTimeRange() throws Exception {
        String json = """
            [
              {"orderId":"O1","orderName":"TV","orderTime":"2025-10-04T10:00:00"},
              {"orderId":"O2","orderName":"Laptop","orderTime":"2025-10-04T11:00:00"},
              {"orderId":"O3","orderName":"Phone","orderTime":"2025-10-04T11:00:00"},
              {"orderId":"O4","orderName":"Watch","orderTime":"2025-10-04T12:30:00"},
              {"orderId":"O5","orderName":"Tab","orderTime":"2025-10-04T12:35:00"},
              {"orderId":"O6","orderName":"SmartTV","orderTime":"2025-10-04T12:36:00"}
            ]
        """;

        ObjectMapper mapper = new ObjectMapper();
        List<Order1> orders = mapper.readValue(json, new TypeReference<>() {});

        TreeMap<LocalDateTime, List<Order1>> orderMap = new TreeMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (Order1 o : orders) {
            LocalDateTime time = LocalDateTime.parse(o.orderTime, fmt);
            orderMap.computeIfAbsent(time, t -> new ArrayList<>()).add(o);
        }

        LocalDateTime start = LocalDateTime.parse("2025-10-04T10:30:00", fmt);
        LocalDateTime end = LocalDateTime.parse("2025-10-04T12:00:00", fmt);

        List<Order1> result = orderMap.subMap(start, true, end, true)
                .values()
                .stream()
                .flatMap(List::stream)
                .toList();

        List<String> resultIds = result.stream().map(o -> o.orderId).toList();

        assertEquals(List.of("O2", "O3"), resultIds);
        assertTrue(result.stream().allMatch(o ->
                !LocalDateTime.parse(o.orderTime, fmt).isBefore(start) &&
                        !LocalDateTime.parse(o.orderTime, fmt).isAfter(end)));
    }

    static class Order1 {
        public String orderId;
        public String orderName;
        public String orderTime;
    }


}