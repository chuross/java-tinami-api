package com.chuross.api.tinami.parameter;

public enum Sort {

    NEW("new"), SCORE("score"), SUPPORT("value"), VIEW("view"), RANDOM("rand");

    private String name;
    private Sort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
