package com.capo.asignacion_mongo.adapter.out.emitEvents;

public interface EmitingEvent<T> {
	void emit(T event);
}
