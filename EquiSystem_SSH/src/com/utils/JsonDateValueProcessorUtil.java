package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessorUtil implements JsonValueProcessor {
	private String format = "yyyy-MM-dd";
	public JsonDateValueProcessorUtil() {
		super();
	}
	public JsonDateValueProcessorUtil(String format) {
		super();
		this.format = format;
	}

//	ArrayValue类型
	@Override
	public Object processArrayValue(Object paramObject, JsonConfig paramJsonConfig) {
		return process(paramObject);
	}
//	bean对象类型
	@Override
	public Object processObjectValue(String paramString, Object paramObject, JsonConfig paramJsonConfig) {
		return process(paramObject);
	}

	public Object process(Object value) {
//		传参是Date，则执行转换操作
		if (value instanceof Date) {
//			设置格式
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
//			格式化日期，并返回
			return sdf.format(value);
		}
//		若不是Date数据，判断是否空值后转为string
		return value == null ? "" : value.toString();
	}
}
