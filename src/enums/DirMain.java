package enums;

import java.util.Arrays;

public class DirMain {
    public static void main(String[] args) {
//        Direction dir = Direction.EAST;
//        switch (dir){
//            case NORTH -> System.out.println("Headed north");
//            case SOUTH -> System.out.println("Headed south");
//            case EAST -> System.out.println("Headed east");
//            case WEST -> System.out.println("Headed west");
//            default -> System.out.println("Invalid direction");
//        }
        Direction[] dirs = Direction.values();
        System.out.println(Arrays.toString(dirs));

        System.out.println(Direction.WEST.ordinal());
        System.out.println(Direction.WEST.getClass().getSimpleName());
        System.out.println(Direction.WEST.name().getClass().getSimpleName());

        System.out.println(Direction.valueOf("WEST"));
       // System.out.println(Direction.valueOf("West"));

        for(Direction dir : dirs){
            System.out.println("Ordinal: " + dir.ordinal() + " Name: " + dir.name().toLowerCase());
        }
    }
}
