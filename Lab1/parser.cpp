#include "parser.h"


void Parser::get_token() {
    register char *temp;
    tok_type = 0;
    temp = token;
    *temp = '\0';
    if (!*exp_ptr) // стрічка порожня
        return;
    while (isspace(*exp_ptr)) // пропускаємо пробіли
        ++exp_ptr;
    if (strchr("+-*/%^=()", *exp_ptr)) { // математична дія чи дужка?
        tok_type = DELIMITER;
        *temp++ = *exp_ptr++;
    } else if (isalpha(*exp_ptr)) { // змінна?
        while (!isdelim (*exp_ptr))
            *temp++ = *exp_ptr++;
        tok_type = VARIABLE;
    } else if (isdigit (*exp_ptr)) { // число?
        while (!isdelim (*exp_ptr))
            *temp++ = *exp_ptr++;
        tok_type = NUMBER;
    }
    *temp = '\0';
}

// конструктор
Parser::Parser() {
    exp_ptr = NULL;
}
// точка входу
double Parser::eval_exp(char* exp) {
    double result;
    exp_ptr = exp;
    get_token();
    if (!token) {
        serror(2); // немає виразу
        return 0.0;
    }
    eval_exp2(result);
    if (*token)
        serror (0);
    return result;
}
// Додавання і віднімання двох елементів
void Parser::eval_exp2(double& result) {
    register char op;
    double temp;
    eval_exp3(result);
    while ((op=*token) == '+' || op == '-') {
        get_token ();
        eval_exp3(temp);
        switch (op) {
            case '-':
                result = result - temp;
                break;
            case '+':
                result = result + temp;
                break;
        }
    }
}
// множення і ділення двох елементів
void Parser::eval_exp3(double& result) {
    register char op;
    double temp;
    eval_exp4(result);
    while ((op=*token) == '*' || op=='/' || op == '%') {
        get_token();
        eval_exp4(temp);
        switch (op) {
            case '*':
                result = result*temp;
                break;
            case '/':
                result = result / temp;
                break;
            case '%':
                result = (int) result % (int) temp;
                break;
        }
    }
}
// піднемення до степеня
void Parser::eval_exp4(double& result) {
    double temp, ex;
    register int i;
    eval_exp5(result);
    if (*token=='^') {
        get_token();
        eval_exp4(temp);
        ex = result;
        if (temp==0.0) {
            result = 1.0;
            return;
        }
        for (int t=(int)temp-1; t>0; --t)
            result =result * (double) ex;
    }
}
// унарний + та -
void Parser::eval_exp5(double& result) {
    register char op;
    op = 0;
    if ((tok_type == DELIMITER) && (*token == '+'|| *token == '-')) {
        op = *token;
        get_token();
    }
    eval_exp6(result);
    if (op == '-')
        result = - result;
}
// обробка виразів в душках
void Parser::eval_exp6(double& result) {
    if (*token == '(') { //!!!
        get_token();
        eval_exp2(result);
        if (*token != ')')
            serror (1);
        get_token();
    } else
        atom(result);
}
// отримати значення числа
void Parser::atom(double& result) {
    switch (tok_type) {
        case NUMBER:
            result = atof (token);
            get_token();
            return;
        default:
            serror(0);
    }
}
// повідомлення про синтаксичну помилку
void Parser::serror(int error) {
    static char *e[] = {
            "Синтаксична помилка" ,
            "Незакриті душки",
            "Немає виразу"
    };
    cout << e[error] << endl;
}
// повертає true, якщо переданий параметр - розділювач
int Parser::isdelim(char c) {
    if (strchr(" +-/*%^=()",c) || c==9 || c=='\r' || c==0)
        return 1;
    return 0;
}
