package enums;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(Season.SUMMER);
        Season season = Season.SUMMER;

        switch(season){
            case SUMMER -> System.out.println("It is summer");
            case WINTER -> System.out.println("It is Winner");
            default -> System.out.println("It is not summer or winter");
        }
        System.out.println(Arrays.toString(Season.values()));
        System.out.println(Season.SPRING.ordinal());
        System.out.println(Season.WINTER.name());
        System.out.println(Season.WINTER.getName());
        Season spring = Season.valueOf("Spring");
        Season[] seasons = Season.values();
        for(Season season1 : Season.values()){
            //do sth
        }
    }
}
