#include "parser.h"
//#include "../src/parser.cpp"

int main(int argc, char** argv) {
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);
    
    char expstr[80];
    Parser obj;
    cout << "Enter a period to exit a program.\n";

    for (;;) {
        cout << "Enter an expression: ";
        cin.getline(expstr,79);
        if (*expstr=='.')
            break;
        cout << "Answer: " << obj.evaluateExpression(expstr) << "\n\n";
    }
    return (EXIT_SUCCESS);
}