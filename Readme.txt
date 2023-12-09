Group No: 3
Harshit Gupta: 2022209
Abhay Kohli: 2022015

GitHub: https://github.com/Harshit22209/StickHeroGame

*We have single-player and multiplayer functionality in our code implementation. We have connected to the MongoDB database to support multiplayer.

Before starting the game, the player needs to log in using their unique username, password, and ID. Each player has their unique ID that nobody else has. If someone tries to login with the ID already registered but a different username or password, then the system will throw the error "Invalid Credentials".

We have also extend our code to support:

Revive: After the game ends, players can choose to revive themselves using the cherries they collected (but it will cost 5 cherries).

Sound: The game includes sound effects. Sound will play when the player throws the stick at the middle of the building (the red point) and when the game ends.

#Save:
-> When the game finishes, the player can choose whether to save it or not. If the player decides to save the game, it will be stored in the database.
 
How data is saved on the MongoDB database and why?
  
 There are two Collections Games and Users.
   1.Users: Store all the information of user like  name,id,password,bestscore and gamesplayed

   2.Games: It stores the data for a game like all the gamescenes,Bestscore,score of all the user played that particular game

We have also use concept of serialization and deserialization to store some data in local. Now why we need to store at local level if we are already storing in cloud. We store at local level
because in cloud data is only saved when the game is over,now if user exitted the game without getting out, then to restore the data we have save it to the local.And whenever user again start the application,all the data that is unsaved will be loaded to database.   

We have used threads to handle the collection of cherries and collision with the platform.And to do this ,we have even calculated velocity of the stickHero.


*Design patterns used:
 Template
 Singleton
 Facade
 Factory

Sources:
mixkit-arcade-bonus,mixkit-arcade-retro-background,mixkit-arcade-retro-gameover: https://mixkit.co/free-sound-effects/arcade/
8bit-music-for-game-68698.mp3 :https://pixabay.com/sound-effects/8bit-music-for-game-68698/


