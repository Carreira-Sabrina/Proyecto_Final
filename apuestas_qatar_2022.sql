-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-04-2023 a las 00:54:47
-- Versión del servidor: 8.0.32
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `apuestas_qatar_2022`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipos`
--

CREATE TABLE `equipos` (
  `codigo` varchar(3) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `equipos`
--

INSERT INTO `equipos` (`codigo`, `nombre`) VALUES
('ARA', 'Arabia Saudita'),
('ARG', 'Argentina'),
('AUS', 'Australia'),
('BRA', 'Brasil'),
('COS', 'Corea del Sur'),
('CRO', 'Croacia'),
('FRA', 'Francia'),
('ING', 'Inglaterra'),
('MAR', 'Marruecos'),
('MEX', 'Mexico'),
('PAB', 'Paises Bajos'),
('POL', 'Polonia'),
('POR', 'Portugal'),
('SUI', 'Suiza');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pronosticos`
--

CREATE TABLE `pronosticos` (
  `id` int NOT NULL,
  `ronda` varchar(3) NOT NULL,
  `participante` varchar(25) NOT NULL,
  `equipo_1` varchar(45) NOT NULL,
  `gana_1` varchar(1) DEFAULT NULL,
  `empate` varchar(1) DEFAULT NULL,
  `gana_2` varchar(1) DEFAULT NULL,
  `equipo_2` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `pronosticos`
--

INSERT INTO `pronosticos` (`id`, `ronda`, `participante`, `equipo_1`, `gana_1`, `empate`, `gana_2`, `equipo_2`) VALUES
(1, '1', 'Mariana', 'Argentina', 'X', '', '', 'Arabia Saudita'),
(2, '1', 'Mariana', 'Polonia', '', 'X', '', 'Mexico'),
(3, '1', 'Mariana', 'Argentina', 'X', '', '', 'Mexico'),
(4, '1', 'Mariana', 'Arabia Saudita', '', '', 'X', 'Polonia'),
(5, '2', 'Mariana', 'Argentina', 'X', '', '', 'Australia'),
(6, '2', 'Mariana', 'Francia', '', 'X', '', 'Polonia'),
(7, '2', 'Mariana', 'Brasil', 'X', '', '', 'Corea del Sur'),
(8, '2', 'Mariana', 'Portugal', '', '', 'X', 'Suiza'),
(9, '3', 'Mariana', 'Marruecos', '', 'X', '', 'Portugal'),
(10, '3', 'Mariana', 'Inglaterra', '', '', 'X', 'Francia'),
(11, '3', 'Mariana', 'Croacia', '', '', 'X', 'Brasil'),
(12, '3', 'Mariana', 'Paises Bajos', '', '', 'X', 'Argentina'),
(13, '4', 'Mariana', 'Argentina', '', '', 'X', 'Croacia'),
(14, '4', 'Mariana', 'Francia', 'X', '', '', 'Marruecos'),
(15, '5', 'Mariana', 'Croacia', '', 'X', '', 'Marruecos'),
(16, '6', 'Mariana', 'Argentina', 'X', '', '', 'Francia'),
(17, '1', 'Pedro', 'Argentina', 'X', '', '', 'Arabia Saudita'),
(18, '1', 'Pedro', 'Polonia', '', '', 'X', 'Mexico'),
(19, '1', 'Pedro', 'Argentina', 'X', '', '', 'Mexico'),
(20, '1', 'Pedro', 'Arabia Saudita', '', 'X', '', 'Polonia'),
(21, '2', 'Pedro', 'Argentina', '', 'X', '', 'Australia'),
(22, '2', 'Pedro', 'Francia', 'X', '', '', 'Polonia'),
(23, '2', 'Pedro', 'Brasil', 'X', '', '', 'Corea del Sur'),
(24, '2', 'Pedro', 'Portugal', 'X', '', '', 'Suiza'),
(25, '3', 'Pedro', 'Marruecos', '', '', 'X', 'Portugal'),
(26, '3', 'Pedro', 'Inglaterra', 'X', '', '', 'Francia'),
(27, '3', 'Pedro', 'Croacia', '', '', 'X', 'Brasil'),
(28, '3', 'Pedro', 'Paises Bajos', '', 'X', '', 'Argentina'),
(29, '4', 'Pedro', 'Argentina', 'X', '', '', 'Croacia'),
(30, '4', 'Pedro', 'Francia', '', 'X', '', 'Marruecos'),
(31, '5', 'Pedro', 'Croacia', '', '', 'X', 'Marruecos'),
(32, '6', 'Pedro', 'Argentina', 'X', '', '', 'Francia'),
(33, '1', 'Cata', 'Argentina', '', '', 'X', 'Arabia Saudita'),
(34, '1', 'Cata', 'Polonia', '', 'X', '', 'Mexico'),
(35, '1', 'Cata', 'Argentina', 'X', '', '', 'Mexico'),
(36, '1', 'Cata', 'Arabia Saudita', '', '', 'X', 'Polonia'),
(37, '2', 'Cata', 'Argentina', 'X', '', '', 'Australia'),
(38, '2', 'Cata', 'Francia', 'X', '', '', 'Polonia'),
(39, '2', 'Cata', 'Brasil', 'X', '', '', 'Corea del Sur'),
(40, '2', 'Cata', 'Portugal', 'X', '', '', 'Suiza'),
(41, '3', 'Cata', 'Marruecos', 'X', '', '', 'Portugal'),
(42, '3', 'Cata', 'Inglaterra', '', '', 'X', 'Francia'),
(43, '3', 'Cata', 'Croacia', 'X', '', '', 'Brasil'),
(44, '3', 'Cata', 'Paises Bajos', '', '', 'X', 'Argentina'),
(45, '4', 'Cata', 'Argentina', 'X', '', '', 'Croacia'),
(46, '4', 'Cata', 'Francia', 'X', '', '', 'Marruecos'),
(47, '5', 'Cata', 'Croacia', 'X', '', '', 'Marruecos'),
(48, '6', 'Cata', 'Argentina', 'X', '', '', 'Francia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `puntajes`
--

CREATE TABLE `puntajes` (
  `id` int NOT NULL,
  `numero_ronda` int DEFAULT NULL,
  `alias_partido` varchar(45) DEFAULT NULL,
  `participante` varchar(45) DEFAULT NULL,
  `puntos_obtenidos` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `equipos`
--
ALTER TABLE `equipos`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `codigo_UNIQUE` (`codigo`);

--
-- Indices de la tabla `pronosticos`
--
ALTER TABLE `pronosticos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `puntajes`
--
ALTER TABLE `puntajes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `pronosticos`
--
ALTER TABLE `pronosticos`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT de la tabla `puntajes`
--
ALTER TABLE `puntajes`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
