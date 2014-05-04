SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`usuario` (
  `id` INT NOT NULL,
  `nome` VARCHAR(200) NOT NULL,
  `email` VARCHAR(200) NOT NULL,
  `ajuda_nome` VARCHAR(200) NULL,
  `ajuda_telefone` INT(8) NULL,
  `sessao_ativa` BLOB NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`pub`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`pub` (
  `id` INT NOT NULL,
  `pub_nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`bebida`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`bebida` (
  `id` INT NOT NULL,
  `bebida_nome` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`festa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`festa` (
  `id` INT NOT NULL,
  `pub_id` INT NOT NULL,
  `usuario_id` INT NOT NULL,
  `data_hora` TIMESTAMP NOT NULL DEFAULT NOW(),
  `consumo_limite` DOUBLE(10,2) NULL,
  `nota_festa` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `pub_id_idx` (`pub_id` ASC),
  INDEX `usuario_id_idx` (`usuario_id` ASC),
  CONSTRAINT `pub_id`
    FOREIGN KEY (`pub_id`)
    REFERENCES `mydb`.`pub` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `usuario_id`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`festa_consumo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`festa_consumo` (
  `id` INT NOT NULL,
  `festa_id` INT NOT NULL,
  `bebida_id` INT NOT NULL,
  `valor_bebida` DOUBLE(10,2) NOT NULL,
  `quantiade_consumida` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `festa_id_idx` (`festa_id` ASC),
  INDEX `bebida_id_idx` (`bebida_id` ASC),
  CONSTRAINT `festa_id`
    FOREIGN KEY (`festa_id`)
    REFERENCES `mydb`.`festa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `bebida_id`
    FOREIGN KEY (`bebida_id`)
    REFERENCES `mydb`.`bebida` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`pub_classificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`pub_classificacao` (
  `id` INT NOT NULL,
  `pub_id` INT NOT NULL,
  `usuario_id` INT NOT NULL,
  `calssificacao` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `usuario_id`
    FOREIGN KEY (`id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pub_id`
    FOREIGN KEY (`id`)
    REFERENCES `mydb`.`pub` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
