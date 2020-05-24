import java.io.*;
import java.util.*;

public class RegFile
{
   private static String[] regNames =
   {"$0" ,""   ,"$v0","$v1","$a0","$a1","$a2","$a3",
   "$t0","$t1","$t2","$t3","$t4","$t5","$t6","$t7",
   "$s0","$s1","$s2","$s3","$s4","$s5","$s6","$s7",
   "$t8","$t9",""   ,""   ,""   ,"$sp",""   ,"$ra"};

   public static int getReg(String reg)
   {
      int i = 0;
      if(reg.equals("$zero"))
         return 0;
      while((regNames[i].equals(reg) == false) && (++i < 32));

      if(i == 32)
         return -1;
      return i;
   }
   public static boolean doR(String[] tokens)
   {
      int i1, i2, i3;
      i1 = i2 = i3 = -1;
      while(lab3.reg[++i1].equals(tokens[1]) == false);
      while(lab3.reg[++i2].equals(tokens[2]) == false);
      while(lab3.reg[++i3].equals(tokens[3]) == false)
      {
         if(i3 >= lab3.reg.length)
         {
            i3 = 1;
            break;
         }
      }
      if(tokens[0].equals("add"))
      {
         lab3.val[i1] = lab3.val[i2] + lab3.val[i3];
      }
      else if(tokens[0].equals("sub"))
      {
         lab3.val[i1] = lab3.val[i2] - lab3.val[i3];

      }
      else if(tokens[0].equals("and"))
      {
         lab3.val[i1] = lab3.val[i2] & lab3.val[i3];
      }
      else if(tokens[0].equals("or"))
      {
         lab3.val[i1] = lab3.val[i2] | lab3.val[i3];
      }
      else if(tokens[0].equals("slt"))
      {
         if(lab3.val[i2] < lab3.val[i3])
         {
            lab3.val[i1] = 1;
         }
         else
         {
            lab3.val[i1] = 0;
         }
      }
      return true;
   }
   public static boolean doI(String[] tokens)
   {
      int i1, i2, i3;
      i1 = i2 = i3 = -1;
      if(tokens[0].equals("addi"))
      {
         while(lab3.reg[++i1].equals(tokens[1]) == false);
         while(lab3.reg[++i2].equals(tokens[2]) == false);
         lab3.val[i1] = lab3.val[i2] + Integer.parseInt(tokens[3]);
      }
      else if(tokens[0].equals("bne"))
      {
         while(lab3.reg[++i1].equals(tokens[1]) == false);
         while(lab3.reg[++i2].equals(tokens[2]) == false);
         if(lab3.val[i1] != lab3.val[i2])
         {
            return true;
         }
      }
      else if(tokens[0].equals("sw"))
      {
         while(lab3.reg[++i1].equals(tokens[1]) == false);
         i2 = Integer.parseInt(tokens[2].substring(0, tokens[2].length()-1));
         lab3.mem[i2] = lab3.val[i1];

      }
      
      return false;
   }
   public static boolean doJ(String[] tokens)
   {
      int i1, i2;
      i1 = i2 = -1;
      if(tokens[0].equals("jal"))
      {
         return true;
      }
      return false;
   }
}
