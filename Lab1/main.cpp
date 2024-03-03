#include "parser.h"

int main(int argc, char** argv) {
    char expstr[80];
    Parser obj;
    cout << "Для виходу введість крапку.\n";

    for (;;) {
        cout << "Введіть вираз: ";
        cin.getline(expstr,79);
        if (*expstr=='.')
            break;
        cout << "Відповідь: "<<obj.eval_exp(expstr) << "\n\n";
    }
    return (EXIT_SUCCESS);
}