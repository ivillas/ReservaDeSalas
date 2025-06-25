
INSERT INTO Reserva (dniEmpleado, idSala, fecha, horaInicio, horaFin) VALUES
('42587458G', 1, '2025-06-24', '09:00:00', '10:00:00'),  -- Reserva de Juan en Sala A
('34879563C', 2, '2025-06-24', '11:00:00', '12:30:00'), -- Reserva de María en Sala B
('45748945D', 3, '2025-06-25', '14:00:00', '15:00:00'); -- Reserva de Luis en Sala C


UPDATE SalaReuniones
SET disponible = FALSE
WHERE id IN (1, 2, 3);