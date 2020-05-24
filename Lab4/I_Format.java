import java.io.*;
import java.util.*;

public class I_Format
{
   private static String[] iNames = {"beq","bne","lw","sw","addi"};
   private static int[]    op     = {4    ,5    ,35   ,43 ,8     };

   public static boolean check(String[] tokens)
   {
      if (tokens.length > 1)
         for (int i = 0; i < iNames.length; i++)
            if (iNames[i].equals(tokens[0]))
               return true;
      return false;
   }

   public static int printInstruction(String[] tokens, int cLine,
      ArrayList<String> labelArray, ArrayList<Integer> labelLineArray)
   {
      int i = -1;
      int j = 0;
      int s = 3;
      boolean negImm = false;
      int rs, rt, imm;
      rs = rt = imm = 0;

      while(iNames[++i].equals(tokens[0]) == false);

      System.out.println("i="+i);
      if(i < 2)
      {
         rs = RegFile.getReg(tokens[1]);
         rt = RegFile.getReg(tokens[2]);

         while(!tokens[3].equals(labelArray.get(j)))
         {
            if(j+1 >= labelArray.size())
               break;
            j++;
         }

         if(j >= labelArray.size())
            return -1;
         imm = labelLineArray.get(j) - (cLine + 2);
      }
      else if (i < 4)
      {
         System.out.println("tokens[1]="+tokens[1]);
         rt = RegFile.getReg(tokens[1]);
         imm = Integer.parseInt(tokens[2].substring(0,tokens[2].length()-1));
         rs = RegFile.getReg(tokens[3].substring(0,tokens[3].length()-1));
      }
      else
      {
         rs = RegFile.getReg(tokens[2]);
         rt = RegFile.getReg(tokens[1]);
         imm = Integer.parseInt(tokens[3]);
      }
      while(--s >= 0)
      {
         lab4.data[s+1] = lab4.data[s];
      }
      lab4.data[0] = tokens[0];
      if(RegFile.doI(tokens))
      {
         return labelLineArray.get(j) - 2;
      }
      if (rt == -1 || rs == -1)
         return -1;

      if (imm < 0)
      {
         negImm = true;
         imm = ~(imm*-1) + 1;
      }
      /*if (negImm)
      {
         System.out.println(
            String.format("%6s",Integer.toBinaryString(op[i])).replace(' ','0') +" " +
            String.format("%5s",Integer.toBinaryString(rs)).replace(' ','0')+" " +
            String.format("%5s",Integer.toBinaryString(rt)).replace(' ','0')+" " +
            String.format("%16s",Integer.toBinaryString(imm)).replace(' ','0').substring(16));
      }
      else
      {
         System.out.println(
            String.format("%6s",Integer.toBinaryString(op[i])).replace(' ','0') +" " +
            String.format("%5s",Integer.toBinaryString(rs)).replace(' ','0')+" " +
            String.format("%5s",Integer.toBinaryString(rt)).replace(' ','0')+" " +
            String.format("%16s",Integer.toBinaryString(imm)).replace(' ','0'));
      }*/
      return cLine;
   }
}
