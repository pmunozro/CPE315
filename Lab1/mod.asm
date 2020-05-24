# Names: Daniel De Leon & Pedro Munoz
# Section: 01
# Description: executes modulo operation. Takes in a number and a divisor
#  and returns the remainder. Divisor must be power of 2.
#
# import java.util.*;
# public class mod
# {
#   public static void main(String[] args)
#   {
#      Scanner in = new Scanner(System.in);
#      System.out.println("This program fast \"mod\"s two numbers");
#      System.out.println("Enter an integer (num)");
#      int num = in.nextInt();
#      System.out.println("Enter an integer (div)");
#      int div = in.nextInt();
#      int result = num & (div - 1);
#      System.out.println(result);
#   }
# }

.globl welcome
.globl prompt1
.globl prompt2
.globl sumText

#  Data Area (this area contains strings to be displayed during the program)
.data

welcome:
   .asciiz " This program fast \"mod\"s two numbers \n\n"

prompt1:
   .asciiz " Enter an integer (num): "

prompt2:
   .asciiz " Enter an integer (div - power of 2): "

sumText: 
   .asciiz " \n Remainder = "

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
   ori     $a0, $a0,0x29
   syscall
   # Read 1st integer from the user (5 is loaded into $v0, then a syscall)
   ori     $v0, $0, 5
   syscall
   
   # save the first integer
   ori     $s0, $0, 0
   addu    $s0, $v0, $s0
   # Display prompt
   ori     $v0, $0, 4  
   
   # This is the starting address of the prompt (notice the
   # different address from the welcome message)
   lui     $a0, 0x1001
   ori     $a0, $a0,0x43
   syscall
   # Read 2nd integer from the user (5 is loaded into $v0, then a syscall)
   ori     $v0, $0, 5
   syscall
   # save the 2nd input
   ori     $s1, $0, 0
   addu    $s1, $v0, $s1
   # subtract 1 from divisor
   addi    $s1, $s1, -1
   # And the num and the divisor
   and     $s0, $s0, $s1
   # Print result
   ori     $v0, $0, 4
   lui     $a0, 0x1001
   ori     $a0, $a0, 0x6c
   syscall
   ori     $v0, $0, 1
   add     $a0, $s0, $0
   syscall
   # Exit (load 10 into $v0)
   ori     $v0, $0, 10
   syscall
   
