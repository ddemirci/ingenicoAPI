package com.ingenico.service;

import jakarta.ws.rs.core.Response;

public abstract class BaseService {
    protected Response NotFound(String message){
        return ResponseWithStatusAndMessage(404,message);
    }

    protected Response ResponseWithStatusAndMessage(int status, String message){
        return Response.status(status).entity(message).build();
    }

    protected  Response Ok(){ return Response.ok().build(); }

    protected <E> Response OkWithEntity(E entity){
        return Response.ok().entity(entity).build();
    }

    protected <E> Response Created(E entity) { return Response.status(201).entity(entity).build();}
}
