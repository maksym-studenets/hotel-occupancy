# Hotel Occupancy App
Hotel Occupancy Backend Coding Challenge Application

---
## Key features
This application implements an algorithms to allocate hotel rooms to potential guests.

There are two types of rooms: **premium** and **economy**. Guests will be assigned rooms according to their willingness to pay,
with higher-paying guests having precedence over lower-paying ones. Guests who are willing pay EUR 100 and more can be assigned
to premium rooms **only**.

After all economy rooms are allocated, highest paying economy guests will be upgraded to premium rooms if there are available ones.

The application calculates occupancy and revenue by room segments.

## Technologies
- Java 21
- Spring Boot 3.4.0
- Docker

---
## Running instructions
Docker must be up and running 
1. Execute `run.sh` script to start the application.
2. The application will start at `http://localhost:8080`

## Usage
The application exposes a single endpoint to calculate hotel room occupancy.

`POST /occupancy`.

Request example:
```curl 
curl --location 'http://localhost:8080/occupancy' \
--header 'Content-Type: application/json' \
--data '{
    "premiumRooms": 6,
    "economyRooms": 5,
    "potentialGuests": [
        23,
        45,
        155,
        374,
        22,
        99.99,
        100,
        101,
        115,
        209
    ]
}'
```

**Request** body sample:
```json
{
  "premiumRooms": 5,
  "economyRooms": 7,
  "potentialGuests": [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]
}
```
Properties:
* `premiumRooms` -  number of premium rooms available
    * required
    * integer
    * must be equal to or greater than 0
* `economyRooms` -  number of economy rooms available
    * required
    * integer
    * must be equal to or greater than 0
* `potentialGuests` - potential guests represented as prices to pay
    * required
    * decimal numbers
    * elements must be strictly greater than 0

**Response** body sample:
```json
{
    "usagePremium": 6,
    "revenuePremium": 1054,
    "usageEconomy": 4,
    "revenueEconomy": 189.99
}
```
Properties:
* `usagePremium` -  number of occupied premium rooms
* `revenuePremium` -  revenue from premium rooms
* `usageEconomy` - number of occupied economy rooms
* `revenueEconomy` - revenue from economy rooms