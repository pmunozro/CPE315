import java.io.*;
import java.util.*;

public class R_Format
{
   private static String[] rNames = {"add", "sll", "sub", "and", "or", "slt", "jr"};
   private static int[]    funct  = {32   ,0     ,34    ,36    , 37  ,42    , 8   };

   public static boolean check(String[] tokens)
   {
      if(tokens.length > 1)
         for(int i = 0; i < rNames.length; i++)
            if(rNames[i].equals(tokens[0]))
               return true;
      return false;
   }
   
   public static boolean printInstruction(String[] tokens)
   {
      int i = -1;
      int rs, rt, rd, shamt;
      shamt = rs = rt = rd = 0;

      while(rNames[++i].equals(tokens[0]) == false);

      if(tokens[0].equals("jr"))
         rs = 31;
      else
      {
         if(tokens[0].equals("sll"))
         {
            rt = RegFile.getReg(tokens[2]);
            shamt = Integer.parseInt(tokens[3]);
         }
         else
         {
            rs = RegFile.getReg(tokens[2]);
            shamt = RegFile.getReg(tokens[3]);
         }
         rd = RegFile.getReg(tokens[1]);
         /*System.out.println(tokens[1] + " = " + rd);
         System.out.println(tokens[2] + " = " + rs);
         System.out.println(tokens[3] + " = " + shamt);*/
      }
      RegFile.doR(tokens);
      /*if(rt == -1 || rs == -1 || rd == -1)
         return false;*/
      /*System.out.println(
         String.format("%6s", Integer.toBinaryString(0)).replace(' ','0') + " " +
         String.format("%5s", Integer.toBinaryString(rs)).replace(' ','0') + " " +
         String.format("%5s", Integer.toBinaryString(rt)).replace(' ','0') + " " +
         String.format("%5s", Integer.toBinaryString(rd)).replace(' ','0') + " " +
         String.format("%5s", Integer.toBinaryString(shamt)).replace(' ','0') + " " +
         String.format("%6s", Integer.toBinaryString(funct[i])).replace(' ','0'));
*/
      return true;
   }
}
