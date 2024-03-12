#include "parser.h"

// constructor
Parser::Parser() {
    expressionPointer = NULL;
    *token = '\0';
    tokenType = 0;

    for (int i = 0; i < NUMVARS; i++)
    {
        vars[i] = 0.0;
    }
}

void Parser::get_token() {
    char *temp = token;
    tokenType = 0;
    *temp = '\0';
    if (!*expressionPointer) // Checking an empty row
        return;
    while (isspace(*expressionPointer)) // Skips spaces
        ++expressionPointer;

    if (strchr("+-*/%^=()", *expressionPointer)) // Checking is it a mathematical operator or a bracket?
    {
        tokenType = DELIMITER;
        *temp++ = *expressionPointer++;
    }
    else if (isalpha(*expressionPointer)) // Checking is it a variable?
    {
        while (!isDelimiter(*expressionPointer))
            *temp++ = *expressionPointer++;
        tokenType = VARIABLE;
    }
    else if (isdigit (*expressionPointer)) // Checking is it a number?
    {
        while (!isDelimiter(*expressionPointer))
            *temp++ = *expressionPointer++;
        tokenType = NUMBER;
    }
    *temp = '\0';
}


// Entranse point
double Parser::evaluateExpression(char* exp) {
    double result;
    expressionPointer = exp;
    get_token();
    if (!token)
    {
        syntaxError(2); // No expression
        return 0.0;
    }
    if (tokenType == VARIABLE)
        numberProcessingOrAssignment(result);
    else
        evaluateSumAndDifference(result);

    if (*token)
        syntaxError(0);
    return result;
}

//Adding and subtracting two elements
void Parser::evaluateSumAndDifference(double& result) {
    char currentOperator;
    double currentValue;
    evaluateMultiplicationAndDivision(result);
    while ((currentOperator=*token) == '+' || currentOperator == '-') {
        get_token ();
        evaluateMultiplicationAndDivision(currentValue);
        switch (currentOperator)
        {
            case '-':
                result -= currentValue;
                break;
            case '+':
                result += currentValue;
                break;
        }
    }
}

// Multiplication and division of two elements
void Parser::evaluateMultiplicationAndDivision(double& result) {
    char currentOperator;
    double currentValue;
    evaluateExponentiation(result);
    while ((currentOperator=*token) == '*' || currentOperator=='/' || currentOperator == '%') {
        get_token();
        evaluateExponentiation(currentValue);
        switch (currentOperator)
        {
            case '*':
                result = result * currentValue;
                break;
            case '/':
                result = result / currentValue;
                break;
            case '%':
                result = (int) result % (int) currentValue;
                break;
        }
    }
}

//Raising to a power
void Parser::evaluateExponentiation(double& result) {
    double base, exponent;
    evaluateUnaryPlusAndMinus(result);
    if (*token=='^')
    {
        get_token();
        evaluateExponentiation(base);
        exponent = result;
        if (base == 0.0)
        {
            result = 1.0;
            return;
        }
        for (int t= (int)base - 1; t > 0; --t)
            result = result * (double) exponent;
    }
}

// Unary + and -
void Parser::evaluateUnaryPlusAndMinus(double& result) {
    char currentOperator;
    currentOperator = 0;
    if ((tokenType == DELIMITER) && (*token == '+' || *token == '-')) {
        currentOperator = *token;
        get_token();
    }
    evaluateParenthesizedExpression(result);
    if (currentOperator == '-')
        result = - result;
}

// Processing of expressions in brackets
void Parser::evaluateParenthesizedExpression(double& result) {
    if (*token == '(')
    {
        get_token();
        evaluateSumAndDifference(result);
        if (*token != ')')
            syntaxError(1);
        get_token();
    }
    else
        evaluateNumericToken(result);
}

// Get the value of a number
void Parser::evaluateNumericToken(double& result) {
    switch (tokenType)
    {
        case NUMBER:
            result = atof(token);
            get_token();
            return;
        case VARIABLE:
            result = findVariable(token);
            get_token();
            return;
        default:
            syntaxError(0);
    }
}

// Syntax error message
void Parser::syntaxError(int error) {
    static const char *errorMessages[] = {
            "Syntax error" ,
            "Open parenthesis",
            "No expression"
    };
    cout << errorMessages[error] << endl;
}

// Returns true if the passed parameter is a delimiter
int Parser::isDelimiter(char c) {
    if (strchr(" +-/*%^=()",c) || c==9 || c=='\r' || c==0)
        return 1;
    return 0;
}

double Parser::findVariable (char *s) {
    if (!isalpha(*s)) {
        syntaxError (1);
        return 0.0;
    }
    return vars[toupper(*token) - 'A'];
}

// Number processing or assignment
void Parser::numberProcessingOrAssignment(double& result) {
    int slot;
    char ttok_type;
    char temp_token[80];

    if (tokenType == VARIABLE)
    {
        strcpy(temp_token, token); // Keep the old item
        ttok_type = tokenType;
    }

    // Calculate the variable address
    slot = toupper(*token) - 'A';
    get_token();

    // Check if there's an assignment operator
    if (*token != '=')
    {
        putback(); // No assignment, so put back the current token
        strcpy(token, temp_token);
        tokenType = ttok_type;
    }
    else
    {
        get_token(); // Yes, move to the next part - assignment
        evaluateSumAndDifference(result);
        this->vars[slot] = result;
        return;
    }
    evaluateSumAndDifference(result);
}

// Return an element to the input stream
void Parser::putback (){
    char *t;
    t = token;
    for (; *t; t++) expressionPointer--;
}