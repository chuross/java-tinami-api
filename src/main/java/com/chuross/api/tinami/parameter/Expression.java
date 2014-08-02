package com.chuross.api.tinami.parameter;

public enum Expression {

    AND("and"), OR("or");

    private String name;
    private Expression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
