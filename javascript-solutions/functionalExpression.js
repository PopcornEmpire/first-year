const cnst = value => () => value;

// :NOTE: Не расширяемо. [] + indexOf
let variables = {'x' : 0, 'y' : 1, 'z' : 2};
const variable = name => (...args) => args[variables[name]];

const binaryOperation = (operation) => (x, y) => (...args) => operation(x(...args), y(...args));

const add = binaryOperation((a, b) => a + b);
const subtract = binaryOperation((a, b) => a - b);
const multiply = binaryOperation((a, b) => a * b);
const divide = binaryOperation((a, b) => a / b);

const negate = x => (...args) => -x(...args);
const square = x => (...args) => x(...args) * x(...args);
const sqrt = x => (...args) => Math.sqrt(Math.abs(x(...args)));

const pi = cnst(Math.PI);
const e = cnst(Math.E);

let expra = subtract(
    multiply(
        cnst(2),
        variable("x")
    ),
    cnst(3)
);

for (let x = 0; x <= 10; x++) {
    let testExpr = add(
        subtract(
            multiply(variable("x"), variable("x")),
            multiply(cnst(2), variable("x"))
        ),
        cnst(1)
    );
    //console.log(`x=${x}, result=${testExpr(x, 0, 0)}`);
}
