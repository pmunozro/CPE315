import java.lang.Math;
import java.util.Scanner;
import java.io.IOException;

class prob1
{
   public static void main(String[] args)
   {
      int i, j;
      System.out.println("Enter a number: ");
      Scanner sc = new Scanner(System.in);
      i = sc.nextInt();
      System.out.println("Enter a divisor ");
      j = sc.nextInt();
      j--;
      j = j & i;
      System.out.println("Remainder is " + j);

   }
}
