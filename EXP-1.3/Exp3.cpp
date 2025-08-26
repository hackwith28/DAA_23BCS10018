#include <iostream>
#include <unordered_map>
using namespace std;

class FrequencyCounter {
public:
    static void countFrequency(int arr[], int n) {
        unordered_map<int, int> freqMap;

        // Count frequency
        for (int i = 0; i < n; i++) {
            freqMap[arr[i]]++;
        }

        // Print frequencies
        for (auto& entry : freqMap) {
            cout << entry.first << "   " << entry.second << endl;
        }
    }
};

int main() {
    int arr1[] = {10, 20, 20, 10, 10, 20, 5, 20};
    int arr2[] = {10, 20, 20};

    cout << "Example 1:" << endl;
    FrequencyCounter::countFrequency(arr1, 8);

    cout << "\nExample 2:" << endl;
    FrequencyCounter::countFrequency(arr2, 3);

    return 0;
}
