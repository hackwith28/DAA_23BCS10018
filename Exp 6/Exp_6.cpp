#include <bits/stdc++.h>
using namespace std;

bool recurse(vector<int> &arr, int index, int target) {
    if(target == 0) return true;
    if(index == 0) return arr[index] == target;

    int pick = false;
    if(arr[index] <= target) {
        pick = recurse(arr, index-1, target-arr[index]);
    }
    int notPick = recurse(arr, index-1, target);

    return pick | notPick;
}

bool memoize(vector<int> &arr, int index, int target, vector<vector<int>> &dp) {
    if(target == 0) return true;
    if(index == 0) return arr[index] == target;

    if(dp[index][target] != -1) return dp[index][target];

    int pick = false;
    if(arr[index] <= target) {
        pick = memoize(arr, index-1, target-arr[index], dp);
    }
    int notPick = memoize(arr, index-1, target, dp);

    return dp[index][target] = pick | notPick;
}

bool tabulate(vector<int> &arr, int target) {
    int n = arr.size();
    vector<vector<bool>> dp(n, vector<bool>(target+1, false));
    for(int i = 0; i < n; i++) dp[i][0] = true;
    if(arr[0] <= target) dp[0][arr[0]] = true;

    for(int i = 1; i < n; i++) {
        for(int j = 1; j <= target; j++) {
            int pick = false;
            if(arr[i] <= j) {
                pick = dp[i-1][j-arr[i]];
            }
            int notPick = dp[i-1][j];
            dp[i][j] = pick | notPick;
        }
    }

    return dp[n-1][target];
}

bool subsetSum(vector<int> &arr, int target) {
    return tabulate(arr, target);
}

int main() {
    vector<int> arr = { 3, 2, 7, 1 };
    int target = 6;
    if(subsetSum(arr, target)) {
        cout<<"True"<<'\n';
    } else {
        cout<<"False"<<'\n';
    }
    return 0;
}
