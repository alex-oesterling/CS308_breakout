# Game Plan
## NAME
**Alex Oesterling, axo**

### Breakout Variant
- When I was a kid, my first exposure to mobile devices was an iPod Nano which I got for Christmas.
One of the games which it had on it was Vortex, which was in the Game Assignment Document as
an example. I think the circular game environment is both an interesting concept and a bit too complex for
my current abilities as a programmer. However, I find the concept exciting, and 8-year-old me 
thought the game was both entertaining and challenging.
- Recently, there have been Breakout-style games where the player launches a stream of balls at an angle
and tries to break as many bricks as possible. Instead of "bouncing" the balls, you try to break as
many bricks as possible before all the balls exit the bottom of the stage. Each brick requires
a large number of collisions to break, but you are given an ever increasing number of balls per 
shot. This variant is interesting because it appeals to the modern mobile game trend:
instead of requiring careful attention (moving the bouncer to keep the ball in the air),
the player can just launch the balls and let them bounce without having to do anything.
### General Level Descriptions
- Time permitting, I want to try randomly-generated levels, but for now, I think I will stick with creating tailored levels 
and focus on the game mechanics. I want to make fun shapes or levels with a lot of power-ups for 
maximum chaos. Here are some of my designs:
![Level Design](Level_Idea.png)
![Portal_Breaker](Portal_Breaker.png)
### Bricks Ideas
- I most definitely want to create a "portal" brick. I plan on making each level have multiple "boards,"
and hitting a portal will teleport you to the other board. You cannot see all the boards together
and have to teleport back and forth between areas in order to clear the entire level. I could make it so 
you can only enter the portal from a certain direction, or have it launch the ball out at a certain direction.
- I also was thinking of a "magnet" or "gravity" brick which would create a force field and bend the trajectory of the
ball. However, this would require a lot of physics and I do not think I will do it in the end,
unless I get some amazing idea or improvement in my coding abilities.
- A bomb block could destroy all adjacent bricks.
- I want to put my power ups inside the bricks, like in Mario Kart with the item boxes. They will
be random and have a "slot machine" selection process for which power up I get.
- I will also do the classic "durability" block which requires multiple collisions to break.
### Power Up Ideas
- Fireball: Each collision breaks "two lives" of a block
- Ghost: The next time the ball would collide with blocks, it passes through until it reaches an empty space
 - Seeker: The ball will always go towards the direction of the mouse
 - Wrecking Ball: The ball maintains its momentum if it breaks a block
 - Inverse: Negative power up, pressing right moves the bouncer left and vice versa
 - Slow: Ball velocity decreases
 - Fast: Ball velocity increases (both will throw off player)
 - Blindness: All the blocks disappear (but still interact with ball)
 - All power ups would last for a certain time duration

### Cheat Key Ideas
- At first I was thinking about a certain key sequence to activate a super mode, but I am worried that 
a series of key detections will be too complex. For this reason, I think I may put a secret 
"menu option" such as a hidden button in the corner
which when clicked starts the game but with the ball following the mouse cursor (seeker mode)
### Something Extra
- My extra component will the the portal mechanic, where each level consists of multiple stages where the ball must 
travel between stages and clear all of them in order to clear the level.
