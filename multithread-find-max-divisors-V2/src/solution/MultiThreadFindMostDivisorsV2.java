package solution;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiThreadFindMostDivisorsV2 {
    static LinkedBlockingQueue<SectionMaxes> resultQueue = new LinkedBlockingQueue<>();
    static ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
    static boolean running;

    public static void main(String[] args){
        final int UPPER_LIMIT = 100000;
        final int TASK_NUM = 200;
        int numWithMostDivisors = -1;
        int mostDivisors = -1;


        makeTaskQueue(UPPER_LIMIT, TASK_NUM);

        //make thread pool
        Scanner scanner = new Scanner(System.in);
        int numOfThreads=-1;
        while(numOfThreads<1||numOfThreads>25){
            System.out.println("How many threads would you like to add to the pool? Please choose a number between 1 and 25.");
            numOfThreads = scanner.nextInt();
            scanner.nextLine();
        }

        running = true;
        Thread[] threadPool = new MaxesTaskThread[numOfThreads];
        long startTime = System.currentTimeMillis();
        for(int i = 0; i<numOfThreads; i++){
            threadPool[i] = new MaxesTaskThread();
            threadPool[i].start();
        }


        for(int i = 0; i<TASK_NUM; i++){
            try{
                SectionMaxes maxes = resultQueue.take();
                if(maxes.divisorMax>mostDivisors){
                    mostDivisors = maxes.divisorMax;
                    numWithMostDivisors = maxes.numMax;
                }
            }catch (InterruptedException e){
            }
        }
        long endTime = System.currentTimeMillis();
        long computationTime = endTime-startTime;
        running = false;


        System.out.println("The number between 1 and " + UPPER_LIMIT + " with the most divisors is " + numWithMostDivisors + ".");
        System.out.println("It has " + mostDivisors + " divisors.");
        System.out.println("Computation time for " + numOfThreads + " threads was " + computationTime + " milliseconds.");
    }

    public static void makeTaskQueue(int UPPER_LIMIT, int TASK_NUM){
        int increment = UPPER_LIMIT/TASK_NUM;
        for(int i = 0; i<TASK_NUM; i++){
            taskQueue.add(new MaxesTask((i*increment)+1, (i+1)*increment));
        }
    }

    static class MaxesTaskThread extends Thread{
        public void run() {
            while (running){
                Runnable task = taskQueue.poll();
                if(task!=null){
                    task.run();
                }
            }
        }
    }


    private static class MaxesTask implements Runnable{
        int maxNum;
        int minNum;
        MaxesTask(int minNum, int maxNum){
            this.maxNum = maxNum;
            this.minNum = minNum;
        }
        public void run() {
            int numWithMax = -1;
            int maxDivisors = -1;
            for (int i=minNum; i<=maxNum; i++){
                int divisorCounter = 0;
                for (int j = 1; j<=i; j++){
                    if(i%j==0){
                        divisorCounter++;
                    }
                }
                if(divisorCounter>maxDivisors){
                    maxDivisors = divisorCounter;
                    numWithMax = i;
                }
            }
            resultQueue.add(new SectionMaxes(numWithMax, maxDivisors));
        }
    }
    private static class SectionMaxes{
        int numMax;
        int divisorMax;
        SectionMaxes(int numMax, int divisorMax){
            this.numMax=numMax;
            this.divisorMax=divisorMax;
        }
    }
}
