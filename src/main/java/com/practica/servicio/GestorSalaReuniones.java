
package com.practica.servicio;

import java.util.ArrayList;
import java.util.List;

import com.practica.modelo.SalaReuniones;
import com.practica.persistencia.GestorBBDD;

/**
 * Clase que gestiona las operaciones relacionadas con las salas de reuniones.
 */
public class GestorSalaReuniones {

    /**
     * Registra una nueva sala de reuniones en el sistema.
     *
     * @param sala La sala de reuniones a registrar.
     */
    public void altaSala(SalaReuniones sala) {
        try {
            GestorBBDD.agregarSala(sala);
            System.out.println("Sala de reuniones registrada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al registrar la sala: " + e.getMessage());
        }
    }

    /**
     * Elimina una sala de reuniones del sistema.
     *
     * @param idSala El identificador único de la sala a eliminar.
     */
    public void bajaSala(int idSala) {
        try {
            GestorBBDD.eliminarSala(idSala);
            System.out.println("Sala de reuniones eliminada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar la sala: " + e.getMessage());
        }
    }

    /**
     * Modifica los datos de una sala de reuniones existente.
     *
     * @param sala La sala de reuniones con los datos actualizados.
     */
    public void modificarSala(SalaReuniones sala) {
        try {
            GestorBBDD.modificarSala(sala);
            System.out.println("Sala de reuniones modificada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al modificar la sala: " + e.getMessage());
        }
    }

    /**
     * Obtiene una lista de todas las salas de reuniones registradas.
     *
     * @return Una lista de objetos `SalaReuniones`. Si ocurre un error, se devuelve una lista vacía.
     */
    public List<SalaReuniones> listarSalas() {
        try {
            return GestorBBDD.listarSalas();
        } catch (Exception e) {
            System.err.println("Error al listar las salas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

    

