package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.Alerta;

import java.util.List;

public interface AlertaService {

    List<Alerta> listar();

    Alerta obtenerPorId(Long id);

    Alerta registrar(Alerta alerta);

    Alerta actualizar(Long id, Alerta alerta);

    void eliminar(Long id);

    List<Alerta> listarPorParcela(Long parcelaId);

    List<Alerta> listarPorEstado(String estado);

    List<Alerta> listarPorParcelaYEstado(Long parcelaId, String estado);

    List<Alerta> listarPorNivel(String nivel);

    List<Alerta> listarPorParcelaOrdenFechaDesc(Long parcelaId);

    List<Alerta> listarPorNivelYEstado(String nivel, String estado);

    List<Alerta> listarNotificacionesPendientes();

    Long contarNotificacionesPendientes();

    List<Alerta> listarAlertasImportantes();

    Alerta marcarComoVista(Long id);
}
