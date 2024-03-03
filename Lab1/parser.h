#ifndef MARHARYTA_PRACHUK_SE211_PARSER_H
#define MARHARYTA_PRACHUK_SE211_PARSER_H

#endif //MARHARYTA_PRACHUK_SE211_PARSER_H

#include <iostream>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

enum types { DELIMITER = 1, VARIABLE, NUMBER };

using namespace std;

class Parser {
public:
    Parser();
    double eval_exp(char *exp);
private:
    char *exp_ptr; // вказівник на вираз
    char token[80]; // поточний елемент
    char tok_type; // тип елемента
    void eval_exp2(double &result);
    void eval_exp3(double &result);
    void eval_exp4(double &result);
    void eval_exp5(double &result);
    void eval_exp6(double &result);
    void atom(double &result);
    void get_token();
    void serror(int error);
    int isdelim(char c);
};
