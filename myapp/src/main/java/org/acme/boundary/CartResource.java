package org.acme.boundary;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.boundary.dto.CartDto;
import org.acme.control.CartService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/carts")
@Tag(name = "cart", description = "All the cart methods")
public class CartResource {
    @Inject
    CartService cartService;

    @GET
    public List<CartDto> findAll() {
        return this.cartService.findAll();
    }

    @GET
    @Path("/active")
    public List<CartDto> findAllActiveCarts() {
        return this.cartService.findAllActiveCarts();
    }

    @GET
    @Path("/customer/{id}")
    public CartDto getActiveCartForCustomer(@PathParam("id") Long customerId) {
        return this.cartService.getActiveCart(customerId);
    }

    @GET
    @Path("/{id}")
    public CartDto findById(@PathParam("id") Long id) {
        return this.cartService.findById(id);
    }

    @POST
    @Path("/customer/{id}")
    public CartDto create(@PathParam("id") Long customerId) {
        return this.cartService.createDto(customerId);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.cartService.delete(id);
    }

}