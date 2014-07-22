/*
 The Zombie-Apocalpse Game

 By : Zunair Syed

 Definition:
 In this program, a user will be able to enjoy a simple game of zombie shooting


 Rules Of the Game
 -Rules are very simple and the user is directed as much as I could have
 1)Dodge Rolling objects by either jumping over them, or standing on the platform when the come
 2)Kill Zombies, by shooting them or using powerups to do it
 3)Keep moving to the right when the "Next Level" arrow come. You need to keep moving until the arrow is out of the screen
 4)Survive as much as possible, when you get to level 3. (before level 3, your objective is to get to level 3 first)

 PROBLEM:
 -The main problem were the collisions of the moving objects in the game, (For example, a rolling object colliding with megaman)
 -The other problem was that using sprites for single animations. It was hard to to that, and sprites require at-least 1 counter if not 2 counters, (since we store sprites in arrays)

 Checklist, The program will do the following:
 -Provide instructions for the user before the game
 -Have buttons that act upon them when they are called using mouse clicks
 -Have 3 levels, which then increase in difficulty each level, by increasing the number of zombies
 -Let the megaman have unlimited shooting bullets, or enough that he will never run out
 -Let the megaman move  and power up with appropriate keys, and have mana to indicate if he has enough energy to powerup again or not
 -If mana is not enough to power up again, don't let him powerup
 -When in power up mode, allow the megaman to be very strong and have no damage done on it self
 -When a rolling object or a zombie collides with the megaman, decrease health, and as health decreases, change the color of the bar
 -if the health bar is emptey, end the game and go to game over screen
 -If on the game over screen, delay close to 5 seconds and restart game again, and show the score too in between

 PROBLEM ANALYSIS
 Inputs and Outputs:
 -From user; (ALL TO MEGAMAN) A-Move Left, D-Move Right, S-PowerUp, Z-Shoot-Bullet, "SPACE"-Jump
 -From Files; pictures include Megaman Pictures, rolling object pictures, powerUp-Megaman pictures, Megaman Jumping pictures, and Background picture. Most are from one sprte sheet
 -All Pictures are taken from the Internet, and edited to work. They belong to each of their respective owners and are credited by me here

 Bugs
 -There still are small bugs, many are un-noticable but there still are
 -One bug is when running, and if you want to run in the other direction, the megaman stops for a moment which he should not
 -One bug is you can stand on the platform on the edge without falling.
 -One bug is the that when jumping and falling to ground and stop, Sometimes you can't start runnin as soon as you stop

 SCREENS
 1)Title Screen
 -Gives user the option to go to either instruction screen or play the game
 -a very sober screen, which mouse click for inputs

 2)Instruction Screen
 -At the start of the program, opens with click on the instruction GUI button
 -Written instructions and rules to play the game
 -Pictures to help explain
 -Has a button where you can go play the game once you've finished reading

 3)Game Screen
 -Where the main game is played, with all the action happening
 -Uses many keys such as 'a' and 'd' and many more
 -When the guy dies (runs out of health), the screen goes to game over screen

 4)Game Over Screen (when the Megaman collides with a "bad" object which is either zombie or rolling object)
 -Many text that show the score of the game, and when the new game is going to start
 -Also has a drawn text that says "Game Over"

 Program Breakdown
 -User should start from the title screen, then go to either instructions or to play the game
 -Once user plays the game, and dies (ends the game), it loops it back so it plays again
 -Meanwhile, everytime the Megaman collides with rolling object, and gets his health to 0, the Game Over Screen Should appear, which tells the score
 */

//--------Import Statements-------//
import java.applet.Applet;
import java.awt.event.KeyListener;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;
//------End of Import Statements-------//



//-----------------------------------------------Start of Class & Program-------------------------------------------------//
public class Zombie_Apocalypse extends Applet implements KeyListener, Runnable, ActionListener //so here on the top, we extend the applet class, so we have the methods, and implememt runnable and so our other stuff work (keyboard and stuff)
{
    //---------------Constants------------------//
    private static final int APPLET_WIDTH = 600; //a constant for the width of the applet
    private static final int APPLET_HEIGHT = 250; //a constant for the height of the applet
    private static final int MEGAMAN_WIDTH = 40; //a constant for the width of the megaman charector
    private static final int MEGAMAN_HEIGHT = 50; //a constant for the height of the  megaman charector
    private static final int ZOMBIES_WIDTH = 30; //a constant for the width of the Zombies
    private static final int ZOMBIES_HEIGHT = 60; //a constant for the height of the Zombies
    private static final int ROCK_WIDTH = 26; //a constant for the width of the rocks that roll
    private static final int PLATFORM_WIDTH = 100; //a constant for the width of the platform on which the megaman can stand on and jump on
    private static final int PLATFORM_HEIGHT = 10; //a constant for the height of the platform on which the megaman can stand on and jump on
    private static final int MEGAMAN_STRENGTH = 8; //a constant for the health bar effect from all the zombies and rolling stuff. IF this is more, than megaman becomes tough to die
    private static final int NUM_ROCKS = 5; //a constant for the number of rocks rolling on the floor there are. 5 is the perfect amount
    private static final int MANA_RECHARGE_SPEED = 1; //a constant for the width of the applet

    //-------------End of Constants------------------//

    //-----------All Variables Declarations----------//

    /*
     Some things to know:
	-Sometimes when creating evil objects, I will use arrays, and that is for my ease of use, and so that i have a lot of objects made with one line
	-However, YOU MUST KNOW THIS: all arrays, of that specific type (so zombies, or rocks etc) are perfectly aligned to each other.
	-ex: zombiex[2] is the xposition and the death boolean for that is zombiedied [2] <--- for that 2nd zombie
     */
    Random random = new Random (); //so this is declaring a random object, which can be used to call random numbers

    //------------Basic Movement Declaration-----------//
    /*
    So here are the basic variable declaration. Basic to move the guy around and make him jump etc
    */

    Image Run[] = new Image [15]; //this is an image array just like other types of array, this also needs how big the array will be, since we have 15 pictures of megaman running right, thus the array is limited to that
    Image ORun[] = new Image [15]; //this is an image array just like other types of array. same thing as above except the pictures are of the guy running left
    Image Jumping[] = new Image [8]; //this is an image array just like other types of array. same thing as above except pictures are the megaman jumping right and only 8 pictures
    Image OJumping[] = new Image [8]; //this is an image array just like other types of array. same thing as above except pictures are the megaman jumping left and only 8 pictures

    int countersCounter1 = 0; //this is a genral counter's counter and this is basically used to delay the counter speed. You will see specfic ones, created for each animation but these one's are specific and used throughout the code
    int countersCounter2 = 0;
    int counter = 1; //here is a genral coutner, and this is used for genral code

    int xpos = 270; //this is the x-position variable of the megaman. Starts from the middle of the screen
    int ypos = 170; //this is the y-position variable of the megaman. Starts from the ground on the screen

    int jumpingCounter = 1; // a counter for the jumping and you will see how these counters are used to call specific pictures from their arrays

    int floorx = -500; //x-position of the floor, and this is needed since we need to move around the background a little. It starts from
    int gravity = 0; //gravity variable for jumping
    int MJumpY = 10; //this is how big the megaman jumps, if this is bigger than the guy jumps higher
    int groundY = 170; //position of the ground's y position

    Image platform;

    /*
    So here are booleans for what's happening in the game. if the guy is not moving (standing still) then standstill will be true.
    if runnning right then that will be true etc
    */
    boolean runningRight = false;
    boolean runningLeft = false;
    boolean isJumping = false;


    boolean Standstill = true;
    boolean MovingRight = true; //this is what the user just did so if he RECENTLY  ran right then this is true
    boolean MovingLeft = false; //same thing as above except for the left

    boolean StandingPlatform = false; //if the guy's standing on platform or not


    //below are some images which are self descriptive
    Image Backgroundimg;
    Image StandStill;
    Image OStandStill; //opposite side, stand still ( facing left)



    int platformX = random.nextInt (500); //so since we want the platform to start from random places when we start prgrogam, we change it's xposition variable and make it random up until 500, so it doesn't go out of screen
    int platformY = 180; //this has to be 180, since my guy's jump can reach uptill 180 thus this is 180

    //--------End of Basic Movement Declaration-----------//


    //-----------Evil Rock Declarations-----------//
    Image evilRock[] = new Image [4]; // so we again have sprites  [4] for the rolling rock
    int RockX[] = new int [NUM_ROCKS]; //the xpoints for all the rocks, this is also in an array since it is much faster and it can produce whatever number (NUM_ROCKS) of objects we want
    int rockSpeed[] = new int [NUM_ROCKS]; //the speed of that SPECific ROCK
    int RockY = 190;
    int rockCounter = 1; //rock counter is needed for the images so we can use all 3 to show an animation
    int RcountersCounter = 0; // counter's counter is actually for delaying the counter above so we can actually slow down the animation so we actually see it properly

    //-------End of Evil Rock Declarations--------//

    //-------Shooting Animation Declarations--------//
    Image RBullet; //picture of bullet facing right side
    Image LBullet; //facing left side

    Image Oshooting[] = new Image [8]; //again an array capturing left and right (below) shooting sprites
    Image Rshooting[] = new Image [8];

    int ShootingCounter = 1; //and these are the counter and the counter's counter for it
    int ShotingCountersCounter = 0;

    boolean isShooting = false; //here are some booleans for the shooting animations, obvious from their name what they do
    boolean hasJustFinishedShooting = false;

    //now this is a little complicated to show. In the game you see that there are unlimited bullets, however there not.
    //There are only 100000 bullets, since the size of the array goes up to that shown below (well the user will never need that many bullets)
    //so below are some stuff (obvious from their name) what they do, and Remember, every  specific array is aligned to the other specific array
    boolean shotLeft[] = new boolean [1000000]; //gets the direction of shot
    int bulletX[] = new int [1000000];
    int bulletY[] = new int [1000000];
    int bulletWidth = 26;
    int bulletHeight = 15;

    int bulletCounter = 0;
    //-------End of Shooting Declarations--------//


    //--------PowerUp Stuff---------//
    Image powerUp[] = new Image [9]; //again array capturing powering up images of megama
    boolean userPowerUps = false; //if the user presses 's' to powerup, this becomes true and this means he's powering up
    int powerUpCounter = 1; //counters for the animation
    int powerUpCounterCounter = 0;
    boolean inPowerUpMode = false; //this is for when the guy is in electricity mode, where he's undamagable
    int powerUpModeCounter = 0; //and here's a counter for the electricity mode picture (going to be saved into the same array above)
    int mana = 80; //mana is like magic power that is going to be a requirement for powering up
    int manaCounter = 0;
    //-----End of PowerUp Stuff-----//



    //------------Zombie Variables & Stuff-----------//

    Image zombies[] = new Image [6]; //again array capturing pictures of zombies
    Image Ozombies[] = new Image [6];

    Image zombieDeath[] = new Image [8]; //this is for the dieing animation of the zombies

    int numZombies = 1; //this is going to be changed below, so don't worry, but this variable is for the number of zombies there are on a specific level
    //just like for the bullets, there are not unlimited zombies, only 10000, however with the difficulty of the game set, the user will never use all zombies
    int zombieCounter[] = new int [10000];
    int zombieCountersCounter[] = new int [10000];
    int zombiex[] = new int [10000]; //x-position of each zombie, set by array (described above)
    boolean zombieDied[] = new boolean [10000]; //gets if zombie died or not
    int zombieDeathCounter[] = new int [10000];
    int zombieDeathCountersCounter[] = new int [10000];
    int zombieSpeed[] = new int [10000]; //speed of zombie,

    Image bloodEffect[] = new Image [3]; // when a zombie hits megaman, or a rolling rock hits megaman, there is a quick blood animation shown, and this is for that. Captures pictures of the blood animation
    int bloodCounter = 1;
    int bloodCountersCounter = 0;

    int healthBarCounter = 0; //this is for the health bar on the top right, a counter's counter for that
    int healthBar = 100; //and this is basically it. this is going to be decreased when megaman gets hurt, and this is going to decrease health
    //------------Zombie Variables & Stuff-----------//


    //------------Level's Decleration & Stuff------------//
    boolean level1 = true; //boolean for which level is on, we start with level 1 thus level 1 is true, and others are false
    boolean level2 = false;
    boolean level3 = false;

    Image nextLevelArrow; //this is the image you see when you just did a level, and this image will be shown on the right saying that you should move to the right, until the new background completly emerges
    boolean level1LetUserMove = false; //these are booleans for that walking between the levels, once your done one level
    boolean level2LetUserMove = false;
    boolean level3LetUserMove = false;


    int score = 0; // a score counter for keeping score
    //--------End of Level's Decleration & Stuff----------//


    //-----------Screen Variables & Stuff---------//
    int megamanCounterTitle = 1; //this counter is for the animation you see when you start the game, it is used for both zombie and the megaman

    boolean gameOver = false; //a boolean that gets if the game over is true or not, when the megaman dies

    boolean gameOverScreen = false; //booleans that get which screen is true (obvious description from name)
    boolean titleScreen = true; //since we want to start from title screen, we make this true and very other false
    boolean instructionScreen = false;

    int replayTimer = 6; //this is used for restarting the game, in 6 seconds. this timer is delayed by the timer below
    int replayTimerCounter = 0;


    Button playGame; //since we have two buttons we declare them here. there shown on title screen
    Button showInstructions;

    Image titleScreenBackground; //these images are basically for the background on each screen. Obvous from there name, you can tell where there used
    Image zombieBackground;
    Image instructionBackground;

    Image gameTitle; //and this is the tittle written on top you see in the title page. this is much cooler than writing with "draw font"

    //-----------Screen Variables & Stuff---------//




    //--------End of All Variables------//

    public void init ()
    {
	//------------------Getting single images------------------//
	/*
	 * Here we just get images for all the image variables using this simple one line code
	 */
	titleScreenBackground = (getImage (getDocumentBase (), "zombiegameoverback7.jpg"));
	gameTitle = (getImage (getDocumentBase (), "gameTitle1.gif"));

	StandStill = (getImage (getDocumentBase (), "StandStill.png"));
	OStandStill = (getImage (getDocumentBase (), "OStandStill.png"));

	Backgroundimg = (getImage (getDocumentBase (), "background4.gif"));
	RBullet = (getImage (getDocumentBase (), "RBullet.gif"));
	LBullet = (getImage (getDocumentBase (), "LBullet.gif"));

	platform = (getImage (getDocumentBase (), "platform.jpg"));

	nextLevelArrow = (getImage (getDocumentBase (), "levelArrow.gif"));

	zombieBackground = (getImage (getDocumentBase (), "zombiebackground.jpg"));
	instructionBackground = (getImage (getDocumentBase (), "instructionBackground1.jpg"));

	//--------------End of Getting single images------------------//

	//-------------Getting Images for all the array & other Stuff-------------//
	/*
	 * So below you will notice many for loops being used, and that is because there much faster and easier to declare arrays with
	 * Also, you will see how images are captured into an array by using "i" and I do that by converting the integer into a string and then since all my sprite pictures are named , numbered and have the extensions
	 * Thus, the for loop goes up to how much the array size was
	 */
	for (int i = 1 ; i < 15 ; i++)
	{
	    //the run images
	    String number = Integer.toString (i);
	    Run [i] = (getImage (getDocumentBase (), "Run" + number + ".gif"));
	    ORun [i] = (getImage (getDocumentBase (), "ORun" + number + ".gif"));
	}

	for (int i = 1 ; i < 8 ; i++)
	{
	    //the jumping images
	    String number = Integer.toString (i);
	    Jumping [i] = (getImage (getDocumentBase (), "Jumping" + number + ".gif"));
	    OJumping [i] = (getImage (getDocumentBase (), "OJumping" + number + ".gif"));

	    //the shooting images
	    Oshooting [i] = (getImage (getDocumentBase (), "Shoot" + number + ".gif"));
	    Rshooting [i] = (getImage (getDocumentBase (), "RShoot" + number + ".gif"));

	    //the zombie dieing images
	    zombieDeath [i] = (getImage (getDocumentBase (), "zombieDie" + number + ".gif"));

	}

	for (int p = 1 ; p < 6 ; p++)
	{ //the zombie walking images and [directions]
	    String number = Integer.toString (p);
	    zombies [p] = (getImage (getDocumentBase (), "zombie" + number + ".gif"));
	    Ozombies [p] = (getImage (getDocumentBase (), "Ozombie" + number + ".gif"));

	}

	for (int i = 1 ; i < 4 ; i++)
	{
	    //the rolling rock images
	    String number = Integer.toString (i);
	    evilRock [i] = (getImage (getDocumentBase (), "evilRock" + number + ".gif"));
	}

	for (int p = 1 ; p < 9 ; p++)
	{
	    //this is for the powering up images
	    String number = Integer.toString (p);
	    powerUp [p] = (getImage (getDocumentBase (), "powerup" + number + ".gif"));
	}


	for (int i = 0 ; i < NUM_ROCKS ; i++)
	{
	    //here we are actually declaring the x-position of rocks and their rock speed
	    RockX [i] = 700 + random.nextInt (250); //we want to start off screen, and have random intervals between each rock, thus we have to use random class
	    rockSpeed [i] = 1 + random.nextInt (3); //this goes here too, but this is much little since it's the speed. it's 1 + random since we don't want it to have a speed of 0
	}

	for (int b = 0 ; b < 10000 ; b++)
	{
	    //declares all the bullets y-position and x-position to be where the megaman is starting from
	    bulletX [b] = xpos;
	    bulletY [b] = ypos;
	    shotLeft [b] = false; //since no bullets have been shot, all are false
	}


	for (int z = 0 ; z < 10000 ; z++)
	{
	    //for the zombie guys stuff
	    zombieCounter [z] = 1 + random.nextInt (4); //this is because we want to start differnt animations in differeint zombies so it doesn't just look like there doing one animation all at once
	    zombieCountersCounter [z] = 0;


	    zombieDied [z] = false; //make all false, since no zombies have died

	    zombieDeathCounter [z] = 1; //this is for the animation of the dieing (NOT COUNITNG HOW MANY DIED)
	    zombieDeathCountersCounter [z] = 0;

	    zombieSpeed [z] = 1 + random.nextInt (2); //declares the zombie speed like how we did for rocks

	    zombiex [z] = random.nextInt (5); //this is for which side the zombies should come from. this is like a coin flip to detemine how which zombies come from right side and which from left
	    if (zombiex [z] <= 3) //so if the coin flip turned out to give less that 3 number, than they start from left off of the screen, (with random spacing so they don't go side by side)
	    {
		zombiex [z] = -200 - random.nextInt (400);
	    }
	    else
	    {
		zombiex [z] = 600 + random.nextInt (400); //other wise from right side
	    }
	}

	bloodEffect [1] = (getImage (getDocumentBase (), "blood" + 1 + ".gif")); //this is not in a for loop because its just 2 lines, so the for loop would be longer..just uses same concept and declares images of blood animation
	bloodEffect [2] = (getImage (getDocumentBase (), "blood" + 2 + ".gif"));
	//-------End of Getting Images for all the array & other Stuff-------------//


	setLayout (null); //sets layout to null, allowing us to specifically set bounds on an object to place it

	//-----Buttons in Title Screen-----//
	playGame = new Button ("PLAY GAME"); //puts a label on it, play game
	playGame.setFont (new Font ("times", Font.BOLD, 30)); //sets the writing font on the button
	playGame.setForeground (Color.RED); //colors the writing on top of button
	playGame.setBackground (Color.GREEN); // sets background of the button
	playGame.setBounds (123, 430, 350, 60); //places it accordingly
	add (playGame); //adds it to the screen
	playGame.addActionListener (this); //adds clickablility to it

	showInstructions = new Button ("INSTRUCTIONS"); //puts a label on it
	showInstructions.setFont (new Font ("times", Font.BOLD, 30));
	showInstructions.setForeground (Color.RED); //colors the writing on top of button
	showInstructions.setBackground (Color.GREEN); // sets background of the button
	showInstructions.setBounds (123, 540, 350, 60); //places it accordingly
	add (showInstructions); //adds it to the screen
	showInstructions.addActionListener (this);  //adds clickablility to it
	//-----End of Buttons in Title Screen-----//

	addKeyListener (this); //last but not least, we add key listner so we can  use the keyboard and call methods below to use them in our game
    }


    public void start ()  //a method needed to start the applet so we can play
    {
	// define a new thread
	Thread th = new Thread (this);
	// start this thread
	th.start ();
    }


    public void stop ()  //a method needed for when the applet stop what happens
    {
	//nothing right now
    }


    public void destroy ()  //this is for the resources, so we don't take up all computer's resources
    {
	//nothing right now
    }



    public void keyPressed (KeyEvent e)
    {
	//every time a key is pressed, this will be called
	// TODO Auto-generated method stub
	int c = e.getKeyCode (); //so basically c is our keyboard key

	//and so below you will see 'ifs' that say if "c" equals to "VK_SOMETHING" and that something will be the key we're reffering to

	if (c == KeyEvent.VK_D && isJumping == false && isShooting == false && gameOverScreen == false && userPowerUps == false && inPowerUpMode == false)
	{

	    // So this "if" is for running right, and for that, the above need to be false. For ex, if the user want's to run right he cannot be powering up, jumping, in the gameover screen, etc

	    //so if he is allowed to do the above, then the following will be done
	    //and below is obiovious to understand becuause of their specific names
	    Standstill = false;
	    runningRight = true;
	    runningLeft = false;
	    isJumping = false;
	    MovingRight = true;
	    MovingLeft = false;
	    hasJustFinishedShooting = false;
	    e.consume (); //this is something we need for the keyboard to work
	}

	if (c == KeyEvent.VK_A && isJumping == false && isShooting == false && gameOverScreen == false && userPowerUps == false && inPowerUpMode == false)
	{

	    // So this "if" is for running left, and for that, the above need to be false. For ex, if the user want's to run left he cannot be powering up, jumping, in the gameover screen, etc

	    //so if he is allowed to do the above, then the following will be done
	    //and below is obiovious to understand becuause of their specific names
	    Standstill = false;
	    runningRight = false;
	    runningLeft = true;
	    isJumping = false;
	    MovingRight = false;
	    MovingLeft = true;
	    hasJustFinishedShooting = false;

	    e.consume (); //this is something we need for the keyboard to work
	}

	if (c == KeyEvent.VK_SPACE && isShooting == false && gameOverScreen == false && userPowerUps == false && inPowerUpMode == false)
	{
	    // So this "if" is for jumping , and for that, the above need to be false. For ex, if the user want's to jumping he cannot be powering up, shooting, in the gameover screen, etc

	    //so if he is allowed to do the above, then the following will be done
	    //and below is obiovious to understand becuause of their specific names
	    Standstill = false;
	    runningRight = false;
	    runningLeft = false;
	    isJumping = true;
	    hasJustFinishedShooting = false;
	    e.consume (); //this is something we need for the keyboard to work
	}
	if (c == KeyEvent.VK_Z && isJumping == false && gameOverScreen == false && userPowerUps == false && inPowerUpMode == false)
	{
	    // So this "if" is for shooting , and for that, the above need to be false. For ex, if the user want's to shooting he cannot be powering up, jumping, or be in the gameover screen, etc

	    //so if he is allowed to do the above, then the following will be done
	    //and below is obiovious to understand becuause of their specific names
	    Standstill = false;
	    runningRight = false;
	    runningLeft = false;
	    isJumping = false;
	    hasJustFinishedShooting = false;
	    isShooting = true;
	    e.consume (); //this is something we need for the keyboard to work
	}

	if (c == KeyEvent.VK_S && isJumping == false && gameOverScreen == false && mana > 25)
	{
	    // So this "if" is for powering up , and for that, first of all the guy cannot be jumping or be in the game over screen. AND for powering up the user will need 25 mana, which is approcimetly 1/4 of the mana bar

	    //so if he is allowed to do the above, then the following will be done
	    //and below is obiovious to understand becuause of their specific names
	    userPowerUps = true;
	    Standstill = false;
	    runningRight = false;
	    runningLeft = false;
	    isJumping = false;
	    MovingRight = false;
	    MovingLeft = false;

	}


    }


    public void keyReleased (KeyEvent arg0)
    {
	//so if the some key is released this method is called
	// TODO Auto-generated method stub
	if (isJumping == false) //so if any key is released, and if the guy was not jumping
	{
	    Standstill = true; //megaman stands still
	}
	counter = 1; //and this counter [the GENRAL counter] becomes 1

    }


    public void keyTyped (KeyEvent arg0)
    {
	//so this is for typing keys, which is just a form of pressed, but quicker
    }



    /** Update - Method, implements double buffering */
    public void update (Graphics g)
    {


	// declare two  variables that will help do the double buffering
	Image dbImage; //the offscreen image
	Graphics dbg=null; //the graphics of the offscreen image

	//first, we create the offscreen buffer and attach the graphics of the offscren buuffer to offscreen image
	dbImage = createImage (this.getSize ().width, this.getSize ().height);
	dbg = dbImage.getGraphics ();

	// clear screen (by filling it with background color. Prepare to do redraw
	dbg.setColor (getBackground ());
	dbg.fillRect (0, 0, this.getSize ().width, this.getSize ().height);
	dbg.setColor (getForeground ());
	
	//do normal redraw
	paint (dbg);
	// transfer the offscreen to our window
	g.drawImage (dbImage, 0, 0, this);
    }


    public void run ()
    {
	// lower ThreadPriority
	Thread.currentThread ().setPriority (Thread.MIN_PRIORITY);

	while (titleScreen == true) //this will loop until the user presses a button on a tittle screen
	{
	    countersCounter2++; //so here were going to animate the megaman and zombies on the title screen. this is going to delay the counter below
	    if (countersCounter2 == 6)
	    {
		counter++; //this counter will be used on zombie
		megamanCounterTitle++; //this one on the megaman
		countersCounter2 = 0;
	    }
	    if (counter == 6) //since we have 6 pictures only, we can only go up to 6
	    {
		counter = 1; //if more, than 1
	    }
	    if (megamanCounterTitle == 15) //since only
	    {
		megamanCounterTitle = 1;
	    }

	    callDelayAndRepaint ();
	}
	counter = 1;
	while (instructionScreen == true) //so if the instruction screen buton was placed, then this keeps happening until the user presses the playGame button which makes this false
	{
	    playGame.setBounds (143, 220, 300, 50); //places it accordingly on the screen

	}

	while (true) //so this means that once in the run method we get here to this loop, the applet will never end, and that's what we want
	{
	    while (gameOver == false) //basically every time game has ended, this loop will end, but then start again since it is in another while (true) loop
	    {
		resize (APPLET_WIDTH, APPLET_HEIGHT); //changes the dimensions of the applet to  APPLET_WIDTH and APPLET_HEIGHT

		/*
		 * So below you will see many if statements ,that state what level the person is in right now.
		 * At the start of each level, you will see many things happening, all zombies being revived again, more zombies are produced
		 * As well as the location of the zombie
		 * Then, after level 1, you should see a while loop that makes the user go to the next level.
		 * This keeps on happening each time this big loop restarts which happens when user dies

		 So, we'll divide the commenting into 2 stages:

		*******************STAGE*1*********************
		 1) Setting Zombies
			-In this stage, first the difficulty will be set. This is done by setting the number of zombies in that level, BEFORE the while loop starts of that level
			-Just like in the init method, all zombies will be made "alive"  by making zombieDied for all zombies false
			-The counter will be randomized, and the Death counter will return to 1, (so animation starts from beggining)
			-Zombies x-position will be set, and they will be done by "a coin flip" just like in the init method, to determine from which side the zombie come from
			-After this, the while loop for that level should be true, and should be in that loop
			-The level loop is exited by killing all zombies on that level
			-You will also see two methods being called, runSimpleGame  AND callDelayAndRepaint  and these methods are for doing the basic stuff.
			-callDelayAndRepaint is just basically for making Thread.sleep (delaying) and Calling repaint so each time the screen can be painted over
			-runSimpleGame is for doing the game. And this is where all the collisions happen, as well as the zombie coming right. Where all the animations happen too

		  *******************STAGE*2*********************
		 2) Let the user move stage
			-In this stage, when the arrow comes, the user must go to the right until a new background completly covers the screen
			-This is where no zombies should come, because the zombie is just moving to a new level, thus no zombie is coming
			-Once this loop is done, the platform should be set again, by making the x-position of it random (but in the screen)
			-You will also see two methods being called, runSimpleGame  AND callDelayAndRepaint  and these methods are for doing the basic stuff.
			-callDelayAndRepaint is just basically for making Thread.sleep (delaying) and Calling repaint so each time the screen can be painted over
			-runSimpleGame is for doing the game. And this is where all the collisions happen, as well as the zombie coming right. Where all the animations happen too

		 */

		if (level1 == true)
		{
		    /********************STAGE*1***********************/
		    numZombies = setDifficulty (10);
		    for (int z = 0 ; z < numZombies ; z++)
		    {
			zombieCounter [z] = 1 + random.nextInt (4);
			zombieCountersCounter [z] = 0;


			zombieDied [z] = false;

			zombieDeathCounter [z] = 1;
			zombieDeathCountersCounter [z] = 0;
			zombiex [z] = random.nextInt (5);
			zombieSpeed [z] = 1 + random.nextInt (2);
			if (zombiex [z] <= 3)
			{
			    zombiex [z] = -200 - random.nextInt (600);
			}
			else
			{
			    zombiex [z] = 800 + random.nextInt (600);
			}
		    }
		    while (level1 == true)
		    {
			runSimpleGame ();
			callDelayAndRepaint ();

		    }
		    /******************End*Of*STAGE*1***********************/

		}
		else if (level2 == true)
		{

		    /********************STAGE*2***********************/
		    level2LetUserMove = true;
		    while (level2LetUserMove == true)
		    {
			runSimpleGame ();
			callDelayAndRepaint ();
			if (floorx < -1300) //so this is how we make the background completly come on the back on the screen
			{
			    level2LetUserMove = false;
			}

		    }
		    platformX = 50 + random.nextInt (500);
		    /******************End*Of*STAGE*2***********************/



		    /********************STAGE*1***********************/
		    numZombies = setDifficulty (15);
		    for (int z = 0 ; z < numZombies ; z++)
		    {
			zombieDied [z] = false;
			zombieCounter [z] = 1 + random.nextInt (4);
			zombieCountersCounter [z] = 0;


			zombieDeathCounter [z] = 1;
			zombieDeathCountersCounter [z] = 0;
			zombiex [z] = random.nextInt (5);
			zombieSpeed [z] = 1 + random.nextInt (2);
			if (zombiex [z] <= 3)
			{
			    zombiex [z] = -200 - random.nextInt (600);
			}
			else
			{
			    zombiex [z] = 800 + random.nextInt (600);
			}
		    }
		    while (level2 == true)
		    {
			runSimpleGame ();
			callDelayAndRepaint ();

		    }
		    /******************End*Of*STAGE*1***********************/

		}
		else if (level3 == true)
		{
		    /********************STAGE*2***********************/
		    level3LetUserMove = true;
		    while (level3LetUserMove == true)
		    {
			runSimpleGame ();
			callDelayAndRepaint ();
			if (floorx < -1998)
			{
			    level3LetUserMove = false;
			}

		    }
		    platformX = 50 + random.nextInt (400);
		    /******************End*Of*STAGE*2***********************/


		    /********************STAGE*1***********************/
		    numZombies = setDifficulty (9999); //set difficulty is 9999, meaning 9999 zombies.  killing 9999 zomies is immposible, because the person would die by then
		    for (int z = 0 ; z < numZombies ; z++)
		    {
			zombieDied [z] = false;
			zombieCounter [z] = 1 + random.nextInt (4);
			zombieCountersCounter [z] = 0;


			zombieDeathCounter [z] = 1;
			zombieDeathCountersCounter [z] = 0;
			zombiex [z] = random.nextInt (5);
			zombieSpeed [z] = 1 + random.nextInt (2);
			if (zombiex [z] <= 3)
			{
			    zombiex [z] = -400 - random.nextInt (10000) - random.nextInt (180000); //since we have 9999 zombies, we must have a greater domain for those zombies, thus the values of the randoms are increased
			}
			else
			{
			    zombiex [z] = 1000 + random.nextInt (10000) + random.nextInt (180000);
			}
		    }

		    for (int z = 0 ; z < 10 ; z++)
		    { //this for loop is actually here, because we do not wan't to bore the player. And that is because sometimes, many zombies will be spawned from so off the screen, that it takes them a while to get here
			/*
			 *There fore, we take the first 10 zombie x-postion, and manually set them close to the screen, so we know that atleast 10 zombies will come as soon as the player reaches level 3
			 *So below, be do the same things, as we did above, except since we declared every property of the zombie above we don't need to do all those things again. Ex, zombie died = false
			*Below,youcanseethattherandominteger is small, and therefore 10 zombies will be close to the screen
			*/
			zombiex [z] = random.nextInt (5);
			if (zombiex [z] <= 3)
			{
			    zombiex [z] = -400 - random.nextInt (600);
			}
			else
			{
			    zombiex [z] = 1000 + random.nextInt (600);
			}
		    }



		    while (level3 == true)
		    {
			runSimpleGame ();
			callDelayAndRepaint ();


		    }
		    /******************End*Of*STAGE*1***********************/

		}
	    }
	    while (gameOver == true) //so this while loop is for the screen of the death when the person dies
	    {
		gameOverScreen = true; //so this becomes true, and this is going to be needed fo rwhen we paint the screen again
		replayTimerCounter++; //so just like all the other animations, and all other counter's counter, this one is going to delay our real time counter (making it look like seconds delay)
		if (replayTimerCounter == 40) //when it becomes 40, then real timer decreases
		{
		    replayTimer--;
		    replayTimerCounter = 0; //and this becomes 0 again for another go
		}
		if (replayTimer < 1) //so if the timer runs out ( becomes 0), the game is played again
		{
		    replayTimer = 6; //becomes 6 again for the next time the person dies


		    //------Making Variables default------//
		    /*
		     * All the variable below, from the top become what they were when the game was played for the first time
		     * This is needed, because we are restarting the game, and that's like restarting the program thus, we have to return each variable to default
		     */
		    gameOver = false; //this is for exiting this loop, and going back to the first while (true) loop
		    gameOverScreen = false;
		    level1LetUserMove = false;
		    level2LetUserMove = false;
		    level3LetUserMove = false;
		    level1 = true; //level 1 is true, since we want to start from level 1 again
		    level2 = false;
		    level3 = false;
		    healthBar = 100; //health bar returns to default again, so he has the noremal health again
		    score = 0;
		    floorx = -500; //this is -500, because this is the background, so this makes sure that the background is the level 1 backgrond right now
		    userPowerUps = false;
		    powerUpCounter = 1;
		    powerUpCounterCounter = 0;
		    inPowerUpMode = false;
		    powerUpModeCounter = 0;
		    mana = 80;
		    manaCounter = 0;
		    for (int i = 0 ; i < NUM_ROCKS ; i++) //just like in the init method, we make all the zombies & rocks default to what they were before (just like in the init method)
		    {
			RockX [i] = 600 + random.nextInt (2500);
			rockSpeed [i] = 1 + random.nextInt (3);
		    }
		    for (int z = 0 ; z < 10000 ; z++)
		    {
			zombieCounter [z] = 1 + random.nextInt (4);
			zombieCountersCounter [z] = 0;


			zombieDied [z] = false;

			zombieDeathCounter [z] = 1;
			zombieDeathCountersCounter [z] = 0;
			zombiex [z] = random.nextInt (5);
			zombieSpeed [z] = 1 + random.nextInt (2);
			if (zombiex [z] <= 3)
			{
			    zombiex [z] = -200 - random.nextInt (400);
			}
			else
			{
			    zombiex [z] = 600 + random.nextInt (400);
			}
		    }
		}
		//------Making Variables default------//

		callDelayAndRepaint (); //delays and repaints screen, and exits this loop because we made game over false. So now we return to the TOP TOP loop, the while (true) loop

	    }

	    // set ThreadPriority to maximum value
	    Thread.currentThread ().setPriority (Thread.MAX_PRIORITY);

	}
    }



    public void paint (Graphics g)  //the paint method where things are drawn
    { //so in this method, least amount of logic is done as much as possible,

	if (titleScreen == true) //so if we're on the title screen, we paint this
	{
	    this.setSize (600, 700); //set dimensions of the screen
	    g.drawImage (titleScreenBackground, 0, 0, 600, 700, this); //draw the background picture
	    g.drawImage (gameTitle, 20, -20, 550, 190, this); //draw the title picture on top
	    g.setFont (new Font ("Verdana", Font.ITALIC, 25));  //A much faster and less writing way to set font. makes it bold, and makes the size 100 so it looks bigger and better
	    g.setColor (Color.WHITE);
	    g.drawString ("By: Zunair Syed", 370, 670); //write my Name

	    g.drawImage (Ozombies [counter], 120, 190, 75, 200, this); //show animation, and this is going to do animation since different picture of the zombie running is in this array, and we increases counter of this array and call pictures
	    g.drawImage (Run [megamanCounterTitle], 320, 210, 120, 170, this); //same as above, but different counter for this, since we have different amount of pictures. Also, the counter is increased in the RUN method
	}
	else if (instructionScreen == true) //else if we are on the instruction screen
	{
	    this.setSize (600, 700); //we set dimensions of the window
	    g.drawImage (instructionBackground, 0, 0, this);  //draw the background, and this image has all the written instructions on it so we don't need to write it again

	}
	else if (gameOverScreen == true) //this is the screen when the person dies
	{
	    this.setSize (600, 400); //sets specific dimensions
	    g.drawImage (zombieBackground, 0, 0, 600, 400, this); //draws the cool zombie background
	    g.setFont (new Font ("Verdana", Font.BOLD, 60));  //A much faster and less writing way to set font. makes it bold,
	    g.setColor ((Color.RED));
	    g.drawString ("GAMEOVER", 115, 130); //writes gameover in red

	    g.setFont (new Font ("Verdana", Font.BOLD, 30));  //A much faster and less writing way to set font. makes it bold,
	    g.setColor ((Color.BLACK));
	    g.drawString ("Your Score : " + score, 180, 210); //writes score and the number score in black

	    g.setFont (new Font ("Verdana", Font.ITALIC, 15));  //A much faster and less writing way to set font. makes it bold, and makes the size 100 so it looks bigger and better
	    g.setColor ((Color.WHITE));
	    g.drawString ("The Game Will Restart In " + replayTimer + " Seconds", 300, 380); //and at the bottom right corner, you can see that there is a timer. that's the replay timer, which decreases in the run method, and that's what we draw here. Thus, if that decreases, the number on the screen will change too
	}
	else
	{
	    this.setSize (APPLET_WIDTH, APPLET_HEIGHT); //sets dimensions of the window to the specific game requirements
	    g.drawImage (Backgroundimg, floorx, 0, this); //draws the background picture, which has all 3 background in it. Its a really long picture
	    g.drawImage (platform, platformX, platformY, PLATFORM_WIDTH, PLATFORM_HEIGHT, this);


	    //-------------level's Stuff------------//
	    g.setFont (new Font ("Verdana", Font.BOLD, 30));  //A much faster and less writing way to set font. makes it bold,

	    //so these if statements is for drawing the level name on the top. If a level is true, than that level will be drawn using font, colored yellow
	    if (level1 == true)
	    {
		g.setColor (Color.YELLOW);
		g.drawString ("LEVEL 1", 192, 45);
	    }
	    else if (level2 == true)
	    {
		g.setColor (Color.YELLOW);
		g.drawString ("LEVEL 2", 192, 45);
	    }
	    else if (level3 == true)
	    {
		g.setColor (Color.YELLOW);
		g.drawString ("LEVEL 3", 192, 45);
	    }

	    //so here we'll do some logic, because in the run method, this was messing up for some reason. Score goes up when a zombie is killed, and there 10 zombies on first level, thus when all 10 are killed new level arrives
	    if (score == 10)
	    {

		level1 = false; //we make level 2 by making this false and the below true
		level2 = true;

	    }
	    else if (score == 25) //otherwise, it's level 3 if all zombies on 2nd level are killed
	    {

		level1 = false;
		level2 = false;
		level3 = true;
	    }

	    if (level2LetUserMove == true || level3LetUserMove == true) //so this is for that arrow thingy, so if we're changing levels, the arrow will be drawn
	    {
		g.drawImage (nextLevelArrow, 470, 100, 130, 70, this);

	    }
	    //------Zombie Stuff------//
	    for (int i = 0 ; i < numZombies ; i++)
	    {
		if (xpos + MEGAMAN_WIDTH > zombiex [i] && xpos < zombiex [i] + ZOMBIES_WIDTH && (ypos >= 169 || ypos == 130) && zombieDied [i] == false) //so this if statement checks collision between megaman and all zombies
		{
		    /*
		     *this is done using math, to create a sort of a hit box, that if the user collides with any zombies it would go true, and blood effect picture below will be drawn
		     *above you can see that there are some weird conditions on the y-position of the megaman, and that is because we want to have blood effects on when the person is standing on the platform
		     *However, we don't want to have blood when their in a jumping animation or in air
		     *Also, we don't want to show blood effects when the zombie has died (or is dieing)
		     */

		    g.drawImage (bloodEffect [bloodCounter], xpos, ypos, this);  //draws the blood picture animation on the postion of the megaman
		}

		if (zombieDied [i] == true)
		{
		    g.drawImage (zombieDeath [zombieDeathCounter [i]], zombiex [i], 160, this); //now here his might seem confusing but it's actually the most efficent way to do animations of zombie dieing at many times for each single zombies
		    //what we do is have an array of the counter of the death images, and thus if many there are many zombies dieing at the same time, it does the death animation at different stages, not just at one stage.
		}

		else
		{ //so if that zombie has not died, then we need to draw him
		    if (zombiex [i] >= xpos) //if he's more than megaman x-position (meaning to the right of him) then we draw zombie facing left pictures and animate that
		    {
			g.drawImage (zombies [zombieCounter [i]], zombiex [i], 160, ZOMBIES_WIDTH, ZOMBIES_HEIGHT, this); //we do the same thing we did for the dieing animation, which means bascially we have counter of array controlling image animation. Thus we don't have 1 animation happening to all zombies
		    }
		    else if (zombiex [i] <= xpos) //or else, if the zombie is to the left of megaman
		    {
			g.drawImage (Ozombies [zombieCounter [i]], zombiex [i], 160, ZOMBIES_WIDTH, ZOMBIES_HEIGHT, this); //draw his animation picture of the zombie facing Right
		    }
		}
	    }

	    //------End Zombie Stuff------//

	    //---------Evil Rock Stuff-------//
	    //so here we'll do some logic again because it's much easier to see here and doesn't effect the outcome of the counter stuff
	    RcountersCounter++; //counter's counter for the rock images stored in the array
	    if (RcountersCounter == 3)
	    {
		rockCounter++; //how we delay the animation so our eyes can see proper animation. This is the real counter by the way
		RcountersCounter = 0;
	    }
	    if (rockCounter == 3)
	    {
		rockCounter = 1; //so since rocks are not that important, and because they move so quickly and have so less sprites, we don't have to make a counter for each indiviual rock like we did for zombies
	    }
	    for (int i = 0 ; i < NUM_ROCKS ; i++) //so using this for loop, we're going to check collisions and other stuff
	    {
		if ((RockX [i] + ROCK_WIDTH) > xpos && RockX [i] < xpos + MEGAMAN_WIDTH && ypos + MEGAMAN_HEIGHT > RockY && inPowerUpMode == true) //this if is for if the rocks hit megaman BUT megaman is in powerup mode, then the rock gets "Killed" and goes out of screen to the right
		{
		    RockX [i] = 600 + random.nextInt (2500); //so we make that rock's x-position randomly to the right off screen. Then he's going to come again, because his x-position is being decreased in the run method

		    if (rockSpeed [i] < 4) //so if the rock gets "Killed", we want to increase the rocks speed, HOWEVER, if the rock has a speed of more than 4, than we don't since we don't want to make it to fast
		    {
			rockSpeed [i] = rockSpeed [i] + random.nextInt (2); //increases by adding the first speed to a random number by calling a "nextInt" from the random class
		    }
		}
		else if ((RockX [i] + ROCK_WIDTH) > xpos && RockX [i] < xpos + MEGAMAN_WIDTH && ypos + MEGAMAN_HEIGHT > RockY) //so just like with zombie collisions, this is also how we do it for rocks and megaman. And collision can't happen if he's in powerup, since if he is then, it would go to the top "if"
		{
		    bloodCountersCounter++; //so basically we're going to do the blood animation, because megaman got hurt, since a rock got collided with him
		    if (bloodCountersCounter == 14) //so just as before, we increase the counter's counter for the blood images, and once it reaches a a perfect delay time [14 in this case] then we increase  real counter
		    {
			bloodCounter++;
			bloodCountersCounter = 0; //and make this 0 ,so it goes on again
		    }
		    if (bloodCounter == 3) //since there is only 2 pictures for the blood animation, it counter for the images cannot go up to 3
		    {
			bloodCounter = 1; //thus it becomes one again
		    }
		    healthBarCounter++; //this is for decreasing the health of the megaman. Again just like all other counter's counter this is also one of them, but for the health integer
		    if (healthBarCounter == MEGAMAN_STRENGTH) //so megaman strenghth the constant, is like how strong he is, because the longer the health counter's counter takes to reach this amount,  the less health decreases
		    {
			healthBar--; //so once the counter's counter reaches the desired strength, we decrease the health
			healthBarCounter = 0; //and make out counter's counter 0 again for the loop
		    }
		    g.drawImage (bloodEffect [bloodCounter], xpos, ypos, this); //so here, we're just drawing the blood pictures stored into the bloodEffect array. And ofcourse, we call them by the blood effect counter
		}
		g.drawImage (evilRock [rockCounter], RockX [i], 190, this); //so here we're also drawing each indiviual rocks (because of their different x-position) stored into the array, which is called by i, since that's like the id number.
		//And the pictures are called by the rockCoutner

		if (RockX [i] < -400) //so here if the rocks goes off screen, and a little more than that, there is no possibilty of gliches, we make the rock come from the right again
		{
		    RockX [i] = 600 + random.nextInt (2500); //by making the rock's x-position off screen to the right, and random so all rocks don't come on one position
		    if (rockSpeed [i] < 4) //as we did for when we killed the rock by the powerup, we will do the same thing here. So look for comments there if you want
		    {
			rockSpeed [i] = rockSpeed [i] + random.nextInt (2);  //increases x-positon by adding the first speed to a random number by calling a "nextInt" from the random class
		    }
		}
	    }

	    //-----End of Evil Rock Stuff---//

	    //-----Drawing Megaman In Correct Aniamtions------//
	    /*
	     * so in here, we'll be drawing the megaman, and his animations
	     * You might notice that the counter is same for many aniamtions,that is used to call the pictures stored in various arrays
	     * That is because the number of pictures is the same, and thus that same counter can be user
	     * Therefore, the integer variable "counter" is a genral counter with many uses
	     */
	    if (runningRight == true && Standstill == false && runningLeft == false && isJumping == false)
	    {
		g.drawImage (Run [counter], xpos, ypos, this); //this is basically drawing the running animation facing right, and this can only happen if the guy is not jumping and not standing still
	    }

	    if (runningRight == false && Standstill == false && runningLeft == true && isJumping == false)
	    {
		g.drawImage (ORun [counter], xpos, ypos, this); //here is the opposite running side [left direction] and this can happen just like above. not jumping, or standing still or running left
	    }

	    if (runningRight == false && Standstill == false && runningLeft == false && isJumping == true) //this is for jumping, if the megaman has pressed the space button
	    {
		//so the below 2 if statements are actually here because we need to make the megaman jump in both direction. Not just in right or just in left
		if (MovingRight == true) //so if he was moving right, (this is made true when the guy ran to the right), this happens
		{
		    g.drawImage (Jumping [jumpingCounter], xpos, ypos, this); //the right side jumping pictures are shown, using a genral jumping counter that will also be used below for the left jumping pictures
		}
		else if (MovingLeft == true) //else if he was moving left, and just like above ade true when the guy ran to the left, this happens
		{
		    g.drawImage (OJumping [jumpingCounter], xpos, ypos, this); //we draw the megaman picture of the guy jumping to the left, with the same counter as above
		}

	    }

	    if (userPowerUps == true && isJumping == false) //if the user is powering up, NOT IN POWERUP MODE (that is electricty part)
	    {
		g.drawImage (powerUp [powerUpCounter], xpos, ypos, this); //the pictures of the guy powering up is shown through the powerupCounter. Note, the electricty part of the powering up, is stored in the same array, but will be called using a different counter
	    }

	    if (Standstill == true && userPowerUps == false && inPowerUpMode == false) //So here basically, if stand still is true, and its true when no keys are pressed, the picture of megaman standing still right side, or left side will be shown depending on the movememnt (or the direction the man was moving in)
	    {
		if (MovingRight == true) //so if he was just moving right
		{
		    g.drawImage (StandStill, xpos, ypos, this); //shows standstill  picture, facing the right side
		}
		else if (MovingLeft == true)
		{ //viceversa
		    g.drawImage (OStandStill, xpos, ypos, this); // here it shows standstill  picture, facing the left side, since the guy was moving left previously...
		}
		else //otherwise (useful in the beggining when game is initially started)
		{
		    g.drawImage (StandStill, xpos, ypos, this); //shows standstill  picture, facing the right side, initially
		}

	    }
	    //-----End of Drawing Megaman Aniamtions------//


	    ///-----------------Shooting Animation------------------------//
	    if (isShooting == true && isJumping == false) //This if statement is mostly for the animation that happens before the bullet is shot. You can see the gun flaring up and that is basically for this
	    {
		if (MovingLeft == true) //here basically defines which side the animation of shooting should be, if guy was moving left, then the image from the array is the left-image array
		{
		    g.drawImage (Oshooting [ShootingCounter], xpos, ypos, this); //draw the correct image  (using counter), from left-side image array
		}
		else
		{
		    g.drawImage (Rshooting [ShootingCounter], xpos, ypos, this); //draw the correct image  (using counter), from right-side image array
		}
	    }
	    //------------------End Of Shooting Animation-------------------------//

	    //------------------Bullets, and their movements---------------------//
	    for (int i = 0 ; i < bulletCounter ; i = i + 3)
	    { //this for loop is extremly important, because instead of moving one bullet at a time, this loop moves all the bullets that were shot. each time the shooting animation was half done, this counter increased since there were bullets shot, and this counter "bulletCounter" basically tells how many bullets were shot. The for loop counter "i" is added to itself by 3, since the counter moves by 3 as well due to the thread delay.
		if (shotLeft [i] == true) //so this array telling the direction of the bullet is propotional to other bullet arrays, and so if this tells that the direction was left, then below x-point will be decreased, since we want to move it to the left side, NOT RIGHT SIDE (because that would no make sense)
		{
		    g.drawImage (LBullet, bulletX [i] - 15, bulletY [i] + 8, this); //since the bullet was direction left, the left side bullet image is shown
		}
		else
		{ //otherwise, if moving right, it will do the opposite thing as described above
		    g.drawImage (RBullet, bulletX [i] + 25, bulletY [i] + 8, this); //the right side picture of bullet is shown

		}
	    }
	    //----------------End of Bullets and their movements-----------------//


	    //------------User Information and UI Drawings----------//

	    //The if statements below are actually going to change the color of the health bar, when the health bar decreases to a set level.
	    if (healthBar <= 30) //so if the health bar is almost out (down to 30), then the health bar color will be red
	    {
		g.setColor (Color.RED); //sets the color of the health bar to be red
	    }
	    else if (healthBar <= 60 && healthBar >= 31) //otherwise, if it's middle, where megaman has been hurt, but not too much
	    {
		g.setColor (Color.ORANGE); //the color then becomes orange
	    }
	    else
	    {
		g.setColor (Color.GREEN); //otherwise, if the health bar is perfect, or close to perfect, the color is set to green. Our healthbar integer started from 110, so basically if its more than 60, its green
	    }
	    g.fillRect (490, 10, healthBar, 30); //so this is where we'll actually draw the fill rectangle on the top right, inside the yellow rectangle we're going to make below. This will width of this will decrease when the counter of health decreases, thus it shortens the health bar if healthbar Counter decreases.

	    g.setColor (Color.RED); //sets the color to red, for the writing done just below
	    g.setFont (new Font ("Verdana", Font.PLAIN, 20)); //A much faster and less writing way to set font. makes it plain
	    g.drawString ("HEALTH", 400, 33); //draws the letters health on top right

	    g.setColor (Color.WHITE); //sets the color to white, for the writing done just below
	    g.drawString ("Score :" + score, 10, 33); //draws the letters "SCORE" and the number score beside it. When score variable increases, the written integer on top left increases too

	    g.setColor (Color.BLUE); //sets the color to blue, for the writing done just below
	    g.setFont (new Font ("Verdana", Font.PLAIN, 20)); //A much faster and less writing way to set font. makes it plain
	    g.drawString ("Mana", 420, 75); //writes the writing mana. Mana is the magical power for the powering up part

	    g.setColor (Color.YELLOW); //sets the color to yellow for the drawings below
	    g.drawRect (489, 50, 81, 31); //Draws a yellow (default color set above), around the MANA bar, so it looks like the MANA bar is being emptied, when the megaman power's up
	    g.drawRect (489, 9, 101, 31); //Draws a yellow (default color set above), around the health bar, so it looks like the health bar is being emptied, when the megaman gets hurt

	    g.setColor (Color.BLUE); //just like for the health bar, we're going to draw the mana bar too. But we're not going to change the colors, we'll just keep it blue
	    g.fillRect (490, 51, mana, 30); //so this is going to draw a filled rectangle, colored blue, and this will decrease/increase as to mana, since mana integer is the width of this rectangle
	    //------------End of User-Interface Drawings----------//

	}

    }


    public void callDelayAndRepaint ()  //this is the calling method, which basically calls Thread.Sleep, and repaint
	//We make this into a method, because in our run method, we need to call these 2 things so many times, that is become in-effecient to write it all out. Instead, we can just use a simple method, (with no returns)
    {
	try
	{
	    Thread.sleep (25);
	}
	catch (InterruptedException ex)
	{ // do nothing
	}
	repaint (); //calls repaint after delay, so the screen gets delayed (our eyes can see it) then repaints (new screen), then delays that screen, and this goes on and on...
    }


    public void runSimpleGame ()
    {
	/*
	 * So this is a method, with no returns and this basically this runs the game you see on the screen
	 * This method is where all animations happen for the variables, although this doesn't draw anything
	 * This method is like an internal logic method, which does the game, and reacts to the user's inputs
	 */

	//------Zombies Movements & Other------//
	for (int i = 0 ; i < numZombies ; i++)
	{ //this top for loop is extremly important since it basicall counts all zombies, and this "i" acts like the ID number for each zombie, And this for loop goes through every single zombie

	    //--------Zombie Dying Animation------//
	    if (zombieDied [i] == true)
	    { //so this if is going to check if that specific zombie has died, and this boolean is true, when a zombie is either hit with bullet, or the powerup thingy that megaman does
		zombieDeathCountersCounter [i]++; //just like with ALL OTHER ANIMATIONS, we have a counter's counter and a real counter of the death animation. One to go through the dying animation in the array of stored images, and one to delay that counter(counter's counter)
		if (zombieDeathCountersCounter [i] == 4) //so if the counter's counter ==4, then the real counter increases. If we want to increase the speed of the dying animation, then we decrease the number from 4, to something less (but not 0)
		{
		    zombieDeathCounter [i]++; //you might have noticed that the counter is also in an array, (of the same size too), and that is because we want to have different dying animations happening to dead zombies at different times, (not just at one time).
		    zombieDeathCountersCounter [i] = 0; //the counter' counter becomes 0, for another loop, to go
		}
		if (zombieDeathCounter [i] == 8) //since there are only 7 pictures, the real counter can only go up to 7, and if it's 8, it has to return to 1, again (to get ready for another time when that zombie comes again)
		{
		    zombieDeathCounter [i] = 1;
		    //so since this if is for when the zombie has done it's dying animation, we can eliminate that zombie, by sending him way off to the screen (which will take hours for him to come back to the real screen)

		    zombieSpeed [i] = 1 + random.nextInt (2); //And we also increase the zombie's speed, just because we want the levels to get harder. (and because that zombie will probrably appear in another level)
		    zombiex [i] = -200 - 100000; //we send him way off to the screen to the right, so he deosn't come in this level again

		    zombieDied [i] = false; //and since he's died, (and we want him to respawn), we can make him alive again by making this boolean true
		}
	    }
	    //-----End of Zombie Dying animation ------//


	    zombieCountersCounter [i]++; //so here, just like all the other animations, this is a counter's counter for the zombie walk, and it will delay the counter to delay the aniamtion of the zomie walking
	    if (zombieCountersCounter [i] == 6) //so here, we delay the real counter, by increasing it when this gets to a certain number (which is 6 in our case because it was the perfect time delay)
	    {
		zombieCounter [i]++; //so we increase the real counter of the walking animation
		zombieCountersCounter [i] = 0; //and make the counter's counter 0 again for another go
	    }
	    if (zombieCounter [i] == 6) //since there are only 5 zombie walking pictures, we can only go up to 5, because the array size would be 5. Thus when the real counter is 5, we make it 1 again
	    {
		zombieCounter [i] = 1;
	    }
	    if (xpos + MEGAMAN_WIDTH > zombiex [i] && xpos < zombiex [i] + ZOMBIES_WIDTH && ypos + MEGAMAN_HEIGHT > 160 && inPowerUpMode == true && (!(zombieDied [i] == true))) //so this if statement acts like a collision if, but this collision is special since it is keeping in mind, that the megaman is in powerup mode or not (which is the electricty part)
	    {
		//so what we do for collision is we basically make a hit box, and test if the megaman and the zombie were in that hit box, using heavy math. And since in this if, we're considerin the poweruped megaman, we make the condition to have the powerupmode to be true,
		zombieDied [i] = true; //so if zombie collides with megaman powered up, we want him to show his zombie diying animation, thus we make this true
		if ((level1LetUserMove == false && level2LetUserMove == false && level3LetUserMove == false)) //and if he's not in the transfering level, THEN increase score. This is to eliminate some gliches that were making the scoring of the game unfair
		{
		    score++; //so if the megaman kills the zombie, the score increases
		}
	    }
	    else if (xpos + MEGAMAN_WIDTH > zombiex [i] && xpos < zombiex [i] + ZOMBIES_WIDTH && ypos + MEGAMAN_HEIGHT > 160 && (!(zombieDied [i] == true))) //just like the above if statement, this is also a collision if. The only thing different here is that the megman does not have to be in powerup mode here, so in this the megaman should get hurt
	    {
		healthBarCounter++; //so we increase the health bar counter, which is a counter's counter for the health bar
		bloodCountersCounter++; //and this is for the blood effect's counter's counter, and we also increase that to show the blood animation
		if (bloodCountersCounter == 14) //so this is how we delay the blood animation
		{
		    bloodCounter++; //and increase the real counter
		    bloodCountersCounter = 0;
		}
		if (bloodCounter == 3) //since we only have 2 pictures of the blood animation, we cannot call a third one
		{
		    bloodCounter = 1; //thus, we have to go to the first one again
		}
		if (healthBarCounter == MEGAMAN_STRENGTH) //and just like above, with the megaman colliding with the rock, here if the counter's counter of the health bar, is equal to the strength we set up at the top, then we decrease the health. This is how we can alter how strong the megaman can becom (by increasing the constant)
		{
		    healthBar--; //so we decrease the health counter (the real one)
		    healthBarCounter = 0; //and make the health counter's counter back to 0, for another go
		}
	    }
	    else if (zombieDied [i] == true) //this is needed, because if the zombie has alredy died, and collides with the megaman, we don't want to do anything, thus we need this emptey else if statement
	    {
	    }
	    else if (zombiex [i] > xpos) //the bottom 2 if statements are used to turn the zombies around so they chase megaman. If zombies are right of megaman, we make them go left
	    {
		zombiex [i] -= zombieSpeed [i]; //decrease x-position, by their speed
	    }
	    else if (zombiex [i] < xpos) //Other wise , if the zombies are to the left of megaman, then we make them go right
	    {
		zombiex [i] += zombieSpeed [i]; //increase x-position, by their speed
	    }
	    for (int s = 0 ; s < bulletCounter ; s = s + 3) //now this for loop is combines the top for loop [for each specfic zombie] with the bullets. This one checks each specfic bullet, because it's going to compare that one zombie with all the bullets, and below are going to be if statments to find collisions between zombies and the shot bullets
	    {
		if (shotLeft [i] == true) //so since we alredy created a boolean for the direction of the bullet shot, weither it was either right or left, we can use it here, because we acually set the bullet manually when drawing it. Thus, when doing collisions, (depending on the direction shot), we will need to adjust collisions, using raw numbers
		{ //so this array telling the direction of the bullet is proportional to other bullet arrays, and so if this tells that the direction was left, then below x-point will be decreased, since we want to move it to the left side, NOT RIGHT SIDE (because that would no make sense)
		    if (bulletX [s] - 15 + bulletWidth > zombiex [i] && bulletX [s] - 15 < zombiex [i] + ZOMBIES_WIDTH && !(zombieDied [i] == true) && bulletY [s] >= 160 && bulletX [s] > -100 && bulletX [s] < 700)
			//so the if statment above is a collision for the bullet and the zombiue, and basically just like all other collisions, we make a hit box, and if both objects are in that hit box, we do somehting. Also, we have other stuff like the bullet has to be in the screen (so user's don't become cheap and use bullets randomly)
			{
			    bulletX [s] = 100000; //so once the bullet hits the zombies, we make the bullet disappear, by making it go off screen
			    zombieDied [i] = true; //and we make this true, to start the zombie dying animation
			    if ((level1LetUserMove == false && level2LetUserMove == false && level3LetUserMove == false)) //and if he's not in the transfering level, THEN increase score. This is to eliminate some gliches that were making the scoring of the game unfair
			    {
				score++; //so if the megaman kills the zombie, the score increases
			    }
			}
		}
		else //else if the direction is right of the bullet
		{
		    if (bulletX [s] + 20 + bulletWidth > zombiex [i] && bulletX [s] + 20 < zombiex [i] + ZOMBIES_WIDTH && !(zombieDied [i] == true) && bulletY [s] >= 160 && bulletX [s] + 25 + bulletWidth < 620) //we basically do the same thing, except we have different raw numbers, and we don't have to care about making the bulle stay to the left of the screen (since it's shot to the right)
		    {
			if ((level1LetUserMove == false && level2LetUserMove == false && level3LetUserMove == false)) //and if he's not in the transfering level, THEN increase score. This is to eliminate some gliches that were making the scoring of the game unfair
			{
			    score++; //so if the megaman kills the zombie, the score increases
			}
			bulletX [s] = 100000; //so once the bullet hits the zombies, we make the bullet disappear, by making it go off screen
			zombieDied [i] = true; //and we make this true, to start the zombie dying animation

		    }
		}

	    }

	}
	//-----End of Zombies Movements------//


	//---------Evil Rock Stuff-------//
	if ((level1LetUserMove == false && level2LetUserMove == false && level3LetUserMove == false)) //so here, if we are not in the transfering level, we make the rocks move by decreasing their x-postion
	{
	    for (int i = 0 ; i < NUM_ROCKS ; i++) //so here, this for loop is made so we can go through each rock
	    {
		RockX [i] -= rockSpeed [i]; //so we can decrease their speed, by their speed
	    }
	}
	else //other wise, if we're in the transferring stage
	{
	    for (int i = 0 ; i < NUM_ROCKS ; i++)
	    {
		if (RockX [i] > -100 && RockX [i] < 610) //and if the rocks are on screen
		{
		    RockX [i] -= rockSpeed [i]; //we make their speed decrease, because we don't want more rocks coming from the right when we're in the transfering stage
		}
	    }

	}
	//-----End of Evil Rock Stuff---//


	// Note: So below, we're going to have many if statements for each animation, including jumping, running other etc
	if (runningRight == true && Standstill == false && runningLeft == false && isJumping == false) //so this is for the running right animation, and this can happen when he's not runnning left, and not jumping, or standing still
	{

	    if ((level1LetUserMove == true || level2LetUserMove == true || level3LetUserMove == true)) //so if he's in one of the transfering stages, then, we make the floor go right when he moves
	    {
		floorx -= 2; //we make floor go left
		platformX -= 2; //and all the paltforms go left
		if (xpos + MEGAMAN_WIDTH < 600) //and if he's onscreen, then we make him go right
		{
		    xpos += 2; // we make him go right, by increasing his x-postion variable
		}
	    }
	    else //other wise, we make him JUST HIM, go right
	    {

		if (xpos + MEGAMAN_WIDTH < 600) //if he's in screen that is
		{
		    xpos += 2; //we make him go right
		}

	    }


	    countersCounter1 = countersCounter1 + 1; //counter's counter, for delaying this running animation,
	    if (countersCounter1 == 2) //so if this counter's counter equals to a desired delay, then we increase the real counter
	    {
		counter++; //increase the real counter
		countersCounter1 = 0; //and make this go back to 0 for another go
	    }
	    if (counter == 15) //since we only have 14 pictures of the megaman running, we can only call 14 NOT 15
	    {
		counter = 1; //so once it reaches 15, we make it 1 again
	    }
	    Standstill = false; //and since he's running, he's not standing still, so we make this false
	}

	if (runningRight == false && Standstill == false && runningLeft == true && isJumping == false) //this is VERY similar to above, except it is for running left
	{


	    if (xpos > 0) //so if he's onscreen, then we can make him go left
	    {
		xpos -= 2; // we can make him go left, by decrase his x-postion

	    }

	    countersCounter1 = countersCounter1 + 1; //counter's counter, for delaying this running animation,
	    if (countersCounter1 == 2) //so if this counter's counter equals to a desired delay, then we increase the real counter
	    {
		counter++; //increase the real counter
		countersCounter1 = 0; //and make this go back to 0 for another go
	    }
	    if (counter == 15) //since we only have 14 pictures of the megaman running, we can only call 14 NOT 15
	    {
		counter = 1; //so once it reaches 15, we make it 1 again
	    }
	    Standstill = false; //and since he's running, he's not standing still, so we make this false
	}


	if (runningRight == false && Standstill == false && runningLeft == false && isJumping == true) //this animation is for jumping
	{
	    if (MovingRight == true) //so there can be 2 different jumping animations. One to left, and one to right, so this one is right, and this can be determined by this variable which is true, when the person has recently ran right
	    {
		if ((level1LetUserMove == true || level2LetUserMove == true || level3LetUserMove == true)) //so if we're not in one of the transfering stages then we need to move the floor too
		{
		    floorx -= 2; //thus we move the floor
		    platformX -= 2; //and the platform too
		}

		if (xpos + MEGAMAN_WIDTH < 600) //And if the megaman is in the screen, then we make him go to the right
		{
		    xpos += 2; // we make him go to the right, by increasing his x-postion variable
		}
		countersCounter1 = countersCounter1 + 1; //increase the counter's counter do delay the real jumping counter
		if (countersCounter1 == 2) //so if this counter's counter equals to a desired delay, then we increase the real counter
		{
		    jumpingCounter++; //then we increase the real counter
		    countersCounter1 = 0; //and make this 0, for another go
		}

		if (jumpingCounter >= 7) //so if the real counter reaches 7, then we need to make it 1 again, since we don't have 7 pictures
		{
		    jumpingCounter = 1; //we make it one again
		}

	    }
	    else if (MovingLeft == true) //so other wise, if the guy was recently moving left
	    {


		if (xpos < 0) //so if he's onscreen, then we can make him go left
		{
		    floorx += 2;
		    platformX += 2;
		}
		else
		{
		    xpos -= 2; // we can make him go left, by decrase his x-postion
		}


		countersCounter1 = countersCounter1 + 1; //counter's counter, for delaying this jumping animation,
		if (countersCounter1 == 2) //so if this counter's counter equals to a desired delay, then we increase the real counter
		{
		    jumpingCounter++; //increase the real counter
		    countersCounter1 = 0; //and make this go back to 0 for another go
		}

		if (jumpingCounter >= 7) //since we only have 6 pictures of the megaman jumping, we can only call 6 NOT 7
		{
		    jumpingCounter = 1; //so once it reaches 7, we make it 1 again
		}

	    }
	    ypos = ypos - MJumpY + gravity; //So this is the main part of jumping. This is how we make him jump in a curve, instead of a line. We have second differenes, and basically we increase gravity each time the loop happens. Thus, at one point, the gravity will overcome the intial jump speed (MJUMPY) and start making him go down and that is basically our jump
	    gravity++; //so we increase the gravty variable as described above

	}

	if (userPowerUps == true && isJumping == false) //so this is for if the user preses the power up button, then what happens. Well, here we want to show the guy, starting to powerup animation
	{

	    powerUpCounterCounter++; //so as with all the other animation, we increas the counter's counter to delay animation
	    if (powerUpCounterCounter == 4) //so if this counter's counter equals to a desired delay, then we increase the real counter
	    {
		powerUpCounter++; //increase the real counter
		powerUpCounterCounter = 0; //and make this go back to 0 for another go
	    }

	    if (powerUpCounter == 9) //since we only have 6 pictures of the megaman going into the electricty part of the power up, and then the 3 after that are electricty part, once it reaches maximum pictures, we make it loop in the electricty part
	    {
		inPowerUpMode = true; //so we make this true, which basically means that megaman is in electricty mode, and that he will be un-hurtable <--- needed for that
		powerUpCounter = 6; //so this is how we loop it, because we keep on making this 6, which is the start of the electricty part animation pictures
	    }
	}
	if (inPowerUpMode == true) //so if he's in the electricty mode
	{
	    powerUpModeCounter++; //then we increase the timer for the  electrical part animation, so we can stop once we reach a desired amount
	    mana--; //AND NOW WE DECREASE MANA. since here is where he's unhurtable
	    if (powerUpModeCounter == 40) //so 40, is how much the electrical part animation is going into, and it acts like a stopper timer, where once it reaches this amount [40] then we stop animation
	    {
		powerUpModeCounter = 0; //make timer 0, for another animation next time
		inPowerUpMode = false; //and make all the powerup varibales default
		userPowerUps = false;
		powerUpCounter = 1; //and make this 1 again, because we want to start this animation from start again if we call it again
		MovingRight = true; //and make the guy stand to the right,because it looks better than making him stand to the left
	    }
	}

	manaCounter += MANA_RECHARGE_SPEED; //so we also increase the mana by increasing it's counter's counter by the constant of the recharge speed

	if (manaCounter >= 15 && mana < 80) //and if the mana is less than full mana, And the mana counter shows that the mana should increase
	{
	    mana++; //then we make mana increase
	    manaCounter = 0; //and make the counter 0, for another go
	}


	checkPlatformJumpCollison (); //and here we also call the method of checkPlatformJumpCollison, which checks Platform And Jump Collisons in the method made by me below



	///-----------------Shooting Animation------------------------//
	if (isShooting == true && isJumping == false) //This if statement is mostly for the animation that happens before the bullet is shot. You can see the gun flaring up and that is basically for this
	{
	    Standstill = false;
	    ShotingCountersCounter++; //just like above, and before, this is a counter's counter made because we want to move the counter slowly....(since we cannot have decimals in counters, it can only be added to itself by 1)

	    if (ShotingCountersCounter == 3) //if this counter's counter equals to 3, then the real counter increases
	    {
		ShootingCounter++; //the real counter increases which will be used for the animation
		ShotingCountersCounter = 0; // this counter's counter will be again start from beggining to do the same thing as above
	    }

	    if (ShootingCounter >= 8) //if the counter is more that 8, (8 is the number of sprite pics i have, so we cannot go more than 8)
	    {
		hasJustFinishedShooting = true;
		isShooting = false; //this basically means that this animation has ended, by making this false,
		ShootingCounter = 1; //everything is reset to default for this animation, and this starts with one because the array of pictures start with 1, NOT 0
		ShotingCountersCounter = 0; //this is also reset to default
		Standstill = true; //since after shooting, the guy should be standing thus, this is true
	    }

	    if (ShootingCounter == 4)
	    { //this is strictly for the bullet, and making the bullet come out properly. the bullet should start comeing out when the shooting animation is half way thorugh it finsihing (looks better) and that is when the counter for animation is 4
		bulletX [bulletCounter] = xpos; //so  the array of x-points of the right one (considered by the counter of "bullet counter" is equal to the position of where the guy is..
		bulletY [bulletCounter] = ypos; //same thing as above, except instead of x-point, this is for y-point
		bulletCounter++; //the counter increases when the bullet should be out
	    }

	    if (MovingLeft == true) //here basically defines which side the animation of shooting should be, if guy was moving left, then the image from the array is the left-image array
	    {
		shotLeft [bulletCounter] = true; //this is for the bullet for later, and defines which way the bullet should move. this array of bullet "which side" is considered left (being true) since the guy was shooting and moving left
	    }
	    else
	    {
		shotLeft [bulletCounter] = false; //this is for the bullet for later, and defines which way the bullet should move. this array of bullet "which side" is considered right (being true) since the guy was shooting and moving right
	    }
	}
	//------------------End Of Shooting Animation-------------------------//

	//------------------Bullets, and their movements---------------------//
	for (int i = 0 ; i < bulletCounter ; i = i + 1)
	{ //this for loop is extremly important, because instead of moving one bullet at a time, this loop moves all the bullets that were shot. each time the shooting animation was half done, this counter increased since there were bullets shot, and this counter "bulletCounter" basically tells how many bullets were shot. The for loop counter "i" is added to itself by 3, since the counter moves by 3 as well due to the thread delay.
	    if (shotLeft [i] == true) //so this array telling the direction of the bullet is propotional to other bullet arrays, and so if this tells that the direction was left, then below x-point will be decreased, since we want to move it to the left side, NOT RIGHT SIDE (because that would no make sense)
	    {
		bulletX [i] -= 5; //as described above, x-point is decreased
	    }
	    else
	    { //otherwise, if moving right, it will do the opposite thing as described above
		bulletX [i] += 5; //as described above, x-point is increased, instead of decreasing
	    }

	}
	//----------------End of Bullets and their movements-----------------//

	if (healthBar < 0) //so if the health is less than 0 (so this means he's dead)
	{

	    gameOver = true; // we make it be GAME OVER, which is going to occur in the run method, by exiting the loop of the gameOver while loop
	    //and we make below all default for the next time it loops again
	    gameOverScreen = false;
	    level1LetUserMove = false;
	    level2LetUserMove = false;
	    level3LetUserMove = false;
	    level1 = false;
	    level2 = false;
	    level3 = false;

	}

    }


    public void checkPlatformJumpCollison ()
    {
	/*
	 * Ok, so this method is for checking the collisions for the platform you see that you can jump on to resist the rocks rolling on the floor.
	 * Making such a platform, requires tremendous amount of work, and many if statements.
	 * Therefore, since we need to make our running the simple game method simple, (because we want to keep it clean), we make this method, and it is a void method, meaning no returns
	 * Below, are all the if statements that can happen when a user uses the platform
	 */

	if (ypos > groundY && isJumping == true && StandingPlatform == false) //so this if statement is actually for just checking normal jump collisions, just jump and touch the ground
	{
	    //so if the megaman is jumping jumped, but it's touching the ground, then jumping becomes false, and the guy stands on the ground
	    isJumping = false; //we make the jumping animation false, by making this false
	    ypos = groundY; //we make him stand on the ground, by making hi y-postion same as the ground level
	    gravity = 0; //we make gravity 0, for the next jump
	    Standstill = true; //and his animation will be standing still
	}
	else if (ypos + MEGAMAN_HEIGHT > platformY && isJumping == true && StandingPlatform == true) //this if is when megman's y-position is same as platform's height (platforms y-postion) or WHEN megaman reaches platform level AFTER jumping
	{
	    ypos = platformY; //so we make his y-postion the same as the platforms y-postion on the screen
	    isJumping = false; //and we stop his jumping animation
	    Standstill = true; //And start his standing still animation
	    gravity = 0; //and make gravity 0, for another jump

	}


	if ((xpos + MEGAMAN_WIDTH > platformX) && (xpos < platformX + PLATFORM_WIDTH && runningRight == false && runningLeft == false && isShooting == false && hasJustFinishedShooting == false))
	{ //if guy is in middle of platform, and not running and is ON TOP after jumped. So basically, if the guy is standing on the platform, not doing anything else
	    StandingPlatform = true; //then we make the variable, standing on platform true, and this is going to help us make the guy jump when he's on the platform [done below]

	    if (isJumping == false) //so if he's not jumping on platform
	    { //Makes the guy stand on PLATFORM after Jumping has been finshed.
		ypos = platformY - MEGAMAN_HEIGHT; //by making his y-postion the same as teh platform y-postion (well where he should have been standing )

		Standstill = true; //and since he's standing still, we make standing still animation true
	    }

	}


	if ((StandingPlatform == true && xpos > platformX + PLATFORM_WIDTH) || (StandingPlatform == true && xpos + MEGAMAN_WIDTH - 4 < platformX))
	{ // So this if statment is for the randge on the platform, on how much the megaman can actually run on to. So if he run's out of the domain of the platform he should fall, and that's what this "if" does.
	    //so if  he is more to the right of the platform, or too much to the left of the platform, then this if occurs
	    StandingPlatform = false; //so now, he's not standing on the platform, so this is false
	    if (isJumping == false) //and if he's not jumping this is going to make him fall, because it makes the y-position of the megaman as the ground postion
	    { //if the guy runs off the platform he falls
		ypos = groundY - 1; //so here it does that, and the -1 is for some gliches that were happening (because the guy would not be standing on the ground )
	    }
	}


    }


    public static int setDifficulty (int numberZombies)
    {
	/*
	 * So this method is simple, and it dictates the difficulty of the game on that specific level. And this is done by setting the number of zombies on that level.
	 * However, right now, you might think that this method is useless, but when I'll be expanding this game, i will need this setDifficulty method, because I will be adding more stuff, that can increase difficulty on a level
	 * But right now, (since this game is heavily scripted by me) this method will take a number as a perameter, and return that and that will be used as the number of zombies on that level
	 */
	return numberZombies;
    }


    public void actionPerformed (ActionEvent evt)
    {
	//so here this method is for the buttons
	if (evt.getSource () == playGame) //so if the user clicks the playgame button,
	{
	    titleScreen = false; //then these screens are off
	    instructionScreen = false;

	    remove (playGame); //and we remove the buttons and go to the title screen
	    remove (showInstructions);
	    //and we go to our play game section (done in the run method)
	}

	if (evt.getSource () == showInstructions)
	{
	    titleScreen = false; //title screen becomes false
	    instructionScreen = true;  //and we go to our instruction screen

	    remove (showInstructions); // and we remove the instruction button but we keep the play game buttons because that's what the user will need if he want's to play the game
	}
    }
}
//-----------------------------------------------End of Class & Program-------------------------------------------------//
