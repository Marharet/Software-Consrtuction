#include <iostream>
#include <windows.h>
using namespace std;

const int NUMVARS = 26;

enum itemType
{
    DELIMITER = 1,
    VARIABLE,
    NUMBER
};

class Parser {
public:
    Parser();
    double evaluateExpression(char *exp);
private:
    char *expressionPointer; // вказівник на вираз
    char token[80]; // поточний елемент
    char tokenType; // тип елемента
    double vars[NUMVARS];

    void get_token();
    void putback ();
    double findVariable(char *s);
    void numberProcessingOrAssignment(double& result);

    void evaluateSumAndDifference(double &result);
    void evaluateMultiplicationAndDivision(double &result);
    void evaluateExponentiation(double &result);
    void evaluateUnaryPlusAndMinus(double &result);
    void evaluateParenthesizedExpression(double &result);
    void evaluateNumericToken(double &result);

    void syntaxError(int error);
    int isDelimiter(char c);
};

