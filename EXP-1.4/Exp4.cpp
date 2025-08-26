//23BCS10018
#include <iostream>
using namespace std;

// Node for Doubly Linked List
class DoublyNode {
public:
    int data;
    DoublyNode* prev;
    DoublyNode* next;
    DoublyNode(int data) {
        this->data = data;
        prev = next = nullptr;
    }
};

// Node for Circular Linked List
class CircularNode {
public:
    int data;
    CircularNode* next;
    CircularNode(int data) {
        this->data = data;
        next = nullptr;
    }
};

class LinkedListDemo {
    // ====== DOUBLY LINKED LIST ======
    DoublyNode* headDLL;

    // ====== CIRCULAR LINKED LIST ======
    CircularNode* headCLL;

public:
    LinkedListDemo() {
        headDLL = nullptr;
        headCLL = nullptr;
    }

    // ====== DOUBLY LINKED LIST METHODS ======
    void insertAtBeginningDLL(int data) {
        DoublyNode* newNode = new DoublyNode(data);
        if (headDLL != nullptr) {
            newNode->next = headDLL;
            headDLL->prev = newNode;
        }
        headDLL = newNode;
    }

    void insertAtEndDLL(int data) {
        DoublyNode* newNode = new DoublyNode(data);
        if (headDLL == nullptr) {
            headDLL = newNode;
            return;
        }
        DoublyNode* temp = headDLL;
        while (temp->next != nullptr) temp = temp->next;
        temp->next = newNode;
        newNode->prev = temp;
    }

    void deleteAtBeginningDLL() {
        if (headDLL == nullptr) return;
        headDLL = headDLL->next;
        if (headDLL != nullptr) headDLL->prev = nullptr;
    }

    void deleteAtEndDLL() {
        if (headDLL == nullptr) return;
        if (headDLL->next == nullptr) {
            headDLL = nullptr;
            return;
        }
        DoublyNode* temp = headDLL;
        while (temp->next != nullptr) temp = temp->next;
        temp->prev->next = nullptr;
    }

    void displayDLL() {
        DoublyNode* temp = headDLL;
        while (temp != nullptr) {
            cout << temp->data << " ";
            temp = temp->next;
        }
        cout << endl;
    }

    // ====== CIRCULAR LINKED LIST METHODS ======
    void insertAtBeginningCLL(int data) {
        CircularNode* newNode = new CircularNode(data);
        if (headCLL == nullptr) {
            newNode->next = newNode;
            headCLL = newNode;
            return;
        }
        CircularNode* temp = headCLL;
        while (temp->next != headCLL) temp = temp->next;
        newNode->next = headCLL;
        temp->next = newNode;
        headCLL = newNode;
    }

    void insertAtEndCLL(int data) {
        CircularNode* newNode = new CircularNode(data);
        if (headCLL == nullptr) {
            newNode->next = newNode;
            headCLL = newNode;
            return;
        }
        CircularNode* temp = headCLL;
        while (temp->next != headCLL) temp = temp->next;
        temp->next = newNode;
        newNode->next = headCLL;
    }

    void deleteAtBeginningCLL() {
        if (headCLL == nullptr) return;
        if (headCLL->next == headCLL) {
            headCLL = nullptr;
            return;
        }
        CircularNode* temp = headCLL;
        while (temp->next != headCLL) temp = temp->next;
        headCLL = headCLL->next;
        temp->next = headCLL;
    }

    void deleteAtEndCLL() {
        if (headCLL == nullptr) return;
        if (headCLL->next == headCLL) {
            headCLL = nullptr;
            return;
        }
        CircularNode* temp = headCLL;
        while (temp->next->next != headCLL) temp = temp->next;
        temp->next = headCLL;
    }

    void displayCLL() {
        if (headCLL == nullptr) return;
        CircularNode* temp = headCLL;
        do {
            cout << temp->data << " ";
            temp = temp->next;
        } while (temp != headCLL);
        cout << endl;
    }
};

// ========== MAIN FUNCTION ==========
int main() {
    LinkedListDemo list;

    cout << "Doubly Linked List Operations:" << endl;
    list.insertAtBeginningDLL(10);
    list.insertAtEndDLL(20);
    list.insertAtBeginningDLL(5);
    list.displayDLL();
    list.deleteAtEndDLL();
    list.displayDLL();

    cout << "\nCircular Linked List Operations:" << endl;
    list.insertAtBeginningCLL(10);
    list.insertAtEndCLL(20);
    list.insertAtBeginningCLL(5);
    list.displayCLL();
    list.deleteAtEndCLL();
    list.displayCLL();

    return 0;
}
