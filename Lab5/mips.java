import java.io.*;
import java.util.*;

public class mips
{
   public static int MIPS(int step, String[] args, String[] script)
   {
      Scanner sc = new Scanner(System.in);
      char c = 0;
      int num1, num2, i, file;
      num1 = num2 = file = 0;
      String myString = null;
      System.out.print("mips> ");
      if(args.length > 1)
      {
         file = 1;
         myString = script[lab5.s];
         c = myString.charAt(0);
         lab5.s++;
      }
      if(file == 0)
      {
         myString = sc.nextLine();
         c = myString.charAt(0);
      }
      if(c == 'q')
      {
         step = -1;
         sc.close();
         return -1;
      }
      else if(c == 'b')
      {
         double acc = (double)lab5.correctpredict*100/lab5.predictions;
         String dbacc = String.format("%.2f", acc);
         System.out.println();
         System.out.println("accuracy "+dbacc+"% ("+lab5.correctpredict+" correct predictions, "+lab5.predictions+" predictions)");
         System.out.println();
      }
      else if(c == 'c')
      {
         System.out.println("\tSimulator reset\n");
         Arrays.fill(lab5.val, 0);
         return 0;
      }
      else if(c == 'h')
      {
         System.out.println("\nh = show help");
         System.out.println("d = dump register state");
         System.out.println("s = single step through the program (i.e. execute 1 instruction and stop)");
         System.out.println("s num = step through num instructions of the program");
         System.out.println("r = run until the program ends");
         System.out.println("m num1 num2 = display data memory from location num1 to num2");
         System.out.println("c = clear all registers, memory, and the program counter to 0");
         System.out.println("q = exit the program\n");
      }
      else if(c == 's')
      {
         if(myString.length() == 1)
         {
            System.out.println("\t1 instruction(s) executed");
            num1 = 1;
         }
         else
         {
            myString = myString.substring(1, myString.length());
            num1 = Integer.parseInt(myString.trim());
            System.out.println("\t"+ num1 + " instruction(s) executed");
         }
         step = num1;
      }
      else if(c == 'm')
      {
         for(i = 2; i < myString.length();i++)
         {
            char t = myString.charAt(i);
            if(t <= '9' && t >= '0')
            {
               num1 = num1*10 + (t - '0');
            }
            else
            {
               break;
            }
         }
         while(i++ < myString.length() - 1)
         {
            char t = myString.charAt(i);
            if(t <= '9' && t >= '0')
            {
               num2 = num2*10 + (t - '0');
            }
            else
            {
               break;
            }
         }
         while(num1 <= num2)
         {
            System.out.print("["+num1+"] = "+ lab5.mem[num1] + "\n");
            num1++;
         }
         num1 = 0;
      }
      else if(c == 'r')
      {
         return -2;
      }
      else if(c == 'd')
      {
         int len = 1, col = 0;
         System.out.println("\n" + lab5.reg[0] + " = " + lab5.val[0]);
         while(len < lab5.reg.length)
         {
            System.out.print(lab5.reg[len] + " = " + lab5.val[len]);
            if(len == 1)
            {
               System.out.print(" ");
            }
            System.out.print( "     ");
            if(col == 3)
            {
               System.out.println();
               col = -1;
            }
            col++;
            len++;
         }
         System.out.println("\n");
      }
      return num1;
   }
}
