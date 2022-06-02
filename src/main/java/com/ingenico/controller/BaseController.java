package com.ingenico.controller;

import jakarta.ws.rs.core.Response;

public abstract class BaseController
{
    protected Response NotFound(String message){ return Response.status(404).entity(message).build(); }
    protected  Response Ok(){ return Response.ok().build(); }
    protected <E> Response OkWithEntity(E entity){
        return Response.ok().entity(entity).build();
    }
    protected <E> Response Created(E entity) { return Response.status(201).entity(entity).build();}
    protected Response Forbidden() {return Response.status(403).entity("User is not authorized to perform this action").build();}
}
