PRINT "This program demonstrates nested GOSUBs."

I=5

GOSUB 100



END



100 FOR T = 1 TO I

  X = X + I

  GOSUB 150

NEXT

RETURN



150 PRINT X

    RETURN