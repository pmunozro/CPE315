# Names: Daniel De Leon & Pedro Munoz
# Section: 01
# Description: reverses the bits of the provded integer. Must be positive.
#
# import java.util.*;
# public class reverse
# {
#   public static void main(String[] args)
#   {
#      Scanner in = new Scanner(System.in);
#      System.out.println("This program reverse-orders a binary number");
#      System.out.println("Enter a positive integer");
#      int num = in.nextInt();
#      int bit = 0;
#      long reverse = 0;
#      for (int i = 0; i < 32; i++)
#      {
#         bit = num & (1 << i);
#         bit >>= i;
#         reverse |= bit << (31 - i);
#      }
#      System.out.println(reverse);
#   }
# }

.globl welcome
.globl prompt
.globl sumText

#  Data Area (this area contains strings to be displayed during the program)
.data

welcome:
   .asciiz " This program reverse-orders a binary number \n\n"

prompt:
   .asciiz " Enter a positive integer (num): "

sumText: 
   .asciiz " \n Reverse-order = "

#Text Area (i.e. instructions)
.text

main:
   # Display the welcome message (load 4 into $v0 to display)
   ori     $v0, $0, 4         
   # This generates the starting address for the welcome message.
   # (assumes the register first contains 0).
   lui     $a0, 0x1001
   syscall
   # Display prompt
   ori     $v0, $0, 4         
   
   # This is the starting address of the prompt (notice the
   # different address from the welcome message)
   lui     $a0, 0x1001
   ori     $a0, $a0,0x30
   syscall
   # Read 1st integer from the user (5 is loaded into $v0, then a syscall)
   ori     $v0, $0, 5
   syscall
   
   # save the first integer
   ori     $s0, $0, 0
   addu    $s0, $v0, $s0
   
   # Initialize registers
   add     $s1, $0, $0  # i
   add     $s2, $0, $0  # bit
   addi    $s3, $0, 1   # iterator
   add     $s4, $0, $0  # result
   add     $s5, $0, $0  # shift-left amount
   addi    $s6, $0, 31  # store the value
   # For loop
   loop: slti $t0, $s1, 32
   beq     $t0, $0, end
   sll     $s3, $s3, $s1
   sub     $s5, $s6, $s1
   sllv    $s2, $s2, $s5
   or      $s4, $s4, $s2
   addi    $s3, $0, 1
   addi    $s1, $s1, 1
   j       loop
   end:
   
   # Print result
   ori     $v0, $0, 4
   lui     $a0, 0x1001
   ori     $a0, $a0, 0x54
   syscall
   ori     $v0, $0, 1
   add     $a0, $s4, $0
   syscall
   # Exit (load 10 into $v0)
   ori     $v0, $0, 10
   syscall
   
