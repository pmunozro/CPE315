import java.io.*;
import java.util.*;

public class predictor
{
   public static boolean predict(int i1, int i2)
   {
      int take;
      int bne = 0;
      if(lab5.table[lab5.GHR] <= 1)
      {
         take = 0;
      }
      else
      {
         take = 1;
      }
      if(lab5.val[i1] != lab5.val[i2])
      {
         lab5.taken = 1;
         bne = 1;
      }
      else
      {
         lab5.taken = 0;
      }
      if(take == lab5.taken)
      {
         lab5.correctpredict += 1;
         if(take == 0)
         {
            lab5.table[lab5.GHR] = lab5.table[lab5.GHR] -= 1;
            if(lab5.table[lab5.GHR] < 0)
               lab5.table[lab5.GHR] = 0;
            lab5.GHR = lab5.GHR << 1;
            if(lab5.GHRcnt == 2)
               lab5.GHR = lab5.GHR & 3;
            else if(lab5.GHRcnt == 4)
               lab5.GHR = lab5.GHR & 15;
            else if(lab5.GHRcnt == 8)
               lab5.GHR = lab5.GHR & 255;
         }
         else
         {
            lab5.table[lab5.GHR] = lab5.table[lab5.GHR] += 1;
            if(lab5.table[lab5.GHR] > 3)
               lab5.table[lab5.GHR] = 3;
            lab5.GHR = lab5.GHR << 1;
            if(lab5.GHRcnt == 2)
               lab5.GHR = lab5.GHR & 3;
            else if(lab5.GHRcnt == 4)
               lab5.GHR = lab5.GHR & 15;
            else if(lab5.GHRcnt == 8)
               lab5.GHR = lab5.GHR & 255;
            lab5.GHR = lab5.GHR | 1;
         }
      }
      else
      {
         if(lab5.taken == 0)
         {
            lab5.table[lab5.GHR] = lab5.table[lab5.GHR] -= 1;
            if(lab5.table[lab5.GHR] < 0)
               lab5.table[lab5.GHR] = 0;
            lab5.GHR = lab5.GHR << 1;
            if(lab5.GHRcnt == 2)
               lab5.GHR = lab5.GHR & 3;
            else if(lab5.GHRcnt == 4)
               lab5.GHR = lab5.GHR & 15;
            else if(lab5.GHRcnt == 8)
               lab5.GHR = lab5.GHR & 255;
         }
         else
         {
            lab5.table[lab5.GHR] = lab5.table[lab5.GHR] += 1;
            if(lab5.table[lab5.GHR] > 3)
               lab5.table[lab5.GHR] = 3;
            lab5.GHR = lab5.GHR << 1;
            if(lab5.GHRcnt == 2)
               lab5.GHR = lab5.GHR & 3;
            else if(lab5.GHRcnt == 4)
               lab5.GHR = lab5.GHR & 15;
            else if(lab5.GHRcnt == 8)
               lab5.GHR = lab5.GHR & 255;
            lab5.GHR = lab5.GHR | 1;
         }
      }
      if(bne == 1)
         return true;
      return true;
   }
}
