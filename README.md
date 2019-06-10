# Icefire Assignment

This project was created to fullfill the Icefire assignment requirements

## How to setup

Run mvn spring-boot:run

## Users

- Gustavo

User: gustavo
Pass: icefirerocks

- Admin

User: admin
Pass: icefirerocks

## How it works

Each user has its own key pars saved on the in-memory database (you can check data.sql file) 
Each time an user login a new JWT token is generated, with that you can request to the rest Api Services

# Routes: 
- GET `/api/information/list`: will list all the informations for the user asking by it (know because of JWT token)

No request body needed.

- POST `/api/information/create`: will pick an information and save it on the database but encrypted with the user public key

{
	"information" : "some text"
}

- POST `/api/information/decrypt`: will pick an information and decrypt it using the user private key

{
	"informationSecured" : "`encrypted data`"
}

## Contact

Gustavo Stabelini (gustavostabelini@gmail.com)