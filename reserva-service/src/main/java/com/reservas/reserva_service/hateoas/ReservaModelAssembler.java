package com.reservas.reserva_service.hateoas;

import com.reservas.reserva_service.controller.ReservaController;
import com.reservas.reserva_service.entity.Reserva;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReservaModelAssembler
        implements RepresentationModelAssembler<Reserva, EntityModel<Reserva>> {

    @Override
    public EntityModel<Reserva> toModel(Reserva reserva) {

        return EntityModel.of(
                reserva,

                linkTo(
                        methodOn(ReservaController.class)
                                .obtenerReserva(reserva.getId())
                ).withSelfRel(),

                linkTo(
                        methodOn(ReservaController.class)
                                .listarReservas()
                ).withRel("reservas")
        );
    }
}