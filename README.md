# Mini Platformer Spring Boot WebSocket Multiplayer

Real-time multiplayer and cosmetic store (Spring Boot, WebSocket/JSON).

## Running the Application

```
./gradlew clean build
docker build --build-arg 'JAR_FILE=build/libs/*-SNAPSHOT.jar' -t server .
docker run -it --name server -p 3030:3030 server
localhost:3030/ws
```

`Endpoint: localhost:3030/ws`

## Features

- Multiplayer: players seeing other players, outfit synchronization.
- Level filter: Players in different levels cannot see each other

## Packets

### Packet structure 
```
{"v": 1, "t": "<Packet>", "p": {...}}
v: version
t: type
p: object
```
### Packet Table
| Packet      | Direction | Arguments                                                                                                                                                                                                   |
|-------------|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Hello       | C→S       | `string client_id: session id, string name: name, Equipment equipped: equipped items`                                                                                                                      |
| Welcome     | S→C       | `string your_id: session id, int catalog_version: market version, int bank: stored golds, string[] owned: owned equipments (for future), Equipment equipped: equipped items, Player[] players: player list` |
| PlayerState | C→S, S→C  | `string id: session id, float x/y: position, float vx: velocity, int facing: facing direction, Equipment equipped: equipped items, int level: active level`                                                   |
| PlayerJoin  | S→C       | `string id: session id, string name: name, float x/y: position, Equipment equipped: equipped items`                                                                                                                 |
| PlayerLeave | S→C       | `string id: session id`                                                                                                                                                                                            |
| Ping        | C→S       | `int at: Instant now nanosec`                                                                                                                                                                           |
| Pong        | S→C       | `int at: Instant now nanosec`                                                                                                                                                                           |
| Error       | S→C       | `string code: error code, string message: error message`                                                                                                                                                    |


## Decisions

- **No DB:** Cash and inventory are managed in-session; records are stored on the player’s device.
- **No Auth:** Login/registration is skipped; the player’s ID is the one provided by the server plus the name they choose.