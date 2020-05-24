import java.io.*;
import java.util.*;

public class lab3
{
   public static String[] reg = {"pc", "$0", "$v0", "$v1", "$a0", "$a1", "$a2",
                                "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5",
                                "$t6", "$t7", "$s0", "$s1", "$s2", "$s3", "$s4",
                                "$s5", "$s6", "$s7", "$t8", "$t9", "$sp", "$ra"};
   public static int[] val = new int[28];               
   public static int[] mem = new int[8192];
   public static int instructionsize;
   static void exitFailure(int i, ArrayList<String[]> lines)
   {
      System.out.println("invalid instruction: " + lines.get(i)[0]);
      System.exit(-1);
   }

   public static void main(String[] args)
   {
      int i;
      int lineCnt = 1;
      String[] tokens;
      ArrayList<String> labelArray = new ArrayList<String>();
      ArrayList<Integer> labelLineArray = new ArrayList<Integer>();
      ArrayList<String[]> lines = new ArrayList<String[]>();
      try
      {
         BufferedReader reader = new BufferedReader(new FileReader(args[0]));
         String line;
         while((line = reader.readLine()) != null)
         {
            line = line.replace(",", " ");
            line = line.replace("$", " $");
            line = line.replace(":", ": ");

            for(i = 0; i < line.length(); i++)
            {
               if(line.charAt(i) == '#')
                  line = line.substring(0, i);
            }
            tokens = line.trim().split("\\s+");
            if(tokens.length > 0 && tokens[0].length() > 0 &&
                  tokens[0].charAt(tokens[0].length()-1) == ':')
            {
               labelArray.add(tokens[0].substring(0, tokens[0].length()-1));
               labelLineArray.add(lineCnt);

               if(tokens.length > 1)
               {
                  lineCnt++;
                  lines.add(Arrays.copyOfRange(tokens, 1, tokens.length));
               }
            }
            else if(tokens.length > 1)
            {
               lines.add(tokens);
               lineCnt++;
            }
         }
         reader.close();
      }
      catch (Exception error)
      {
         error.printStackTrace();
      }
      instructionsize = lines.size();
      int step = 0;
      int d = i = 0;
      Arrays.fill(val, 0);
      while(true)
      {
         if(step == 0)
         {
            step = mips.MIPS(step);
         }
         if(step == -1)
         {
            break;
         }
         /*if(step == -2)
         {
            i = 0;
            step = 0;
         }*/
         for(d = 0; i < lines.size() && d != step; i++)
         {
            if(R_Format.check(lines.get(i)))
            {
               if(!R_Format.printInstruction(lines.get(i)))
                  exitFailure(i, lines);
            }
            else if(I_Format.check(lines.get(i)))
            {
               if((i = I_Format.printInstruction(lines.get(i), i, 
                  labelArray,labelLineArray)) == -1)
                  exitFailure(i, lines);
            }
            else if(J_Format.check(lines.get(i)))
            {
               if((i = J_Format.printInstruction(lines.get(i), i, 
                  labelArray, labelLineArray)) == -1)
                  exitFailure(i, lines);
            }
            else
               exitFailure(i, lines);
            step--;
            val[0] = i + 1;
         }
         if(step != 0)
         {
            step = 0;
         }
      }
   }
}
