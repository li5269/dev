-- MySQL Script generated by MySQL Workbench
-- Fri Sep 14 11:20:57 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema blogdb
-- -----------------------------------------------------
-- Schema used with the Film application
DROP SCHEMA IF EXISTS `blogdb` ;

-- -----------------------------------------------------
-- Schema blogdb
--
-- Schema used with the Film application
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `blogdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `blogdb` ;

-- -----------------------------------------------------
-- Table `blogdb`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `blogdb`.`User` ;

CREATE TABLE IF NOT EXISTS `blogdb`.`User` (
  `UserNo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `UserName` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `Gender` VARCHAR(45) NOT NULL,
  `Profile` VARCHAR(100) NULL,
  `PersonalSign` VARCHAR(45) NULL,
  `Follower_Num` INT NOT NULL,
  `Level` INT NOT NULL,
  PRIMARY KEY (`UserNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `blogdb`.`Category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `blogdb`.`Category` ;

CREATE TABLE IF NOT EXISTS `blogdb`.`Category` (
  `Category` VARCHAR(45) NOT NULL,
  `Label` VARCHAR(45) NOT NULL,
  `CategoryId` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`CategoryId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `blogdb`.`Blog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `blogdb`.`Blog` ;

CREATE TABLE IF NOT EXISTS `blogdb`.`Blog` (
  `BlogNo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Created_Time` DATETIME(6) NOT NULL,
  `Label` VARCHAR(45) NOT NULL,
  `Image` VARCHAR(45) NULL,
  `Content` VARCHAR(1000) NOT NULL,
  `Author` INT UNSIGNED NOT NULL,
  `Title` VARCHAR(30) NOT NULL,
  `Category_CategoryId` INT UNSIGNED NOT NULL,
  `Collected_Num` INT NOT NULL,
  PRIMARY KEY (`BlogNo`),
  INDEX `fk_Blog_User_idx` (`Author` ASC),
  INDEX `fk_Blog_Category1_idx` (`Category_CategoryId` ASC),
  CONSTRAINT `fk_Blog_User`
    FOREIGN KEY (`Author`)
    REFERENCES `blogdb`.`User` (`UserNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Blog_Category1`
    FOREIGN KEY (`Category_CategoryId`)
    REFERENCES `blogdb`.`Category` (`CategoryId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = 'maintain blogs';


-- -----------------------------------------------------
-- Table `blogdb`.`Collection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `blogdb`.`Collection` ;

CREATE TABLE IF NOT EXISTS `blogdb`.`Collection` (
  `Blog_BlogNo` INT UNSIGNED NOT NULL,
  `User_UserNo` INT UNSIGNED NOT NULL,
  `CollectionNo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`CollectionNo`),
  INDEX `fk_Blog_has_User_User1_idx` (`User_UserNo` ASC),
  INDEX `fk_Blog_has_User_Blog1_idx` (`Blog_BlogNo` ASC),
  CONSTRAINT `fk_Blog_has_User_Blog1`
    FOREIGN KEY (`Blog_BlogNo`)
    REFERENCES `blogdb`.`Blog` (`BlogNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Blog_has_User_User1`
    FOREIGN KEY (`User_UserNo`)
    REFERENCES `blogdb`.`User` (`UserNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `blogdb`.`Comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `blogdb`.`Comment` ;

CREATE TABLE IF NOT EXISTS `blogdb`.`Comment` (
  `User_UserNo` INT UNSIGNED NOT NULL,
  `Blog_BlogNo` INT UNSIGNED NOT NULL,
  `Content` VARCHAR(140) NOT NULL,
  `Created_Time` DATETIME(6) NOT NULL,
  `CommentNo` INT UNSIGNED NOT NULL,
  INDEX `fk_User_has_Blog_Blog1_idx` (`Blog_BlogNo` ASC),
  INDEX `fk_User_has_Blog_User1_idx` (`User_UserNo` ASC),
  PRIMARY KEY (`CommentNo`),
  CONSTRAINT `fk_User_has_Blog_User1`
    FOREIGN KEY (`User_UserNo`)
    REFERENCES `blogdb`.`User` (`UserNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Blog_Blog1`
    FOREIGN KEY (`Blog_BlogNo`)
    REFERENCES `blogdb`.`Blog` (`BlogNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `blogdb`.`Follow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `blogdb`.`Follow` ;

CREATE TABLE IF NOT EXISTS `blogdb`.`Follow` (
  `User_UserNo` INT UNSIGNED NOT NULL,
  `Follower` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`User_UserNo`, `Follower`),
  INDEX `fk_User_has_User_User2_idx` (`Follower` ASC),
  INDEX `fk_User_has_User_User1_idx` (`User_UserNo` ASC),
  CONSTRAINT `fk_User_has_User_User1`
    FOREIGN KEY (`User_UserNo`)
    REFERENCES `blogdb`.`User` (`UserNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_User_User2`
    FOREIGN KEY (`Follower`)
    REFERENCES `blogdb`.`User` (`UserNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
