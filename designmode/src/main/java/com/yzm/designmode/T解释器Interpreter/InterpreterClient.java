package com.yzm.designmode.T解释器Interpreter;

import java.util.Stack;

/**
 * 解释器
 */
public class InterpreterClient {
    public static void main(String[] args) {
        String statement = "3 * 2 * 4 / 6 % 5";

        Calculator calculator = new Calculator();
        calculator.build(statement);

        System.out.println("result = " + calculator.compute());
    }

}

/**
 * 抽象表达式
 */
interface Node {
    int interpret();
}

/**
 * 非终结表达式：主要用解释该表达式的值
 */
class ValueNode implements Node {
    private int value;

    public ValueNode(int value) {
        this.value = value;
    }

    public int interpret() {
        return this.value;
    }
}

/**
 * 终结表达式抽象类，由于该终结表达式需要解释多个运算符号，同时用来构建抽象语法树
 */
abstract class SymbolNode implements Node {
    protected Node left;
    protected Node right;
    Stack stack = new Stack();

    public SymbolNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
}


class MulNode extends SymbolNode {
    public MulNode(Node left, Node right) {
        super(left, right);
    }

    public int interpret() {
        return left.interpret() * right.interpret();
    }
}

class ModNode extends SymbolNode {
    public ModNode(Node left, Node right) {
        super(left, right);
    }

    public int interpret() {
        return super.left.interpret() % super.right.interpret();
    }
}

class DivNode extends SymbolNode {
    public DivNode(Node left, Node right) {
        super(left, right);
    }

    public int interpret() {
        return super.left.interpret() / super.right.interpret();
    }
}

class Calculator {
    private String statement;
    private Node node;

    public void build(String statement) {
        Node left = null, right = null;
        Stack<Node> stack = new Stack<>();

        String[] statementArr = statement.split(" ");

        for (int i = 0; i < statementArr.length; i++) {
            if (statementArr[i].equalsIgnoreCase("*")) {
                left = stack.pop();
                int val = Integer.parseInt(statementArr[++i]);
                right = new ValueNode(val);
                stack.push(new MulNode(left, right));
            } else if (statementArr[i].equalsIgnoreCase("/")) {
                left = stack.pop();
                int val = Integer.parseInt(statementArr[++i]);
                right = new ValueNode(val);
                stack.push(new DivNode(left, right));
            } else if (statementArr[i].equalsIgnoreCase("%")) {
                left = stack.pop();
                int val = Integer.parseInt(statementArr[++i]);
                right = new ValueNode(val);
                stack.push(new ModNode(left, right));
            } else {
                stack.push(new ValueNode(Integer.parseInt(statementArr[i])));
            }
        }
        this.node = stack.pop();
    }

    public int compute() {
        return node.interpret();
    }
}