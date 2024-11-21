"use strict";

// :NOTE: Обобщить с унарной
function BinaryOperation(a, b, operation, symbol, prefixSymbol) {
    return {
        evaluate: (x, y, z) => operation(a.evaluate(x, y, z), b.evaluate(x, y, z)),
        toString: () => a.toString() + " " + b.toString() + " " + symbol,
        prefix: () => prefixSymbol + a.prefix() + " " + b.prefix() + ")"
    };
}

function UnaryOperation(operand, operation, prefixSymbol) {
    return {
        evaluate: (x, y, z) => operation(operand.evaluate(x, y, z)),
        toString: () => "" + operand.toString() + " " + prefixSymbol + "",
        prefix: () => "(" + prefixSymbol + " " + operand.prefix() + ")"
    };
}

function Const(value) {
    return {
        evaluate: () => value,
        toString: () => value.toString(),
        prefix: () => value.toString()
    };
}

function Variable(name) {
    return {
        evaluate: (x, y, z) => {
            // :NOTE: вынести выше + [] + indexOf
            const variableValues = {'x': x, 'y': y, 'z': z};
            return variableValues[name];
        },
        toString: () => name,
        prefix: () => name
    };
}

// :NOTE: const Negate = makeOp(func, sign)
function Negate(operand) {
    return UnaryOperation(operand, x => -x, "negate");
}

function Add(a, b) {
    return BinaryOperation(a, b, (x, y) => x + y, "+", "(+ ");
}

function Subtract(a, b) {
    return BinaryOperation(a, b, (x, y) => x - y, "-", "(- ");
}

function Multiply(a, b) {
    return BinaryOperation(a, b, (x, y) => x * y, "*", "(* ");
}

function Divide(a, b) {
    return BinaryOperation(a, b, (x, y) => x / y, "/", "(/ ");
}

function Sin(operand) {
    return UnaryOperation(operand, Math.sin, "sin");
}

function Cos(operand) {
    return UnaryOperation(operand, Math.cos, "cos");
}

// :NOTE: prefix & toString should be same for all ops
function Mean(...operands) {
    return {
        evaluate: (x, y, z) => operands.reduce((acc, curr) => acc + curr.evaluate(x, y, z), 0) / operands.length,
        toString: () => `mean(${operands.map(op => op.toString()).join(" ")})`,
        prefix: () => `(mean ${operands.map(op => op.prefix()).join(" ")})`
    };
}

function Var(...operands) {
    return {
        evaluate: (x, y, z) => {
            const mean = operands.reduce((acc, curr) => acc + curr.evaluate(x, y, z), 0) / operands.length;
            return operands.reduce((acc, curr) => acc + Math.pow(curr.evaluate(x, y, z) - mean, 2), 0) / operands.length;
        },
        toString: () => `var(${operands.map(op => op.toString()).join(" ")})`,
        prefix: () => `(var ${operands.map(op => op.prefix()).join(" ")})`
    };
}

function parsePrefix(expression) {
    // :NOTE: из-за этого вывода был TL
    // :NOTE: но тесты все равно упали на "(negate)"
        // console.log(expression);
        let formatted = expression.replace(/([\(\)])/g, " $1 ");
            formatted = formatted.replace(/(\d)([^\d\s-])/g, "$1 $2");
            formatted = formatted.replace(/\s{2,}/g, " ").trim();
        let index = 0;
        let tokens = formatted.match(/\S+/g);

    function parseToken() {
        if (index >= tokens.length) throw new Error("Unexpected end of expression");
        let token = tokens[index++];
        if (token === "(") {
            let operation = tokens[index++];
            if (!(operation in operations)) {
                throw new Error("Unknown operation '" + operation + "'");
            }
            let args = [];
            while (tokens[index] !== ")") {
                args.push(parseToken());
            }
            index++;
            if (operations[operation].length !== args.length && !operation in operations_sec) {
                throw new Error("Incorrect number of arguments for " + operation);
            }
            return operations[operation](...args);
        } else if (token === ")") {
            throw new Error("Unexpected token ')'");
        } else if (!isNaN(parseFloat(token))) {
            return Const(parseFloat(token));
        } else if (["x", "y", "z"].includes(token)) {
            return Variable(token);
        } else {
            throw new Error("Unknown token '" + token + "'");
        }
    }
    let result = parseToken();
    if (index !== tokens.length) {
        throw new Error("Extra tokens after valid expression");
    }
    return result;
}

// :NOTE: build in runtime
let operations = {
    '+': Add,
    '-': Subtract,
    '*': Multiply,
    '/': Divide,
    'negate': Negate,
    'sin': Sin,
    'cos': Cos,
    'mean': Mean,
    'var': Var
};

let operations_sec = {
    'mean': Mean,
    'var': Var
};

/*try {
    let expr = parsePrefix("( + x 2A)");
    console.log(expr.evaluate(2, 0, 0));
    console.log(expr.toString());
    console.log(expr.prefix());
} catch (e) {
    console.error(e);
}
*/
function parse(expression) {
    let stack = [];
    expression.trim().split(/\s+/).forEach(token => {
        if (token in operations) {
            if (token === 'negate' || token === 'sin' || token === 'cos') {
                let operand = stack.pop();
                stack.push(operations[token](operand));
            } else {
                let right = stack.pop(), left = stack.pop();
                stack.push(operations[token](left, right));
            }
        } else {
            if ( token in variables ) {
                stack.push(Variable(token));
            }
            else {
                if (token !== '') {
                    stack.push(Const(parseFloat(token)));
                }
            }
        }
    });
    return stack.pop();
}

/*try {
    let expr = parsePrefix("( + x 2A)");
    console.log(expr.evaluate(2, 0, 0));
    console.log(expr.toString());
    console.log(expr.prefix());
} catch (e) {
    console.error(e);
}
*/

// :NOTE: проверка от 05.04.24: Exception in thread "main" java.lang.AssertionError: Parsing error expected for (negate)
