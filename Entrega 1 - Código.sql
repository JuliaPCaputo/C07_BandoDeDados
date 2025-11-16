-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `cinema` DEFAULT CHARACTER SET utf8 ;
USE `cinema` ;

CREATE TABLE IF NOT EXISTS `cinema`.`Filme` (
  `Titulo` VARCHAR(100) NOT NULL,
  `Duracao` INT NOT NULL,
  `Genero` VARCHAR(45) NOT NULL,
  `Classificacao` VARCHAR(10) NOT NULL,
  `Diretor` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Titulo`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cinema`.`Sala` (
  `idSala` INT NOT NULL,
  `Capacidade` INT NOT NULL,
  `tipoSala` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`idSala`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cinema`.`Sessao` (
  `idSessao` INT NOT NULL,
  `Data` DATE NOT NULL,
  `Horario` VARCHAR(45) NOT NULL,
  `Preco` DOUBLE NOT NULL,
  `Filme_Titulo` VARCHAR(100) NOT NULL,
  `Sala_idSala` INT NOT NULL,
  PRIMARY KEY (`idSessao`, `Filme_Titulo`, `Sala_idSala`),
  INDEX `fk_Sessao_Filme1_idx` (`Filme_Titulo` ASC) VISIBLE,
  INDEX `fk_Sessao_Sala1_idx` (`Sala_idSala` ASC) VISIBLE,
  CONSTRAINT `fk_Sessao_Filme1`
    FOREIGN KEY (`Filme_Titulo`)
    REFERENCES `cinema`.`Filme` (`Titulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sessao_Sala1`
    FOREIGN KEY (`Sala_idSala`)
    REFERENCES `cinema`.`Sala` (`idSala`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cinema`.`Ingresso` (
  `idIngresso` INT NOT NULL,
  `Poltrona` INT NOT NULL,
  `tipoIngresso` VARCHAR(10) NOT NULL,
  `Preco` DOUBLE NOT NULL,
  `Status` BOOLEAN NOT NULL,
  `Sessao_idSessao` INT NOT NULL,
  PRIMARY KEY (`idIngresso`, `Sessao_idSessao`),
  INDEX `fk_Ingresso_Sessao1_idx` (`Sessao_idSessao` ASC) VISIBLE,
  CONSTRAINT `fk_Ingresso_Sessao1`
    FOREIGN KEY (`Sessao_idSessao`)
    REFERENCES `cinema`.`Sessao` (`idSessao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cinema`.`Cliente` (
  `Nome` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Telefone` VARCHAR(45) NOT NULL,
  `CPF` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CPF`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cinema`.`Compra` (
  `Ingresso_idIngresso` INT NOT NULL,
  `Cliente_CPF` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Ingresso_idIngresso`, `Cliente_CPF`),
  INDEX `fk_Compra_Ingresso_idx` (`Ingresso_idIngresso` ASC) VISIBLE,
  INDEX `fk_Compra_Cliente1_idx` (`Cliente_CPF` ASC) VISIBLE,
  CONSTRAINT `fk_Compra_Ingresso`
    FOREIGN KEY (`Ingresso_idIngresso`)
    REFERENCES `cinema`.`Ingresso` (`idIngresso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Compra_Cliente1`
    FOREIGN KEY (`Cliente_CPF`)
    REFERENCES `cinema`.`Cliente` (`CPF`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
