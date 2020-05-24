import java.io.*;
import java.util.*;

public class lab6
{
   public static int[] bsize = {1, 2, 4, 1, 1, 4, 1}; 
   public static int[] assoc = {1, 1, 1, 2, 4, 4, 1};
   public static int[] csize = {2048, 2048, 2048, 2048, 2048, 2048, 4096};
   public static int[] hits = new int[7];
   public static double[] hrate = new double[7];
   public static void main(String[] args)
   {
      try
      {
         BufferedReader reader = new BufferedReader(new FileReader(args[0]));
         String line;
         int address;
         while((line = reader.readLine()) != null)
         {
            address = Integer.parseInt(line.substring(3, line.length()), 16);
            direct2k_1.main(address);
            direct2k_2.main(address);
            direct2k_4.main(address);
            assoc2w_1.main(address);
            assoc4w_1.main(address);
            assoc4w_4.main(address);
            direct4k_1.main(address);
         }
      }
      catch(Exception error)
      {
         error.printStackTrace();
      }
      int c = 0;
      hits[0] = direct2k_1.hits;
      hits[1] = direct2k_2.hits;
      hits[2] = direct2k_4.hits;
      hits[3] = assoc2w_1.hits;
      hits[4] = assoc4w_1.hits;
      hits[5] = assoc4w_4.hits;
      hits[6] = direct4k_1.hits;
      hrate[0] = direct2k_1.hits/50000.0;
      hrate[1] = direct2k_2.hits/50000.0;
      hrate[2] = direct2k_4.hits/50000.0;
      hrate[3] = assoc2w_1.hits/50000.0;
      hrate[4] = assoc4w_1.hits/50000.0;
      hrate[5] = assoc4w_4.hits/50000.0;
      hrate[6] = direct4k_1.hits/50000.0;
      while(c < 7)
      {
         System.out.println("Cache #"+(c + 1));
         System.out.println("Cache size: "+csize[c]+"B Associativity: "+assoc[c]+"  Block size: "+bsize[c]);
         System.out.println("Hits: "+hits[c]+"  Hit Rate: "+String.format("%.2f",hrate[c])+"%");
         System.out.println("---------------------------");
         c++;
      }
   }
}
