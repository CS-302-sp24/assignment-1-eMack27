import java.util.Random;

class Philosopher extends Thread {
  private Chopstick left, right;
  private Random random;
  private int thinkCount;
  private int numCycles;
  private int thinkingTime;
  private int eatingTime;
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
    try {
      if (handChoice == 0)  // all philosphers are right-handed
      {
        while(thinkCount < numCycles) 
        {
          ++thinkCount;
          if (thinkCount % 10 == 0)
            System.out.println("Philosopher " + this.id + " has thought " + thinkCount + " times");
          Thread.sleep(random.nextInt(thinkingTime));   // Think for a while
          synchronized(this.right) {                    // Grab right chopstick 
            System.out.println("Philosopher " + this.id + "has the right chopstick");
            synchronized(this.left) {                   // Grab left chopstick 
              System.out.println("Philosopher " + this.id + "wants the left chopstick");
              Thread.sleep(random.nextInt(eatingTime)); // Eat for a while
            }
          }
        }
      }
      else if (handChoice == 1) // all even-numbered philosphers are right-handed, all odd-numbered are left-handed
      {
        while(thinkCount < numCycles) 
        {
          ++thinkCount;
          if (thinkCount % 10 == 0)
            System.out.println("Philosopher " + this.id + " has thought " + thinkCount + " times");
          Thread.sleep(random.nextInt(thinkingTime));     // Think for a while
          if (this.id % 2 == 0) 
          {
            synchronized(right) 
            {                         // Grab right chopstick 
              System.out.println("Philosopher " + this.id + " has the right chopstick");
              synchronized(left) 
              {                        // Grab left chopstick 
                System.out.println("Philosopher " + this.id + " wants the left chopstick");
                Thread.sleep(random.nextInt(eatingTime)); // Eat for a while
              }
            }
          }
          else
          {
            synchronized(left) 
            {                          // Grab left chopstick 
              System.out.println("Philosopher " + this.id + " has the left chopstick");
              synchronized(right) 
              {                       // Grab right chopstick 
                System.out.println("Philosopher " + this.id + " wants the right chopstick");
                Thread.sleep(random.nextInt(eatingTime)); // Eat for a while
              }
            }
          }
        }
      }
      else
      {
        throw new InterruptedException("Error: rl must be either 1 (all philosphers are right-handed) or 0 (all even-numbered philosphers are right-handed, all odd-numbered are left-handed)");
      }
    } catch(InterruptedException e) {}
  }
}
