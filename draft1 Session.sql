use draft1;

CREATE TABLE User 
(
  userId INT AUTO_INCREMENT NOT NULL,
  userName VARCHAR(255),
  password VARCHAR(255),
  PRIMARY KEY(userId)
);
CREATE TABLE Role 
(
  roleName VARCHAR(255) NOT NULL,
  userId INT NOT NULL,
  PRIMARY KEY(roleName,userId),
  FOREIGN KEY(userId) REFERENCES User(userId)
);
CREATE TABLE Plant
(
  address VARCHAR(255) NOT NULL,
  plantId INT NOT NULL auto_increment,
  name VARCHAR(255) NOT NULL,
  currentCapacity INT NOT NULL,
  PRIMARY KEY (plantId)
);
CREATE TABLE Employee
(
  employeeId INT NOT NULL,
  position VARCHAR(255) NOT NULL,
  salary INT NOT NULL,
  city VARCHAR(255) NOT NULL,
  dateOfJoining DATE NOT NULL,
  gender VARCHAR(255) NOT NULL,
  lname VARCHAR(255) NOT NULL,
  fname VARCHAR(255) NOT NULL,
  plantId INT NOT NULL,
  PRIMARY KEY (employeeId),
  FOREIGN KEY (employeeId) REFERENCES User(userId) on delete cascade,
  FOREIGN KEY (plantId) REFERENCES Plant(plantId) on delete cascade on update cascade
);

CREATE TABLE Supplier
(
  supplierId INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  contact INT NOT NULL,
  PRIMARY KEY (supplierId),
  FOREIGN KEY (supplierId) REFERENCES User(userId) on delete cascade
);

CREATE TABLE RawMaterial
(
  rawMaterialId INT NOT NULL auto_increment,
  name VARCHAR(255) NOT NULL,
  price INT NOT NULL,
  PRIMARY KEY (rawMaterialId)
);

CREATE TABLE Supplies
(
  rawMaterialId INT NOT NULL auto_increment,
  supplierId INT NOT NULL,
  PRIMARY KEY (rawMaterialId, supplierId),
  FOREIGN KEY (rawMaterialId) REFERENCES RawMaterial(rawMaterialId),
  FOREIGN KEY (supplierId) REFERENCES Supplier(supplierId)
);

CREATE TABLE Stores
(
  quantity INT NOT NULL,
  rawMaterialId INT NOT NULL,
  plantId INT NOT NULL,
  PRIMARY KEY (rawMaterialId, plantId),
  FOREIGN KEY (rawMaterialId) REFERENCES RawMaterial(rawMaterialId),
  FOREIGN KEY (plantId) REFERENCES Plant(plantId)
);

CREATE TABLE SupplyOrder
(
  supplyOrderId INT NOT NULL auto_increment,
  quantity INT NOT NULL,
  employeeId INT NOT NULL,
  supplierId INT NOT NULL,
  rawMaterialId INT NOT NULL,
  PRIMARY KEY (supplyOrderId),
  FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
  FOREIGN KEY (supplierId) REFERENCES Supplier(supplierId),
  FOREIGN KEY (rawMaterialId) REFERENCES RawMaterial(rawMaterialId)
);

CREATE TABLE EmployeeEmail
(
  email VARCHAR(255) NOT NULL,
  employeeId INT NOT NULL ,
  PRIMARY KEY (email, employeeId),
  FOREIGN KEY (employeeId) REFERENCES Employee(employeeId)
);

CREATE TABLE EmployeePhoneNo
(
  phoneNo INT NOT NULL,
  employeeId INT NOT NULL ,
  PRIMARY KEY (phoneNo, employeeId),
  FOREIGN KEY (employeeId) REFERENCES Employee(employeeId)
);

CREATE TABLE SupplierAddress
(
  address VARCHAR(255) NOT NULL,
  supplierId INT NOT NULL,
  PRIMARY KEY (address, supplierId),
  FOREIGN KEY (supplierId) REFERENCES Supplier(supplierId)
);

CREATE TABLE Supervisor
(
  employeeId INT NOT NULL,
  supervisorId INT NOT NULL,
  PRIMARY KEY (employeeId, supervisorId),
  FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
  FOREIGN KEY (supervisorId) REFERENCES Employee(employeeId)
);

CREATE TABLE Client
(
  clientId INT NOT NULL,
  city VARCHAR(255) NOT NULL,
  street VARCHAR(255) NOT NULL,
  phoneNumber INT NOT NULL,
  gender VARCHAR(255) NOT NULL,
  lname VARCHAR(255) NOT NULL,
  fname VARCHAR(255) NOT NULL,
  employeeId INT ,
  PRIMARY KEY (clientId),
  FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
  UNIQUE (phoneNumber),
  FOREIGN KEY (clientId) REFERENCES User(userId) on delete cascade
);

CREATE TABLE Products
(
  name VARCHAR(255) NOT NULL,
  productId INT NOT NULL auto_increment,
  price INT NOT NULL,
  employeeId INT NOT NULL,
  PRIMARY KEY (productId),
  FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
  UNIQUE (name)
);

CREATE TABLE ClientOrder
(
  clientOrderId INT NOT NULL auto_increment,
  receivedDate DATE NOT NULL,
  dueDate DATE NOT NULL,
  status VARCHAR(255) NOT NULL,
  remark VARCHAR(255) NOT NULL,
  priority INT NOT NULL,
  clientId INT NOT NULL,
  approvedByEmployeeId INT NOT NULL,
  assignedToEmployeeId INT NOT NULL,
  PRIMARY KEY (clientOrderId),
  FOREIGN KEY (clientId) REFERENCES Client(clientId),
  FOREIGN KEY (approvedByEmployeeId) REFERENCES Employee(employeeId),
  FOREIGN KEY (assignedToEmployeeId) REFERENCES Employee(employeeId)
);

CREATE TABLE Feedback
(
  feedbackId INT NOT NULL auto_increment,
  date DATE NOT NULL,
  remark VARCHAR(255) NOT NULL,
  clientOrderId INT NOT NULL,
  employeeId INT NOT NULL,
  clientId INT NOT NULL,
  PRIMARY KEY (feedbackId),
  FOREIGN KEY (clientOrderId) REFERENCES ClientOrder(clientOrderId),
  FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
  FOREIGN KEY (clientId)   REFERENCES Client(clientId)
);

CREATE TABLE ConsistsOf
(
  quantity INT NOT NULL,
  clientOrderId INT NOT NULL,
  productId INT NOT NULL,
  PRIMARY KEY (clientOrderId, productId),
  FOREIGN KEY (clientOrderId) REFERENCES ClientOrder(clientOrderId),
  FOREIGN KEY (productId) REFERENCES Products(productId)
);

CREATE TABLE View
(
  clientId INT NOT NULL,
  productId INT NOT NULL,
  PRIMARY KEY (clientId, productId),
  FOREIGN KEY (clientId) REFERENCES Client(clientId),
  FOREIGN KEY (productId) REFERENCES Products(productId)
);

CREATE TABLE Manufactures
(
  timeRequired time NOT NULL,
  productId INT NOT NULL,
  plantId INT NOT NULL,
  PRIMARY KEY (productId, plantId),
  FOREIGN KEY (productId) REFERENCES Products(productId),
  FOREIGN KEY (plantId) REFERENCES Plant(plantId)
);

CREATE TABLE ClientEmail
(
  email VARCHAR(255) NOT NULL,
  clientId INT NOT NULL,
  PRIMARY KEY (email, clientId),
  FOREIGN KEY (clientId) REFERENCES Client(clientId)
);

CREATE TABLE ClientOrderLog
(
  eventTime DATE NOT NULL,
  discription VARCHAR(255) NOT NULL,
  clientOrderId INT NOT NULL,
  PRIMARY KEY (eventTime, clientOrderId),
  FOREIGN KEY (clientOrderId) REFERENCES ClientOrder(clientOrderId)
);
CREATE TABLE MadeOf (
  productId INT NOT NULL,
  rawMaterialId INT NOT NULL,
  quantity INT,
  PRIMARY KEY (productId, rawMaterialId),
  FOREIGN KEY (productId) REFERENCES Products(productId),
  FOREIGN KEY (rawMaterialId) REFERENCES RawMaterial(rawMaterialId)
);
