/**
 * Created by penguinni on 17.04.16.
 */

"use strict";

function cnst(a) {
    return function() {
        return +a;
    }
}

function variable(name) {
    return function () {
        return arguments[variables[name]];
    }
}

function operation(calc) {
    return function() {
        var funcs = arguments;
        return function() {
            var operands = [];
            for (var i = 0; i < funcs.length; i++) {
                operands.push(funcs[i].apply(null, arguments));
            }
            return calc.apply(null, operands);
        }
    }
}

var variables = {x : 0, y : 1, z : 2, f : 4};

var negate   = operation(function (a) { return -a });
var abs      = operation(Math.abs);
var log      = operation(Math.log);
var add      = operation(function (a, b) { return a + b });
var subtract = operation(function (a, b) { return a - b });
var multiply = operation(function (a, b) { return a * b });
var divide   = operation(function (a, b) { return a / b });
var mod      = operation(function (a, b) { return a % b });
var power    = operation(Math.pow);
var med      = operation(function () {
    Array.prototype.sort.call(arguments, function (a, b) { return a - b; });
    return arguments[(arguments.length - arguments.length % 2) / 2];
});

var operations = {
    negate : {operands : 1, func : negate},
    abs    : {operands : 1, func : abs},
    log    : {operands : 1, func : log},
    '+'    : {operands : 2, func : add},
    '-'    : {operands : 2, func : subtract},
    '*'    : {operands : 2, func : multiply},
    '/'    : {operands : 2, func : divide},
    '%'    : {operands : 2, func : mod},
    '**'   : {operands : 2, func : power},
    med    : {operands : 3, func : med}
};

function getOperands(number, stack) {
    var operands = [];
    for (var i = 0; i < number; i++) {
        operands[number - i - 1] = stack.pop();
    }
    return operands;
}

function parse(expression) {
    var stack = [];
    expression.split(/\s+/).forEach(function (token) {
        if (!token) {
            return;
        }
        if (operations[token]) {
            stack.push(operations[token].func.apply(null, getOperands(operations[token].operands, stack)));
        } else if (variables[token] != undefined) {
            stack.push(variable(token));
        } else if (!isNaN(token)) {
            stack.push(cnst(token));
        }
    });

    return stack.pop();
}

//println(parse("x y z med")(5, 10, 2));
//println(5/0);