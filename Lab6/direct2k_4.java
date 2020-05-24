import java.io.*;
import java.util.*;

public class direct2k_4
{
   public static long[] tag = new long[128];
   public static int hits = 0;
   public static void main(int address)
   {
      int index;
      long idtag = 2097151;
      int newaddy;
      newaddy = address >> 4;
      index = newaddy % 128;
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
