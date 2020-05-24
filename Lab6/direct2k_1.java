import java.io.*;
import java.util.*;

public class direct2k_1
{
   public static long[] tag = new long[512];
   public static int hits = 0;
   public static void main(int address)
   {
      int index;
      long idtag = 2097151;
      int newaddy;
      newaddy = address >> 2;
      index = newaddy % 512;
      idtag = address & (idtag << 11);
      if(tag[index] == -1)
      {
         tag[index] = idtag;
      }
      else
      {
         if(tag[index] == idtag)
         {
            hits++;
         }
         else
         {
            tag[index] = idtag;
         }
      }
   }
}
