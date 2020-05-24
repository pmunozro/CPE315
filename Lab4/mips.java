import java.io.*;
import java.util.*;

public class mips
{
   public static int MIPS(int step)
   {
      Scanner sc = new Scanner(System.in);
      char c;
      int num1, num2, i;
      num1 = num2 = 0;
      String myString;
      System.out.print("mips> ");
      myString = sc.nextLine();
      c = myString.charAt(0);
      if(c == 'q')
      {
         step = -1;
         sc.close();
         return -1;
      }
      else if(c == 'c')
      {
         System.out.println("\tSimulator reset\n");
         num1 = -2;
         Arrays.fill(lab4.val, 0);
      }
      else if(c == 'h')
      {
         System.out.println("\nh = show help");
         System.out.println("d = dump register state");
         System.out.println("p = show pipeline registers");
         System.out.println("s = step through a single clock cycle step (i.e. simulate 1 cycle and stop)");
         System.out.println("s num = step through num clock cycles");
         System.out.println("r = run until the program ends and display timing summary");
         System.out.println("m num1 num2 = display data memory from location num1 to num2");
         System.out.println("c = clear all registers, memory, and the program counter to 0");
         System.out.println("q = exit the program\n");
      }
      else if(c == 's')
      {
         if(myString.length() == 1)
         {
            lab4.Step = 1;
            if(lab4.stall == 0)
            {
               num1 = 1;
            }
         }
         else
         {
            myString = myString.substring(1, myString.length());
            num1 = Integer.parseInt(myString.trim());
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
            if(t<= '9' && t >= '0')
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
            System.out.print("["+num1+"] = "+ lab4.mem[num1] + "\n");
            num1++;
         }
         num1 = 0;
      }
      else if(c == 'r')
      {
         num1 = lab4.instructionsize;
      }
      else if(c == 'd')
      {
         int len = 1, col = 0;
         System.out.println("\n" + lab4.reg[0] + " = " + lab4.val[0]);
         while(len < lab4.reg.length)
         {
            System.out.print(lab4.reg[len] + " = " + lab4.val[len]);
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
