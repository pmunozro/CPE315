import java.io.*;
import java.util.*;

public class J_Format
{
   private static String[] jNames = {"j","jal"};
   private static int[]    op     = {2  , 3};

   public static boolean check(String[] tokens)
   {
      if (tokens.length > 1)
         for (int i = 0; i < jNames.length; i++)
            if (jNames[i].equals(tokens[0]))
               return true;
      return false;
   }

   public static int printInstruction(String[] tokens, int cline,
      ArrayList<String> labelArray, ArrayList<Integer> labelLineArray)
   {
      int i = -1;
      int j = 0;
      int k;
      int imm;
      int s = 3;

      while((jNames[++i].equals(tokens[0])) == false);

      while(!(tokens[1].equals(labelArray.get(j))))
      {
         if(j+1 >= labelArray.size())
            break;
         j++;
      }

      imm = labelLineArray.get(j) - 1;
      while(--s >= 0)
      {
         lab4.data[s+1] = lab4.data[s];
      }
      lab4.data[0] = tokens[0];
      if(RegFile.doJ(tokens, imm))
      {
         return labelLineArray.get(j) - 2;
      }
      if(imm < 0)
         return -1;

      /*System.out.println(
         String.format("%6s",Integer.toBinaryString(op[i])).replace(' ','0') +" " +
         String.format("%26s",Integer.toBinaryString(imm)).replace(' ','0'));
        */ 
      return cline;
   }
}
