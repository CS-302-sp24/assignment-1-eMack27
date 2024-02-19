import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class Philosopher extends Thread {
  private Chopstick left, right;
  private Random random;
  private int thinkCount = 0;
  private int numCycles;
  private int thinkingTime;         // Max thinking time
  private int currentThinkingTime;  // Current thinking time
  private int eatingTime;           // Max eating time
  private int currentEatingTime;    // Current eating time
  private int handChoice;
  private int id;

  public Philosopher(Chopstick left, Chopstick right, int nc, int tt, int et, int rl, int id) {
    this.left = left; this.right = right;
    this.numCycles = nc;
    this.thinkingTime = tt;
    this.eatingTime = et;
    this.handChoice = rl;
    this.id = id;
    random = new Random();
  }

  public void run() {
    try 
    {
      FileWriter trace = new FileWriter("Trace.txt", true);
      if (handChoice == 0)  // all philosphers are right-handed
      {
        if(numCycles == 0){--thinkCount;} // if numCycles = 0, run infinitely
        while(thinkCount < numCycles) 
        {
          ++thinkCount;
          if (thinkCount % 10 == 0)
          {
            trace.write("Philosopher " + this.id + " has thought " + thinkCount + " times" + "\r\n");
          }
          currentThinkingTime = random.nextInt(thinkingTime);
          Thread.sleep(random.nextInt(currentThinkingTime));   // Think for a while
          trace.write("Philosopher " + this.id + " thinks for " + currentThinkingTime + " ms" + "\r\n");
          synchronized(this.right) 
          {                    // Grab right chopstick 
            trace.write("Philosopher " + this.id + " has the right chopstick" + "\r\n");
            synchronized(this.left) 
            {                   // Grab left chopstick 
              trace.write("Philosopher " + this.id + " wants the left chopstick" + "\r\n");
              currentEatingTime = random.nextInt(eatingTime);
              Thread.sleep(currentEatingTime); // Eat for a while
              trace.write("Philosopher " + this.id + " eats for " + currentEatingTime + " ms" + "\r\n");
              trace.write("Philosopher " + this.id + " releases the left chopstick" + "\r\n");
            }
            trace.write("Philosopher " + this.id + " releases the right chopstick" + "\r\n");
          }
          if(thinkCount == 0){--thinkCount;}  // if numCycles = 0, run infinitely
        }
      }
      else if (handChoice == 1) // all even-numbered philosphers are right-handed, all odd-numbered are left-handed
      {
        if(numCycles == 0){--thinkCount;} // if numCycles = 0, run infinitely
        while(thinkCount < numCycles) 
        {
          ++thinkCount;
          if (thinkCount % 10 == 0)
          {
            trace.write("Philosopher " + this.id + " has thought " + thinkCount + " times" + "\r\n");
          }
          currentThinkingTime = random.nextInt(thinkingTime);
          Thread.sleep(random.nextInt(currentThinkingTime));   // Think for a while
          trace.write("Philosopher " + this.id + " thinks for " + currentThinkingTime + " ms" + "\r\n");
          if (this.id % 2 == 0) 
          {
            synchronized(right)   // Grab right chopstick  
            {                         
              trace.write("Philosopher " + this.id + " has the right chopstick" + "\r\n");
              trace.write("Philosopher " + this.id + " wants the left chopstick" + "\r\n");
              synchronized(left)  // Grab left chopstick 
              {                        
                trace.write("Philosopher " + this.id + " has the left chopstick" + "\r\n");
                currentEatingTime = random.nextInt(eatingTime);
                Thread.sleep(currentEatingTime); // Eat for a while
                trace.write("Philosopher " + this.id + " eats for " + currentEatingTime + "ms" + "\r\n");
                trace.write("Philosopher " + this.id + " releases the left chopstick" + "\r\n");
              }
              trace.write("Philosopher " + this.id + " releases the right chopstick" + "\r\n");
            }
          }
          else
          {
            synchronized(left)    // Grab left chopstick 
            {                          
              trace.write("Philosopher " + this.id + " has the left chopstick" + "\r\n");
              trace.write("Philosopher " + this.id + " wants the right chopstick" + "\r\n");
              synchronized(right) // Grab right chopstick 
              {                       
                trace.write("Philosopher " + this.id + " has the right chopstick" + "\r\n");
                currentEatingTime = random.nextInt(eatingTime);
                Thread.sleep(currentEatingTime); // Eat for a while
                trace.write("Philosopher " + this.id + " eats for " + currentEatingTime + " ms" + "\r\n");
                trace.write("Philosopher " + this.id + " releases the right chopstick" + "\r\n");
              }
              trace.write("Philosopher " + this.id + " releases the left chopstick" + "\r\n");
            }
          }
          if(thinkCount == 0){--thinkCount;}  // if numCycles = 0, run infinitely
        }
      }
      else
      {
        trace.close();
        throw new InterruptedException("Error: rl must be either 1 (all philosphers are right-handed) or 0 (all even-numbered philosphers are right-handed, all odd-numbered are left-handed)");
      }
    } catch(InterruptedException | IOException e) {e.printStackTrace();}
  }
}
