Minimize

z1_0_1 + z1_0_2 + z1_0_3 + z1_0_4 + z1_1_2 + z1_1_3 + z1_1_4 + z1_2_3 + z1_2_4 + z1_3_4


Subject To

b0: z1_0_1 >= 1.0
b1: z1_0_2 >= 1.0
b2: z1_0_3 >= 1.0
b3: z1_0_4 >= 1.0
b4: z1_1_2 >= 1.0
b5: z1_1_3 >= 1.0
b6: z1_1_4 >= 1.0
b7: z1_2_3 >= 1.0
b8: z1_2_4 >= 1.0
b9: z1_3_4 >= 1.0
c0: h_0_1 - x_0 + x_1 = -1
c1: h_0_2 - x_0 + x_2 = -2
c2: h_0_3 - x_0 + x_3 = -3
c3: h_0_4 - x_0 + x_4 = -4
c4: h_1_2 - x_1 + x_2 = -1
c5: h_1_3 - x_1 + x_3 = -2
c6: h_1_4 - x_1 + x_4 = -3
c7: h_2_3 - x_2 + x_3 = -1
c8: h_2_4 - x_2 + x_4 = -2
c9: h_3_4 - x_3 + x_4 = -1
e0: z2_0_1 >= 1.0
e1: z2_0_2 >= 1.0
e2: z2_0_3 >= 1.0
e3: z2_0_4 >= 1.0
e4: z2_1_2 >= 1.0
e5: z2_1_3 >= 1.0
e6: z2_1_4 >= 1.0
e7: z2_2_3 >= 1.0
e8: z2_2_4 >= 1.0
e9: z2_3_4 >= 1.0
z0: - y$0 + x_0 - x_1 = 0
z1: - y$1 + x_0 - x_2 = 0
z2: - y$2 + x_0 - x_3 = 0
z3: - y$3 + x_0 - x_4 = 0
z4: - y$4 + x_1 - x_2 = 0
z5: - y$5 + x_1 - x_3 = 0
z6: - y$6 + x_1 - x_4 = 0
z7: - y$7 + x_2 - x_3 = 0
z8: - y$8 + x_2 - x_4 = 0
z9: - y$9 + x_3 - x_4 = 0
z10: - y$10 + h_0_1 = 0
z11: - y$11 + h_0_2 = 0
z12: - y$12 + h_0_3 = 0
z13: - y$13 + h_0_4 = 0
z14: - y$14 + h_1_2 = 0
z15: - y$15 + h_1_3 = 0
z16: - y$16 + h_1_4 = 0
z17: - y$17 + h_2_3 = 0
z18: - y$18 + h_2_4 = 0
z19: - y$19 + h_3_4 = 0


Bounds


x_0 <= 4
x_1 <= 4
x_2 <= 4
x_3 <= 4
x_4 <= 4
y$0 free
y$1 free
y$2 free
y$3 free
y$4 free
y$5 free
y$6 free
y$7 free
y$8 free
y$9 free
y$10 free
y$11 free
y$12 free
y$13 free
y$14 free
y$15 free
y$16 free
y$17 free
y$18 free
y$19 free



General

 x_0 x_1 x_2 x_3 x_4



General Constraints


gc0: z1_0_1 = ABS ( y$0 )
gc1: z1_0_2 = ABS ( y$1 )
gc2: z1_0_3 = ABS ( y$2 )
gc3: z1_0_4 = ABS ( y$3 )
gc4: z1_1_2 = ABS ( y$4 )
gc5: z1_1_3 = ABS ( y$5 )
gc6: z1_1_4 = ABS ( y$6 )
gc7: z1_2_3 = ABS ( y$7 )
gc8: z1_2_4 = ABS ( y$8 )
gc9: z1_3_4 = ABS ( y$9 )
gc10: z2_0_1 = ABS ( y$10 )
gc11: z2_0_2 = ABS ( y$11 )
gc12: z2_0_3 = ABS ( y$12 )
gc13: z2_0_4 = ABS ( y$13 )
gc14: z2_1_2 = ABS ( y$14 )
gc15: z2_1_3 = ABS ( y$15 )
gc16: z2_1_4 = ABS ( y$16 )
gc17: z2_2_3 = ABS ( y$17 )
gc18: z2_2_4 = ABS ( y$18 )
gc19: z2_3_4 = ABS ( y$19 )

End
