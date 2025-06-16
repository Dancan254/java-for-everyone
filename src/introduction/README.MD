# Java Data Types & Variables

## Memory Model
```
JAVA MEMORY - "Office Building"

┌─────────────────┐
│ Room 1001       │ ← int age = 25
│ [    25    ]    │
├─────────────────┤
│ Room 1002       │ ← double salary = 5000.50
│ [ 5000.50  ]    │
├─────────────────┤
│ Room 1003       │ ← boolean active = true
│ [   true   ]    │
└─────────────────┘

Variable = Labeled Box
Data Type = Box Size
Value = Contents
```

---

## Primitive Data Types
```
TYPE        SIZE     RANGE               EXAMPLE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
byte        8 bits   -128 to 127         byte temp = 25
short       16 bits  -32K to 32K         short year = 2024
int         32 bits  -2.1B to 2.1B       int count = 100
long        64 bits  Very large          long distance = 150L

float       32 bits  ~7 digits           float price = 19.99f
double      64 bits  ~15 digits          double pi = 3.14159

char        16 bits  Unicode             char grade = 'A'
boolean     1 bit    true/false          boolean pass = true
```

---

## Variable Naming Rules
```
✅ CORRECT                    ❌ INCORRECT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
studentAge                   student-age
firstName                    first name
maxScore                     2ndScore
isActive                     is-active
accountBalance               account$balance

CONSTANTS: MAX_STUDENTS, PI, UNIVERSITY_NAME
```

---

## Operator Precedence
```
HIGH PRIORITY    →    LOW PRIORITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

()              Parentheses
++ --           Increment/Decrement
* / %           Multiplication, Division, Modulus
+ -             Addition, Subtraction
< > <= >=       Comparison
== !=           Equality
&&              Logical AND
||              Logical OR
=               Assignment

Example: 2 + 3 * 4 = 14 (not 20)
```

---

## Type Casting Visual
```
IMPLICIT CASTING (Automatic)
┌─────┐    ┌─────────┐    ┌────────────┐
│ int │ ──→│ double  │ ──→│   String   │
│ 5   │    │  5.0    │    │   "5.0"    │
└─────┘    └─────────┘    └────────────┘
  Small         Large         Largest

EXPLICIT CASTING (Manual)
┌────────────┐    ┌─────────┐    ┌─────┐
│   double   │ ──→│  int    │ ──→│byte │
│   9.8      │    │   9     │    │  9  │
└────────────┘    └─────────┘    └─────┘
  Data Loss!      Truncated    Possible Loss
```

---

## Scanner Input Methods
```
METHOD              READS               EXAMPLE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
nextInt()          Integer only        25
nextDouble()       Decimal number      3.14
nextLine()         Entire line         "John Doe"
next()             Single word         "John"
nextBoolean()      true/false          true

⚠️  REMEMBER: Use nextLine() after nextInt()!
```

---

## String Concatenation Order
```
EXPRESSION                 RESULT          WHY?
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
5 + 3 + "Java"            "8Java"         Math first
"Java" + 5 + 3            "Java53"        String first
5 + (3 + "Java")          "53Java"        Parentheses force
"Result: " + (5 + 3)      "Result: 8"     Parentheses force

LEFT-TO-RIGHT EVALUATION!
```

---

## Common Gotchas
```
❌ TRAP                           ✅ SOLUTION
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
7 / 2 = 3                       7.0 / 2 = 3.5
0.1 + 0.2 ≠ 0.3                Use BigDecimal for exact math
name == "John"                  name.equals("John")
nextInt() then nextLine()       Call nextLine() after nextInt()
```

---

## Session Objectives
```
BY THE END OF THIS SESSION, YOU WILL:

□ Choose appropriate data types for different scenarios
□ Declare and initialize variables correctly  
□ Apply operators with proper precedence
□ Handle user input with Scanner
□ Debug common type-related errors
□ Write a complete program with calculations

LET'S CODE! 💻
```