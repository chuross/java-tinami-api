package com.chuross.api.tinami;

public enum ViewLevel {
    PUBLIC("public"), USER("user"), SUPPORTER("supporter");

    private String name;
    private ViewLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ViewLevel resolve(String name) {
        for(ViewLevel viewLevel : values()) {
            if(viewLevel.getName().equals(name)) {
                return viewLevel;
            }
        }
        return null;
    }
}
