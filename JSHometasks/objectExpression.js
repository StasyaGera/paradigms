"use strict";

function Const(a) {
    this.value = +a;
}

var ZERO = new Const(0);
var ONE = new Const(1);

Const.prototype.evaluate = function () { return this.value; };
Const.prototype.toString = function () { return this.value.toString(); };
Const.prototype.prefix = function () { return this.toString(); };
Const.prototype.postfix = function () { return this.toString(); };
Const.prototype.diff = function () { return ZERO; };
Const.prototype.simplify = function () { return this; };


function Variable(name) {
    this.name = name;
}
Variable.prototype.evaluate = function () { return arguments[variables[this.name]]; };
Variable.prototype.toString = function () { return this.name; };
Variable.prototype.prefix = function () { return this.toString; };
Variable.prototype.postfix = function () { return this.toString; };
Variable.prototype.diff = function(variable) { return variable === this.name ? ONE : ZERO; };
Variable.prototype.simplify = function () { return this; };


function Operation() {
    this.operands = arguments;
}
Operation.prototype.representation = undefined;
Operation.prototype.calc = function () { return this; };
Operation.prototype.rule = function () { return this; };
Operation.prototype.cut  = function () { return this; };

Operation.prototype.evaluate = function () {
    var applied = [], args = arguments;
    Array.prototype.forEach.call(this.operands, function (operand) {
        applied.push(operand.evaluate.apply(operand, args));
    });
    return this.calc.apply(null, applied);
};
Operation.prototype.toString = function () {
    var result = "";
    Array.prototype.forEach.call(this.operands, function (operand) {
        result += operand.toString() + ' ';
    });
    return result + this.representation;
};
Operation.prototype.prefix = function () {
    var result = '(' + this.representation;
    Array.prototype.forEach.call(this.operands, function (operand) {
        result += ' ' + operand.prefix();
    });
    return result + ')';
};
Operation.prototype.postfix = function () {
    var result = '(';
    Array.prototype.forEach.call(this.operands, function (operand) {
        result += operand.postfix() + ' ';
    });
    return result + this.representation + ')';
};
Operation.prototype.diff = function () {
    var applied = [], args = arguments;
    Array.prototype.forEach.call(this.operands, function (operand) {
        applied.push(operand.diff.apply(operand, args));
    });
    print(); // wat
    return this.rule(this.operands, applied);
};
Operation.prototype.simplify = function () {
    var constants = true;
    var values = [];

    for (var i = 0; i < this.operands.length; i++) {
        this.operands[i] = this.operands[i].simplify();
        if (!(this.operands[i] instanceof Const)) {
            constants = false;
        }
        values.push(this.operands[i].value);
    }

    if (constants) {
        return new Const(this.calc.apply(null, values));
    }

    return this.cut.apply(this, values);
};

function create(representation, calc, rule, cut) {
    var result = function() {
        Operation.apply(this, arguments);
    };
    result.prototype = Object.create(Operation.prototype);

    if (representation) {
        result.prototype.representation = representation;
    }
    if (calc) {
        result.prototype.calc = calc;
    }
    if (rule) {
        result.prototype.rule = rule;
    }
    if (cut) {
        result.prototype.cut = cut;
    }

    return result;
}

var Negate = create(
    "negate",
    function (a) { return -a; },
    function (operands, diffed) { return new Negate(diffed[0]); },
    function (operand) {
        if (operand instanceof Negate) {
            return operand.operands[0];
        }
        return this;
    }
);

var Sin = create(
    "sin",
    Math.sin,
    function (operands, diffed) { return new Multiply(new Cos(operands[0]), diffed[0]); }
);

var Cos = create(
    "cos",
    Math.cos,
    function (operands, diffed) { return new Multiply(new Negate(new Sin(operands[0])), diffed[0]); }
);

var ArcTan = create(
    "atan",
    Math.atan,
    function (operands, diffed) { return new Multiply(new Divide(ONE, (new Add (ONE, new Multiply(operands[0], operands[0])))), diffed[0]); }
);

var Exp = create(
    "exp",
    Math.exp,
    function (operands, diffed) { return new Multiply(this, diffed[0]); }
);

var Add = create(
    "+",
    function (a, b) { return a + b; },
    function (operands, diffed) { return new Add(diffed[0], diffed[1]); },
    function (first, second) {
        var choose = function(one, another) {
            if (one === 0) {
                return another;
            }
            return this;
        }

        return (first != undefined) ? choose(first, second) : choose(second, first);
    }
);

var Subtract = create(
    "-",
    function (a, b) { return a - b; },
    function (operands, diffed) { return new Subtract(diffed[0], diffed[1]); },
    function (first, second) {
        if (first === 0) {
            return new Negate(second);
        }
        if (second === 0) {
            return first;
        }
        return this;
    }
);
var Multiply = create(
    "*",
    function (a, b) { return a * b; },
    function (operands, diffed) { return new Add(new Multiply (diffed[0], operands[1]), new Multiply(operands[0], diffed[1])); },
    function (first, second) {
        var choose = function (one, other) {
            switch (one) {
                case 1:
                    return other;
                case 0:
                    return ZERO;
                default:
                    return this;
            }
        }
        return (first != undefined) ? choose(first, second) : choose(second, first);
    }
);

var Divide = create(
    "/",
    function (a, b) { return a / b; },
    function (operands, diffed) {
        return new Divide(new Subtract(new Multiply(diffed[0], operands[1]), new Multiply(operands[0], diffed[1])), new Multiply(operands[1], operands[1]));
    },
    function (first, second) {
        if (first === 0) {
            return ZERO;
        }
        if (second === 1) {
            return first;
        }
        return this;
    }
);

function InputError(message, position) {
    this.name = "InputError";
    this.message = message;
    if (position != undefined) {
        this.message += " at position " + position;
    }
}
InputError.prototype = Error.prototype;

var variables = {"x": 0, "y" : 1, "z" : 2};

var unary = {
    negate : Negate,
    sin    : Sin,
    cos    : Cos,
    atan   : ArcTan,
    exp    : Exp
};

var binary = {
    '+'  : Add,
    '-'  : Subtract,
    '*'  : Multiply,
    '/'  : Divide
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
        if (binary[token]) {
            var second = stack.pop(), first = stack.pop();
            stack.push(new binary[token](first, second));
        } else if (unary[token]) {
            stack.push(new unary[token](stack.pop()));
        } else if (variables[token] != undefined) {
            stack.push(new Variable(token));
        } else if (!isNaN(token)) {
            stack.push(new Const(token));
        }
    });

    return stack.pop();
}

function Parser(expression) {
    this.input = expression;
    this.tokens = [];
    this.parenCnt = 0;

    var i = this.skipWS(0);
    while (i < this.input.length) {
        var token = this.readNext(i);
        if (token) {
            this.tokens.push(token);
            i += token.length;
        } else {
            i++;
        }
        i = this.skipWS(i);
    }

    if (this.parenCnt) {
        throw new InputError("Missing closing parenthesis");
    }

    if (!this.tokens.length) {
        throw new InputError("Empty input");
    }
}
Parser.prototype.isSymbol = function (symbol) {
    return (/\W/.test(symbol) && !Parser.prototype.isParen(symbol) && !Parser.prototype.isWhiteSpace(symbol));
}
Parser.prototype.isDigit = function (symbol) {
    return (/\d/.test(symbol));
}
Parser.prototype.isLetter = function (symbol) {
    return (/\w/.test(symbol));
}
Parser.prototype.isWhiteSpace = function (symbol) {
    return (/\s/.test(symbol));
}
Parser.prototype.isParen = function (symbol) {
    return (symbol == '(' || symbol == ')');
}
Parser.prototype.isOperation = function (word) {
    return (binary[word] != undefined || unary[word] != undefined);
}
Parser.prototype.isVariable = function (word) {
    return (variables[word] != undefined);
}
Parser.prototype.skipWS = function (index) {
    while (Parser.prototype.isWhiteSpace(this.input.charAt(index))) {
        index++;
    }
    return index;
}
Parser.prototype.readSimilar = function (criteria, i) {
    var word = "";

    if (criteria == Parser.prototype.isDigit && this.input.charAt(i) == '-' && criteria(this.input.charAt(i + 1))) {
        word += this.input.charAt(i++);
    }
    while (criteria(this.input.charAt(i))) {
        word += this.input.charAt(i++);
    }
    if (word != "") {
        return word;
    }
}
Parser.prototype.readNext = function (index) {
    var next;

    if (this.isParen(this.input.charAt(index))) {
        next = this.input.charAt(index);
        if (next == '(') {
            this.parenCnt++;
        } else {
            if (!this.parenCnt) {
                throw new InputError("Missing closing parenthesis");
            }
            this.parenCnt--;
        }
        return next;
    }

    next = this.readSimilar(this.isDigit, index);
    if (next) { return next; }

    next = this.readSimilar(this.isLetter, index);
    if (next) {
        if (this.isVariable(next) || this.isOperation(next)) {
            return next;
        }

        if (next.length == 1) {
            throw new InputError("Unexpected symbol", index);
        }
        throw new InputError("Unknown command", index);
    }

    next = this.readSimilar(this.isSymbol, index);
    if (this.isOperation(next)) {
        return next;
    } else if (next != undefined) {
        throw new InputError("Unexpected symbol", index);
    }
}
Parser.prototype.parse = function (mode) {
    var token = this.tokens.shift();
    if (token == '(') {
        var operator;
        if (mode == "prefix") {
            operator = this.tokens.shift();
        }

        var operands = [], operand = this.parse(mode);
        var cond;
        switch (mode) {
            case "prefix":
                cond = function (o) { return o != ')'; }
                break;
            case "postfix":
                cond = function (o) { return !Parser.prototype.isOperation(o) && o != ')'; }
                break;
        }

        while (cond(operand)) {
            operands.push(operand);
            operand = this.parse(mode);
        }
        if (mode == "postfix") {
            if (operand == ')') {
                throw new InputError("Missing operator");
            }
            operator = operand;
            if (this.tokens.shift() != ')') {
                throw new InputError("Incorrect input");
            }
        }

        if (binary[operator]) {
            if (operands.length != 2) {
                throw new InputError("Invalid binary (" + operands.length + " arguments)");
            }
            return new binary[operator](operands[0], operands[1]);
        }

        if (unary[operator]) {
            if (operands.length != 1) {
                throw new InputError("Invalid unary (" + operands.length + " arguments)");
            }
            return new unary[operator](operands[0]);
        }

        throw new InputError("Expected operator, found " + operator);
    }

    if (variables[token] != undefined) {
        return new Variable(token);
    }

    if (!isNaN(token)) {
        return new Const(token);
    }

    if (unary[token] || binary[token]) {
        if (mode == "prefix") {
            throw new InputError("Invalid input");
        }
        if (mode == "postfix") {
            return token;
        }
    }

    if (token == ')') {
        return token;
    }
};

function parseMode(expression, mode) {
    var parser = new Parser(expression), result;
    result = parser.parse(mode);

    if (parser.tokens.length) {
        throw new InputError("Invalid input");
    }
    return result;
}
function parsePrefix(expression) {
    return parseMode(expression, "prefix");
}
function parsePostfix(expression) {
    return parseMode(expression, "postfix");
}

//println(parsePrefix('(+ x 2)'));