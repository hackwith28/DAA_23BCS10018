#include <bits/stdc++.h>
using namespace std;

template <typename T>
class MyStack {
private:
    int capacity;
    int top;
    T* stack;

public:
    MyStack(int size) {
        capacity = size;
        top = -1;
        stack = new T[capacity];
    }

    ~MyStack() { // destructor to free memory
        delete[] stack;
    }

    void push(T element) {
        if (isFull()) {
            cout << "Stack Overflow! Cannot push " << element << endl;
        } else {
            stack[++top] = element;
            cout << element << " pushed into stack." << endl;
        }
    }

    T pop() {
        if (isEmpty()) {
            cout << "Stack Underflow! Cannot pop." << endl;
            return T(); // return default value of T
        } else {
            return stack[top--];
        }
    }

    T peek() {
        if (isEmpty()) {
            cout << "Stack is empty. No top element." << endl;
            return T();
        } else {
            return stack[top];
        }
    }

    bool isEmpty() {
        return top == -1;
    }

    bool isFull() {
        return top == capacity - 1;
    }
};

// Demo
int main() {
    MyStack<int> intStack(5);

    intStack.push(10);
    intStack.push(20);
    intStack.push(30);

    cout << "Top element: " << intStack.peek() << endl;

    cout << "Popped: " << intStack.pop() << endl;
    cout << "Popped: " << intStack.pop() << endl;

    cout << "Is stack empty? " << (intStack.isEmpty() ? "Yes" : "No") << endl;
    cout << "Is stack full? " << (intStack.isFull() ? "Yes" : "No") << endl;

    return 0;
}
