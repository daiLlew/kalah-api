
# Kalah Game API

## Getting stated
### Prerequisites
 - Java version 8 or higher
 - git
 - Maven

Get the code:
````bash
git clone git@github.com:daiLlew/kalah-api.git
````

## Build the code
````bash
mvn clean package
````

## Run the app
````bash
java -jar target/kalah-api-1.0-SNAPSHOT.jar
````
Once the app has started it will be running on [http://localhost:8080/games](http://localhost:8080/games).

### How to play

#### Create a new game:
```bash
curl --header "Content-Type: application/json" --request POST http://localhost:8080/games
```
Returns response:
```json
{
  "id": 1,
  "uri": "http://localhost:8080/games/1"
}
```

#### Get the game status:
```bash
curl --header "Content-Type: application/json" --request GET http://localhost:8080/games/1/status
```
Returns response:
```json
{
  "gameId": 1,
  "state": "CREATED",
  "playerTurn": "player-1"
}
```
If the game has ended the status response will also include details about the winner & points. 

#### Making a move:  
```bash
curl --header "Content-Type: application/json" --header "Player-Id: player-1" --request PUT http://localhost:8080/games/1/pits/1
```
Returns response:
```json
{
  "uri": "http://localhost:8080/games/1",
  "id": 1,
  "status": {
    "1": 0,
    "2": 7,
    "3": 7,
    "4": 7,
    "5": 7,
    "6": 7,
    "7": 7,
    "8": 6,
    "9": 6,
    "10": 6,
    "11": 6,
    "12": 6,
    "13": 6,
    "14": 6
  }
}
```
After each turn use the `GET:/game/{id}/status` endpoint to determined who's turn it is & the current state of the game.

**Note**: You must provide the request header `Player-Id` with value `player-1` or `player-2`. If no header or an 
invalid header is provided you will get a resopnse similar to:

````json
{
  "timestamp": "2019-08-15T18:49:12.763+0000",
  "status": 400,
  "error": "Bad Request",
  "message": "Missing request header 'Player-Id' for method parameter of type String",
  "path": "/games/1/pits/1"
}
````
A player can only execute a move successfully on their turn. If you attempt to make a move during the other players 
turn you'll get an error response similar to:

````json
{
  "timestamp": "2019-08-15T18:46:22.430+0000",
  "status": 400,
  "error": "Bad Request",
  "message": "cannot move stone from an empty pit",
  "path": "/games/1/pits/1"
}
```` 
### Determining the winner
To determined if the game is over and which player won use status endpoint (see previous example).

### Debugging
After each move the server log will include a simple representation of the current game state: This can be useful for
 debugging issues or getting clear idea of the current state of play.

```bash
DEBUG

| Game ID   | Current Player | State      |
+           +                +            +
| 1         | ONE            | IN_PROGRESS|
+           +                +            +

Pits:
        (13,6) (12,6) (11,6) (10,6) (9,6)  (8,6)

 (14,6)                                           (7,7)

        (1,0)  (2,7)  (3,7)  (4,7)  (5,7)  (6,7)
```
