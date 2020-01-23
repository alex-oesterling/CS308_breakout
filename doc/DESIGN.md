game
====

This project implements the game of Breakout.

### Name: 
Alex Oesterling, axo
### Roles:
As an individual project, my role was to write all the software to run the application,
from backend physics logic to frontend image rendering. I had to create the 
objects and their hierarchies and then render and update them in the main method.

### Design goals, new features
In this project, my design goals were to make a very simple breakout game with
room for the addition of new power-ups and other mechanics. By creating an abstract
Brick class, I made the addition of new bricks extremely easy to execute. Another programmer
simply has to create a new brick class and have it extend the Brick parent. Then, they can
easily incorporate its creation into the main method by either adding an additional case
to the text scanner for the new brick, or by manually adding them to levels in the code.
Personally, after I finished refactoring and organizing my code, I was able to add in multiple
bricks in the span of an hour because of how easy it was to extend the Brick class
and modify its collision method by a slight bit for each new brick.  

One design I failed at was adding multiple balls. I have a "switch case" or large if tree
scenario for my ball, where an internal string stores its "status" as powered up or not, and
which type of power. Then, it has as series of if statements changing its rendering depending
on this string. If I were to redo this portion I would also make a Ball hierarchy allowing 
for the easy implementation of newer and newer balls.
### High-level design of project: Purpose and interaction of classes
The high level perspective on this project is that it takes 4 base classes: a ball, a brick, a bumper, and a main method.
The main method handles the instantiation of the game: The creation of scenes, the creation
of the objects in the game (ball, brick, bumper), and their instantiation. It handles the
calls to switch scenes, loading a level from a text file, and the logic controlling when 
the game starts and when the game is over.

The ball, bumper, and brick all extend the abstract PortalBrick parent which has an ```update()``` method,
an inherent ImageView object (which is rendered in main), and X and Y positions. They all
pass their ImageViews into the main method to be rendered and added to a scene. 

The ball class handles its own motion around the stage, bouncing off of walls and also checking
collisions with the bumper and brick. It calls the ```collide()``` methods of each brick as it collides
with them, and also stores the lives and score values and updates them as it breaks bricks or falls 
off the stage. 

The brick class contains a ```collision()``` and ```damage()``` method, which tracks its own status,
and triggers any and all effects when the ball collides with it. It then will damage
itself and if its health becomes 0, it will remove itself from the list and scene.

The bumper class controls its own motion with the arrow keys. However, most of its collision
physics are stored within the ball for simplicity (the collision changes the ball's motion,
so the bumper does not need to know about this motion).

The ball controls most of the interaction, determining bouncing physics and calling collision
methods. I think that I put too much of the game code into the ball class, but I made this
choice early on in my project and was "too deep" before I could change it back. I wanted to
hide all of the motion and update methods from the main class and have it neatly packaged
inside one of the objects, but soon enough the need to update "game state" variables like
lives and score by the ball on collisions required me to keep a lot of game data within the
ball class.
### Assumptions or decisions made to simplify design
One assumption/decision I made to simplify my design was to make powerups automatically 
occur on the destruction of "power up" bricks, instead of having a little power up object
drop from the brick to be caught by the paddle. The reason I did this is because it makes the interaction
between my three objects much simpler. Instead of creating a third object which needed to 
have falling physics as well as collision detection with the bumper, it was much easier to
have the brick immediately give the power up to the ball. This decision allowed me to 
focus more on the brick and ball mechanics and give them a lot of additional features such
as my portal mechanic.

Another assumption/decision I made to simplify my design was to avoid having bumper/paddle power
ups. The reason I did this is because I would have had to have multiple "states" for the
bumper which would have been modified by another class: the brick. There would be a dependency
between the brick and bumper, and my little knowledge of good coding would force me to 
pass the bumper as an additional object into the bumper leading to sharing of unnecessary 
information and more dependent code.
### Describe how to add new features, especially ones not completed by deadline
To add additional features to my project it depends on the type of feature one desires to add.
* To add additional bricks to my project, simply create a new class and have it extend the abstract Brick.
This implementation will allow a programmer to start creating new instances of the brick in the 
main class by adding an additional case to the scanning level loader (ie add an if statement for a 
new possible number to read from a level.txt file). This would require a simple modification to the 
```loadlevel()``` method in the main class.
* To add a other ball power-ups to my project, one would have to add an additional "case" to the ```setmode()``` 
method in the ball class, adding an additional possible string representing the new mode as 
well as a corresponding imagefile. Then, one would have to add code depending on how
the powerup affects the game. For example, if it were another power up that changed the way
the ball collided with the bricks, such as the wrecking ball power up, a coder would have to edit
the ```update()``` method in the ball class to change how it bounced off of bricks or triggered
collisions. If the ball would change how much damage it would do to the bricks, then one may
have to alter the ```damage()``` and ```collide()``` method inherited by the brick classes.
* To power up the bumper/paddle, one would have to add a lot more code because I did not develop
my own way of powering up the paddle which could be extended by a new feature. It would be quite easy to
have a ```setMode()``` method to allow the main game or another object to power it up and change its state,
 and then some sort of modification to its ```update()``` method to allow this "power up" to actually
affect its interactions with the other objects in the game. For example, to make a wide paddle,
the new ```setMode()``` method could change the ```getImage()``` ImageView object using ```setFitWidth()```
.