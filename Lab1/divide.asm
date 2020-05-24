# Names: Daniel De Leon & Pedro Munoz 
# Section: 01
# Description: divides a 64-bit unsigned number (two 32-bit numbers) by 
#  a 31-bit number. divisor must be a positive power of 2.
#
# import java.util.*;
# 
# public class divide
# {
#    public static void main(String[] args)
#    {
#       Scanner in = new Scanner(System.in);
#       System.out.println("This program divides a 64 bit int by a 31 bit int");
#       System.out.println("Enter a positive integer for the upper word");
#       int upper = in.nextInt();
#       System.out.println("Enter a positive integer for the lower word");
#       int lower = in.nextInt();
#       System.out.println("Enter a positive integer for the divisor");
#       int div = in.nextInt();
#       int shift = 0;
#
#       while(div != 1)
#       {
#          shift++;
#          div /= 2;
#       }
#       int underFlow = upper & (~(0xFF << shift));
#       upper >>= shift;
#       lower >>= shift;
#       lower |= underFlow << (32 - shift);
#       System.out.println(upper + "," + lower);
#    }
# }
.globl welcome
.globl prompt1
.globl prompt2
.globl prompt3
.globl sumText
.globl comma
#  Data Area (this area contains strings to be displayed during the program)
.data
welcome:
   .asciiz " This program divides a 64 bit int by a 31 bit int \n\n"
prompt1:
   .asciiz " Enter a positive integer for the upper word: "
prompt2:
   .asciiz " Enter a positive integer for the lower word: "
prompt3:
   .asciiz " Enter a positive integer for the divisor: "
sumText: 
   .asciiz " \n Quotient = "
comma:
   .asciiz " , "
#Text Area (i.e. instructions)
.text
main:
   ori     $v0, $0, 4         
   lui     $a0, 0x1001
   syscall
   ori     $v0, $0, 4         
   
   lui     $a0, 0x1001
   ori     $a0, $a0,0x36
   syscall
   ori     $v0, $0, 5
   syscall
   
   # save upper word 
   addu    $s1, $0, $v0
   
   ori     $v0 $0, 4
   
   lui     $a0, 0x1001
   ori     $a0, $a0,0x65
   syscall
   ori     $v0, $0, 5
   syscall
   # save lower word
   addu    $s0, $0, $v0
   ori     $v0 $0, 4
   
   lui     $a0, 0x1001
   ori     $a0, $a0,0x94
   syscall
   ori     $v0, $0, 5
   syscall
   
   # save divsor
   addu    $s2, $0, $v0
   
   # Initialize registers
   add     $s3, $0, $0  # shift amount
   addi    $s4, $0, 1  # the number 1
   # For loop
   loop: beq  $s4, $s2, end
   addi    $s3, $s3, 1
   srl     $s2, $s2, 0x1
   j       loop
   end:
   addi    $s5, $0, 0xff
   sllv    $s5, $s5, $s3
   nor     $s5, $s5, $s5
   and     $s5, $s1, $s5
   srlv    $s1, $s1, $s3
   srlv    $s0, $s0, $s3
   addi    $s7, $0, 32
   sub     $s6, $s7, $s3
   
   sllv    $s5, $s5, $s6
   or      $s0, $s0, $s5
   # Print result
   ori     $v0, $0, 4
   lui     $a0, 0x1001
   ori     $a0, $a0, 0xc2
   syscall
   ori     $v0, $0, 1
   add    $a0, $0, $s1
   syscall
   ori     $v0, $0, 4
   lui     $a0, 0x1001
   ori     $a0, $a0, 0xcf
   syscall
   
   ori     $v0, $0, 1
   add     $a0, $0, $s0
   syscall
   # Exit (load 10 into $v0)
   ori     $v0, $0, 10
   syscall
   
