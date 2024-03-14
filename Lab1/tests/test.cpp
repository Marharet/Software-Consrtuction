#include <iostream>
#include <cassert>
#include "../src/parser.cpp"


int main() {
    Parser parser;

    // testForAddition
    char* input = "15-3";
    double expected_result = 12;
    double parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // testForDivisionAndMultiplication
    input = "2*5/10";
    expected_result = 1;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // testForDivision
    input = "15/3";
    expected_result = 5;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // testForMultiplication
    input = "5*0";
    expected_result = 0;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // testForSeveralActions
    input = "18/2*3+1";
    expected_result = 28;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // testForExponentiation
    input = "5^0";
    expected_result = 1;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // testForCheckingPriority
    input = "(12+3)*2-10";
    expected_result = 20;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // testForProcessingOrAssignment
    input = "x=10";
    expected_result = 10;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    std::cout << "All tests passed successfully!" << std::endl;

    return 0;
}
