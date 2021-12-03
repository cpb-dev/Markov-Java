import java.lang.Math;
import java.util.*;
import java.io.PrintWriter;
class Markov{

//##################################

public static  double getTransProb(int i,int j,int k){

double sr = Math.sqrt(k); //sqrt of numstates

double Pprob = 0; //Prob of moving

if(i % sr == 1){ //If the original state is on the left side

    //Hardcode for staying in the same position
    if(j == i){
        if(k == 9){
            if(i == 1 || i == 7){
                Pprob = 0.5;
            }
            if(i == 4){
                Pprob = 0.25;
            }
        }
        if(k == 4){
            if(i == 1 || i == 3){
                Pprob = 0.5;
            }
        }
        
    }

    if(i - 1 == j){ //Can't move left
        Pprob = 0;
    }
    if(i + 1 == j){ //Moving right
        Pprob = 0.25;
    }
    if(i + sr == j || i - sr == j){ 
        if(j > k || i < 1){ //Out of bounds
            Pprob = 0;
        } else { //Moving up or down
            Pprob = 0.25;
        }
    }

}

if(i % sr == 2){ //Middle

    if(i == j){
        if(i == 5){
            Pprob = 0;
        }
        if(i == 2 || i == 8){
            Pprob = 0.25;
        }
    }

    if(i - 1 == j || i + 1 == j){ //Moving to the side
        Pprob = 0.25;
    }
    if(i + sr == j || i - sr == j){
        if(j > k || i < 1){
            Pprob = 0; //Out of Bounds
        } else { 
            Pprob = 0.25; //Moving up or down
        }
    }

}

if(i % sr == 0){ //Right side

    if(j == i){
        if(k == 9){
            if(i == 3 || i == 9){
                Pprob = 0.5;
            }
            if(i == 6){
                Pprob = 0.25;
            }
        }
        if(k == 4){
            if(i == 2 || i == 4){
                Pprob = 0.5;
            }
        }
    }

    if(i + 1 == j){ //Can't move right
        Pprob = 0;
    }
    if(i - 1 == j){ //Moving left
        Pprob = 0.25;
    }
    if(i + sr == j || i - sr == j){ 
        if(j > k || i < 1){ //Out of bounds
            Pprob = 0;
        } else { //Moving up or down
            Pprob = 0.25;
        }
    }

}

if(j >= k+1 || j <= 0){ //Out of bounds
    Pprob = 0;
}

double a = i * 3.14;
double b = j * 3.14;

double Pacc = 0.0;

if(i > j){
    Pacc = a / b;
}
else{
    Pacc = b / a;
}

double p = Math.min(1, Pacc);
double Ptrans = p * Pprob; //Needs to equal a quater

return Ptrans;

}

//##################################

public static double getSejProb(int s1,int s2,int numStates,double TS){

    double sr = Math.sqrt(numStates);
    
    double moveProb = 0;
    double current = s1;
    int correct = 0;
    int n = 0;

    while(n < TS){ //Loop until TS is achieved
        double ran = Math.random() * (1 - 0);
        double ranresult = ran;

        //choosing random move for new state
        if(ranresult > 0 && ranresult <= 0.25){
            current = s1 - 1;
        }
        if(ranresult > 0.25 && ranresult <= 0.5){
            current = s1 + 1;
        }
        if(ranresult > 0.5 && ranresult <=0.75){
            current = s1 + 3;
        }
        if(ranresult > 0.75 && ranresult <= 1){
            current = s1 - 3;
        }
        if(current < 1 || current > numStates){
            current = s1;
        }

        if(current % sr == 1){ //Staying still
            if(current == s1){
                if(current == 1 || current == 7){
                    moveProb = 0.5;
                    s1 = (int) current;
                }
                if(current == 4){
                    moveProb = 0.25;
                    s1 = (int) current;
                }
            }

            if(current - 1 == s1){ //left is out of bounds
                moveProb = 0;
            }
            if(current + 1 == s1){
                moveProb = 0.25;
                s1 = (int) current;
            }

            //Moving up or down (always 0, except 4 which returns 0.25)
            if(current + sr == s1 || current - sr == s1){
                if(s2 > numStates || s1 < 1){
                    moveProb = 0;
                } else {
                    moveProb = 0.25;
                    s1 = (int) current;
                }
            }
        }

        if(current % sr == 0){
            if(current == s1){ //Staying the same
                if(current == 3 || current == 9){
                    moveProb = 0.5;
                    s1 = (int) current;
                }
                if(current == 6){
                    moveProb = 0.25;
                    s1 = (int) current;
                }
            }
            //Moving left
            if(current - 1 == s1){
                moveProb = 0.25;
                s1 = (int) current;
            }
            //Right is out of bounds
            if(current + 1 == s1){
                moveProb = 0;
            }

            //Moving up or down, only 6 has a value
            if(current + sr == s1 || current - sr == s1){
                if(s2 > numStates || s1 < 1){
                    moveProb = 0;
                } else {
                    moveProb = 0.25;
                    s1 = (int) current;
                }
            }
        }

        if(current % sr == 2){
            //If the same number as s1
            if(current == s1){
                if(current == 5){
                    moveProb = 0;
                    s1 = (int) current;
                }
                if(current == 2 || current == 8){
                    moveProb = 0.25;
                    s1 = (int) current;
                }
            }
            //Moving left or right yields the same result in the middle column
            if(current - 1 == s1 || current + 1 == s1){
                moveProb = 0.25;
                s1 = (int) current;
            }
            //Moving up and down
            if(current + sr == s1 || current - sr == s1){
                if(current > numStates || s1 < 1){
                    moveProb = 0;
                    s1 = (int) current;
                } else {
                    moveProb = 0.25;
                    s1 = (int) current;
                }
            }
        }
        n = n + 1; //add one to number of tries
        s1 = (int) current;

        if(s1 == s2){
            correct = correct + 1;
        }
    }
    
    double ptrans = correct / TS;

    if(s2 < 1 || s2 > numStates){
        return 0;
    }

    return ptrans; //return probability of transition

}

//##################################

public static double getBiasTransProb(int s1, int s2,double[] ssprob)
{
    //ssprob.length determines numbers in array/grid
    double numStates = ssprob.length;
    double sr = Math.sqrt(numStates);
    double Pprob = 0;

    if(s1 % sr == 1){ //left side
        //If s1 and s2 are the same number
        if(s2 == s1){ 
            Pprob = ssprob[s2 - 1]; 
            //needs to be -1 to ensure it lines up with array no.
        }
        //Can't move left
        if(s1 - 1 == s2){ 
            Pprob = 0;
        }
        //Moving right
        if(s1 + 1 == s2){
            Pprob = ssprob[s2 - 1];
        }
        //Up/Down and Out of Bounds
        if(s1 + sr == s2 || s1 - sr == s2){
            if(s2 > numStates || s1 < 1){
                Pprob = 0;
            } else {
                Pprob = ssprob[s2 - 1];
            }
        }
    }

    if(s1 % sr == 2){ //middle
        //Staying in the same state
        if(s2 == s1){
            Pprob = ssprob[s2 - 1];
        }
        //Moving to the side
        if(s1 - 1 == s2 || s1 + 1 == s2){
            Pprob = ssprob[s2 - 1];
        }
        //Moving Up/Down/Out of Bounds
        if(s1 + sr == s2 || s1 - sr == s2){
            if(s2 > sr || s1 < 1){
                Pprob = 0;
            } else {
                Pprob = ssprob[s2 - 1];
            }
        }
    }

    if(s1 % sr == 0){ //right side
        //Staying in the same state
        if(s2 == s1){
            Pprob = ssprob[s2 - 1];
        }
        //can't move right
        if(s1 + 1 == s2){
            Pprob = 0;
        }
        //Moving left
        if(s1 - 1 == s2){
            Pprob = ssprob[s2 - 1];
        }
        //Moving Up/Down/Out of Bounds
        if(s1 + sr == s2 || s1 - sr == s2){
            if(s2 > numStates || s1 < numStates){
                Pprob = 0;
            } else {
                Pprob = ssprob[s2 - 1];
            }
        }
    }
    //Out of Bounds
    if(s2 >= numStates + 1 || s2 <= 0){
        Pprob = 0;
    }

    double prob = 1/numStates;
    double pacc = Math.min(1, prob/prob);
    double transProb = pacc * Pprob;

    return transProb;

}

//##################################

public static double  getContTransProb(int s1,int s2,double[] rates){

    double numrates = rates.length;
    double Pprob = 0;

    //Array for each state
    double[] state1 = {rates[0], rates[1]};
    double[] state2 = {rates[2], rates[3]};
    double[] state3 = {rates[4], rates[5]};

    if(s1 == 1){
        //can just have s2= 2 or 3
        if(s2 == 2){
            Pprob = state1[0];
        }
        if(s2 == 3){
            Pprob = state1[1];
        } 
    }
    if(s1 == 2){
        if(s2 == 1){
            Pprob = state2[0];
        }
        if(s2 == 3){
            Pprob = state2[1];
        } 
    }
    if(s1 == 3){
        if(s2 == 1){
            Pprob = state3[0];
        }
        if(s2 == 2){
            Pprob = state3[1];
        } 
    }
    if(s1 == s2){ //Can't self loop
        Pprob = 0;
    }
    if(s2 <= 0 || s2 > numrates){ //If the states are too high
        Pprob = 0;
    }

    double prob = 1/numrates;
    double pacc = Math.min(1, prob/prob);
    double transProb = pacc * Pprob;

return transProb;

}

//##################################

public static double getContSejProb(int s1,int s2,double[] rates,double TSC){

    double numStates = rates.length;
    double sr = Math.sqrt(numStates);

    //Creating array for all the states
    double state1[] = {rates[0], rates[1]};
    double state2[] = {rates[2], rates[3]};
    double state3[] = {rates[4], rates[5]};

    double moveprob = 0;
    double current = s1;
    double correct = 0;

    int n = 0;
    double sum = 0;
    double T = 0;
    
    for(int i = 0; i < numStates; i++){
        sum = sum + rates[i];
    }

    while(T < TSC){
        double ran = Math.random();

        if(current == 1){
            if(ran > 0 && ran <= 0.5){
                moveprob = state1[0];
                current = 2;
            }
            if(ran > 0.5 && ran <= 1){
                moveprob = state1[1];
                current = 3;
            }
        }
        if(s1 == 2){
            if(ran > 0 && ran <= 0.5){
                moveprob = state2[0];
                current = 1;
            }
            if(ran > 0.5 && ran <= 1){
                moveprob = state2[1];
                current = 3;
            }
        }
        if(s1 == 3){
            if(ran > 0 && ran <= 0.5){
                moveprob = state3[0];
                current = 1;
            }
            if(ran > 0.5 && ran <= 1){
                moveprob = state3[1];
                current = 2;
            }
        }

        //Formula to work out wait time
        double prob = 1/numStates;
        double rand = Math.random();
        double P = sum * prob;
        double log = Math.log(rand);
        double wait = - 1/P * log;
        T = T + wait;

        s1 = (int) current;
        if(s1 == s2){
            correct = correct + 1;
        }
    }

    double ptrans = correct / T;
    //If the states are too high
    if(s2 <= 0 || s2 > numStates){ 
        moveprob = 0;
    }

return ptrans;
}

}//end class
