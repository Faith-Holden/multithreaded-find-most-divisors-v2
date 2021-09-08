# multithreaded-find-most-divisors-v2
My solution for Chapter 12 Exercise 3 of “Introduction to Programming Using Java”.

Problem Description:
In the previous exercise, you divided up a large task into a small number of large pieces
and created a thread to execute each task. Because of the nature of the problem, this
meant that some threads had much more work to do than others—it is much easier to
find the number of divisors of a small number than it is of a big number. As discussed in
Subsection 12.3.1, a better approach is to break up the problem into a fairly large number
of smaller problems. Subsection 12.3.2 shows how to use a thread pool to execute the
tasks: Each thread in the pool runs in a loop in which it repeatedly takes a task from a
queue and carries out that task. Implement a thread pool strategy for solving the same
maximum-number-of-divisors problem as in the previous exercise.
To make things even more interesting, you should try a new technique for combining
the results from all the tasks: Use two queues in your program. Use a queue of tasks, as
usual, to hold the tasks that will be executed by the thread pool (Subsection 12.3.2). But
also use a queue of results produced by the threads. When a task completes, the result
from that task should be placed into the result queue. The main program can read results
from the second queue as they become available, and combine all the results to get the
final answer. The result queue will have to be a blocking queue (Subsection 12.3.3), since
the main program will have to wait for results to become available. Note that the main
program knows the exact number of results that it expects to read from the queue, so it
can do so in a for loop; when the for loop completes, the main program knows that all
the tasks have been executed.
