import java.io.*;
import java.util.*;

public class assoc4w_1
{
   public static int counter = 0;
   public static long[] tag1 = new long[128];
   public static long[] tag2 = new long[128];
   public static long[] tag3 = new long[128];
   public static long[] tag4 = new long[128];
   public static int[] lru1 = new int[128];
   public static int[] lru2 = new int[128];
   public static int[] lru3 = new int[128];
   public static int[] lru4 = new int[128];
   public static int hits = 0;
   public static void main(int address)
   {
      int index;
      long idtag = 8388607;
      int newaddy;
      newaddy = address >> 2;
      index = newaddy % 128;
      idtag = address & (idtag << 9);
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
      else if(tag3[index] == idtag)
      {
         hits++;
         lru3[index] = counter;
      }
      else if(tag4[index] == idtag)
      {
         hits++;
         lru4[index] = counter;
      }
      else
      {
         int one = lru1[index];
         int two = lru2[index];
         int three = lru3[index];
         int four = lru4[index];
         if(tag1[index] == 0)
         {
            tag1[index] = idtag;
            lru1[index] = counter;
         }
         else if(tag2[index] == 0)
         {
            tag2[index] = idtag;
            lru2[index] = counter;
         }
         else if(tag3[index] == 0)
         {
            tag3[index] = idtag;
            lru3[index] = counter;
         }
         else if(tag4[index] == 0)
         {
            tag4[index] = idtag;
            lru4[index] = counter;
         }
         else if(one < two && one < three && one < four)
         {
            /*System.out.println("one = "+one);
            System.out.println("two = "+ two);
            System.out.println("three = "+ three);
            System.out.println("four = "+ four);*/
            tag1[index] = idtag;
            lru1[index] = counter;
         }
         else if(two < one && two < three && two < four)
         {
            tag2[index] = idtag;
            lru2[index] = counter;
         }
         else if(three < one && three < two && three < four)
         {
            tag3[index] = idtag;
            lru3[index] = counter;
         }
         else/* if(four < one && four < two && four < three)*/
         {
            tag4[index] = idtag;
            lru4[index] = counter;
         }
      }
      counter += 1;
   }
}
