public class DiningPhilosophers {

  public static void main(String[] args) throws InterruptedException {
    int np = Integer.parseInt(args[0]); // number of philosophers/chopsticks
    int nc = Integer.parseInt(args[1]); // number of cycles that each philosopher will go through
    int tt = Integer.parseInt(args[2]); // maximum thinking time duration (ms)
    int et = Integer.parseInt(args[3]); // maximum eating time duration (ms)
    int rl = Integer.parseInt(args[4]); // If 0, all philosophers are right-handed. If 1, even-numbered philosophers are right-handed and odd-numbered philosophers are left-handed
    
    Philosopher[] philosophers = new Philosopher[np];
    Chopstick[] chopsticks = new Chopstick[np];
    
    for (int i = 0; i < np; ++i)
    {
      chopsticks[i] = new Chopstick(i);
    }

    for (int i = 0; i < np; ++i) 
    {
      philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % np], nc, tt, et, rl, i);
      philosophers[i].start();
    }

    for (int i = 0; i < np; ++i)
    {
      philosophers[i].join();
    }
  }
}
