PRINT "This program demonstrates all features."

FOR X = 1 TO 100

PRINT X; X/2, X; X*X

NEXT

GOSUB 300

PRINT "hello"

H = 12

IF H<11 THEN GOTO 200

PRINT 12-4/2

PRINT 100

200 A = 100/2

IF A>10 THEN PRINT "this is ok"

PRINT A

PRINT A+34

H = 9

H = 15

Y = 10

PRINT H+Y

END

300 PRINT "this is a subroutine"

    RETURN