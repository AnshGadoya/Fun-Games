package com.example.fungames.soundMemory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.fungames.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class smg_CustomView extends View {
    // variables
    private static final String TAG = "smg_CustomView";
    private Paint red, green, blue, yellow;
    private Rect square_red,square_green,square_blue,square_yellow;
    private smg_gameScreen mainActivity = (smg_gameScreen) this.getContext();
    private int alphaValue = 50;
    private boolean gameStart;
    private int gameMode;
    private int playerNumber;
    private int level;
    private int speedInMillis = 1000;
    private boolean isTimerRunning;
    private Random random;
    private static final long START_TIME_IN_MLLSEC = 10000;
    private long timeLeft = START_TIME_IN_MLLSEC;
    private CountDownTimer countDownTimer;
    private ArrayList<Integer> arrListSequence = new ArrayList<Integer>();
    private int currentCard;
    private int current_sequence_index;
    private int player_sequence_index;


    public smg_CustomView(Context context) {
        super(context);
        init();
    }
    public smg_CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public smg_CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(){
        // create the paint objects for rendering our rectangles
        red = new Paint(Paint.ANTI_ALIAS_FLAG);
        green = new Paint(Paint.ANTI_ALIAS_FLAG);
        blue = new Paint(Paint.ANTI_ALIAS_FLAG);
        yellow = new Paint(Paint.ANTI_ALIAS_FLAG);

        red.setColor(getResources().getColor(R.color.color_Red_secondary));
        green.setColor(getResources().getColor(R.color.color_Green_secondary));
        blue.setColor(getResources().getColor(R.color.color_Blue_secondary));
        yellow.setColor(getResources().getColor(R.color.color_Yellow_secondary));

    }

    @Override
   /* public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        square_red = new Rect(0, 0, width/2, height/2);
        square_green = new Rect(width/2, 0, width, height/2);
        square_blue = new Rect(0, height/2, width/2, height);
        square_yellow = new Rect(width/2, height/2, width, height);

        canvas.drawRect(square_red,red);
        canvas.drawRect(square_green,green);
        canvas.drawRect(square_blue,blue);
        canvas.drawRect(square_yellow,yellow);

    }*/
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height); // Make it square
        float centerX = size / 2f;
        float centerY = size / 2f;
        float strokeWidth = 37f; // Border width
        float radius = size / 2f - strokeWidth / 2;

        // ---- Stroke Paint (Black Border) ----
        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeWidth);

        // ---- Fill Paints (Separate for each color) ----
//        red.setColor(Color.parseColor("#F44336"));   // Red
//        green.setColor(Color.parseColor("#8BC34A")); // Green
//        blue.setColor(Color.parseColor("#00BCD4"));  // Blue
//        yellow.setColor(Color.parseColor("#FFC107"));// Yellow

        red.setStyle(Paint.Style.FILL);
        green.setStyle(Paint.Style.FILL);
        blue.setStyle(Paint.Style.FILL);
        yellow.setStyle(Paint.Style.FILL);

        // ---- Score Paint ----
        Paint scorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(size / 14); // Dynamic sizing
        scorePaint.setTextAlign(Paint.Align.CENTER);
        scorePaint.setFakeBoldText(true);

        // ---- Center Circle Paint ----
        Paint centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setColor(Color.parseColor("#23272a")); // Center circle color

        // ---- Outer circle boundary for arcs ----
        RectF oval = new RectF(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
        );

        // ----------------- Draw Arcs -----------------------

        // --- Top-Right (green) ---
        Path pathBlue = new Path();
        pathBlue.moveTo(centerX, centerY);
        pathBlue.lineTo(centerX, centerY - radius);
        pathBlue.arcTo(oval, -90, 90);
        pathBlue.close();
        canvas.drawPath(pathBlue, green);
        canvas.drawPath(pathBlue, strokePaint);

        // --- Bottom-Right (yellow) ---
        Path pathGreen = new Path();
        pathGreen.moveTo(centerX, centerY);
        pathGreen.lineTo(centerX + radius, centerY);
        pathGreen.arcTo(oval, 0, 90);
        pathGreen.close();
        canvas.drawPath(pathGreen, yellow);
        canvas.drawPath(pathGreen, strokePaint);

        // --- Bottom-Left (blue) ---
        Path pathRed = new Path();
        pathRed.moveTo(centerX, centerY);
        pathRed.lineTo(centerX, centerY + radius);
        pathRed.arcTo(oval, 90, 90);
        pathRed.close();
        canvas.drawPath(pathRed, blue);
        canvas.drawPath(pathRed, strokePaint);

        // --- Top-Left (red) ---
        Path pathYellow = new Path();
        pathYellow.moveTo(centerX, centerY);
        pathYellow.lineTo(centerX - radius, centerY);
        pathYellow.arcTo(oval, 180, 90);
        pathYellow.close();
        canvas.drawPath(pathYellow, red);
        canvas.drawPath(pathYellow, strokePaint);

        // ----------------- Center Circle --------------------
        float centerRadius = radius / 2.5f; // Smaller inner circle
        canvas.drawCircle(centerX, centerY, centerRadius, centerPaint);
        canvas.drawCircle(centerX, centerY, centerRadius, strokePaint); // Circle border

        // ----------------- Score Text --------------------
        String scoreText = "SCORE:\n" + level;
        Paint.FontMetrics fontMetrics = scorePaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;

        canvas.drawText("SCORE:", centerX, centerY - textHeight / 4, scorePaint);
        canvas.drawText(String.valueOf(level), centerX, centerY + textHeight / 2, scorePaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        int touchedCardCode = 0;
        //check if there is a game taking place
        if(gameStart){
            int action = event.getActionMasked();
            // get coordinates
            int x = (int) event.getX();
            int y = (int) event.getY();

            switch(action) {

                case (MotionEvent.ACTION_DOWN) :
                    Log.d(TAG,"Action was DOWN");
                    if(x >= 0 && x <= 400 && y >=0 && y <= 400){
                        Log.d(TAG, "Red Square: " + x + " " + y);
                        playSound("red");
                        red.setAlpha(alphaValue);
                        touchedCardCode = 1;

                    }else if(x > 400 && x <= 800 && y >=0 && y <= 400){
                        Log.d(TAG, "Green Square: " + x + " " + y);
                        playSound("green");
                        green.setAlpha(alphaValue);
                        touchedCardCode = 2;
                    }else if(x > 0 && x <= 400 && y >400 && y <= 800){
                        Log.d(TAG, "Blue Square: " + x + " " + y);
                        playSound("blue");
                        blue.setAlpha(alphaValue);
                        touchedCardCode = 3;
                    }else if(x > 400 && x <= 800 && y >400 && y <= 800){
                        Log.d(TAG, "Yellow Square: " + x + " " + y);
                        playSound("yellow");
                        yellow.setAlpha(alphaValue);
                        touchedCardCode = 4;
                    }

                    invalidate();

                    //Players Game Logic
                        //one player mode = 1

                        if(gameMode == 1){
                            Log.d(TAG, "1 player mode: " + gameMode);
                            currentCard = arrListSequence.get(player_sequence_index);

                            if(player_sequence_index < arrListSequence.size()){
                                Log.d(TAG, "player_sequence_index < arrListSequence.size()" +player_sequence_index + " "+ arrListSequence.size());
                                //check if the press card number matches with the number in the sequence
                                if(touchedCardCode == currentCard){
                                    Log.d(TAG, "touch card matches: ");
                                    player_sequence_index++;
                                    if(player_sequence_index == arrListSequence.size()){
                                        Log.d(TAG, "player_sequence_index == arrListSequence.size()" +player_sequence_index + " "+ arrListSequence.size());

                                        pausedTimer();
                                        //stop it for 750 millisecond  so user can see simon first flashed card
                                        SystemClock.sleep(750);
                                        level++;
                                        //increment speed depending on the current level
                                        if(level == 3){
                                            speedInMillis = 650;
                                        }else if(level == 6){
                                            speedInMillis = 500;
                                        }else if(level == 9){
                                            speedInMillis = 350;
                                        }else if(level == 12){
                                            speedInMillis = 250;
                                        }else if(level == 15){
                                            speedInMillis = 150;
                                        }
                                        update_Tv_Level(level);
                                        resetTimer();
                                        simonTurn();
                                        player_sequence_index = 0;
                                    }
                                }else{
                                    //if the press card number does NOT match with the number in the sequence
                                    playerLose(1);
                                }

                            }

                        //two player mode = 2
                        }
                        else if(gameMode == 2){
                            Log.d(TAG, "2 player mode: " + gameMode);

                            if(playerNumber == 1){
                                Log.d(TAG, "Player 1 touched card --> " + touchedCardCode);
                                Log.d(TAG, "player_sequence_index < arrListSequence.size() "+player_sequence_index+" "+arrListSequence.size());
                                //if it is the first touch of the game
                                if(arrListSequence.isEmpty()){
                                    pausedTimer();
                                    resetTimer();
                                    Log.d(TAG, "arrListSequence.isEmpty()"+arrListSequence.size());
                                    //add pressed card number to the sequence
                                    arrListSequence.add(touchedCardCode);
                                    //change to player 2
                                    playerNumber = 2;
                                    update_Tv_Player("PLAYER " + playerNumber);
                                    mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_Yellow_secondary));
                                    //start timer
                                    startTimer();


                                }else if(player_sequence_index < arrListSequence.size() && touchedCardCode == arrListSequence.get(player_sequence_index)){
                                    Log.d(TAG, "plySeq, arrSize, touchedCard, current card: "+ player_sequence_index + arrListSequence.size() + touchedCardCode + arrListSequence.get(player_sequence_index));
                                    //increment by 1 our sequence so we can check the next card
                                    player_sequence_index++;

                                }else if(player_sequence_index == arrListSequence.size()){
                                    pausedTimer();
                                    //increment level and display it
                                    level ++;
                                    update_Tv_Level(level);
                                    //add number to the array
                                    arrListSequence.add(touchedCardCode);
                                    resetTimer();
                                    //change to player 1
                                    playerNumber = 2;
                                    //reset player_sequence_index
                                    player_sequence_index = 0;
                                    //change to player 2
                                    update_Tv_Player("PLAYER " + playerNumber);
                                    mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_Yellow_secondary));
                                    //start timer
                                    startTimer();

                                }else {
                                    playerLose(1);

                                }

                            }else if(playerNumber ==2){
                                Log.d(TAG, "Player 2 touched card --> " + touchedCardCode);
                                Log.d(TAG, "player_sequence_index < arrListSequence.size() "+player_sequence_index+" "+arrListSequence.size());

                                if(player_sequence_index < arrListSequence.size() && touchedCardCode == arrListSequence.get(player_sequence_index)){
                                    Log.d(TAG, " plySeq < arrSize, touchedCard, current card: "+ player_sequence_index + arrListSequence.size() + touchedCardCode + arrListSequence.get(player_sequence_index));
                                    //increment by 1 our sequence so we can check the next card
                                    player_sequence_index++;

                                    //if all touches were successful
                                }else if(player_sequence_index == arrListSequence.size()){
                                    pausedTimer();
                                    resetTimer();
                                    //increment level and display it
                                    level ++;
                                    update_Tv_Level(level);
                                    //add number to the array
                                    arrListSequence.add(touchedCardCode);
                                    //change to player 1
                                    playerNumber = 1;
                                    //reset player_sequence_index
                                    player_sequence_index = 0;
                                    //change to player 2
                                    update_Tv_Player("PLAYER " + playerNumber);
                                    mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_white));
                                    startTimer();

                                }else {
                                    playerLose(2);

                                }
                            }

                        }


                    return true;

                case (MotionEvent.ACTION_UP) :
                    Log.d(TAG,"Action was UP");
                    restoreCardsState();
                    return true;

            }
        }else{
            return false;
        }

        return super.onTouchEvent(event);

    }

    private void playerLose(int playerNum){
        Log.d(TAG, "WRONG CARD: ");
        pausedTimer();
        //play a lose sound
        playSound("lose");
        //update the player name
        update_Tv_Player("PLAYER "+playerNum+" LOSE");
        Toast.makeText(getContext(), "PLAYER "+ playerNum + " LOSE", Toast.LENGTH_LONG).show();
        mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_Red_secondary));
        //set button back to be active
        if(gameMode == 1){
            mainActivity.getMode1().setEnabled(true);
            mainActivity.getMode1().setText("Start Again");
        }else if(gameMode == 2){
            mainActivity.getMode2().setEnabled(true);
            mainActivity.getMode2().setText("2 PLAYER MODE");
        }
        //freeze the main cards panel
        gameStart = false;
        //reset timer
        resetTimer();
    }

    public void setGameStart(boolean gameStart) {
        Log.d(TAG, "setGameStart: " + gameStart);
        this.gameStart = gameStart;
    }

    public void restoreCardsState(){
        red.setAlpha(255);
        green.setAlpha(255);
        blue.setAlpha(255);
        yellow.setAlpha(255);
        invalidate();
    }


    public int genRandomNum(){
        random = new Random();
        return random.nextInt(4) + 1;
    }



    private void update_Tv_Player(String name) {
        mainActivity.getCurrent_player().setText(name);

    }


    private void update_Tv_Level(int level) {
//        mainActivity.getCurrLevel().setText("LEVEL: "+ level);
    }


    public void flashCard(final int cardNumber){
        Log.d(TAG, "flashCard: ");
        final Handler flash_Card_handler = new Handler();

        if(cardNumber == 1){
            playSound("red");
            red.setAlpha(alphaValue);
        }else if(cardNumber == 2){
            playSound("green");
            green.setAlpha(alphaValue);
        }else if(cardNumber == 3){
            playSound("blue");
            blue.setAlpha(alphaValue);
        }else if(cardNumber == 4){
            playSound("yellow");
            yellow.setAlpha(alphaValue);
        }
        invalidate();

        final Runnable runnable =new Runnable() {
            @Override
            public void run() {
                if(cardNumber == 1){
                    red.setAlpha(255);
                }else if(cardNumber == 2){
                    green.setAlpha(255);
                }else if(cardNumber == 3){
                    blue.setAlpha(255);
                }else if(cardNumber == 4){
                    yellow.setAlpha(255);
                }
                invalidate();
            }
        };
        flash_Card_handler.postDelayed(runnable,speedInMillis);

    }

    public void simonTurn(){
        Log.d(TAG, "simonTurn: ");
        update_Tv_Player("SIMON");
        mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_Yellow_secondary));

        Log.d(TAG, "array size: " +  arrListSequence.size());
        //when simon is playing user cannot touch the cards
        gameStart = false;
        update_Tv_Level(level);
        //check if the user reach the winning level = 10
        if(level == 10){
            Log.d(TAG, "touch card matches and player wins");
            player_sequence_index = 0;
            gameStart = false;
            update_Tv_Player("PLAYER WINS");
            mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_Blue_secondary));
            Toast.makeText(getContext(), "PLAYER WINS", Toast.LENGTH_LONG).show();
            pausedTimer();
            mainActivity.getMode1().setVisibility(View.VISIBLE);
            //leave method
            return;

        }

        final Handler handler = new Handler();  // create a handler to link to our runnable
        handler.post(new Runnable() {           // create the runnable
            @Override
            public void run() {
                Log.i(TAG, "current_sequence_index:" + current_sequence_index);

                red.setAlpha(255);
                green.setAlpha(255);
                blue.setAlpha(255);
                yellow.setAlpha(255);
                invalidate();

                if(current_sequence_index < arrListSequence.size()) {  // if there are more elements in the sequence to process
                    int currentPanel = arrListSequence.get(current_sequence_index);
                    if (currentPanel == 1) {                // if the current panel of the sequence is 1
                        Log.d(TAG, "set alpha red card:  50");
                        red.setAlpha(alphaValue);               // set red be transparent
                        playSound("red");
                    } else if (currentPanel == 2)  {          // if the current panel of the sequence is 2
                        Log.d(TAG, "set alpha green card:  50");
                        green.setAlpha(alphaValue);
                        playSound("green");
                    }else if(currentPanel==3) {           // if the current panel of the sequence is 2
                        Log.d(TAG, "set alpha blue card:  50");
                        blue.setAlpha(alphaValue);
                        playSound("blue");
                    }else if(currentPanel==4) {          // if the current panel of the sequence is 2
                        Log.d(TAG, "set alpha yellow card:  50");
                        yellow.setAlpha(alphaValue);
                        playSound("yellow");
                    }

                    current_sequence_index++;
                    handler.postDelayed(this, speedInMillis); // execute this runnable again
                }else if(current_sequence_index == arrListSequence.size()){
                    //once simon has completed to play the previous numbers, It does add nother to the list and flashes the card
                    int myRandNumb = genRandomNum();
                    Log.d(TAG, "myRandNumb: " + myRandNumb);
                    arrListSequence.add(myRandNumb);
                    playerNumber = 1;
                    update_Tv_Player("PLAYER " + playerNumber);
                    mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_white));
                    //allow user to touch the panels
                    gameStart = true;
                    flashCard(myRandNumb);

                    startTimer();

                }
            }
        });
        current_sequence_index=0; // reset the index back to 0 so the sequence can be played again


    }


    public void playSound(String sound){
        //function that play sound according to sound String name
        int audioRes = 0;
        switch (sound) {
            case "red":
                audioRes = R.raw.fa;
                break;
            case "green":
                audioRes = R.raw.mi;
                break;
            case "blue":
                audioRes = R.raw.si;
                break;
            case "yellow":
                audioRes = R.raw.sol;
                break;
            case "lose":
                audioRes = R.raw.lose;
                break;
            case "start":
                audioRes = R.raw.game_start;
                break;
        }
        MediaPlayer p = MediaPlayer.create(getContext(), audioRes);
        p.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        p.start();

    }

    public void resetValues(){
        //if the timer is running then stop it and reset it
        if(isTimerRunning){
            pausedTimer();
            resetTimer();
        }
        if(gameMode == 1){
            mainActivity.getMode1().setEnabled(true);
            mainActivity.getMode1().setText("1 PLAYER MODE");
        }else if(gameMode == 2){
            mainActivity.getMode2().setEnabled(true);
            mainActivity.getMode2().setText("2 PLAYER MODE");
        }
        restoreCardsState();
        level = 0;
        mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_white));
        speedInMillis = 1000;
        player_sequence_index = 0;
        current_sequence_index = 0;
        arrListSequence.clear();
        update_Tv_Level(0);

    }

    public void setGameMode(int gameModeCode) {
        if(gameModeCode == 1){
            Log.d(TAG, "Player VS Simon ");
            //clear variables, array of movements and text views
        }/*else if(gameModeCode == 2){
            Log.d(TAG, "Player_1 VS Player_2 ");
        }*/
        this.gameMode = gameModeCode;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //set the min desire size
        int size = 800;
        //int variables to store width and height
        int width;
        int height;

        //int variables get width and height
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than
            width = Math.min(size, widthSize);
        } else {
            //whatever I want
            width = size;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than
            height = Math.min(size, heightSize);
        } else {
            //Be whatever size I want
            height = size;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }
    public void startTimer(){
        Log.i("Timer","StartTimer()");
        mainActivity.getTimer().setText("10");

        final Handler myTimeHandler = new Handler();

        final Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                mainActivity.getTimer().setText("10");
                Log.d(TAG, "timer running : ");
                countDownTimer = new CountDownTimer(timeLeft,1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeLeft = millisUntilFinished;
                        update_Tv_Timer();
                    }

                    @Override
                    public void onFinish() {
                        if(playerNumber == 1){
                            mainActivity.getCurrent_player().setText("PLAYER 1 LOSE");
                        }
                        else if(playerNumber == 2){
                            mainActivity.getCurrent_player().setText("PLAYER 2 LOSE");
                        }
                        mainActivity.getCurrent_player().setTextColor(getResources().getColor(R.color.color_Red_secondary));
                        Toast.makeText(getContext(), "PLAYER "+ playerNumber + " LOSE", Toast.LENGTH_LONG).show();
                        //disable panel
                        gameStart = false;
                        //timer will stop running
                        isTimerRunning = false;
                        //make the button Active
                        if(gameMode == 1){
                            mainActivity.getMode1().setEnabled(true);
                            mainActivity.getMode1().setText("1 PLAYER MODE");
                        }else if(gameMode == 2){
                            mainActivity.getMode2().setEnabled(true);
                            mainActivity.getMode2().setText("2 PLAYER MODE");
                        }
                        playSound("lose");
                        resetTimer();
                    }
                }.start();
                isTimerRunning = true;

            }
        };
        //waits for a 500 milliseconds to execute the timer
        myTimeHandler.postDelayed(timerRunnable,500);
    }
    public void resetTimer(){
        Log.i(TAG,"ResetTimer()");
        //reset time left to 10 seconds
        timeLeft = START_TIME_IN_MLLSEC;
        mainActivity.getTimer().setText("10");
        isTimerRunning = false;
        update_Tv_Timer();

    }
    public void pausedTimer(){
        Log.i(TAG,"PauseTimer()");
        isTimerRunning = false;
        countDownTimer.cancel();

    }
    public void update_Tv_Timer(){
        Log.i(TAG,"update_Tv_Timer()");
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d", seconds);
        mainActivity.getTimer().setText(timeLeftFormatted);

    }
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
