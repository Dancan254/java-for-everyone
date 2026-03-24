package enums;

public enum Season {
    SUMMER("summer"),
    WINTER("winter"),
    SPRING("Spring"),
    AUTUMN("autumn");

    private final String name;

    Season(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
