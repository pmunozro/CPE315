import java.io.*;
import java.util.*;

public class lab4
{
   public static String[] reg = {"pc", "$0", "$v0", "$v1", "$a0", "$a1", "$a2",
                                "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5",
                                "$t6", "$t7", "$s0", "$s1", "$s2", "$s3", "$s4",
                                "$s5", "$s6", "$s7", "$t8", "$t9", "$sp", "$ra"};
   public static int[] val = new int[28];               
   public static int[] mem = new int[8192];
   public static int stall = 0;
   public static int squash = 0;
   public static int[] jump = {0};
   public static String[] pipe = {"if/id", "id/exe", "exe/mem", "mem/wb"}; 
   public static String[] data = {"empty", "empty", "empty", "empty"};
   public static String[] id = {"empty"};
   public static int cycles = 0;
   public static int instructionsize;
   public static int Step = 0;
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
      int done = 0;
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
         if(step == -2)
         {
            i = 0;
            step = 0;
         }
         if(stall == 1)
         {
            cycles++;
            stall = 0;
            lab4.data[3] = lab4.data[2];
            lab4.data[2] = lab4.data[1];
            lab4.data[1] = "stall";
            lab4.id[0] = "empty";
         }
         if(squash == 1)
         {
            squash--;
            lab4.data[3] = lab4.data[2];
            lab4.data[2] = lab4.data[1];
            lab4.data[1] = lab4.data[0];
            lab4.data[0] = "squash";
            step = 0;
            val[0] += 1;
            squash = 0;
         }
         if(jump[0] != 0)
         {
            i = jump[0];
         }
         for(d = 0; i < lines.size() && d < step && stall == 0; i++)
         {
            jump[0] = 0;
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
            cycles++;
         }
         if(Step == 1)
         {
            System.out.print("\n"+lab4.reg[0]+"\t"+lab4.pipe[0]+"\t"+lab4.pipe[1]);
            System.out.println("\t"+lab4.pipe[2]+"\t"+lab4.pipe[3]);
            System.out.print(lab4.val[0]+"\t"+lab4.data[0]+"\t"+lab4.data[1]);
            System.out.println("\t"+lab4.data[2]+"\t"+lab4.data[3]+"\n");
            Step = 0;
         }
         if(i == lines.size())
         {
            if(!data[3].equals("empty"))
            {
               int s = 3;
               while(--s >= 0)
               {
                  data[s+1] = data[s];
               }
               data[0] = "empty";
               val[0] += 1;
               stall = 3;
               cycles++;
            }
            else
            {
               done = 1;
            }
         }
         if(i == lines.size() && done == 1)
         {
            double cpi = (double)cycles/i;
            String dbcpi = String.format("%.3f", cpi);
            System.out.println("\nProgram complete");
            System.out.println("CPI = "+dbcpi+"\tCycles = "+cycles+"\tInstructions = "+i);
            System.out.println();
            break;
         }
      }
   }
}
