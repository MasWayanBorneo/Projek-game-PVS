-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 12, 2026 at 05:04 AM
-- Server version: 8.0.41
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_game`
--

-- --------------------------------------------------------

--
-- Table structure for table `game_library`
--

CREATE TABLE `game_library` (
  `id` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `icon_resource` varchar(255) NOT NULL,
  `jar_path` varchar(255) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `game_library`
--

INSERT INTO `game_library` (`id`, `title`, `icon_resource`, `jar_path`, `description`) VALUES
(1, 'Path Finding', '/icons/default.png', 'Games/PathFindingTerbaru.jar', 'Game Mencari Jalan Tercepat'),
(14, 'Sudoku 2', '/icons/Games2.png', 'Games/sudoku_projek.jar', 'By Wayan'),
(20, 'Tebak Kata', '/icons/TebakKata.png', 'Games/WorldBoxQuest.jar', 'Create by LORD delvian');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game_library`
--
ALTER TABLE `game_library`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_title` (`title`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `game_library`
--
ALTER TABLE `game_library`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
