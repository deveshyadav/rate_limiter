import java.util.*;
import java.util.stream.Collectors;

/**
 * (apple,banana,apple,orange,banana,apple). o/p  orange-1,bananna-2,apple-3
 */
public class Testing1 {

    public static void main(String[] args) {



        List<String> frutList = Arrays.asList("apple","banana", "apple","orange","banana","apple");

        Map<String,Long> fruitMap = frutList.stream().collect(Collectors.groupingBy( f-> f, Collectors.counting()));

        System.out.println(fruitMap);


    }



}
