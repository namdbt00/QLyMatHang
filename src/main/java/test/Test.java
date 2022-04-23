package test;

public class Test {
    public static boolean ktNt(int n ) {
        if (n >= 2) {
            for (int i = 2; i <= Math.sqrt(n); i++)
                if (n % i == 0)
                    return false;
            return true;
        }
        return false;
    }

}
