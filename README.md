# Multiplayer Stick-Hero Game

## Technologies Used

- **Backend**: Java
- **Frontend**: JavaFx
- **Database**: MongoDB


## Introduction

This project brings the famous Stick Hero game to the world of multiplayer gaming using JavaFX and MongoDB. The game features both single-player and multiplayer functionalities, allowing users to compete with others. The game includes features like revive options, sound effects, and the ability to save game progress.

## Features

### User Authentication
- **Login**: Players need to log in using their unique username, password, and ID.
- **Error Handling**: The system throws an "Invalid Credentials" error if the ID is already registered but the username or password is incorrect.

### Game Mechanics
- **Single-player and Multiplayer Modes**: Supports both single-player and multiplayer gameplay.
- **Revive**: Players can revive themselves using cherries collected during the game (costs 5 cherries).
- **Sound Effects**: Includes sound effects for various game events like throwing the stick and game over.

### Data Management
- **MongoDB Integration**: 
  - **Users Collection**: Stores user information including name, ID, password, best score, and games played.
  - **Games Collection**: Stores game data including game scenes, best scores, and scores of all users who played that particular game.
- **Local Storage**: Uses serialization and deserialization to store data locally in case the game exits unexpectedly. Unsaved data is loaded to the database when the user starts the application again.

### Concurrency and Physics
- **Concurrency**: Uses threads to handle cherry collection and collision detection.
- **Physics Calculations**: Calculates the velocity of Stick Hero to manage collisions.

### Design Patterns
- **Template**
- **Singleton**
- **Facade**
- **Factory**

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Harshit22209/StickHeroGame.git
