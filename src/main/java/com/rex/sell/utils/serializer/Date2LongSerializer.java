package com.rex.sell.utils.serializer;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @ClassName Date2LongSerializer
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/19 8:22
 * @Version 1.0
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(date.getTime()/1000);
    }
}
