package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.repository.AgendaRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.VehicleRepository;

public class AgendaController {

    private AgendaRepository agendaRepository;

    public AgendaController() {
        this.agendaRepository = getAgendaRepository();
    }


    private AgendaRepository getAgendaRepository() {
        return Repositories.getInstance().getAgendaRepository();
    }
}
