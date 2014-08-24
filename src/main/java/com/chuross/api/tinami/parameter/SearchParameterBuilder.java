package com.chuross.api.tinami.parameter;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.List;

public class SearchParameterBuilder implements Cloneable, Serializable {

    private String authKey;
    private String text;
    private String tag;
    private Expression expression;
    private Sort sort;
    private List<ContentType> contetntTypes;
    private Integer page;
    private Integer perpage;
    private Long creatorId;
    private Boolean safe;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getText() {
        return text;
    }

    public SearchParameterBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public SearchParameterBuilder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Expression getExpression() {
        return expression;
    }

    public SearchParameterBuilder setExpression(Expression expression) {
        this.expression = expression;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public SearchParameterBuilder setSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public List<ContentType> getContetntTypes() {
        return contetntTypes;
    }

    public void setContetntTypes(List<ContentType> contetntTypes) {
        this.contetntTypes = contetntTypes;
    }

    public SearchParameterBuilder setContentTypes(List<ContentType> contentTypes) {
        this.contetntTypes = contentTypes;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public SearchParameterBuilder setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getPerpage() {
        return perpage;
    }

    public SearchParameterBuilder setPerpage(Integer perpage) {
        this.perpage = perpage;
        return this;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public SearchParameterBuilder setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Boolean isSafe() {
        return safe;
    }

    public SearchParameterBuilder setSafe(Boolean safe) {
        this.safe =safe;
        return this;
    }

    public SearchParameter build() {
        SearchParameterBuilder builder = clone();
        String authKey = builder.getAuthKey();
        String text = builder.getText();
        String tag = builder.getTag();
        Expression expression = builder.getExpression();
        Sort sort = builder.getSort();
        List<ContentType> contentTypes = builder.getContetntTypes();
        Integer page = builder.getPage();
        Integer perpage = builder.getPerpage();
        Long creatorId = builder.getCreatorId();
        Boolean safe = builder.isSafe();
        return new SearchParameter(authKey, text, tag, expression, sort, contentTypes, page, perpage, creatorId, safe);
    }

    @Override
    protected SearchParameterBuilder clone() {
        return SerializationUtils.clone(this);
    }

}
