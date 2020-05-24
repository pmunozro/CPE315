/* Name: Pedro Munoz
*  Section: 1
*  Description: This program performs predictions on whether the program will jump
*  or not, i.e. it will guess if the next state will be taken or not taken. The program
*  demonstrates that with a bigger Global History Register table, the program has a higher
*  percentage of guessing the next step, to jump or not to jump, that is the question.
*  
**/
import java.io.*;
import java.util.*;

public class lab5
{
   public static String[] reg = {"pc", "$0", "$v0", "$v1", "$a0", "$a1", "$a2",
                                "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5",
                                "$t6", "$t7", "$s0", "$s1", "$s2", "$s3", "$s4",
                                "$s5", "$s6", "$s7", "$t8", "$t9", "$sp", "$ra"};
   public static int[] val = new int[28];               
   public static int[] mem = new int[8192];
   public static int[] table = new int[256];
   public static int jal = 0;
   public static int s = 0;
   public static int jr = 0;
   public static int taken = 0;
   public static int GHR = 0;
   public static int GHRcnt = 2;
   public static int instructionsize;
   public static int predictions = 0;
   public static int correctpredict = 0;
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
      if(args.length > 2)
      {
         GHRcnt = Integer.parseInt(args[2]);
      }
      instructionsize = lines.size();
      int step = 0;
      int read = 0;
      int d = i = 0;
      String[] script = new String[5];
      Arrays.fill(val, 0);
      Arrays.fill(table, 0);
      while(true)
      {
         if(args.length > 1 && read == 0)
         {
            try
            {
               BufferedReader reader = new BufferedReader(new FileReader(args[1]));
               while((script[s] = reader.readLine()) != null)
               {
                  s++;
               }
               read = 1;
               s = 0;
               reader.close();
            }
            catch(Exception error)
            {
               error.printStackTrace();
            }
         }
         if(step == 0)
         {
            step = mips.MIPS(step, args, script);
         }
         if(step == -1)
         {
            break;
         }
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
            if(jal == 1)
            {
               jal = 0;
            }
            if(jr == 1)
            {
               i = val[27] - 1;
               jr = 0;
            }
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
