import java.io.*;
import java.util.*;

public class direct2k_2
{
   public static long[] tag = new long[256];
   public static int hits = 0;
   public static void main(int address)
   {
      int index;
      long idtag = 2097151;
      int newaddy;
      newaddy = address >> 3;
      index = newaddy % 256;
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
