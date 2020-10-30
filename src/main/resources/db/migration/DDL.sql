CREATE SCHEMA `customer_api` ;

CREATE TABLE `customer_api`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(36) NOT NULL `gender`,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `birthDate` DATE NOT NULL,
  `cpf` VARCHAR(12) NOT NULL,
  `gender` VARCHAR(10) NOT NULL,
  `createdAt` DATETIME NULL,
  `updateAt` DATETIME NULL,
  PRIMARY KEY (`id`));
  
ALTER TABLE `customer_api`.`customer` 
ADD UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE,
ADD UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE;
;
  
  
CREATE TABLE `customer_api`.`adress` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(2) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `neighborhood` VARCHAR(45) NOT NULL,
  `zipCode` VARCHAR(10) NOT NULL,
  `street` VARCHAR(100) NOT NULL,
  `number` INT NOT NULL,
  `additionalInformation` VARCHAR(100) NULL,
  `main` TINYINT NOT NULL,
  `customerId` INT NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `customer_api`.`adress` 
ADD INDEX `fk_idx` (`customerId` ASC) VISIBLE;
;
ALTER TABLE `customer_api`.`adress` 
ADD CONSTRAINT `fk_customer`
  FOREIGN KEY (`customerId`)
  REFERENCES `customer_api`.`customer` (`id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;