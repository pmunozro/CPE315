import java.io.*;
import java.util.*;

public class direct4k_1
{
   public static long[] tag = new long[1024];
   public static int hits = 0;
   public static void main(int address)
   {
      int index;
      int newaddy;
      long idtag = 1048575;
      newaddy = address >> 2;
      index = newaddy % 1024;
      idtag = address & (idtag << 12);
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
