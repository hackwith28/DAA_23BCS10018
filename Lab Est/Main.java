// Node of Linked List
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// Queue implementation using Linked List
class QueueLL {
    private Node front, rear;
    private int size;

    QueueLL() {
        front = rear = null;
        size = 0;
    }

    // Insert element at the end
    public void enqueue(int data) {
        Node newNode = new Node(data);

        if (rear == null) {          // empty queue
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }

        size++;
        System.out.println(data + " enqueued");
    }

    // Remove element from the front
    public int dequeue() {
        if (front == null) {
            System.out.println("Queue is empty");
            return -1;
        }

        int val = front.data;
        front = front.next;

        if (front == null) {    // queue became empty
            rear = null;
        }

        size--;
        return val;
    }

    // Peek (front element)
    public int peek() {
        if (front == null) {
            System.out.println("Queue is empty");
            return -1;
        }
        return front.data;
    }

    // Check empty
    public boolean isEmpty() {
        return front == null;
    }

    // Size
    public int size() {
        return size;
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        QueueLL q = new QueueLL();

        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);

        System.out.println("Front element: " + q.peek());
        System.out.println("Dequeued: " + q.dequeue());
        System.out.println("Dequeued: " + q.dequeue());

        System.out.println("Queue empty? " + q.isEmpty());
        System.out.println("Size: " + q.size());
    }
}
