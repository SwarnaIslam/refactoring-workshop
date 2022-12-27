package workshop;

public class FizzBuzz {
    public static String say(int number) {
        if(isFizzBuzz(number)){
            return "FizzBuzz";
        }
        else if(isFizz(number)){
            return "Fizz";
        }
        else if(isBuzz(number)){
            return "Buzz";
        }
        return String.valueOf(number);
    }
    private static boolean isFizz(int currentNumber){
        return currentNumber%3==0;
    }
    private static boolean isBuzz(int currentNumber){
        return currentNumber%5==0;
    }
    private static boolean isFizzBuzz(int currentNumber){
        return currentNumber%15==0;
    }
}
