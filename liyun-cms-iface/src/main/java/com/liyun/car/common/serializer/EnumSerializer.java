package com.liyun.car.common.serializer;

import java.io.IOException;
import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.liyun.car.common.service.Enum;
import com.liyun.car.common.utils.BeanInvokeUtils;
@Component
public class EnumSerializer extends JsonSerializer<Enum> {  
    @Override  
    public void serialize(Enum ienum, JsonGenerator jgen, SerializerProvider provider)   
      throws IOException, JsonProcessingException {
        /*jgen.writeStartObject();  
        jgen.writeNumberField("id", value.id);  
        jgen.writeStringField("itemName", value.itemName);  
        jgen.writeNumberField("owner", value.owner.id);  
        jgen.writeEndObject();  */
    	jgen.writeStartObject();
    	Field[] fields = ienum.getClass().getDeclaredFields();
		for(Field field : fields){
			if(field.getType().getSimpleName().equals("String")){
				try {
					jgen.writeStringField(field.getName(), String.valueOf(BeanInvokeUtils.invokeMethod(ienum, field.getName())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		jgen.writeEndObject();
    }  
}  