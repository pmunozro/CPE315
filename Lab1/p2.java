import java.util.Scanner;

class p2
{
   public static void main(String[] args)
   {
      int i, j, y, z, num;
      int result = 0;
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter a number: ");
      i = sc.nextInt();
      System.out.println("Enter a number to be as an exponent: ");
      j = sc.nextInt();
      if(i == 1)
      {
         result = 1;
      }
      else if (j == 1)
      {
         result = i;
      }
      else
      {
         num = i;
         result = result + num;
         for(y = j-1; y > 0; y--)
         {
            for(z = i-1; z > 0; z--)
            {
               result = result + num;
            }
            num = result;
         }
      }
      System.out.println(i + " raised to the power of " + j + " = " + result);
   }
}
