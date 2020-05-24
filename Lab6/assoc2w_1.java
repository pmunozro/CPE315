import java.io.*;
import java.util.*;

public class assoc2w_1
{
   public static int counter = 0;
   public static long[] tag1 = new long[256];
   public static long[] tag2 = new long[256];
   public static int[] lru1 = new int[256];
   public static int[] lru2 = new int[256];
   public static int hits = 0;
   public static void main(int address)
   {
      int index;
      long idtag = 4194303;
      int newaddy;
      newaddy = address >> 2;
      index = newaddy % 256;
      idtag = address & (idtag << 10);
      if(tag1[index] == idtag)
      {
         hits++;
         lru1[index] = counter;
      }
      else if(tag2[index] == idtag)
      {
         hits++;
         lru2[index] = counter;
      }
      else
      {
         if(lru1[index] < lru2[index])
         {
            tag1[index] = idtag;
            lru1[index] = counter;
         }
         else
         {
            tag2[index] = idtag;
            lru2[index] = counter;
         }
      }
      counter += 1;
   }
}
