package com.chuross.api.tinami.element.transformer;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.util.Date;

public class DateformatTransformer implements Transform<Date> {

    private DateFormat dateformat;

    public DateformatTransformer(DateFormat dateFormat) {
        this.dateformat = dateFormat;
    }

    @Override
    public Date read(String value) throws Exception {
        return dateformat.parse(value);
    }

    @Override
    public String write(Date value) throws Exception {
        return dateformat.format(value);
    }

}
