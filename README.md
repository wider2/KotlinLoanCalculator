I use Kotlin language instead of Java, to be modern and expand/improve my testing experience with Kotlin.

### Platform and tools:
    Android Studio 4
    Kotlin language
    MVVM View model
    RxJava2 for asynchronous events
    Google Room as SQL database
    AndroidX ConstraintLayout UI
    
### Testing
    Unit test
    Activity tests
    Mockk library instead of Mockito
    UI Espresso test
    Room database test

### Development process
    Before start work with Android system, first of all I have created the algorithm of the task.
    Decision engine implemented as a console application that simulate logic of credit calculator.
    See file Algorithm.md

### Step by step
    1. In fact, we ask user to input personal code correctly
    2. After that imitation of "sign in" procedure he can use loan calculator
    3. If debt found then he got negative conclusion
    4. If credit rating >= 1 and loan amount could be more, then we give more
    5. If credit rating < 1 then we try to find max possible amount
    6. if last assertion is not possible then decrease loan period to find last possible amount
