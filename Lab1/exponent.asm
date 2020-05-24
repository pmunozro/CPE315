# Names: Daniel De Leon & Pedro Munoz
# Section: 01
# Description: executes an exponent operation 
#
# import java.util.Scanner;
# 
# class p2
# {
#    public static void main(String[] args)
#    {
#       int i, j, y, z, num;
#       int result = 0;
#       Scanner sc = new Scanner(System.in);
#       System.out.println("Enter a number: ");
#       i = sc.nextInt();
#       System.out.println("Enter a number to be as an exponent: ");
#       j = sc.nextInt();
#       if(i == 1 || j == 0)
#       {
#          result = 1;
#       }
#       else if (j == 1)
#       {
#          result = i;
#       }
#       else
#       {
#          num = i;
#          result = result + num;
#          for(y = j-1; y > 0; y--)
#          {
#             for(z = i-1; z > 0; z--)
#             {
#                result = result + num;
#             }
#             num = result;
#          }
#       }
#       System.out.println(i + " raised to the power of " + j + " = " + result);
#    }
# }

.globl welcome
.globl prompt1
.globl prompt2
.globl sumText

#  Data Area (this area contains strings to be displayed during the program)
.data

welcome:
   .asciiz " This program calculates an exponentiation \n\n"

prompt1:
   .asciiz " Enter base number: "

prompt2:
   .asciiz " Enter exponent number: "

sumText: 
   .asciiz " \n Result = "

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
   ori     $a0, $a0,0x2E
   syscall
   # Read 1st integer from the user (5 is loaded into $v0, then a syscall)
   ori     $v0, $0, 5
   syscall
   
   # save the base
   ori     $s0, $0, 0
   addu    $s0, $v0, $s0
   
   # print prompt2
   ori     $v0, $0, 4
   lui     $a0, 0x1001
   ori     $a0, $a0,0x43
   syscall
   # read exponent
   ori     $v0, $0, 5
   syscall
   # save exponent
   ori     $s1, $v0, 0
   addi    $t0, $0, 1
   beq     $t0, $s0, end1
   beq     $0, $s1, end1
   beq     $t0, $s1, end2
   beq     $0, $s0, end2
   
   # set num variable
   ori     $s3, $s0, 0
   # start result
   ori     $s2, $s0, 0
   
   # first for loop
   loop1: beq $s1, $t0, print
   #decrement 
   addi    $s1, $s1, -1
   # base: t1 = s0
   ori     $t1, $s0, 0
   #add to num
   ori     $s3, $s2, 0
   # second for loop
   loop2: beq $t1, $t0, loop1
   add     $s2, $s2, $s3
   # decrement
   addi    $t1, $t1, -1
   j       loop2
   end1:
   ori     $s2, $0, 1
   j       print
   end2:
   ori     $s2, $s0, 0
   j       print
   print:
   # Print result
   ori     $v0, $0, 4
   lui     $a0, 0x1001
   ori     $a0, $a0, 0x5E
   syscall
   ori     $v0, $0, 1
   add     $a0, $s2, $0
   syscall
   # Exit (load 10 into $v0)
   ori     $v0, $0, 10
   syscall
   
