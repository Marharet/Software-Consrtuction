#include <iostream>
#include <cassert>
#include "../src/parser.h"
#include "../src/parser.cpp"

int main() {
    Parser parser;

    // Test 1
    char* input = "15-3";
    double expected_result = 12;
    double parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // Test 2
    input = "2*5/10";
    expected_result = 1;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // Test 3
    input = "15/3";
    expected_result = 5;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // Test 4
    input = "5*0";
    expected_result = 0;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // Test 5
    input = "18/2*3+1";
    expected_result = 28;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // Test 6
    input = "5^0";
    expected_result = 1;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // Test 7
    input = "(12+3)*2-10";
    expected_result = 20;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);

    // Test 8
    input = "x=10";
    expected_result = 10;
    parsed_value = parser.evaluateExpression(input);
    assert(parsed_value == expected_result);


    std::cout << "All tests passed successfully!" << std::endl;

    return 0;
}
