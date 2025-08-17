import org.junit.jupiter.api.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MapTest {

    @Test
    void testCommonMapUsecases() {
        // Case 1: Basic put, get, containsKey
        Map<String, Integer> map = new HashMap<>();
        // TODO: put("apple", 100), put("banana", 200), put("orange", 300)

        Assertions.assertEquals(3, map.size());
        Assertions.assertTrue(map.containsKey("banana"));
        Assertions.assertEquals(200, map.get("banana"));
        NavigableMap<Integer,Integer> mm = new TreeMap<>();

        // Case 2: Updating values conditionally using computeIfPresent
        // TODO: Increase value of "apple" by 50 if present

        Assertions.assertEquals(150, map.get("apple"));

        // Case 3: Using computeIfAbsent
        // TODO: Insert "grape" with default 400 if not present

        Assertions.assertEquals(400, map.get("grape"));

        // Case 4: Iteration and filtering
        // TODO: Create filtered map where value > 200

        Map<String, Integer> filtered = map.entrySet().stream()
            .filter(e -> e.getValue() > 200)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Assertions.assertFalse(filtered.containsKey("apple"));
        Assertions.assertTrue(filtered.containsKey("grape"));
    }

    @Test
    void testGroupByAndCounting() {
        List<String> words = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");

        // TODO: Group and count frequency of each word using Map<String, Long>

        Map<String, Long> frequency = words.stream()
            .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        Assertions.assertEquals(3, frequency.get("apple"));
        Assertions.assertEquals(2, frequency.get("banana"));
        Assertions.assertEquals(1, frequency.get("orange"));
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

        Assertions.assertEquals(List.of("Alice", "John", "Bob"), new ArrayList<>(sorted.keySet()));
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
        Assertions.assertEquals(2, deptSalaries.get("HR").size());
        Assertions.assertEquals(6000, deptSalaries.get("HR").get("Bob"));
        Assertions.assertEquals(8000, deptSalaries.get("IT").get("Charlie"));
    }

    @Test
    void testMergeMapsAndConflictResolution() {
        Map<String, Integer> map1 = new HashMap<>(Map.of("A", 10, "B", 20));
        Map<String, Integer> map2 = new HashMap<>(Map.of("B", 30, "C", 40));

        // TODO: Merge both maps, if conflict, sum the values

        Map<String, Integer> merged = new HashMap<>(map1);
        map2.forEach((k, v) -> merged.merge(k, v, Integer::sum));

        Assertions.assertEquals(3, merged.size());
        Assertions.assertEquals(10, merged.get("A"));
        Assertions.assertEquals(50, merged.get("B")); // 20 + 30
        Assertions.assertEquals(40, merged.get("C"));
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

        Assertions.assertEquals(2, result.get("HR"));
        Assertions.assertEquals(1, result.get("Tech"));
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

        Assertions.assertEquals("Bob", result.get("HR").get().getName());
        Assertions.assertEquals("Charlie", result.get("Tech").get().getName());
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

        Assertions.assertEquals(2, result.get(true).size());
        Assertions.assertEquals(1, result.get(false).size());
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

        Assertions.assertEquals(3, allItems.size());
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


}