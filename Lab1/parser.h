#include <iostream>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <windows.h>
using namespace std;


class Parser {
public:

    Parser();
    double evaluateExpression(char *exp);
private:
    char *expressionPointer; // вказівник на вираз
    char token[80]; // поточний елемент
    char tokenType; // тип елемента

    void evaluateSumAndDifference(double &result);
    void evaluateMultiplicationAndDivision(double &result);
    void evaluateExponentiation(double &result);
    void evaluateUnaryPlusAndMinus(double &result);
    void evaluateParenthesizedExpression(double &result);
    void evaluateNumericToken(double &result);
    void get_token();
    void syntaxError(int error);
    int isDelimiter(char c);
};
