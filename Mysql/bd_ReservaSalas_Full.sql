-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: mysql:3306
-- Tiempo de generación: 23-06-2025 a las 13:42:54
-- Versión del servidor: 5.7.44
-- Versión de PHP: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ReservaSalas`
--
CREATE DATABASE IF NOT EXISTS `ReservaSalas` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ReservaSalas`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Empleados`
--

CREATE TABLE `Empleados` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `departamento` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Empleados`
--

INSERT INTO `Empleados` (`dni`, `nombre`, `apellidos`, `email`, `departamento`) VALUES
('23541558H', 'María', 'Gómez', 'maria.gomez@proyecto.com', 'Ventas'),
('34879563C', 'Luis', 'Martínez', 'luis.martinez@proyecto.com', 'IT'),
('42587458G', 'Juan', 'Pérez', 'juan.perez@proyecto.com', 'Recursos Humanos'),
('43578941Y', 'Carlos', 'Lopez', 'carlos.lopez@proyecto.com', 'Ventas'),
('45748945D', 'Ana', 'Fernández', 'ana.fernandez@proyecto.com', 'Marketing');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Reserva`
--

CREATE TABLE `Reserva` (
  `idReserva` int(11) NOT NULL,
  `dniEmpleado` varchar(20) DEFAULT NULL,
  `idSala` int(11) DEFAULT NULL,
  `fecha` date NOT NULL,
  `horaInicio` time NOT NULL,
  `horaFin` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Reserva`
--

INSERT INTO `Reserva` (`idReserva`, `dniEmpleado`, `idSala`, `fecha`, `horaInicio`, `horaFin`) VALUES
(1, '42587458G', 1, '2025-06-24', '09:00:00', '10:00:00'),
(2, '34879563C', 2, '2025-06-24', '11:00:00', '12:30:00'),
(3, '45748945D', 3, '2025-06-25', '14:00:00', '15:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `SalaReuniones`
--

CREATE TABLE `SalaReuniones` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `capacidad` int(11) NOT NULL,
  `disponible` tinyint(1) NOT NULL,
  `recursosDisponibles` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `SalaReuniones`
--

INSERT INTO `SalaReuniones` (`id`, `nombre`, `capacidad`, `disponible`, `recursosDisponibles`) VALUES
(1, 'Sala A', 15, 0, 'Proyector, Pizarra'),
(2, 'Sala B', 10, 0, 'Proyector, Televisor, Pizarra'),
(3, 'Sala C', 15, 0, 'Pizarra'),
(4, 'Sala D', 10, 1, 'Proyector');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Empleados`
--
ALTER TABLE `Empleados`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `Reserva`
--
ALTER TABLE `Reserva`
  ADD PRIMARY KEY (`idReserva`),
  ADD KEY `dniEmpleado` (`dniEmpleado`),
  ADD KEY `idSala` (`idSala`);

--
-- Indices de la tabla `SalaReuniones`
--
ALTER TABLE `SalaReuniones`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Reserva`
--
ALTER TABLE `Reserva`
  MODIFY `idReserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `SalaReuniones`
--
ALTER TABLE `SalaReuniones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Reserva`
--
ALTER TABLE `Reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`dniEmpleado`) REFERENCES `Empleados` (`dni`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`idSala`) REFERENCES `SalaReuniones` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;