package com.tikal.subebus.util;

import java.lang.reflect.Type;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;


public class JsonConvertidor {

	public static Object fromJson(String json, Type tipo) {
		Gson g = new Gson();
		return g.fromJson(json, tipo);
	}

	public static Object fromJson(String json, Class<?> clase) {
		Gson g;
		g = new Gson();
		return g.fromJson(json, clase);
	}

	public static String toJson(Object o) {
		Gson g = new Gson();
		return g.toJson(o);
	}

	public static String estadoFromJson(String json) {
		int indexIni = json.indexOf("estado") + 9;
		int indexFin = json.indexOf("localidad") - 3;
		String estado = json.substring(indexIni, indexFin);
		return estado;

	}

	static Gson getGsonWithSpecificTypeAdapter(Class<?> clase, TypeAdapter<?> adapter) {
		GsonBuilder gBuilder = new GsonBuilder();
		gBuilder.registerTypeAdapter(clase, adapter.nullSafe());
		return gBuilder.create();
	}

}
