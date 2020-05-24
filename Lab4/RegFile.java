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
      while(lab4.reg[++i1].equals(tokens[1]) == false);
      while(lab4.reg[++i2].equals(tokens[2]) == false);
      while(lab4.reg[++i3].equals(tokens[3]) == false)
      {
         if(i3 >= lab4.reg.length)
         {
            i3 = 1;
            break;
         }
      }
      if(tokens[0].equals("add"))
      {
         lab4.val[i1] = lab4.val[i2] + lab4.val[i3];
      }
      else if(tokens[0].equals("sub"))
      {
         lab4.val[i1] = lab4.val[i2] - lab4.val[i3];

      }
      else if(tokens[0].equals("and"))
      {
         lab4.val[i1] = lab4.val[i2] & lab4.val[i3];
      }
      else if(tokens[0].equals("or"))
      {
         lab4.val[i1] = lab4.val[i2] | lab4.val[i3];
      }
      if(lab4.data[1].equals("lw") && (lab4.id[0].equals(tokens[2]) || lab4.id[0].equals(tokens[3])))
      {
         lab4.stall = 1;
      }
      return true;
   }
   public static boolean doI(String[] tokens)
   {
      int i1, i2, i3;
      i1 = i2 = i3 = -1;
      if(tokens[0].equals("addi"))
      {
         while(lab4.reg[++i1].equals(tokens[1]) == false);
         while(lab4.reg[++i2].equals(tokens[2]) == false);
         lab4.val[i1] = lab4.val[i2] + Integer.parseInt(tokens[3]);
      }
      else if(tokens[0].equals("bne"))
      {
         while(lab4.reg[++i1].equals(tokens[1]) == false);
         while(lab4.reg[++i2].equals(tokens[2]) == false);
         if(lab4.val[i1] != lab4.val[i2])
         {
            return true;
         }
      }
      else if(tokens[0].equals("sw"))
      {
         while(lab4.reg[++i1].equals(tokens[1]) == false);
         tokens[2] = tokens[2].trim().substring(0, tokens[2].length() - 1);
         tokens[3] = tokens[3].trim().substring(0, tokens[3].length()-1);
         i2 = Integer.parseInt(tokens[2]);
         lab4.mem[i2] = lab4.val[i1];

      }
      else if(tokens[0].equals("lw"))
      {
         while(lab4.reg[++i1].equals(tokens[1]) == false);
         tokens[2] = tokens[2].trim().substring(0, tokens[2].length() - 1);
         tokens[3] = tokens[3].trim().substring(0, tokens[3].length() - 1);
         i2 = Integer.parseInt(tokens[2]);
         if(lab4.data[1].equals("lw") && (lab4.id[0].equals(tokens[2]) || lab4.id[0].equals(tokens[3])))
         {
            lab4.stall = 1;
         }
         lab4.id[0] = tokens[1];
      }
      if(lab4.data[1].equals("lw") && (lab4.id[0].equals(tokens[2]) || lab4.id[0].equals(tokens[3])))
      {
         lab4.stall = 1;
      }
      return false;
   }
   public static boolean doJ(String[] tokens, int imm)
   {
      int i1, i2;
      i1 = i2 = -1;
      if(tokens[0].equals("jal"))
      {
         return true;
      }
      else if(tokens[0].equals("j"))
      {
         lab4.jump[0] = imm;
         lab4.squash = 1;
      }
      return false;
   }
}
