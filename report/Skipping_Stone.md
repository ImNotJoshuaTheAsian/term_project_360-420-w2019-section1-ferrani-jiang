Sofiane Ferrani and Joshua Jiang
Skipping a stone: how many times can we possibly skip a stone in a lake given its initial angle of attack?
Goals of the simulation
The goals of our simulation are to first, calculate the maximum number of skips possibly done when skipping a stone in a lake given initial conditions, and second, graph the trajectory of the stone on the lake in a coordinate system of x with respect to y (with y = 0 at water level).
Theory and equations
 
***we interchanged y and z in our coordinate system
There are two major stages of the trajectory of the stones. There is first the “projectile” stage, where the stone’s acceleration is only affected by gravity and drag. It occurs when the stone is above water level (y > 0). Second, there is the “collisional” stage. It occurs when the stone is at or below water level (y =< 0). 
	Equation of motion for “projectile” stage (when y > 0)
Along the x direction, the stone’s motion is affected by drag. Considering the force acting on the center of mass, we have:
a_x=(-F_Drag cos(β))/m=(-〖(C〗_Drag)(ρ_air)(v^2)(A_stone)cos(β))/2m
v_xi=v_(x(i-1))+a_x (∆t)
x_i=x_(i-1)+v_(i-1) cos(β)(∆t)+a_x 〖∆t〗^2
Along the y direction, the acceleration is affected by both the drag force and by gravity:
a_y=-g-(F_Drag  sin⁡(β))/m=-g-(〖(C〗_Drag)(ρ_air)(v^2)(A_stone)sin(β))/2m
v_yi=v_(y(i-1))+a_x (∆t)
y_i=y_(i-1)+v_(i-1) sin(β)(∆t)+a_y 〖∆t〗^2
It is to be noted that, assuming a high spin velocity, the stone’s angle of incidence θ is stabilized during the impact, thanks to the gyroscopic effect. Therefore, we will assume it will not change with respect to the initial condition. However, the angle β does change constantly and must be updated through:
β_i=tan^(-1)⁡〖V_yi/V_xi 〗

	Equation of motion for “collisional” stage (when y =< 0)
During the collisional process, Newton’s third law of action and reaction applies: the force water exerts on the stone will be equal to the force the reaction force of water possesses both a lift (along n) and friction (along t) component. Therefore, we use θ as the angle of reference to define x and y components of acceleration. The x component of acceleration gives: 
a_x=(-〖(C〗_lift sin⁡(θ)+C_friction cos⁡(θ))(ρ_water)(v^2)(A_(stone submerged)))/2m
In the y component, we must add gravity:
a_y==-g+(〖(C〗_lift cos⁡(θ)+C_friction sin⁡(θ))(ρ_water)(v^2)(A_(stone submerged)))/2m
The submerged area of the stone, knowing that it is cylindrical and has radius r and height h, is given by:
A_(stone submerged)=r^2 (cos^(-1)⁡〖(1- |y|/(r sin⁡θ ))-(1- |y|/(r sin⁡θ ))〗 √(1-(1- |y|/(r sin⁡θ ))^2 ))
Finally, energy will progressively be dissipated in the form of friction through the trajectory, until the final collision occurs ― not enough kinetic energy is generated to counter balance the work of friction throughout the collision. 
It is important to note that the effects of the spin on the angular motion as well as on the consequent variation of the angle θ has been neglected. In fact, most of the papers we have come across either do not even mention the spin, or do not include it in their own calculation of their data. Furthermore, it is shown that the decrease in angular momentum (caused by the spin) throughout the trajectory is negligible relative to the decrease in speed of the center of mass.
Algorithm
Our program will first set initial conditions for the drag coefficient of air, air density, water density, lift coefficient in water, friction coefficient in water, radius of the stone, height of the stone (or width), the mass of the stone, gravitational acceleration, initial y position, initial x position, initial angle of attack β, initial inclination θ, and initial velocity given to the stone.
Then, we will define methods for acceleration in x and y for both the projectile and collisional stage. A method for the submerged area of the stone in water should also be programmed.
Arrays of time, angle β, acceleration (four types), position x, position y, velocity in x and velocity in y will be defined. A condition referring to the value of y (bigger or smaller than 0) will determine whether the acceleration used in the equations of motion is derived from the projectile or collisional phase. For each possibility (true or false), we would use Euler’s method to update every value of every array. The simulation stops when the velocity in x is smaller or equal to 0 m/s, or when y is smaller than -1 m.
Finally, math.plot library will be used to graph the trajectory, and the number of skips will be found using a method that counts the number of times v_y=0 in its array, meaning the number of times the stone was at a maximum height in projectile (which equals the number of minima in collisional motion).
Sources:
http://ilm-perso.univ-lyon1.fr/~lbocquet/JFM_skippingstones.pdf
https://arxiv.org/pdf/physics/0210015.pdf?fbclid=IwAR1woDtKytnLSjvXn70O6Exr2CumMm3KjqteXmQlSWqXIbI88qkeXBmwR1k
http://ilm-perso.univ-lyon1.fr/~lbocquet/JFM_skippingstones.pdf
