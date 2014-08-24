package com.chuross.api.tinami.parameter;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class SearchParameter {

    private String authKey;
    private String text;
    private String tag;
    private Expression expression;
    private Sort sort;
    private List<ContentType> contetntTypes;
    private Integer page;
    private Integer perpage;
    private Long creatorId;
    private Boolean safe = false;

    SearchParameter(String authKey, String text, String tag, Expression expression, Sort sort, List<ContentType> contetntTypes, Integer page, Integer perpage, Long creatorId, Boolean safe) {
        this.authKey = authKey;
        this.text = text;
        this.tag = tag;
        this.expression = expression;
        this.sort = sort;
        this.contetntTypes = contetntTypes;
        this.page = page;
        this.perpage = perpage;
        this.creatorId = creatorId;
        this.safe = safe;
    }

    public List<NameValuePair> getParameters() {
        List<NameValuePair> parameters = Lists.newArrayList();
        setAuthKeyIfNotNull(parameters);
        setTextIfNotNull(parameters);
        setTagIfNotNull(parameters);
        setExpressionIfNotNull(parameters);
        setSortIfNotNull(parameters);
        setContetntTypesIfNotNull(parameters);
        setPageIfNotNull(parameters);
        setPerpageIfNotNull(parameters);
        setCreatorIdIfNotNull(parameters);
        setSafeIfNotNull(parameters);
        return parameters;
    }

    private void setAuthKeyIfNotNull(List<NameValuePair> parameters) {
        if(StringUtils.isBlank(authKey)) {
            return;
        }
        parameters.add(new BasicNameValuePair("auth_key", authKey));
    }

    private void setTextIfNotNull(List<NameValuePair> parameters) {
        if(StringUtils.isBlank(text)) {
            return;
        }
        parameters.add(new BasicNameValuePair("text", text));
    }

    private void setTagIfNotNull(List<NameValuePair> parameters) {
        if(StringUtils.isBlank(tag)) {
            return;
        }
        parameters.add(new BasicNameValuePair("tag", tag));
    }

    private void setExpressionIfNotNull(List<NameValuePair> parameters) {
        if(expression == null) {
            return;
        }
        parameters.add(new BasicNameValuePair("search", expression.getName()));
    }

    private void setSortIfNotNull(List<NameValuePair> parameters) {
        if(sort == null) {
            return;
        }
        parameters.add(new BasicNameValuePair("sort", sort.getName()));
    }

    private void setContetntTypesIfNotNull(List<NameValuePair> parameters) {
        if(contetntTypes == null) {
            return;
        }
        for(ContentType contentType : contetntTypes) {
            parameters.add(new BasicNameValuePair("cont_type[]", String.valueOf(contentType.getCode())));
        }
    }

    private void setPageIfNotNull(List<NameValuePair> parameters) {
        if(page == null) {
            return;
        }
        parameters.add(new BasicNameValuePair("page", String.valueOf(page > 0 ? page : 1)));
    }

    private void setPerpageIfNotNull(List<NameValuePair> parameters) {
        if(perpage == null) {
            return;
        }
        parameters.add(new BasicNameValuePair("perpage", String.valueOf(perpage > 0 ? perpage : 1)));
    }

    private void setCreatorIdIfNotNull(List<NameValuePair> parameters) {
        if(creatorId == null) {
            return;
        }
        parameters.add(new BasicNameValuePair("prof_id", String.valueOf(creatorId)));
    }

    private void setSafeIfNotNull(List<NameValuePair> parameters) {
        if(safe == null) {
            return;
        }
        parameters.add(new BasicNameValuePair("safe", safe ? "1" : "0"));
    }

}
