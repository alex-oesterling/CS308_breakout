game
====

This project implements the game of Breakout.

Name: 
Alex Oesterling, axo
### Timeline

Start Date: 
1/12/20
Finish Date: 
1/19/20
Hours Spent:
25-30
### Resources Used
Google
StackExchange
TA's: Jack Proudfoot, Anna Darwish
JavaFX Tutorial
### Running the Program

* Main class:  
    * Main.java
* Data files needed: 
    * Level1.txt, Level2.txt, Level3.txt for levels 
    * ball.png, basic_brick.png, damaged_brick.png, dura_brick.png, fire_ball.png, fireball.png, ghost.png, ghost_ball.png, health.png, paddle.gif, portal1.png, portal2.png, seeker.png, seeker_ball.png, wrecking_ball.png, wreckingball.png  
* Key/Mouse inputs:
    * s/d or left/right, spacebar, r, l, and s
* Cheat keys:
    * s - activates seeker mode (ball follows mouse)
    * l - adds more lives
    * r - resets ball to paddle
    * 1-0 on the number bar - goes to the numbered level, 0 for main menu
* Known Bugs:
    * If there is a block on the other side of a portal, the ball will get stuck inside it. I could add more "checks" for 
    if it is actually inside a block (there is a boolean tracking this to prevent the ball from bouncing when going through
    a portal but it also causes the ball to not call the collide method on the brick on the other side) but I did not have time.

* Extra credit:
    * My portal mechanic is the extra component

### Notes/Assumptions

My big assumption is relying on JavaFX when I do not really know how it works. For some things such as ```getBoundsInLocal()``` vs ```getBoundsInParent```
I literally have no idea what differs in functionality and both worked perfectly fine for my code. I think I also had some large assumptions such as
not being able to create my own classes and have then be able to be added to groups/extend the Node object. My workaround with ImageViews was alright, but
I would have wished to not have had to do that. I hope we learn this in the future!  

As far as assignment wording goes, I made some big assumptions like assuming that my powerups did not have to "drop" out of the sky. Instead,
I just made it so that you got the power up as soon as you hit the brick. I thought this still made for challenging game but also did not
require a whole new set of objects and renderings for power ups. I also assumed that it would be okay to not have any power ups affecting my "paddle" and 
instead focus completely on the ball. I decided to go this way because I find it a more interesting game if it is simple and focuses around one 
thing (the ball) instead of having crazy paddle effects, multiple balls all with different power ups, etc. I think overall it is still
 a game I am proud to have made so I hope that failing to make the paddle spicy does not cost me.

### Impressions

Overall, a crazy first project which truly gives us a taste for what this class is going to be like. The moments when the code
isn't working and you don't know why suck, and the moments when you solve a problem (or even better: the code works on the first 
try!) are amazing. I think this is a great way to be introduced to the expectations of the class, while giving us a crash course
on the tools we will use (IntelliJ, JavaFX).