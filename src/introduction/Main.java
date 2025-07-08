package introduction;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //obj1
//        Simple obj1 = new Simple("Ian");
//        Simple obj2 = new Simple("Ian");
//
//        System.out.println("Obj1 " + System.identityHashCode(obj1));
//        System.out.println("Obj2 " + System.identityHashCode(obj2));

        //anagrams
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = groupAnagrams(strs);
        System.out.println(result);

    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            //get the characters
            char[] arr = str.toCharArray();
            //sort the chars - aet
            Arrays.sort(arr);
            String key = new String(arr);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }
}
