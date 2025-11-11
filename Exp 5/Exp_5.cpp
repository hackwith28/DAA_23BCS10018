#include <bits/stdc++.h>
using namespace std;

int partition(vector<int> &arr, int low, int high) {
    int pivot = arr[low];
    int i = low;
    int j = high;

    while(i < j) {
        while(arr[i] <= pivot && i <= high-1) {
            i++;
        }

        while(arr[j] > pivot && j >= low+1) j--;

        if(i < j) swap(arr[i], arr[j]);
    }
    swap(arr[j], arr[low]);
    return j;
}

void qS(vector<int> &arr, int n, int low, int high) {
    if(low >= high) return;
    int part = partition(arr, low, high);
    qS(arr, n, low, part-1);
    qS(arr, n, part+1, high);
}

void quickSort(vector<int> &arr) {
    int n = arr.size();
    int low = 0, high = n-1;
    qS(arr, n, low, high);
}

int main() {
    vector<int> arr = { 10, 7, 8, 9, 1, 5 };
    cout<<"Array before Soring: ";
    for(int i = 0; i < arr.size(); i++) {
        cout<<arr[i]<<' ';
    }
    cout<<'\n';
    quickSort(arr);
    cout<<"Array after Sorting: ";
    for(int i = 0; i < arr.size(); i++) {
        cout<<arr[i]<<' ';
    }
    cout<<'\n';
    return 0;
}
