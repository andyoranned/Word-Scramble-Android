/* Name:        Anne D.
 * Date:        December 1st, 2021
 * Class:       COSC 1437- 13459
 * Assignment:  Project III
 * 				Create an Android word game app w/ responsive GUI consisting of:
 *                              TextView that shows a String randomly selected from array & scrambled
 *                              EditText to accept user's guesses
 *                              2 Buttons to Start/Finish game
 *                              Chronometer to time how long it takes to guess correctly
 *                              Toasts to announce Win/Lose
 */
package com.example.wordscramblegame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView scrambleField;         //text box to display scrambled words
    EditText inputField;            //input text field
    Button startingButton;          //button to start game
    Button stoppingButton;          //button to stop game
    Chronometer timeKeeper;         //stop watch
    String answer = "";             //to hold unscrambled answer value
    String[] stringArray = {"Honey Ham", "Reindeer Games", "Christmas Tree", "Sugar Cookies",
                            "New Year's Eve", "Cranberry Relish", "Frosty the Snowman"};             //array for scramble

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //linking UI elements to variables
        scrambleField = (TextView)findViewById(R.id.textView);
        inputField = (EditText)findViewById(R.id.editText);
        startingButton= (Button)findViewById(R.id.startButton);
        stoppingButton=(Button)findViewById(R.id.stopButton);
        timeKeeper=(Chronometer)findViewById(R.id.Chronometer);

        //START button event handling
        startingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //variables
                int randString = (int)(Math.random()*stringArray.length);                           //variable used to choose a random string from stringArray
                answer = stringArray[randString];                                                   //assigns the random String from array to answer variable before scrambling
                int tempInt1;                                                                       //int variables to represent position in String to swap
                int tempInt2;
                String tempString = "";                                                             //temporary String to hold String from array and perform scramble operations
                String tempS = answer;

                for (int i=0; i<=tempS.length(); i++)                                               //for loop to scramble characters of the string stringArray[randString]
                {
                    /* do-while loop ensures tempInt1 is not larger than or equal to
                     * tempInt2 so that there is not a runtime error during scramble
                     * operations.
                     */
                    do {
                        tempInt1 = (int)(Math.random() * tempS.length());                            //assign random values to represent position in String to be swapped
                        tempInt2 = (int)(Math.random()* tempS.length());
                    }while(tempInt1>=tempInt2);

                    //SCRAMBLE OPERATIONS
                    tempString = tempS.substring(0, tempInt1);                                      //front of string to letter in position designated by tempInt1 added to tempString
                    tempString = tempString + tempS.substring(tempInt2, tempInt2+1);                //letter designated by tempInt2 replaces letter originally in tempInt1 position
                    tempString = tempString + tempS.substring(tempInt1+1, tempInt2);                //Substring between two swapped letters is add to tempString
                    tempString = tempString + tempS.substring(tempInt1, tempInt1+1);                //letter designated by tempInt1 replaces letter originally in tempInt2 position
                    tempString = tempString + tempS.substring(tempInt2+1, answer.length());         //remaining substring beyond tempInt2's position is added to tempString
                    tempS = tempString;
                }//end for loop for scrambling
                scrambleField.setText(tempString);                                                      //display formatted string in the scrambleField TextField

                //Chronometer events
                timeKeeper.setBase(SystemClock.elapsedRealtime());                                      //zero out Chronometer
                timeKeeper.start();                                                                     //start Chronometer
            }//end onClick
        });//end startingButton

        //STOP button event handling
        stoppingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CharSequence textCS = (CharSequence) inputField.getText();                              //get user input from inputField EditText box
                String textString = textCS.toString();                                                  //change input into a String to compare to answer

                /* Compares the user input with correct answer. If correct, timer stops and
                 * displays a congratulations. If incorrect, displays "Try Again."
                 */
                if (textString.equals(answer)){
                    timeKeeper.stop();                                                                  //Chronometer stops
                    //Initiate and display Toast banner for winners
                    Toast winnerToast = Toast.makeText(getApplicationContext(), "WINNER!", Toast.LENGTH_LONG);
                    winnerToast.show();
                }
                else {
                    //Initiate and display Toast banner for incorrect answer
                    Toast loserToast = Toast.makeText(getApplicationContext(), "Try again.", Toast.LENGTH_SHORT);
                    loserToast.show();
                }//end if-statement
            }//end onClick method
        });//end stoppingButton
    }//end of onCreate method
}//end MainActivity class