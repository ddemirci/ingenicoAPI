package com.ingenico.service;

import jakarta.ws.rs.core.Response;

public abstract class BaseService {
    protected Response NotFound(String message){
        return Response.status(404,message).build();
    }

    protected  Response Ok(){
        return Response.ok().build();
    }

    protected <E> Response OkWithEntity(E entity){
        return Response.ok().entity(entity).build();
    }
}
